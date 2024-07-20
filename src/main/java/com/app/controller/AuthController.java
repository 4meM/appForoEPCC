package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.controller.dto.LoginRequestDTO;
import com.app.controller.dto.SignupFieldsDTO;
import com.app.domain.user.ForoUser;
import com.app.services.interfaces.IUserService;

@RestController
@PreAuthorize("permitAll")
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private IUserService userService;

  @GetMapping("/hello")
  public String test () {
    return "Endpoint /auth run!";
  }

  @PostMapping("/signup")
  public ForoUser signupUser (@RequestBody SignupFieldsDTO signupFields) {
    return userService.registerUser(signupFields);
  }


  @PostMapping("/login")
  public String loginUser (@RequestBody LoginRequestDTO loginRequest) {
    return userService.loginUser(loginRequest);
  }

}
