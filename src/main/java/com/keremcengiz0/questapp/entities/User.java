package com.keremcengiz0.questapp.entities;

import lombok.Data;
import javax.persistence.*;

@Data
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    private int avatar;

}
