package com.babcock.user.helper;

import com.babcock.user.model.domain.User;
import com.babcock.user.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class PersistenceHelper {

    @Autowired
    protected UserRepository userRepository;

    public User createUser(String username, String firstname, String lastname){
        User user = new User(username, firstname, lastname);
        userRepository.save(user);
        return user;
    }

    public User createActiveUser(String username, String firstname, String lastname){
        User user = createUser(username, firstname, lastname);
        user.setActive(true);
        userRepository.save(user);
        return user;
    }

    public User findByUsername(String username){
        User user = userRepository.findByUsername(username);
        return user;
    }

    public void removeUser(User user){
        userRepository.delete(user);
    }
}
