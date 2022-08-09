package com.service;

import com.entity.Animal;

import java.util.List;
import java.util.Optional;

public interface AnimalService
{
		boolean addAnimal(Animal animal);

		List<Animal> getAllAnimals();

		List<Animal> getAllByUser(String userName);

		Optional<Animal> getAnimalById(long animalId);

		boolean updateAnimal(Animal animal, String username);

		boolean deleteAnimal(long animalId, String username);

		boolean deleteAnimals(List<Long> animalsId, String username);

		boolean updateAnimals(List<Animal> animals, String username);

		boolean addAnimals(List<Animal> animals);
}
