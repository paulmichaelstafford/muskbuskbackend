package com.mustbusk.backend.app.model.user;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mustbusk.backend.app.model.FrontEndException;

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

	public Page<User> findAll(Pageable pageable)
	{
		return userRepository.findAll(pageable);
	}
}

