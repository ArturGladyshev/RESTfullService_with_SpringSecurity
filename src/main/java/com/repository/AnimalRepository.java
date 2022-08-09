package com.repository;

import com.entity.Animal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimalRepository extends CrudRepository<Animal, Long>
{
		Optional<Animal> findById(long id);

		List<Animal> findAll();

		List<Animal> findAllByUsername(String userName);

		Optional<Animal> findAnimalByNickname(String nickname);

}

