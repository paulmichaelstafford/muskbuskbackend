package com.mustbusk.backend.app.model.user.role;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleService
{
	private final RolePersistence rolePersistence;

	@Autowired
	public RoleService(RolePersistence rolePersistence)
	{
		this.rolePersistence = rolePersistence;
	}

	public Map<RoleType, Role> getAllRoles()
	{
		Map<RoleType, Role> roles = new HashMap<>();
		for (int i = 0; i < RoleType.values().length; i++)
		{
			Optional<Role> optionalRole = rolePersistence.findByName(RoleType.values()[i].toString());
			if (optionalRole.isPresent())
			{
				roles.put(RoleType.values()[i], optionalRole.get());
			}
		}
		return roles;
	}

	public Role save(Role role)
	{
		return rolePersistence.save(role);
	}
}
