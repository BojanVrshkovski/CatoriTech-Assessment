package com.catoritech.service;

import com.catoritech.entity.requests.ContactRequest;

public interface ContactService {
	Long createContact(ContactRequest contactRequest);
}
