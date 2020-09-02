/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.repository;

import it.lmattino.sportsfacilitiesapp.model.ResourceType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Mattinò
 */
public interface ResourceTypeRepository extends CrudRepository<ResourceType, Long>{
    @Query(value = "select t from ResourceType t "
            + "left join fetch t.propertyTypes "
            + "where t.label = :label")
    public ResourceType findByLabel(@Param("label") String label);
    
}
