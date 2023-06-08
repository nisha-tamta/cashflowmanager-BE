package com.mcs044.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mcs044.expensetracker.entity.Role;

/*
 * Create a repository interface that extends JpaRepository to interact with the role entity in the database.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
