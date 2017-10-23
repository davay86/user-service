package com.babcock.user.model.repositories;

import com.babcock.user.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByActiveIsFalse();
    User findByUsername(String username);

}

