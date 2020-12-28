package com.mustbusk.backend.controller.fe;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mustbusk.backend.app.model.user.UserDAO;
import com.mustbusk.backend.app.model.user.UserService;
import com.mustbusk.backend.config.WebMvnConfig;

@RestController
@RequestMapping(path = WebMvnConfig.API_ENDPOINT + "user")
public class UserController
{
	private final UserService userService;

	@Autowired
	public UserController(UserService userService)
	{
		this.userService = userService;
	}

	@RequestMapping(value = "/getLoggedInUser", method = RequestMethod.GET)
	public ResponseEntity<UserDAO> getLoggedInUser(Principal principalUser)
	{
		return new ResponseEntity<>(userService.findUserByEmail(principalUser.getName()), HttpStatus.OK);
	}
}
