package com.repository;

import com.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>
{
		User findById(long id);

		Optional<User> findByUsername(String name);

		Boolean existsByUsername(String username);

		List<User> findAll();

}
