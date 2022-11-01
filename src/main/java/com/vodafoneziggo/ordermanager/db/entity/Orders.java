package com.vodafoneziggo.ordermanager.db.entity;

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
 * This is Database Entity class for Orders table
 *
 * @author Thambi Thurai Chinnadurai
 */
@Entity
@Table
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Long orderId;

    private Long productId;

    private String firstName;

    private String lastName;

    private String email;
}
