package com.catoritech.controller;

import com.catoritech.entity.Contact;
import com.catoritech.entity.dto.ContactDto;
import com.catoritech.entity.requests.ContactRequest;
import com.catoritech.service.ContactService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ContactController {

	private final ContactService contactService;

	@Autowired
	public ContactController(ContactService contactService) {
		this.contactService = contactService;
	}

	@PostMapping("/createContact")
	@PreAuthorize("hasAnyAuthority('BUSINESS','INDIVIDUAL')")
	public ResponseEntity<Void> createContact(@RequestBody ContactRequest contactRequest) {
		Long id = contactService.createContact(contactRequest);

		URI location = UriComponentsBuilder.fromUriString("/contact/{id}")
		                                   .buildAndExpand(id)
		                                   .toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/contacts/{id}")
	@PreAuthorize("hasAuthority('BUSINESS')")
	public ResponseEntity<ContactDto> readContactById(@PathVariable @NotNull Long id) {
		ContactDto contactDto = contactService.readContactById(id);

		return ResponseEntity.ok(contactDto);
	}

	@GetMapping("/contact/profile")
	public ResponseEntity<ContactDto> readContactOwnInformation() {
		ContactDto contactDto = contactService.readContactInformation();

		return ResponseEntity.ok(contactDto);
	}

	@GetMapping("/contact/{id}")
	@PreAuthorize("hasAnyAuthority('BUSINESS','INDIVIDUAL')")
	public ResponseEntity<ContactDto> readContactByIdNew(@PathVariable @NotNull Long id) {
		ContactDto contactDto = contactService.readContactByIdNew(id);

		return ResponseEntity.ok(contactDto);
	}

	@DeleteMapping("/contact/{id}")
	@PreAuthorize("hasAnyAuthority('BUSINESS','INDIVIDUAL')")
	public ResponseEntity<Contact> deleteContactById(@PathVariable @NotNull Long id) {
		contactService.deleteContactById(id);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/contact/all")
	@PreAuthorize("hasAnyAuthority('BUSINESS','INDIVIDUAL')")
	public List<ContactDto> readAllContacts() {
		return contactService.readAllContacts();
	}

	@PatchMapping("/contact/update/{id}")
	@PreAuthorize("hasAnyAuthority('BUSINESS','INDIVIDUAL')")
	public ResponseEntity<Void> updateContactById(
		@PathVariable @NotNull Long id, @RequestBody ContactRequest contactRequest) {
		contactService.updateContactById(id, contactRequest);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/contact/search")
	@PreAuthorize("hasAnyAuthority('BUSINESS','INDIVIDUAL')")
	public ResponseEntity<List<ContactDto>> searchContacts(@RequestParam("searchTerm") String searchTerm) {
		List<ContactDto> foundContacts = contactService.searchContacts(searchTerm);

		if (!foundContacts.isEmpty()) {
			return new ResponseEntity<>(foundContacts, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
