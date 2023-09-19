package com.catoritech.service;

import com.catoritech.entity.dto.ContactDto;
import com.catoritech.entity.requests.ContactRequest;

public interface ContactService {
	Long createContact(ContactRequest contactRequest);
	ContactDto readContactById(Long id);
	ContactDto readContactInformation();
	ContactDto readContactByIdNew(Long id);
	void deleteContactById(Long id);
}
