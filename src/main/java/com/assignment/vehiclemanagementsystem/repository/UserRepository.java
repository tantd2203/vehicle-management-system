package com.assignment.vehiclemanagementsystem.repository;

import java.util.List;
import java.util.Optional;

import com.assignment.vehiclemanagementsystem.entity.User;
import com.assignment.vehiclemanagementsystem.entity.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/*
* @author: TanTD1
* @since: 06/08/2024 10:45
* @description:  UserRepository handles all database operations related to user
* @update:
* */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User>  findByEmail(String username);
    Optional<User>  findByUsername(String username);
    @Query("SELECT r FROM Role r JOIN UserHasRole uhr ON r.id = uhr.role.id WHERE uhr.user.id = ?1")
    List<Role> findAllByUserId(Integer userId);

}
