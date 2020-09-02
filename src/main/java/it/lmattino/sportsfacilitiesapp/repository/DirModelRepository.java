/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.repository;

import it.lmattino.sportsfacilitiesapp.model.DirModel;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Mattinò
 */
@Repository
public interface DirModelRepository extends JpaRepository<DirModel, Long> {
    
    Optional<ArrayList<DirModel>> myFindById(@Param("id") Long id);
    
    @Query(value = "select r from DirModel r "
            + "left join fetch r.parent "
            + "left join fetch r.childs "
            + "where r.id = :id")
    Optional<DirModel> my2FindById(@Param("id") Long id);
}
