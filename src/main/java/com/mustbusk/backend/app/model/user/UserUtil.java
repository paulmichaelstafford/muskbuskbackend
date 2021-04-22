package com.mustbusk.backend.app.model.user;

public class UserUtil
{
	public static UserDAO convertToUserDAO(User user)
	{
		UserDAO userDAO = new UserDAO();
		userDAO.setId(user.getId());
		userDAO.setActive(user.getActive());
		userDAO.setEmail(user.getEmail());
		userDAO.setRoles((user.getRoles()));
		userDAO.setFirstName(user.getFirstName());
		userDAO.setLastName(user.getLastName());
		userDAO.setCreatedAt(user.getCreatedAt());
		userDAO.setUpdatedAt(user.getUpdatedAt());
		return userDAO;
	}
}
