package com.nr.stringville.dto;


import javax.persistence.Column;

@lombok.Data
public class UserDTO {
    private String name;
    private int score;
    private int validSubmissions;
    private int totalSubmissions;
}
