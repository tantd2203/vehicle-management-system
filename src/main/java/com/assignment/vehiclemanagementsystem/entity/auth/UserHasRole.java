package com.assignment.vehiclemanagementsystem.entity.auth;

import com.assignment.vehiclemanagementsystem.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_user_has_roles")
public class UserHasRole extends AbstractEntityAuth<Integer> {

        @ManyToOne
        @JoinColumn(name = "role_id")
        private Role role;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;


}
