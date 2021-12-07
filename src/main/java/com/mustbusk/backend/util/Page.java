package com.mustbusk.backend.util;

import java.util.List;

public class Page<T>
{
	int totalPages;
	long totalElements;
	int number;
	int size;
	int numberOfElements;
	List<T> content;
	boolean hasContent;
	String sortColumn;
	SortDirection sortDirection;

	public int getTotalPages()
	{
		return totalPages;
	}

	public void setTotalPages(int totalPages)
	{
		this.totalPages = totalPages;
	}

	public long getTotalElements()
	{
		return totalElements;
	}

	public void setTotalElements(long totalElements)
	{
		this.totalElements = totalElements;
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public int getNumberOfElements()
	{
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements)
	{
		this.numberOfElements = numberOfElements;
	}

	public List<T> getContent()
	{
		return content;
	}

	public void setContent(List<T> content)
	{
		this.content = content;
	}

	public boolean isHasContent()
	{
		return hasContent;
	}

	public void setHasContent(boolean hasContent)
	{
		this.hasContent = hasContent;
	}

	public String getSortColumn()
	{
		return sortColumn;
	}

	public void setSortColumn(String sortColumn)
	{
		this.sortColumn = sortColumn;
	}

	public SortDirection getSortDirection()
	{
		return sortDirection;
	}

	public void setSortDirection(SortDirection sortDirection)
	{
		this.sortDirection = sortDirection;
	}
}
