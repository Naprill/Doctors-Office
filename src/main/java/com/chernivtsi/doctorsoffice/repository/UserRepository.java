package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

	@Modifying
	@Query("UPDATE User u set u.enabled = :enabled where u.id = :id")
	void updateUserEnabled(@Param("enabled") boolean enabled, @Param("id") Long userId);

	/**
	 * Method provides data for autocomplete AJAX request.
	 * Get list of all users, whose first name or last name contains entered fragment
	 */
	@Query("Select u from User u where " +
			"lower(u.firstName) like concat('%', concat(lower(:fragment) , '%') ) " +
			"or lower(u.lastName) like concat('%', concat(lower(:fragment) , '%'))  ")
	List<User> findByNameIgnoreCaseContaining(@Param("fragment") String fragment);
}
