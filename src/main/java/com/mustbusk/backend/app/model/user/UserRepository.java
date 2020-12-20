package com.mustbusk.backend.app.model.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mustbusk.backend.app.model.Active;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long>
{
	Optional<User> findByEmail(String email);

	List<User> findAllByActive(Active active);
}
