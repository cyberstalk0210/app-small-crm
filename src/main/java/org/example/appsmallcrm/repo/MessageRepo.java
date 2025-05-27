package org.example.appsmallcrm.repo;

import org.example.appsmallcrm.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {
}
