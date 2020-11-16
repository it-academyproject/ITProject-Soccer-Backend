package com.itacademy.soccer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itacademy.soccer.dto.typeUser.TypeUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="user") // Tab User
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Id User Auto-Generated

    @Column(name="type")
    @JsonProperty("type_user") // Change the field name to "type_user" in JSON Response 
    @Enumerated(EnumType.STRING) // IMPORTANT: This converts 0 or 1 to Manager or Admin (INTEGER TO STRING)
    private TypeUser typeUser;

    @Column(name="email")
    private String email; // User Email = Name User

    @Column(name="password")
    private String password; // User Password

    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    //@JsonIgnore 
    private Team team;  // User Team Relation One To One (STANDBY)*/
   
    
    public User() { } // Constructor

    public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}


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
    
    
}