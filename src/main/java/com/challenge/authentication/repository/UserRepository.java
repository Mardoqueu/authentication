package com.challenge.authentication.repository;

import com.challenge.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository is a Spring Data JPA repository interface for managing User entities.
 *
 * This repository provides CRUD operations and additional methods for querying User entities
 * in the database. It extends JpaRepository to inherit standard database operations.
 *
 * @repository marks this interface as a Spring Data Repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

