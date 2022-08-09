package com.service;

import com.entity.Animal;
import com.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalServiceImp implements AnimalService
{
		private AnimalRepository animalRepository;

		public AnimalServiceImp(@Autowired AnimalRepository animalRepository)
		{
				this.animalRepository = animalRepository;
		}

		private Optional<Animal> getAnimalByNickname(String nickname)
		{
				return animalRepository.findAnimalByNickname(nickname);
		}

		@Override public List<Animal> getAllAnimals()
		{
				return animalRepository.findAll();
		}

		@Override public List<Animal> getAllByUser(String userName)
		{
				return animalRepository.findAllByUsername(userName);
		}

		@Override public Optional<Animal> getAnimalById(long animalId)
		{
				return animalRepository.findById(animalId);
		}

		@Override public boolean addAnimal(Animal animal)
		{
				Optional<Animal> foundAnimal = getAnimalById(animal.getId());
				if(foundAnimal.isPresent())
						return false;
				foundAnimal = getAnimalByNickname(animal.getNickname());
				if(foundAnimal.isPresent())
						return false;
				animalRepository.save(animal);
				return true;
		}
		@Override
		public boolean addAnimals(List<Animal> animals)
		{
				for(Animal animal : animals)
				{
						Optional<Animal> foundAnimal = getAnimalById(animal.getId());
						if(foundAnimal.isPresent())
								return false;
						foundAnimal = getAnimalByNickname(animal.getNickname());
						if(foundAnimal.isPresent())
								return false;
				}
				animals.stream().forEach(animal -> animalRepository.save(animal));
				return true;
		}

		@Override public boolean updateAnimal(Animal animal, String username)
		{
				Optional<Animal> foundAnimal = getAnimalByNickname(animal.getNickname());
				if(foundAnimal.isPresent() && animal.getId() != foundAnimal.get().getId())
						return false;
				foundAnimal = getAnimalById(animal.getId());
				if(foundAnimal.isEmpty() || foundAnimal.get().getUsername() != username)
						return false;
				animalRepository.delete(foundAnimal.get());
				animalRepository.save(animal);
				return true;
		}

		@Override
		public boolean updateAnimals(List<Animal> animals, String username)
		{
				for(Animal animal : animals)
				{
						Optional<Animal> foundAnimal = getAnimalByNickname(animal.getNickname());
						if(foundAnimal.isPresent() && animal.getId() != foundAnimal.get().getId())
								return false;
						foundAnimal = getAnimalById(animal.getId());
						if(foundAnimal.isEmpty() || foundAnimal.get().getUsername() != username)
								return false;
				}
				animals.stream().forEach(animal -> {
						animalRepository.delete(getAnimalById(animal.getId()).get());
						animalRepository.save(animal);
				});
				return true;
		}

		@Override public boolean deleteAnimal(long animalId, String username)
		{
				Optional<Animal> foundAnimal = getAnimalById(animalId);
				if(foundAnimal.isPresent() && foundAnimal.get().getUsername() == username)
				{
						animalRepository.delete(foundAnimal.get());
						return true;
				}
				return false;
		}

		@Override
		public boolean deleteAnimals(List<Long> animalsId, String username)
		{
				List<Animal> animals = new ArrayList<>();
				for(long id : animalsId)
				{
						Optional<Animal> foundAnimal = getAnimalById(id);
						if(foundAnimal.isEmpty() && foundAnimal.get().getUsername() != username)
								return false;
						animals.add(foundAnimal.get());
				}
				animals.stream().forEach(animal -> animalRepository.delete(animal));
				return true;
		}
}
