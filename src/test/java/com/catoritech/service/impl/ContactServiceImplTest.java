package com.catoritech.service.impl;

import com.catoritech.entity.Contact;
import com.catoritech.entity.dto.ContactDto;
import com.catoritech.entity.requests.ContactRequest;
import com.catoritech.exceptions.ContactAlreadyExistException;
import com.catoritech.exceptions.ContactInvalidIdException;
import com.catoritech.repository.ContactRepository;
import com.catoritech.util.ContactFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Optional;
import static com.catoritech.util.ContactConstants.ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
public class ContactServiceImplTest {
	public ContactRequest contactRequest;
	public Contact contact;
	public ContactDto contactDto;

	@InjectMocks
	private ContactServiceImpl contactService;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private ContactRepository contactRepository;

	@Before
	public void setUp() {
		contactRequest = ContactFactory.getContactRequest();
		contact = ContactFactory.getDefaultContact();
		contactDto = ContactFactory.getDefaultContactDto();
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
}
