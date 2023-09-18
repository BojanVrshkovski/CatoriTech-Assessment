package com.catoritech.service.impl;

import com.catoritech.entity.Contact;
import com.catoritech.entity.dto.ContactDto;
import com.catoritech.entity.requests.ContactRequest;
import com.catoritech.exceptions.ContactAlreadyExistException;
import com.catoritech.repository.ContactRepository;
import com.catoritech.service.ContactService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static com.catoritech.constants.LoggerAndExceptionConstants.ALREADY_EXIST_CONTACT_DB_MESSAGE;
import static com.catoritech.constants.LoggerAndExceptionConstants.SUCCESSFULLY_ADDED_CONTACT_MESSAGE;

@Service
public class ContactServiceImpl implements ContactService {
	private static final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);

	private final ContactRepository contactRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public ContactServiceImpl(ContactRepository contactRepository, ModelMapper modelMapper) {
		this.contactRepository = contactRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public Long createContact(ContactRequest contactRequest) {
		Contact contact;
		try {
			contact = modelMapper.map(contactRequest,Contact.class);
			contact = contactRepository.save(contact);
			log.info(String.format(SUCCESSFULLY_ADDED_CONTACT_MESSAGE,contactRequest.getFirstName()));
		}catch (DataIntegrityViolationException e){
			log.error(String.format(ALREADY_EXIST_CONTACT_DB_MESSAGE,contactRequest.getPhone()));
			throw new ContactAlreadyExistException(String.format(ALREADY_EXIST_CONTACT_DB_MESSAGE, contactRequest.getPhone()));
		}
		return contact.getId();
	}

	@Override
	public ContactDto readContactById(Long id) {
		return null;
	}
}
