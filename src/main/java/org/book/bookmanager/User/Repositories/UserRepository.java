package org.book.bookmanager.User.Repositories;

import org.book.bookmanager.User.Model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
    UserDetails findByEmail(String email);

    boolean existsByEmail(String email);
}
