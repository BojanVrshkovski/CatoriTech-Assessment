package com.catoritech.repository;

import com.catoritech.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
	@Query("SELECT c FROM Contact c WHERE c.userId = :userId")
	Optional<Contact> findByUserId(Long userId);

	@Modifying
	@Query("UPDATE Contact c SET c.firstName = :firstName, c.lastName = :lastName, c.address = :address,c.phone= :phone,c.VAT = :VAT WHERE c.id = :id")
	int updateContactById(
		@Param("id") long id,
		@Param("firstName") String firstName,
		@Param("lastName") String lastName,
		@Param("address") String address,
		@Param("phone") String phone,
		@Param("VAT") String VAT
	);

	@Query("SELECT c FROM Contact c WHERE c.firstName LIKE %:searchTerm% OR c.phone LIKE %:searchTerm%")
	List<Contact> findByFirstNameContainingOrPhoneContaining(@Param("searchTerm") String searchTerm);
}
