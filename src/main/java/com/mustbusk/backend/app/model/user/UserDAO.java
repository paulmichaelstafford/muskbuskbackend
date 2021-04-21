package com.mustbusk.backend.app.model.user;

import java.time.Instant;
import java.util.Set;

import com.mustbusk.backend.app.model.Active;
import com.mustbusk.backend.app.model.user.role.Role;

public class UserDAO
{

	private Long id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
//	private Active active;
	private Set<Role> roles;
	private Instant createdAt;
	private Instant updatedAt;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public Active getActive()
	{
		return Active.ACTIVE;
	}

	public void setActive(Active active)
	{

	}

	public Set<Role> getRoles()
	{
		return roles;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}

	public Instant getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt)
	{
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt()
	{
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt)
	{
		this.updatedAt = updatedAt;
	}
}
