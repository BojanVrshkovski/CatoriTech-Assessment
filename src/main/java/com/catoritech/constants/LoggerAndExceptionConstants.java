package com.catoritech.constants;

public final class LoggerAndExceptionConstants {
	public static final String ALREADY_EXIST_USER_DB_MESSAGE = "The user with username: %s alredy exists in the db";
	public static final String SUCCESSFULLY_REGISTERED_USER_MESSAGE = "The user with username: %s is successfully added in the db";
	public static final String USER_NOT_FOUND_MESSAGE = "The user with username: %s is not found in the db";
	public static final String CONTACT_NOT_FOUND_FOR_USER_MESSAGE = "Contact not found for user with ID: %d";
	public static final String CAUGHT_EXCEPTION_MESSAGE = "Caught exception:";
	public static final String SUCCESSFULLY_ADDED_CONTACT_MESSAGE = "The contact with name: %s is successfully added in the db";
	public static final String ALREADY_EXIST_CONTACT_DB_MESSAGE = "The contact with phone number: %s alredy exists in the db";
	public static String READ_CONTACT_MESSAGE = "Contact with id: %d is trying to fetch his information";
	public static String BUSINESS_CAN_NOT_ACCESS_CONTACT = "Business user with username %s does not have access to this contact";
	public static String INDIVIDUAL_USER_CAN_ACCESS_OWN_INFO = "Individual user can only access their own contact information";
	public static String NO_CONTACTS_FOUND = "No contacts found";
	public static String NO_ROWS_UPDATED = "No rows updated, something went wrong";
}
