package com.springboot.myapp.model;

import com.springboot.myapp.enums.Role;
import com.springboot.myapp.repository.UserRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "users")
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority sga= new SimpleGrantedAuthority(role.toString());
        return List.of(sga);
    }
}
