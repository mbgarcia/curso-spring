package br.com.curso.spring.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.curso.spring.exception.UserNotFoundException;
import br.com.curso.spring.model.PasswordResetTokenEntity;
import br.com.curso.spring.model.UserEntity;
import br.com.curso.spring.repository.PasswordResetTokenRepository;
import br.com.curso.spring.repository.UserRepository;
import br.com.curso.spring.request.UserControllerPutRequest;
import br.com.curso.spring.shared.AmazonSESEmailSender;
import br.com.curso.spring.shared.AmazonSESResetPasswordSender;
import br.com.curso.spring.shared.Utils;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	PasswordResetTokenRepository passwordResetRepository;
	
	public UserEntity createUser(UserEntity user){
		if (repository.existsByEmail(user.getEmail()))
			throw new RuntimeException("User already exists");
		
		user.setUserId(utils.generateUserId(30));
		user.setEncryptedPassword(passwordEncoder.encode(user.getPassword()));
		user.setEmailVerificationToken(utils.generateEmailToken(user.getEmail()));
		user.getAddresses().stream().forEach(address -> address.setUser(user));
		
		UserEntity storedUser = repository.save(user);
		
		new AmazonSESEmailSender().sendEmail(user);
		
		return storedUser;
	}
	
	public UserEntity updateUser(String publicId, UserControllerPutRequest user) {
		UserEntity stored = findUserByPublicId(publicId);
		
		stored.setFirstName(user.getFirstName());
		stored.setLastName(user.getLastName());
		
		return repository.save(stored);
	}
	
	public void deleteUser(String publicId) {
		UserEntity stored = findUserByPublicId(publicId);
		stored.setDeleted(true);
		stored.setDeletedAt(LocalDateTime.now());
		repository.save(stored);
	}
	
	public UserEntity findUserByPublicId(String publicId) {
		UserEntity user =  repository.findByUserId(publicId);
		
		if (user == null)
			throw new UserNotFoundException(publicId);
		
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = repository.findByEmail(email);
		
		if (user == null) throw new UsernameNotFoundException(email);
		
		return new User(user.getEmail(), user.getEncryptedPassword(), user.getEmailVerificationStatus(),
				true, true, true, new ArrayList<>());
	}

	public UserEntity findUserByEmail(String email) {
		UserEntity user = repository.findByEmail(email);
		
		if (user == null)
			throw new UserNotFoundException(email);
		
		return user;
	}
	
	public List<UserEntity> getUsers(int page, int limit){
		if (page > 0) page -= 1;
		
		Pageable pageable = PageRequest.of(page, limit);
		
		return repository.findAll(pageable).getContent();
	}

	public Boolean verifyEmailToken(String token) {
		boolean verified = false;
		
		UserEntity entity = repository.findByEmailVerificationToken(token);
		
		if (entity != null) {
			boolean tokenExpired = Utils.hasTokenExpired(token);
			
			if (!tokenExpired) {
				entity.setEmailVerificationToken("");
				entity.setEmailVerificationStatus(true);
				repository.save(entity);
				
				verified = true;
			}
		}
		
		return verified;
	}

	public void resetPassword(String email) {
		UserEntity user = repository.findByEmail(email);
		
		if (user == null) {
			throw new UserNotFoundException(email);			
		}
		
		String token = utils.generateEmailToken(email);
		
		PasswordResetTokenEntity tokenEntity = new PasswordResetTokenEntity();
		tokenEntity.setToken(token);
		tokenEntity.setUser(user);
		passwordResetRepository.save(tokenEntity);
		
		new AmazonSESResetPasswordSender().sendEmail(user, token);
	}
}