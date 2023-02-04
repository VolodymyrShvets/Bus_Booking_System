package com.booking.system.repository;

import com.booking.system.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByEmail(String email);

    User findByEmail(String email);

    void deleteByEmail(String email);
}
