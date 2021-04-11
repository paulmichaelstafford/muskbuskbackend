package com.mustbusk.backend.app.model.user;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mustbusk.backend.app.model.Active;
import com.mustbusk.backend.app.model.FrontEndException;
import com.mustbusk.backend.app.model.user.role.RoleService;

@Service()
public class UserService
{
	private final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserPersistence userPersistence;
	private final RoleService roleService;

	@Autowired
	public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserPersistence userPersistence, RoleService roleService)
	{
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userPersistence = userPersistence;
		this.roleService = roleService;
	}

	public UserDAO saveNewUser(UserDAO user)
	{
		if (userPersistence.findByEmail(user.getEmail()).isPresent())
		{
			throw new FrontEndException(HttpStatus.CONFLICT, String.format("User %s already exists", user.getEmail()));
		}

		User userToSave = new User();
		userToSave.setEmail(user.getEmail());
		userToSave.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userToSave.setFirstName(user.getFirstName());
		userToSave.setLastName(user.getLastName());
		userToSave.setRoles(user.getRoles());
		userToSave.setActive(Active.ACTIVE);
		userToSave.setPassword(bCryptPasswordEncoder.encode(userToSave.getPassword()));

		return UserUtil.convertToUserDAO(userPersistence.save(userToSave));
	}

	public UserDAO updateUser(Long userIdToUpdate, User passedUser)
	{
		User originalUser = userPersistence.findById(userIdToUpdate)
			.orElseThrow(() -> new FrontEndException(HttpStatus.BAD_REQUEST, String.format("User ID %d not found", userIdToUpdate)));

		//I'm not updating the password
		if (passedUser.getPassword() == null)
		{
			passedUser.setPassword(originalUser.getPassword());
		}
		else
		{
			passedUser.setPassword(bCryptPasswordEncoder.encode(passedUser.getPassword()));
		}

		checkBeforeUpdating(originalUser, passedUser);
		return UserUtil.convertToUserDAO(userPersistence.update(userIdToUpdate, passedUser));

	}

	private void checkBeforeUpdating(User originalUser, User userToUpdate)
	{
		if (originalUser.equals(userToUpdate))
		{
			throw new FrontEndException(HttpStatus.NOT_MODIFIED,
				String.format("Nothing has changed in " + "the passed user {%s}", userToUpdate.toString()));
		}

		if (originalUser.getUpdatedAt().toEpochMilli() != userToUpdate.getUpdatedAt().toEpochMilli())
		{
			throw new FrontEndException(HttpStatus.UNPROCESSABLE_ENTITY,
				String.format("user{%s} has more recently been updated." + "Please reload the user. ", userToUpdate.getId()));
		}
	}

	public void deleteUser(Long id)
	{
		userPersistence.delete(id);
	}

	public UserDAO findUserByEmail(String email)
	{
		return UserUtil.convertToUserDAO(userPersistence.findByEmail(email)
			.orElseThrow(() -> new FrontEndException(HttpStatus.BAD_REQUEST, String.format("User email %s not found", email))));
	}

	public void init()
	{
		String adminUserEmail = "patstaffordjnr@gmail.com";
		if (!userPersistence.findByEmail(adminUserEmail).isPresent())
		{
			String password = "patrickIsDumb";
			UserDAO adminUser = new UserDAO();
			adminUser.setEmail(adminUserEmail);
			adminUser.setPassword(password);
			adminUser.setRoles(new HashSet<>(roleService.getAllRoles().values()));
			adminUser.setFirstName("Patrick");
			adminUser.setLastName("Dumb");
			adminUser.setActive(Active.ACTIVE);
			saveNewUser(adminUser);
			logger.info("Created user {} with password {}", adminUserEmail, password);
		}
	}
}
