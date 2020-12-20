package com.mustbusk.backend.controller.fe;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController
{
	@CrossOrigin()
	@RequestMapping(value = "/auth/user", method = RequestMethod.GET)
	public Principal user(Principal user)
	{
		return user;
	}
}
