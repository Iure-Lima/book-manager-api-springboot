package org.book.bookmanager.User.Services;

import org.book.bookmanager.User.Model.UserModel;
import org.book.bookmanager.User.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(email);
    }

    public boolean existsUser(String email){
        return this.userRepository.existsByEmail(email);
    }

    public void saveUser(UserModel userModel){
        this.userRepository.save(userModel);
    }
}
