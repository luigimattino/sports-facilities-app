/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class ResourceType {
    @Id @GeneratedValue
    private Long id;
    private String label;
 
    @OneToMany(
        mappedBy = "resourceType",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<ResourcePropertyType> propertyTypes = new ArrayList<>();
    
    public void addPropertyType(ResourcePropertyType propertyType) {
        propertyTypes.add(propertyType);
        propertyType.setResourceType(this);
    }
 
    public void removePropertyType(ResourcePropertyType propertyType) {
        propertyTypes.remove(propertyType);
        propertyType.setResourceType(null);
    }
}
