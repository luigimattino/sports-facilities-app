/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.repository;

import it.lmattino.sportsfacilitiesapp.model.Address;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Mattinò
 */
public interface AddressRepository extends CrudRepository<Address, Long>{
    
}
