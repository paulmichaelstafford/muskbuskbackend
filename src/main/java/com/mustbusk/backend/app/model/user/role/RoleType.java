package com.mustbusk.backend.app.model.user.role;

import java.util.HashMap;
import java.util.Map;

public enum RoleType
{
	ADMIN("ADMIN"),
	BUSKER("BUSKER"),
	USER("USER");

	private static final Map map = new HashMap<>();

	static
	{
		for (RoleType type : RoleType.values())
		{
			map.put(type.value, type);
		}
	}

	private final String value;

	RoleType(String s)
	{
		value = s;
	}

	public String getValue()
	{
		return value;
	}

	public boolean equalsName(String otherName)
	{
		// (otherName == null) check is not needed because name.equals(null) returns false
		return value.equals(otherName);
	}

	public String toString()
	{
		return this.value;
	}
}
