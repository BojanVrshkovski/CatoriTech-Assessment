package com.catoritech.service.impl;

import com.catoritech.entity.Contact;
import com.catoritech.entity.dto.ContactDto;
import com.catoritech.entity.enums.UserRole;
import com.catoritech.entity.requests.ContactRequest;
import com.catoritech.exceptions.BusinessCanNotAccessContactException;
import com.catoritech.exceptions.ContactAlreadyExistException;
import com.catoritech.exceptions.ContactInvalidIdException;
import com.catoritech.exceptions.IndividualUserCanNotAccessException;
import com.catoritech.exceptions.UserNotFoundException;
import com.catoritech.repository.ContactRepository;
import com.catoritech.util.ContactFactory;
import com.catoritech.util.UserFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Optional;
import static com.catoritech.util.ContactConstants.ID;
import static com.catoritech.util.UserConstants.USERNAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.catoritech.entity.User;
import com.catoritech.repository.UserRepository;
import org.springframework.security.core.Authentication;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class ContactServiceImplTest {
	public ContactRequest contactRequest;
	public Contact contact;
	public ContactDto contactDto;
	public User user;

	@InjectMocks
	private ContactServiceImpl contactService;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private ContactRepository contactRepository;
	@Mock
	private UserRepository userRepository;

	@Before
	public void setUp() {
		contactRequest = ContactFactory.getContactRequest();
		contact = ContactFactory.getDefaultContact();
		contactDto = ContactFactory.getDefaultContactDto();
		user = UserFactory.getDefaultUser();
	}

	@Test
	public void testCreateContact_Success() {
		when(modelMapper.map(contactRequest, Contact.class)).thenReturn(contact);
		when(contactRepository.save(contact)).thenReturn(contact);

		Long createdContactId = contactService.createContact(contactRequest);

		assertNotNull(createdContactId);
		assertEquals(contact.getId(), createdContactId);
	}

	@Test
	public void testCreateContact_DuplicatePhone() {
		when(modelMapper.map(contactRequest, Contact.class)).thenReturn(contact);
		when(contactRepository.save(contact)).thenThrow(DataIntegrityViolationException.class);

		assertThrows(ContactAlreadyExistException.class, () -> contactService.createContact(contactRequest));
	}

	@Test
	public void testReadContactById_success() {
		when(contactRepository.findById(ID)).thenReturn(Optional.of(contact));
		when(modelMapper.map(any(),any())).thenReturn(contactDto);
		ContactDto result = contactService.readContactById(ID);

		verify(contactRepository, times(1)).findById(ID);
		Assertions.assertNotNull(result);
	}

	@Test
	public void testReadContactById_nonExistingId_invalidIdException_throws_success() {
		when(contactRepository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ContactInvalidIdException.class, () -> contactService.readContactById(ID));
		verify(contactRepository, times(1)).findById(ID);
	}

	@Test
	public void testReadContactInformationSuccess() {
		UserDetails userDetails = Mockito.mock(UserDetails.class);
		when(userDetails.getUsername()).thenReturn(USERNAME);

		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);

		Authentication authentication = Mockito.mock(Authentication.class);
		when(authentication.getPrincipal()).thenReturn(userDetails);
		when(securityContext.getAuthentication()).thenReturn(authentication);

		when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

		when(contactRepository.findByUserId(1L)).thenReturn(Optional.of(contact));

		when(modelMapper.map(contact, ContactDto.class)).thenReturn(contactDto);

		ContactDto result = contactService.readContactInformation();
		assertEquals(contactDto, result);
	}

	@Test
	public void testReadContactInformationUserNotFound() {
		UserDetails userDetails = Mockito.mock(UserDetails.class);
		when(userDetails.getUsername()).thenReturn(USERNAME);

		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);

		Authentication authentication = Mockito.mock(Authentication.class);
		when(authentication.getPrincipal()).thenReturn(userDetails);
		when(securityContext.getAuthentication()).thenReturn(authentication);

		when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> contactService.readContactInformation());
	}

	@Test
	public void testReadContactInformationContactNotFound() {
		UserDetails userDetails = Mockito.mock(UserDetails.class);
		when(userDetails.getUsername()).thenReturn(USERNAME);

		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);

		Authentication authentication = Mockito.mock(Authentication.class);
		when(authentication.getPrincipal()).thenReturn(userDetails);
		when(securityContext.getAuthentication()).thenReturn(authentication);

		when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

		when(contactRepository.findByUserId(1L)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> contactService.readContactInformation());
	}

	@Test(expected = BusinessCanNotAccessContactException.class)
	public void testReadContactByIdNew_BusinessUserCanNotAccessContact() {
		User businessUser = user;

		UserDetails userDetails = Mockito.mock(UserDetails.class);
		when(userDetails.getUsername()).thenReturn(USERNAME);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);

		when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(businessUser));

		when(contactRepository.findById(ID)).thenReturn(Optional.of(contact));

		contactService.readContactByIdNew(ID);
	}

	@Test(expected = IndividualUserCanNotAccessException.class)
	public void testReadContactByIdNew_IndividualUserCanNotAccessOtherContact() {
		User individualUser = user;
		individualUser.setUserRole(UserRole.INDIVIDUAL);

		UserDetails userDetails = Mockito.mock(UserDetails.class);
		when(userDetails.getUsername()).thenReturn(USERNAME);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);

		when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(individualUser));

		when(contactRepository.findById(ID)).thenReturn(Optional.of(contact));

		contactService.readContactByIdNew(ID);
	}

	@Test(expected = ContactInvalidIdException.class)
	public void testReadContactByIdNew_InvalidContactId() {
		UserDetails userDetails = Mockito.mock(UserDetails.class);
		when(userDetails.getUsername()).thenReturn(USERNAME);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);

		when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(new User()));

		when(contactRepository.findById(ID)).thenReturn(Optional.empty());

		contactService.readContactByIdNew(ID);
	}

	@Test(expected = UserNotFoundException.class)
	public void testReadContactByIdNew_UserNotFound() {
		UserDetails userDetails = Mockito.mock(UserDetails.class);
		when(userDetails.getUsername()).thenReturn(USERNAME);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);

		when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

		contactService.readContactByIdNew(ID);
	}
}
