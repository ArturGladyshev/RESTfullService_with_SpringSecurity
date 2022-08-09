package com.service;

import com.entity.Animal;
import com.entity.User;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService
{

		private AnimalService animalService;

		private UserRepository userRepository;

		@Autowired
		public UserServiceImp( AnimalService animalService, UserRepository userRepository)
		{
				this.animalService = animalService;
				this.userRepository = userRepository;
		}

		@Override
		public boolean addUser(User user)
		{
				User foundUser = getUserById(user.getId());
				if(foundUser != null)
						return false;
				if(user.getAnimals() != null)
						user.getAnimals().forEach(animal -> {
								animal.setUsername(user.getUsername());
						});
				animalService.addAnimals(user.getAnimals());
				userRepository.save(user);
				return true;
		}

		@Override
		public List<User> getAllUsers()
		{
				return userRepository.findAll();
		}

		@Override
		public User getUserById(long userId)
		{
				return userRepository.findById(userId);
		}


		@Override
		public Optional<User> getUserByUsername(String username)
		{
				return userRepository.findByUsername(username);
		}

		@Override
		public boolean updateUser(User user)
		{
				User foundUser = getUserById(user.getId());
				if(foundUser != null)
				{
						if(user.getAnimals() == null && foundUser.getAnimals() != null)
						{
								foundUser.getAnimals().stream().forEach(animal -> animalService.deleteAnimal(animal.getId(), user.getUsername()));
						}
						if(user.getAnimals() != null)
						{
								user.getAnimals().stream().forEach(animal -> animal.setUsername(user.getUsername()));
								if(foundUser.getAnimals() != null)
								{
										List<Animal> animals = user.getAnimals().stream().filter(animal -> foundUser.getAnimals().
											contains(animal) || !foundUser.getAnimals().contains(animal)).collect(Collectors.toList());
										user.setAnimals(animals);
										foundUser.getAnimals().stream().forEach(animal -> {
												if(!animals.contains(animal))
														animalService.deleteAnimal(animal.getId(), user.getUsername());
										});
								}
								animalService.addAnimals(user.getAnimals());
						}
						userRepository.save(user);
						return true;
				}
				return false;
		}

		@Override
		public void deleteUser(String username)
		{
				Optional<User> optionalUser = this.getUserByUsername(username);
				if(optionalUser.isPresent())
				{
						userRepository.delete(optionalUser.get());
						if(optionalUser.get().getAnimals() != null)
								animalService.deleteAnimals(optionalUser.get().getAnimals().stream().map(animal -> animal.getId())
									.collect(Collectors.toList()), username);
				}
		}
}
