package com.app.services.interfaces;

import com.app.domain.user.Person;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.app.controller.dto.LoginRequestDTO;
import com.app.domain.user.ForoUser;

public interface IUserService extends UserDetailsService {

  public ForoUser registerUser(Person person);

  public ForoUser getUserbyId(Long id);

  public String loginUser(LoginRequestDTO loginRequest);

  public Authentication authenticate (String username, String password);

}
