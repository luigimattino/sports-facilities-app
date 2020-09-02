/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.model;

import io.swagger.annotations.ApiModelProperty;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import lombok.Data;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Mattinò
 */
@Entity
@Data
public class Structure {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @ApiModelProperty(value = "Address")
    @OneToOne(mappedBy = "structure", cascade = CascadeType.ALL)
    private Address address;
    
}
