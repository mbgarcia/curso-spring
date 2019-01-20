package br.com.curso.spring.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.curso.spring.model.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>{
	UserEntity findByUserId(String userId);
	UserEntity findByEmail(String email);
	boolean existsByEmail(String email);
	
	@Query(value="SELECT * FROM users where first_name = :firstName"
			, nativeQuery=true)
	public List<UserEntity> findUsersByFirstName(@Param("firstName") String firstName);

	@Query(value="SELECT * FROM users where first_name LIKE %:search%"
			, countQuery="SELECT count(*) FROM users where first_name = %:search%"			
			, nativeQuery=true)
	public Page<UserEntity> findSimilarUsersByFirstName(Pageable pageable, String search);
}
