package org.example.finance_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true , nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Status{
        ACTIVE,
        INACTIVE
    };

    @Enumerated(EnumType.STRING)
    private Status status;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return  username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return  password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return  role; }

    public Status getStatus() { return  status; }
    public void setStatus(Status status) { this.status = status; }
}
