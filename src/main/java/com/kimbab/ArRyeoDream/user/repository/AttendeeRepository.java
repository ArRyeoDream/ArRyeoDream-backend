package com.kimbab.ArRyeoDream.user.repository;

import com.kimbab.ArRyeoDream.user.entity.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttendeeRepository extends JpaRepository<Attendee, Long> {
    Optional<Attendee> findByNameAndPhone(String name, String phone);
}
