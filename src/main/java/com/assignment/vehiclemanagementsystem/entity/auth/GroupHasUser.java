package com.assignment.vehiclemanagementsystem.entity.auth;

import com.assignment.vehiclemanagementsystem.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_group_has_user")
public class GroupHasUser extends AbstractEntityAuth<Integer> {

    @ManyToOne
    @JoinColumn (name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;
}
