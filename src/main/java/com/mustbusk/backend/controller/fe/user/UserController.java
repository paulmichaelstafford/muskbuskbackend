package com.mustbusk.backend.controller.fe.user;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

	@GetMapping(value = "/getLoggedInUser")
	public ResponseEntity<UserDAO> getLoggedInUser(Principal principalUser)
	{
		return new ResponseEntity<>(userService.findByEmail(principalUser.getName()), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAll")
	public ResponseEntity<List<UserDAO>> getAll()
	{
		return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
	}
	
	
	
}
