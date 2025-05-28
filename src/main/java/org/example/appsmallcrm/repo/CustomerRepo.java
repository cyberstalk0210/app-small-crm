package org.example.appsmallcrm.repo;

import org.example.appsmallcrm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
