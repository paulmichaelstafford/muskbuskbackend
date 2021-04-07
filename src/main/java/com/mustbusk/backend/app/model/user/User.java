package com.mustbusk.backend.app.model.user;

import java.time.Instant;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mustbusk.backend.app.model.Active;
import com.mustbusk.backend.app.model.user.role.Role;

@Entity
@Table(name = "user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Column(name = "email", length = 100, unique = true)
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
	private String email;
	@Column(name = "password")
	@Length(min = 5, message = "*Your password must have at least 5 characters")
	@NotEmpty(message = "*Please provide your password")
	private String password;
	@Column(name = "first_name", length = 100)
	@NotEmpty(message = "*Please provide your name")
	private String firstName;
	@Column(name = "last_name", length = 100)
	@NotEmpty(message = "*Please provide your last name")
	private String lastName;
	@Enumerated
	@Column(name = "active")
	private Active active;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	@Column(name = "created_at")
	private Instant createdAt;
	@Column(name = "updatedAt")
	private Instant updatedAt;

	@PrePersist
	void createdAt()
	{
		this.createdAt = this.updatedAt = Instant.now();
	}

	@PreUpdate
	void updatedAt()
	{
		this.updatedAt = Instant.now();
	}

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
		return active;
	}

	public void setActive(Active active)
	{
		this.active = active;
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


