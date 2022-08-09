package com.controller;

import com.entity.Animal;
import com.entity.User;
import com.service.AnimalService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AnimalController
{
		private UserService userService;

		private AnimalService animalService;

		@Autowired
		public AnimalController(UserService userService, AnimalService animalService)
		{
				this.userService = userService;
				this.animalService = animalService;
		}


		@GetMapping(value = "/users/animalList/{username}")
		@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
		public ResponseEntity<?> read(@PathVariable(name = "username") String username)
		{
				List<Animal> animals = animalService.getAllByUser(username);
				if(animals != null && !animals.isEmpty())
								return new ResponseEntity<>(animals, HttpStatus.OK);
				return new ResponseEntity<>("Animals not found", HttpStatus.NOT_FOUND);
		}


		@GetMapping(value = "/users/animals/{id}")
		@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
		public ResponseEntity<?> read(@PathVariable(name = "id") long id)
		{
				Optional<Animal> animal = animalService.getAnimalById(id);
				return animal.isPresent()
					? new ResponseEntity<>(animal.get(), HttpStatus.OK)
					: new ResponseEntity<>("Animal not found", HttpStatus.NOT_FOUND);
		}


		@PostMapping(value = "users/animals/{username}")
		@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
		public ResponseEntity<?> save(
			@PathVariable(name = "username") String username, @RequestBody List<Animal> animals)
		{
				Optional<User> user = userService.getUserByUsername(username);
				if(user.isEmpty())
						return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
				if(animals.isEmpty())
						return new ResponseEntity<>("Not a single animal was added to the request", HttpStatus.NO_CONTENT);
				boolean isAdded = animalService.addAnimals(animals);
				if(isAdded)
						return new ResponseEntity<>(HttpStatus.CREATED);
				return new ResponseEntity<>("The animal with the added nickname or id already exists in the database",
					HttpStatus.NOT_FOUND);
		}


		@DeleteMapping(value = "users/animals/{username}")
		@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
		public ResponseEntity<?> delete(@PathVariable(name = "username") String username, @RequestBody List<Long> animalsId)
		{
				Optional<User> user = userService.getUserByUsername(username);
				if(user.isEmpty())
						return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
				if(animalsId.isEmpty())
						return new ResponseEntity<>("Not a single animal id was added to the request by delete", HttpStatus.NOT_FOUND);
				boolean isDeleted;
				isDeleted = animalService.deleteAnimals(animalsId, username);
				if(isDeleted)
						new ResponseEntity<>(HttpStatus.OK);
				return new ResponseEntity<>("One or more animals were not found in the database", HttpStatus.NOT_FOUND);
		}


		@PutMapping(value = "users/animals/{username}")
		@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
		public ResponseEntity<?> update(@PathVariable(name = "username") String username, @RequestBody List<Animal> animals)
		{
				Optional<User> user = userService.getUserByUsername(username);
				if(user.isEmpty())
						return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
				if(animals.isEmpty())
						return new ResponseEntity<>("Not a single animal has been was added to the request by update", HttpStatus.NO_CONTENT);
				boolean isUpdated;
				isUpdated = animalService.updateAnimals(animals, username);
				if(isUpdated)
						return new ResponseEntity<>(HttpStatus.OK);
				return new ResponseEntity<>("One or more animals were not found in the database", HttpStatus.NOT_FOUND);
		}
}

