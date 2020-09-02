/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.repository;

import it.lmattino.sportsfacilitiesapp.configuration.RepositoryConfiguration;
import it.lmattino.sportsfacilitiesapp.model.Address;
import it.lmattino.sportsfacilitiesapp.model.Structure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import org.junit.After;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 *
 * @author Mattinò
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryConfiguration.class})
public class StructureRepositoryTest {
    private StructureRepository structureRepository;
    private AddressRepository addressRepository;
    
    @Autowired
    public void setStructureRepository(StructureRepository structureRepository) {
        this.structureRepository = structureRepository;
    }
    
    @Autowired
    public void setAddressRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
    
    @Test
    public void testSaveStructure(){
        //setup structure
        Structure structure = new Structure();
        structure.setDescription("Spring Framework test");
        structure.setName("Structure Test");
        //save structure, verify has ID value after save
        assertNull(structure.getId()); //null before save
        structureRepository.save(structure);
        assertNotNull(structure.getId()); //not null after save
        //fetch from DB
        Structure fetchedStructure = structureRepository.findById(structure.getId()).orElse(null);
        //should not be null
        assertNotNull(fetchedStructure);
        //should equal
        assertEquals(structure.getId(), fetchedStructure.getId());
        assertEquals(structure.getDescription(), fetchedStructure.getDescription());
        //update description and save
        fetchedStructure.setDescription("New Description");
        structureRepository.save(fetchedStructure);
        //get from DB, should be updated
        Structure fetchedUpdatedStructure = structureRepository.findById(fetchedStructure.getId()).orElse(null);
        assertEquals(fetchedStructure.getDescription(), fetchedUpdatedStructure.getDescription());
        //verify count of structures in DB
        long structureCount = structureRepository.count();
        assertEquals(structureCount, 1);
        //get all structures, list should only have one
        Iterable<Structure> structures = structureRepository.findAll();
        int count = 0;
        for(Structure p : structures){
            count++;
        }
        assertEquals(count, 1);
    }
    
    @Test
    public void testSaveStructureWithAddress(){
        //setup structure
        Structure structure = new Structure();
        structure.setDescription("Structure Spring Framework test");
        structure.setName("Structure Test 1");
        
        Address address = new Address();
        address.setStreet("FK Street");
        address.setStreetNo(25);
        address.setCity("FK City");
        address.setZipCode(19999);
        address.setCountry("FK Country");
        
        address.setStructure(structure);
        structure.setAddress(address);
        //save structure, verify has ID value after save
        assertNull(structure.getId()); //null before save
        structureRepository.save(structure);
        assertNotNull(structure.getId()); //not null after save
        
        //fetch from DB
        Structure fetchedStructure = structureRepository.findById(structure.getId()).orElse(null);
        //should not be null
        assertNotNull(fetchedStructure);
        
        Address fetchedAddress = fetchedStructure.getAddress();
        assertNotNull(fetchedAddress);
        assertEquals("FK Street", fetchedAddress.getStreet());
        assertEquals("FK City", fetchedAddress.getCity());
        
        structureRepository.deleteById(structure.getId());
        //verify count of structures in DB
        long structureCount = structureRepository.count();
        assertEquals(structureCount, 0);
    }
    
    
    @After
    public void checkAddress(){ 
        //get all address, list should not have any
        Iterable<Address> addresses = addressRepository.findAll();
        int count = 0;
        for(Address a : addresses){
            count++;
        }
        assertEquals(count, 0);
    }
}
