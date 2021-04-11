package com.mustbusk.backend.controller.fe.admin;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mustbusk.backend.app.model.user.UserDAO;
import com.mustbusk.backend.app.model.user.UserService;
import com.mustbusk.backend.app.model.user.role.RoleDAO;
import com.mustbusk.backend.app.model.user.role.RoleService;
import com.mustbusk.backend.config.WebMvnConfig;

@RestController
@RequestMapping(path = WebMvnConfig.API_ENDPOINT + "admin")
public class AdminController
{
	private final UserService userService;
	private final RoleService roleService;

	@Autowired
	public AdminController(UserService userService, RoleService roleService)
	{
		this.userService = userService;
		this.roleService = roleService;
	}

	@GetMapping(value = "/getLoggedInUser")
	public ResponseEntity<UserDAO> getLoggedInUser(Principal principalUser)
	{
		return new ResponseEntity<>(userService.findUserByEmail(principalUser.getName()), HttpStatus.OK);
	}

	@GetMapping(value = "/roles")
	public ResponseEntity<List<RoleDAO>> getRoles()
	{
		return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
	}

	@PostMapping()
	ResponseEntity<UserDAO> newEmployee(@RequestBody UserDAO newEmployee) {
		return new ResponseEntity<>(userService.saveNewUser(newEmployee), HttpStatus.OK);
	}




}
