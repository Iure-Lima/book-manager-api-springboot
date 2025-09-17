package org.book.bookmanager.User.Controllers;

import jakarta.validation.Valid;
import org.book.bookmanager.User.DTOs.AuthenticationDTO;
import org.book.bookmanager.User.DTOs.RegisterDTO;
import org.book.bookmanager.User.Model.UserModel;
import org.book.bookmanager.User.Services.TokenService;
import org.book.bookmanager.User.Services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid RegisterDTO registerDTO){
        if (userService.existsUser(registerDTO.email())) return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());

        UserModel newUser = new UserModel();
        BeanUtils.copyProperties(registerDTO, newUser);
        newUser.setPassword(encryptedPassword);

        userService.saveUser(newUser);

        var usernamePassword = new UsernamePasswordAuthenticationToken(registerDTO.email(), registerDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        if (!userService.existsUser(authenticationDTO.email())) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Credentials");
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.password());
            var auth = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((UserModel) auth.getPrincipal());
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Credentials");
        }
    }

}
