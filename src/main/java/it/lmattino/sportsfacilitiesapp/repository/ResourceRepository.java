/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.repository;

import it.lmattino.sportsfacilitiesapp.model.Resource;
import java.util.ArrayList;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Mattinò
 */
public interface ResourceRepository extends JpaRepository<Resource, Long>, ResourceWithChildrenRepository {
    @Query(value = "select r from Resource r "
            + "join fetch r.type "
            + "join fetch r.properties props "
            + "join fetch props.type "
            + "where r.id = :id")
    Optional<Resource> findByIdWithProperties(@Param("id") Long id);
    
    
    @Query(value = "select r from Resource r "
            + "left join fetch r.parent "
            + "left join fetch r.childs "
            + "join fetch r.type "
            + "join fetch r.properties props "
            + "join fetch props.type "
            + "where r.id = :id")
    Optional<Resource> findByIdWithChildsAndProperties(@Param("id") Long id);
    Optional<ArrayList<Object[]>> myFindById(@Param("id") Long id);
}
