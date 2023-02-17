package com.example.gestioninfractionsadja.data.dto.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
  
  @NotBlank
  private String username;

  @NotBlank
  private String password;

  public LoginRequest() {}

  public LoginRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return this.username.replaceAll("\\s+","");
  }

  public String getPassword() {
    return this.password;
  }
}
