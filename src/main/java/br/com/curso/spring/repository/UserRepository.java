package br.com.curso.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.curso.spring.model.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{
	boolean existsByEmail(String email);
}
