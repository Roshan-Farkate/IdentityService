package com.xseedai.identityservice.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "UserCredential")
public class UserCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
   
    private String name;
    
   
    private String email;
    
   
    private String password;
    
    private String resetToken;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
    )
  
    private Set<Role> roles = new HashSet<>();
    
    
    //private boolean isActive;
    
    
   // @Column(name = "CreatedBy")
    //private String createdBy;
    
    
   // @Column(name = "CreatedOn", columnDefinition = "datetime")
    //private java.util.Date createdOn;

   // @Column(name = "ModifiedBy", columnDefinition = "uniqueidentifier")
   // private String modifiedBy;

    //@Column(name = "ModifiedOn", columnDefinition = "datetime")
    //private java.util.Date modifiedOn;
    
	public boolean isResetTokenExpired() {
		// TODO Auto-generated method stub
		return false;
	}

}
