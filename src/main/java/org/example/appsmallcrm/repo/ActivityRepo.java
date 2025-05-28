package org.example.appsmallcrm.repo;

import org.example.appsmallcrm.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepo extends JpaRepository<Activity, Long> {
}
