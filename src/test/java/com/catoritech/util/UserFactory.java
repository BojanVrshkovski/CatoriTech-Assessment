package com.catoritech.util;

import com.catoritech.entity.User;
import static com.catoritech.util.UserConstants.ID;
import static com.catoritech.util.UserConstants.PASSWORD;
import static com.catoritech.util.UserConstants.USERNAME;
import static com.catoritech.util.UserConstants.USER_ROLE;

public final class UserFactory {
	public static User getDefaultUser(){
		User user = new User(ID,USERNAME,PASSWORD,USER_ROLE);
		return user;
	}
}
