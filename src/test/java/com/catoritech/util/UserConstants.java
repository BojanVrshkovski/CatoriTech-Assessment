package com.catoritech.util;

import com.catoritech.entity.enums.UserRole;

public final class UserConstants {
	public static final Long ID = 1L;
	public static final String USERNAME = "Bojan";
	public static final String PASSWORD = "Bojan1V";
	public static final UserRole USER_ROLE = UserRole.BUSINESS;

	public static final String USER_NOT_FOUND = "User with provided id is not found";
	public static String BUSINESS_CAN_NOT_ACCESS_CONTACT_EXCEPTION = "Business user with username %s does not have access to this contact";
	public static String INDIVIDUAL_USER_CAN_ACCESS_OWN_INFO_EXCEPTION = "Individual user can only access their own contact information";
}
