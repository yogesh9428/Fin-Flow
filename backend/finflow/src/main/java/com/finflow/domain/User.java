package com.finflow.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name" , nullable = false , length = 30)
    private String name;

    @NotBlank
    @Column(name = "email" , nullable = false , length =  50)
    private String email;

    @NotBlank
    @Column(name = "password" , nullable = false , length = 25)
    private String password;

    @Column(name = "enabled" , nullable = false)
    private boolean enabled = true;

    @Column(name = "created_at" , nullable = false , updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private Set roles = new HashSet<>();

    @PrePersist
    protected void onCreate(){
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = Instant.now();
    }

    public User(String name , String email , String passwordHash){
        this.name = name;
        this.email = email;
        this.password = passwordHash;
    }


}
