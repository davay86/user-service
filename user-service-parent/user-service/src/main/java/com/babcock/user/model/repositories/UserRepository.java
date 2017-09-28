package com.babcock.securityadmin.model.repositories;

import com.babcock.user.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Long countByPayNumber(String payNumber);
}

