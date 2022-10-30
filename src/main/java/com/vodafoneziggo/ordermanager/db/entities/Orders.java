package com.vodafoneziggo.ordermanager.db.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OrderEntity class
 * Entity class to map Order object into database
 */
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long orderId;
    private Long productId;
    private String firstName;
    private String lastName;
    private String email;
}
