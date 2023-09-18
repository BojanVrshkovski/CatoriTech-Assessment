package com.catoritech.service.impl;

import com.catoritech.entity.Contact;
import com.catoritech.entity.User;
import com.catoritech.entity.dto.ContactDto;
import com.catoritech.entity.requests.ContactRequest;
import com.catoritech.exceptions.ContactAlreadyExistException;
import com.catoritech.exceptions.ContactInvalidIdException;
import com.catoritech.exceptions.UserNotFoundException;
import com.catoritech.repository.ContactRepository;
import com.catoritech.repository.UserRepository;
import com.catoritech.service.ContactService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static com.catoritech.constants.LoggerAndExceptionConstants.ALREADY_EXIST_CONTACT_DB_MESSAGE;
import static com.catoritech.constants.LoggerAndExceptionConstants.CONTACT_NOT_FOUND_FOR_USER_MESSAGE;
import static com.catoritech.constants.LoggerAndExceptionConstants.READ_CONTACT_MESSAGE;
import static com.catoritech.constants.LoggerAndExceptionConstants.SUCCESSFULLY_ADDED_CONTACT_MESSAGE;
import static com.catoritech.constants.LoggerAndExceptionConstants.USER_NOT_FOUND_MESSAGE;

@Service
public class ContactServiceImpl implements ContactService {
	private static final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);

	private final ContactRepository contactRepository;
	private final ModelMapper modelMapper;
	private final UserRepository userRepository;

	@Autowired
	public ContactServiceImpl(ContactRepository contactRepository, ModelMapper modelMapper, UserRepository userRepository) {
		this.contactRepository = contactRepository;
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
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
		log.info(String.format(READ_CONTACT_MESSAGE, id));
		Contact contact = contactRepository.findById(id).orElseThrow(ContactInvalidIdException::new);
		return modelMapper.map(contact,ContactDto.class);
	}

	@Override
	public ContactDto readContactInformation() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username = userDetails.getUsername();

		User user = userRepository.findByUsername(username)
		                          .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE,username)));

		Contact contact = contactRepository.findByUserId(user.getId())
		                                   .orElseThrow(() -> new EntityNotFoundException(String.format(CONTACT_NOT_FOUND_FOR_USER_MESSAGE,user.getId())));

		return modelMapper.map(contact, ContactDto.class);
	}

}
