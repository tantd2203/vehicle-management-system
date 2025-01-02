package com.assignment.vehiclemanagementsystem.entity;

import com.assignment.vehiclemanagementsystem.constant.Role;
import com.assignment.vehiclemanagementsystem.entity.auth.GroupHasUser;
import com.assignment.vehiclemanagementsystem.entity.auth.UserHasRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.swing.*;
import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_user")
public class User extends AbstractEntity<Long> implements UserDetails {

    @Column(name = "user_name", nullable = false, unique = true)
    private String username;


    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number" ,nullable = false)
    private String phoneNumber;

    @Column(name = "full_name")
    private String fullName;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;


    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "device_id")
    private String deviceID;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vehicle> vehicles = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private Set<GroupHasUser> groups = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UserHasRole> roles = new HashSet<>();

    /*
     * @author: TanTD1
     * @since: 8/21/2024 10:15 AM
     * @description:

     * */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // xac thuc co con han hay k
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
