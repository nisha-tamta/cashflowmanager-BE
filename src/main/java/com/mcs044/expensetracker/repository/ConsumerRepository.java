package com.mcs044.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mcs044.expensetracker.entity.Consumer;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {
    public Consumer findByUsername(String username);
	public Consumer findByEmailAddress(String emailAddress);
}
