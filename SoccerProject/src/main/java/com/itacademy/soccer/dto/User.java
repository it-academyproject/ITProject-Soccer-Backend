package com.itacademy.soccer.dto;

import javax.persistence.*;

@Entity
@Table(name="user") // Tab User
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Id User Auto-Generated

    @Column(name="type")
    private Enum typeUser; // Type User (Manager/Admin)

    @Column(name="email")
    private String email; // User Email = Name User

    @Column(name="password")
    private String password; // User Password

    /*
    @OneToOne //(mappedBy = "team", Cascade = CascadeType.ALl)
    @Column(name="teamId")
    private Team team;  // User Team Relation One To One (STANDBY)
    */

    public User() { } // Constructor

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Enum getTypeUser()
    {
        return typeUser;
    }

    public void setTypeUser(Enum typeUser)
    {
        this.typeUser = typeUser;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
/*
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }*/
}
