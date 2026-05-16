package com.lostandfound.model;

import java.io.Serializable;

/**
 * UserModel - Represents a user in the system
 * Package: com.lostandfound.model
 */
public class UserModel implements Serializable {

    private static final long serialVersionUID = 1L; // fixes serialization warning

    private int    id;
    private String fullName;
    private String email;
    private String phone;
    private String studentId;
    private String password;
    private String role;

    public UserModel() {}

    public UserModel(int id, String fullName, String email, String phone,
                     String studentId, String password, String role) {
        this.id        = id;
        this.fullName  = fullName;
        this.email     = email;
        this.phone     = phone;
        this.studentId = studentId;
        this.password  = password;
        this.role      = role;
    }

    // Getters
    public int    getId()        { return id; }
    public String getFullName()  { return fullName; }
    public String getEmail()     { return email; }
    public String getPhone()     { return phone; }
    public String getStudentId() { return studentId; }
    public String getPassword()  { return password; }
    public String getRole()      { return role; }

    // Setters
    public void setId(int id)                { this.id = id; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String email)       { this.email = email; }
    public void setPhone(String phone)       { this.phone = phone; }
    public void setStudentId(String sid)     { this.studentId = sid; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role)         { this.role = role; }

    // ✅ Added toString for easier debugging/logging
    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", studentId='" + studentId + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
