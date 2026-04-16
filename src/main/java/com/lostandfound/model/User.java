package com.lostandfound.model;

//This class holds all user information
public class User {
 private int userId;
 private String role;
 private String fullName;
 private String email;
 private String phone;
 private String studentId;
 private String passwordHash;

 // These methods return the value of each field
 public int getUserId() { return userId; }
 public void setUserId(int userId) { this.userId = userId; }
 public String getRole() { return role; }
 public void setRole(String role) { this.role = role; }
 public String getFullName() { return fullName; }
 public void setFullName(String fullName) { this.fullName = fullName; }
 public String getEmail() { return email; }
 public void setEmail(String email) { this.email = email; }
 public String getPhone() { return phone; }
 public void setPhone(String phone) { this.phone = phone; }
 public String getStudentId() { return studentId; }
 public void setStudentId(String studentId) { this.studentId = studentId; }
 public String getPasswordHash() { return passwordHash; }
 public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}
