package com.nr.stringville.dto;


import com.sun.istack.NotNull;
import lombok.NoArgsConstructor;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.*;


@Entity
@Table(name = "User")
@NoArgsConstructor
@lombok.Data
public class User {
    @Id
    @NotNull
    @Column(name = "name")
    private String name;
    @Column(name = "score")
    private int score;
    @Column(name = "validSubmissions")
    private int validSubmissions;
    @Column(name = "totalSubmissions")
    private int totalSubmissions;
}
