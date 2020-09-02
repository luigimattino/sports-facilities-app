/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import lombok.Data;

/**
 *
 * @author Mattinò
 */
@Entity
@Data
public class Address {
    
    @Id
    private Long id;
    private String fullCopy;
    private String street;
    private Integer streetNo;
    private String locale;
    private String city;
    private String state;
    private String country;
    private Integer zipCode;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonIgnore
    private Structure structure;

}
