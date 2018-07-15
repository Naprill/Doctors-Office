package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * DAO layer for {@link User}
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

	Optional<User> findById(final Long id);

	@Query("Select u from User u where u.email = :email")
	Optional<User> findByEmail(@Param("email") String email);

	@Query("Select u from User u order by u.firstName")
	Page<User> findAll(Pageable pageable);

	@Modifying
	@Query("UPDATE User u set u.hashedPassword = :password where u.id = :id")
	void updatePassword(@Param("password") String password, @Param("id") Long longId);

}
