package com.mustbusk.backend.controller.fe;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mustbusk.backend.app.model.user.UserDAO;
import com.mustbusk.backend.app.model.user.UserService;

@RestController
public class AuthController
{

	private final UserService userService;

	@Autowired
	public AuthController(UserService userService)
	{
		this.userService = userService;
	}

	@CrossOrigin()
	@RequestMapping(value = "/auth/user", method = RequestMethod.GET)
	public UserDAO user(Principal user)
	{
		return userService.findUserByEmail(user.getName());
	}
}
