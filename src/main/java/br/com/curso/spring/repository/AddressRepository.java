package br.com.curso.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.curso.spring.model.AddressEntity;
import br.com.curso.spring.model.UserEntity;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long>{
	List<AddressEntity> findByUser(UserEntity user);
	
	@Query("SELECT e FROM AddressEntity e where e.user.userId = :userId")
	List<AddressEntity> findByUserId(String userId);
}
