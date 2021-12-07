package com.mustbusk.backend.util;

import java.util.HashMap;
import java.util.Map;

public enum SortDirection
{
	DESC("DESC"), ASC("ASC");

	private static final Map map = new HashMap<>();

	static
	{
		for (SortDirection type : SortDirection.values())
		{
			map.put(type.value, type);
		}
	}

	private final String value;

	SortDirection(String s)
	{
		value = s;
	}

	public String getValue()
	{
		return value;
	}

	public boolean equalsName(String otherName)
	{
		return value.equals(otherName);
	}

	public String toString()
	{
		return this.value;
	}
}
