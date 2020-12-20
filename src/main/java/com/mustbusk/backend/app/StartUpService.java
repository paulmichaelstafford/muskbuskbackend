package com.mustbusk.backend.app;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.mustbusk.backend.app.model.user.UserService;

@Service
public class StartUpService
{
	private final UserService userService;

	public StartUpService(UserService userService)
	{
		this.userService = userService;
	}

	@PostConstruct
	public void startUp()
	{
		userService.init();
	}
}
