package br.com.curso.spring.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.curso.spring.model.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>{
	UserEntity findByUserId(String userId);
	UserEntity findByEmail(String email);
	boolean existsByEmail(String email);
}
