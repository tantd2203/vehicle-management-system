package com.assignment.vehiclemanagementsystem.entity.auth;

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
@Table(name = "tbl_role_has_permissions")
public class RoleHasPermission  extends AbstractEntityAuth<Integer> {

    @ManyToOne
    @JoinColumn (name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn (name = "permission_id")
    private Permission permission;
}
