package com.service;

import com.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService
{
		boolean addUser(User user);

		List<User> getAllUsers();

		User getUserById(long userId);

		boolean updateUser(User user);

		void deleteUser(String username);

		Optional<User> getUserByUsername(String username);
}
