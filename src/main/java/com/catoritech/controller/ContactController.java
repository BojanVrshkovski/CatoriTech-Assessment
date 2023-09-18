package com.catoritech.controller;

import com.catoritech.entity.dto.ContactDto;
import com.catoritech.entity.requests.ContactRequest;
import com.catoritech.service.ContactService;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class ContactController {
	private final ContactService contactService;

	@Autowired
	public ContactController(ContactService contactService) {
		this.contactService = contactService;
	}

	@PostMapping("/createContact")
	@PreAuthorize("hasAuthority('BUSINESS')")
	public ResponseEntity<Void> createContact(@RequestBody ContactRequest contactRequest) {
		Long id = contactService.createContact(contactRequest);

		URI location = UriComponentsBuilder.fromUriString("/contact/{id}")
		                                   .buildAndExpand(id)
		                                   .toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/contact/{id}")
	@PreAuthorize("hasAuthority('BUSINESS')")
	public ResponseEntity<ContactDto> readEmployeeById(@PathVariable @NotNull Long id) {
		ContactDto contactDto = contactService.readContactById(id);

		return ResponseEntity.ok(contactDto);
	}

}
