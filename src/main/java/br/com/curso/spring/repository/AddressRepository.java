package br.com.curso.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.curso.spring.model.AddressEntity;
import br.com.curso.spring.model.UserEntity;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long>{
	List<AddressEntity> findByUser(UserEntity user);
}
