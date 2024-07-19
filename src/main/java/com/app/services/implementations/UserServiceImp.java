package com.app.services.implementations;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;

import com.app.domain.user.Person;
import com.app.controller.dto.LoginRequestDTO;
import com.app.domain.user.ForoUser;
import com.app.repositories.PersonRepositoryImp;
import com.app.repositories.UserRepositoryImp;
import com.app.resources.JwtUtil;
import com.app.services.interfaces.IUserService;


@Service
public class UserServiceImp implements IUserService{

  @Autowired
  private PersonRepositoryImp personRepository;
  @Autowired
  private UserRepositoryImp userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private JwtUtil jwtUtil;

  @Override
  @Transactional
  public ForoUser registerUser(Person person){
    try {
      Person personCreated = personRepository.save(person);
      ForoUser userCreated = new ForoUser();
      userCreated.setPerson(personCreated);
      return userRepository.save(userCreated);
    } catch(Exception e){
      throw new RuntimeException("Error al crear usuario",e);
    }
  };

  @Override
  @Transactional(readOnly = true)
  public ForoUser getUserbyId(Long id){ 
    return userRepository.findById(id)
      .orElseThrow();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    ForoUser userFound = userRepository.findUserByUsername(username)
          .orElseThrow(()-> new UsernameNotFoundException("El usuario "+username+" no fue encontrado"));

    List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

    userFound.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName()))));

    return new User(userFound.getUsername(),
                    userFound.getPassword(),
                    userFound.isEnabled(),
                    userFound.isAccountNoExpired(),
                    userFound.isCredentialNoExpired(),
                    userFound.isAccountNoLocked(),
                    authorityList);
    
  };

  public Authentication authenticate (String username, String password) {
    UserDetails userFound = this.loadUserByUsername(username);

    if (userFound == null) {
      throw new BadCredentialsException("Invalid username or password!");
    }

    if (!passwordEncoder.matches(password, userFound.getPassword())){
      throw new BadCredentialsException("Invalid username or password!");
    }

    return new UsernamePasswordAuthenticationToken(username, userFound.getPassword(),userFound.getAuthorities());

  }

  public String loginUser(LoginRequestDTO loginRequest) {
    String username = loginRequest.username();
    String password = loginRequest.password();

    Authentication auth = this.authenticate(username, password);
    SecurityContextHolder.getContext().setAuthentication(auth);

    String tokenUser = this.jwtUtil.generateToken(auth);

    return tokenUser;
  }
}
