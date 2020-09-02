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
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Mattinò
 */
@Entity
@Data
@Builder
@NoArgsConstructor@AllArgsConstructor
public class ResourcePropertyType {
    @Id @GeneratedValue
    private Long id;
    private String label;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ResourceType resourceType;
}
