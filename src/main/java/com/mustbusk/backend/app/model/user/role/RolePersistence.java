package com.mustbusk.backend.app.model.user.role;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleDao")
public class RolePersistence
{
	private final RoleRepository roleRepository;

	@Autowired
	public RolePersistence(RoleRepository roleRepository)
	{
		this.roleRepository = roleRepository;
	}

	public Optional<Role> findByName(String roleName)
	{
		return roleRepository.findByRoleName(roleName);
	}

	public List<Role> findAll()
	{
		return roleRepository.findAll();
	}

	@Transactional
	public Role save(Role role)
	{
		return roleRepository.save(role);
	}
}
