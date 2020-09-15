package com.itacademy.soccer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itacademy.soccer.dto.typeUser.TypeUser;

import javax.persistence.*;
import java.lang.reflect.Type;

@Entity
@Table(name="user") // Tab User
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Id User Auto-Generated

    @Column(name="type")
    @Enumerated(EnumType.STRING) // IMPORTANT: This converts 0 or 1 to Manager or Admin (INTEGER TO STRING)
    private TypeUser typeUser;

    @Column(name="email")
    private String email; // User Email = Name User

    @Column(name="password")
    private String password; // User Password
    
  
        
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="teamId")
    @JsonIgnore
    private Team team;  // User Team Relation One To One (STANDBY)*/
    
 //   @Column(name="teamId")
  //  private int team;  // User Team Relation by ID/ because if I try as entity there is conflict with IPlayerActionsDAO
    

    public User() { } // Constructor

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public TypeUser getTypeUser()
    {
        return typeUser;
    }

    public void setTypeUser(TypeUser typeUser)
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

    
   public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    
  /*  public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }
   */ 
       
}
