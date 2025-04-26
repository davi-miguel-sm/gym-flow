package com.gymflow.config;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gymflow.model.User;

public class CostumerUserDetails implements UserDetails {

  private final User user;

  @Autowired
  public CostumerUserDetails(User user) {
    this.user = user;
  }

  public UUID getId() {
    return user.getId();
  }

  public String getBio() {
    return user.getBio();
  }

  public String getProfilePic() {
    return user.getProfilePic();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.emptyList(); // Add roles if needed
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public User getUser() {
    return user;
  }

}
