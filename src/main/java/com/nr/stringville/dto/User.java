package com.nr.stringville.dto;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;


@Entity
@Table(name = "User")
@NoArgsConstructor
@AllArgsConstructor
@lombok.Data
public class User {
    @Id
    @NotNull
    @Column(name = "name")
    private String name;
    @Column(name = "score")
    private int score;
}
