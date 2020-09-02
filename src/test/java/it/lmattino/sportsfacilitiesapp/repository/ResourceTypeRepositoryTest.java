/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.repository;

import it.lmattino.sportsfacilitiesapp.configuration.RepositoryConfiguration;
import it.lmattino.sportsfacilitiesapp.model.ResourcePropertyType;
import it.lmattino.sportsfacilitiesapp.model.ResourceType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Mattinò
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryConfiguration.class})
public class ResourceTypeRepositoryTest {

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;
    @Autowired
    private ResourcePropertyTypeRepository resourcePropertyTypeRepository;

    @Test
    public void testing() {
        saveResourceType();
        saveResourceTypeWithPorpertyTypes();
        findResourceTypeByLabel();
    }

    public void saveResourceType() {
        ResourceType resourceType = new ResourceType();
        resourceType.setLabel("CAMPO DI CALCIO A");
        //save structure, verify has ID value after save
        assertNull(resourceType.getId()); //null before save
        resourceTypeRepository.save(resourceType);
        assertNotNull(resourceType.getId()); //not null after save
    }

    public void saveResourceTypeWithPorpertyTypes() {
        ResourceType resourceType = new ResourceType();
        resourceType.setLabel("CAMPO DI CALCIO B");
        resourceType.addPropertyType(ResourcePropertyType.builder().label("LARGHEZZA").build());
        resourceType.addPropertyType(ResourcePropertyType.builder().label("LUNGHEZZA").build());
        resourceType.addPropertyType(ResourcePropertyType.builder().label("ALTITUDINE").build());
        //save resource type, verify has ID value after save
        assertNull(resourceType.getId()); //null before save
        resourceTypeRepository.save(resourceType);
        assertNotNull(resourceType.getId()); //not null after save
        //get all resource property type, list should only have one
        /*Iterable<ResourcePropertyType> resourcePropertyTypes = resourcePropertyTypeRepository.findAll();
        int count = 0;
        for (ResourcePropertyType rpt : resourcePropertyTypes) {
            count++;
        }
        assertEquals(count, 3);*/
    }

    public void findResourceTypeByLabel() {
        ResourceType resourceType = resourceTypeRepository.findByLabel("CAMPO DI CALCIO B");

        assertNotNull(resourceType); //not null
    }
}
