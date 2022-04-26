package com.example.login.map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
  private String type = "Bearer";
  private String accessToken;
//  private String refreshToken;

  public JwtResponse(JwtResponse jwt) {
    this.accessToken = jwt.accessToken;
//    this.refreshToken=jwt.refreshToken;
  }

//
//  private String type = "Bearer";
//  private Long id;
//  private String username;
//  private String email;
//  private List<String> roles;
//
//  public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
//    this.token = accessToken;
//    this.id = id;
//    this.username = username;
//    this.email = email;
//    this.roles = roles;
//  }
}
