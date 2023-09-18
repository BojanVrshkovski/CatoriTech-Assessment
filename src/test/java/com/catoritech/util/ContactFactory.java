package com.catoritech.util;

import com.catoritech.entity.Contact;
import com.catoritech.entity.dto.ContactDto;
import com.catoritech.entity.requests.ContactRequest;

import static com.catoritech.util.ContactConstants.ADDRESS;
import static com.catoritech.util.ContactConstants.FIRST_NAME;
import static com.catoritech.util.ContactConstants.ID;
import static com.catoritech.util.ContactConstants.LAST_NAME;
import static com.catoritech.util.ContactConstants.PHONE;
import static com.catoritech.util.ContactConstants.USER_ID;
import static com.catoritech.util.ContactConstants.VAT;

public final class ContactFactory {

	public static ContactRequest getContactRequest(){
		ContactRequest contactRequest = new ContactRequest(FIRST_NAME,LAST_NAME,ADDRESS,PHONE,VAT,USER_ID);
		return contactRequest;
	}

	public static Contact getDefaultContact(){
		Contact contact = new Contact(ID,FIRST_NAME,LAST_NAME,ADDRESS,PHONE,VAT,USER_ID);
		return contact;
	}

	public static ContactDto getDefaultContactDto(){
		ContactDto contactDto = new ContactDto(ID,FIRST_NAME,LAST_NAME,ADDRESS,PHONE,VAT,USER_ID);
		return contactDto;
	}
}
