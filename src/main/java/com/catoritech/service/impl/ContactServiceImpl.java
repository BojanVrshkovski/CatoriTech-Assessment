package com.catoritech.service.impl;

import com.catoritech.entity.Contact;
import com.catoritech.entity.requests.ContactRequest;
import com.catoritech.repository.ContactRepository;
import com.catoritech.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

	private final ContactRepository contactRepository;

	@Autowired
	public ContactServiceImpl(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}

	@Override
	public Long createContact(ContactRequest contactRequest) {
		Contact contact = new Contact();
		try {

		}
		return null;
	}
}
