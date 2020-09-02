/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.repository;

import it.lmattino.sportsfacilitiesapp.model.Resource;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Mattinò
 */
@Slf4j
@Repository
public class ResourceWithChildrenRepositoryImpl implements ResourceWithChildrenRepository {

    @Autowired
    private EntityManager em;

    @Override
    @Transactional
    public Optional<Resource> saveWithParent(Resource resource, Resource parent) {
        try {

            resource.setParent(parent);
            parent.addChildren(resource);
            em.persist(resource);
            em.merge(parent);
            return Optional.of(resource);
        } catch (Exception e) {
            log.error("An error occurs: ", e);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public int deleteIfHasNoChildById(Long id) {
        int result = 0;
        try {
            String selectQuery = "select count(r) from Resource r where r.parent.id = :id";
            Long count = (Long) em.createQuery(selectQuery).setParameter("id", id).getSingleResult();
            if (count == 0L) {
                deleteRelatedResourceProperties(id);
                deleteResource(id);
                result = 1;
            }
            return result;
        } catch (Exception e) {
            log.error("An error occurs: ", e);
        }
        return 0;
    }

    @Transactional
    private void deleteResource(Long id) {
        String deleteQuery = "delete from Resource r "
                + "where r.id = :id";
        em.createQuery(deleteQuery).setParameter("id", id).executeUpdate();
        em.flush();
    }

    @Transactional
    private void deleteRelatedResourceProperties(Long id) {
        String selectQuery = "select rp from ResourceProperty rp where rp.resource.id = :id";
        em.createQuery(selectQuery).setParameter("id", id).getResultStream()
                .forEach(em::remove);
        em.flush();
    }

}
