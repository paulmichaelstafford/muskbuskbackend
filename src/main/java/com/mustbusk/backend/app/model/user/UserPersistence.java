package com.mustbusk.backend.app.model.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mustbusk.backend.app.model.FrontEndException;
import com.mustbusk.backend.util.SortDirection;

@Service()
public class UserPersistence
{
	private final UserRepository userRepository;

	@Autowired
	public UserPersistence(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}

	@Transactional
	public void delete(Long id)
	{
		if (userRepository.findById(id).isPresent())
		{
			throw new FrontEndException(HttpStatus.BAD_REQUEST, String.format("User ID %d not found", id));
		}
		userRepository.deleteById(id);
	}

	@Transactional
	public User save(User userToSave)
	{
		return userRepository.save(userToSave);
	}

	@Transactional()
	public User update(Long userIdToEdit, User userToUpdate)
	{
		if (userRepository.findById(userIdToEdit).isPresent())
		{
			throw new FrontEndException(HttpStatus.BAD_REQUEST, String.format("User ID not found %d", userIdToEdit));
		}
		return userRepository.save(userToUpdate);
	}

	public Optional<User> findByEmail(String email)
	{
		return userRepository.findByEmail(email);
	}

	public Optional<User> findById(Long id)
	{
		return userRepository.findById(id);
	}

	public com.mustbusk.backend.util.Page<UserDAO> findAll(int page, int size, SortDirection sortDirection, String sortColumn)
	{
		Page<User> tempUser = userRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(sortDirection.toString()), sortColumn)));
		com.mustbusk.backend.util.Page<UserDAO> pageUser = new com.mustbusk.backend.util.Page();
		pageUser.setTotalPages(tempUser.getTotalPages());
		pageUser.setTotalElements(tempUser.getTotalElements());
		pageUser.setNumber(tempUser.getNumber());
		pageUser.setSize(tempUser.getSize());
		pageUser.setNumberOfElements(tempUser.getNumberOfElements());
		pageUser.setContent(tempUser.getContent().stream().map(UserUtil::convertToUserDAO).collect(Collectors.toList()));
		pageUser.setSortColumn(sortColumn);
		pageUser.setSortDirection(sortDirection);
		return pageUser;
	}
	
	public List<User> getAll()
	{
		return userRepository.findAll();
	}
}

