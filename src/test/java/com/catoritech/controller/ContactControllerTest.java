package com.catoritech.controller;

import com.catoritech.entity.Contact;
import com.catoritech.entity.dto.ContactDto;
import com.catoritech.entity.requests.ContactRequest;
import com.catoritech.exceptions.BusinessCanNotAccessContactException;
import com.catoritech.exceptions.ContactAlreadyExistException;
import com.catoritech.exceptions.ContactInvalidIdException;
import com.catoritech.exceptions.EmptyContactListException;
import com.catoritech.exceptions.IndividualUserCanNotAccessException;
import com.catoritech.exceptions.UserNotFoundException;
import com.catoritech.service.impl.ContactServiceImpl;
import com.catoritech.util.ContactFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.catoritech.util.ContactConstants.EXCEPTION_MESSAGE;
import static com.catoritech.util.ContactConstants.ID;
import static com.catoritech.util.UserConstants.BUSINESS_CAN_NOT_ACCESS_CONTACT_EXCEPTION;
import static com.catoritech.util.UserConstants.INDIVIDUAL_USER_CAN_ACCESS_OWN_INFO_EXCEPTION;
import static com.catoritech.util.UserConstants.USERNAME;
import static com.catoritech.util.UserConstants.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class ContactControllerTest {
	private MockMvc mockMvc;
	public ContactRequest contactRequest;
	public Contact contact;
	public ContactDto contactDto;
	@Mock
	private ContactServiceImpl contactService;

	@InjectMocks
	private ContactController contactController;

	@Before
	public void setUp() {
		contactRequest = ContactFactory.getContactRequest();
		contact = ContactFactory.getDefaultContact();
		contactDto = ContactFactory.getDefaultContactDto();
		mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();
	}


	@Test
	public void testCreateContact_success() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(contactRequest);
		when(contactService.createContact(any())).thenReturn(contact.getId());
		mockMvc.perform(post("/createContact")
			                .contentType(MediaType.APPLICATION_JSON)
			                .content(json))
		       .andExpect(status().isCreated())
		       .andExpect(header().string("Location", "/contact/1"));
	}

	@Test(expected = ServletException.class)
	public void testCreateContact_throws() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(contactRequest);
		when(contactService.createContact(any()))
			.thenThrow(new ContactAlreadyExistException(EXCEPTION_MESSAGE));

		mockMvc.perform(post("/createContact")
			                .contentType(MediaType.APPLICATION_JSON)
			                .content(json))
		       .andExpect(status().isBadRequest())
		       .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is(EXCEPTION_MESSAGE)));
	}

	@Test
	public void testReadContactById_existingId_success() {
		when(contactService.readContactById(ID)).thenReturn(contactDto);

		ResponseEntity<ContactDto> response = contactController.readContactById(ID);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(contactDto, response.getBody());
	}

	@Test
	public void testReadContactOwnInformation_Success() {
		when(contactService.readContactInformation()).thenReturn(contactDto);

		ResponseEntity<ContactDto> response = contactController.readContactOwnInformation();

		assertEquals(HttpStatus.OK, response.getStatusCode());

		assertEquals(contactDto, response.getBody());
	}

	@Test
	public void testReadContactByIdNew_BusinessUserCanAccessContact() {
		UserDetails userDetails = Mockito.mock(UserDetails.class);
		when(userDetails.getUsername()).thenReturn(USERNAME);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);

		when(contactService.readContactByIdNew(ID)).thenReturn(contactDto);

		ResponseEntity<ContactDto> response = contactController.readContactByIdNew(ID);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(contactDto, response.getBody());
	}

	@Test(expected = BusinessCanNotAccessContactException.class)
	public void testReadContactByIdNew_BusinessUserCanNotAccessContact() {
		UserDetails userDetails = Mockito.mock(UserDetails.class);
		when(userDetails.getUsername()).thenReturn(USERNAME);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);

		when(contactService.readContactByIdNew(ID)).thenThrow(new BusinessCanNotAccessContactException(String.format(BUSINESS_CAN_NOT_ACCESS_CONTACT_EXCEPTION,USERNAME)));

		contactController.readContactByIdNew(ID);
	}


	@Test
	public void testReadContactByIdNew_IndividualUserCanAccessOwnContact() {
		UserDetails userDetails = Mockito.mock(UserDetails.class);
		when(userDetails.getUsername()).thenReturn(USERNAME);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);

		when(contactService.readContactByIdNew(ID)).thenReturn(contactDto);

		ResponseEntity<ContactDto> response = contactController.readContactByIdNew(ID);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(contactDto, response.getBody());
	}

	@Test(expected = IndividualUserCanNotAccessException.class)
	public void testReadContactByIdNew_IndividualUserCanNotAccessOtherContact() {
		UserDetails userDetails = Mockito.mock(UserDetails.class);
		when(userDetails.getUsername()).thenReturn(USERNAME);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);

		when(contactService.readContactByIdNew(ID)).thenThrow(new IndividualUserCanNotAccessException(INDIVIDUAL_USER_CAN_ACCESS_OWN_INFO_EXCEPTION));

		contactController.readContactByIdNew(ID);
	}

	@Test(expected = ContactInvalidIdException.class)
	public void testReadContactByIdNew_InvalidContactId() {
		UserDetails userDetails = Mockito.mock(UserDetails.class);
		when(userDetails.getUsername()).thenReturn(USERNAME);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);

		when(contactService.readContactByIdNew(ID)).thenThrow(new ContactInvalidIdException());

		contactController.readContactByIdNew(ID);
	}

	@Test(expected = UserNotFoundException.class)
	public void testReadContactByIdNew_UserNotFound() {
		UserDetails userDetails = Mockito.mock(UserDetails.class);
		when(userDetails.getUsername()).thenReturn(USERNAME);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);

		when(contactService.readContactByIdNew(ID)).thenThrow(new UserNotFoundException(USER_NOT_FOUND));

		contactController.readContactByIdNew(ID);
	}

	@Test
	public void testDeleteContactById_Success() throws Exception {
		UserDetails userDetails = Mockito.mock(UserDetails.class);
		when(userDetails.getUsername()).thenReturn(USERNAME);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);

		Mockito.doNothing().when(contactService).deleteContactById(ID);

		mockMvc.perform(delete("/contact/" + ID))
		       .andExpect(status().isNoContent());

		Mockito.verify(contactService).deleteContactById(ID);
	}

	@Test
	public void testReadAllContactsSuccess() {
		List<ContactDto> expectedContacts = Arrays.asList(
			contactDto,
			contactDto
		);

		when(contactService.readAllContacts()).thenReturn(expectedContacts);

		List<ContactDto> result = contactController.readAllContacts();

		assertNotNull(result);
		assertEquals(expectedContacts.size(), result.size());
	}

	@Test
	public void testReadAllContactsFailure() {
		when(contactService.readAllContacts()).thenThrow(EmptyContactListException.class);

		assertThrows(EmptyContactListException.class, () -> contactController.readAllContacts());
	}
}
