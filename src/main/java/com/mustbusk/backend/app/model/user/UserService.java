package com.mustbusk.backend.app.model.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.mustbusk.backend.app.model.Active;
import com.mustbusk.backend.app.model.FrontEndException;
import com.mustbusk.backend.app.model.user.role.RoleService;
import com.mustbusk.backend.util.Page;
import com.mustbusk.backend.util.SortDirection;

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

	public UserDAO saveNew(UserDAO user)
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

	public UserDAO update(Long userIdToUpdate, UserDAO passedUser)
	{
		User originalUser = userPersistence.findById(userIdToUpdate)
			.orElseThrow(() -> new FrontEndException(HttpStatus.BAD_REQUEST, String.format("User ID %d not found", userIdToUpdate)));

		User userToSave = new User();
		userToSave.setEmail(passedUser.getEmail());

		if (passedUser.getPassword() == null)
		{
			passedUser.setPassword(originalUser.getPassword());
		}
		else
		{
			passedUser.setPassword(bCryptPasswordEncoder.encode(passedUser.getPassword()));
		}

		userToSave.setFirstName(passedUser.getFirstName());
		userToSave.setLastName(passedUser.getLastName());
		userToSave.setRoles(passedUser.getRoles());
		userToSave.setActive(passedUser.getActive());
		checkBeforeUpdating(originalUser, userToSave);
		return UserUtil.convertToUserDAO(userPersistence.update(userIdToUpdate, userToSave));
	}

	private void checkBeforeUpdating(User originalUser, User userToUpdate)
	{
		if (originalUser.equals(userToUpdate))
		{
			throw new FrontEndException(HttpStatus.NOT_MODIFIED, String.format("Nothing has changed in the passed user %s", userToUpdate.getId()));
		}

		if (originalUser.getUpdatedAt().toEpochMilli() != userToUpdate.getUpdatedAt().toEpochMilli())
		{
			throw new FrontEndException(HttpStatus.UNPROCESSABLE_ENTITY,
				String.format("user %s has more recently been updated." + "Please reload the user. ", userToUpdate.getId()));
		}
	}

	public void delete(Long id)
	{
		userPersistence.delete(id);
	}

	public UserDAO findByEmail(String email)
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
			saveNew(adminUser);
			logger.info("Created user {} with password {}", adminUserEmail, password);
		}
	}

	public Page<UserDAO> findPaginated(int page, int size, SortDirection sortDirection, String sortColumn)
	{
		return userPersistence.findAll(page, size, sortDirection, sortColumn);
	}
	

	public List<UserDAO> getAll()
	{
		List<UserDAO> users = new ArrayList<>();
		userPersistence.getAll().stream().map(UserUtil::convertToUserDAO).forEach(users::add);
		return users;
	}
}
