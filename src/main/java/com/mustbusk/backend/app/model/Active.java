package com.mustbusk.backend.app.model;

import java.util.HashMap;
import java.util.Map;

public enum Active
{
	INACTIVE(0),
	ACTIVE(1);

	private static final Map map = new HashMap<>();

	static
	{
		for (Active type : Active.values())
		{
			map.put(type.value, type);
		}
	}

	private final int value;

	Active(int value)
	{
		this.value = value;
	}

	public static Active valueOf(int pageType)
	{
		return (Active) map.get(pageType);
	}

	public int getValue()
	{
		return value;
	}
}
