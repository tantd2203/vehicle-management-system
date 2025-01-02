package com.assignment.vehiclemanagementsystem.entity.auth;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntityAuth<T extends Serializable> implements  Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private T id;
//
//    @CreatedBy
//    @Column(name = "created_by")
//    private T createdBy;
//
//    @LastModifiedBy
//    @Column(name = "updated_by")
//    private T updatedBy;
//
//    @Column(name = "created_at")
//    @CreationTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdAt;
//
//    @Column(name = "updated_at")
//    @UpdateTimestamp
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date updatedAt;
}
