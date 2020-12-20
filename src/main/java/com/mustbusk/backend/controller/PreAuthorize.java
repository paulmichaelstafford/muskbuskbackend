package com.mustbusk.backend.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mustbusk.backend.app.model.user.UserDAO;
import com.mustbusk.backend.app.model.user.UserService;

public class PreAuthorize
{
	private final UserService userService;
	private final Logger logger = LoggerFactory.getLogger(PreAuthorize.class);

	public PreAuthorize(UserService userService)
	{
		this.userService = userService;
	}

	public boolean isUserAllowed(Principal principalUser, Long passedClientUserId)
	{
		boolean isAllowed = true;

		UserDAO userDAO = userService.findUserByEmail(principalUser.getName());
		if (userDAO == null)
		{
			logger.error("Email user{} does is not in db", principalUser.getName());
			isAllowed = false;
		}
		else
		{
			if (!userDAO.getId().equals(passedClientUserId))
			{
				logger.error("UserId {} does not have the same ID as the passed user ID {} in cache", userDAO.getId().toString(), passedClientUserId);
				isAllowed = false;
			}
		}
		return isAllowed;
	}
}
