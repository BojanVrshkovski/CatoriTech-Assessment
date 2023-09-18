package com.catoritech.controller;

import com.catoritech.entity.Contact;
import com.catoritech.entity.dto.ContactDto;
import com.catoritech.entity.requests.ContactRequest;
import com.catoritech.exceptions.ContactAlreadyExistException;
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
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.catoritech.util.ContactConstants.EXCEPTION_MESSAGE;
import static com.catoritech.util.ContactConstants.ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
}
