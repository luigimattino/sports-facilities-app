/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.repository;

import it.lmattino.sportsfacilitiesapp.model.Resource;
import java.util.Optional;

/**
 *
 * @author Mattinò
 */
public interface ResourceWithChildrenRepository {
    public Optional<Resource> saveWithParent(Resource resource, Resource parent);
    public int deleteIfHasNoChildById(Long id);
}
