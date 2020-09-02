/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import it.lmattino.sportsfacilitiesapp.model.Structure;
import it.lmattino.sportsfacilitiesapp.repository.StructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Mattinò
 */
@RestController
@RequestMapping("/structure")
@Api(value="Sport Structure", description="Operations pertaining to Structure")
public class StructureController {
    

    private StructureRepository structureRepository;

    @Autowired
    public void setStructureRepository(StructureRepository structureRepository) {
        this.structureRepository = structureRepository;
    }

    @ApiOperation(value = "View a list of available structure",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(value = "/list", method= RequestMethod.GET, produces = "application/json")
    public Iterable<Structure> list(Model model){
        Iterable<Structure> structureList = structureRepository.findAll();
        return structureList;
    }

    @ApiOperation(value = "Search a structure with an ID",response = Structure.class)
    @RequestMapping(value = "/show/{id}", method= RequestMethod.GET, produces = "application/json")
    public Structure showStructure(@PathVariable Long id, Model model){
       Structure structure = structureRepository.findById(id).get();
        return structure;
    }
    
    @ApiOperation(value = "Add a structure")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity saveStructure(@RequestBody Structure structure){
        if ( structure.getAddress() != null ) {
            structure.getAddress().setStructure(structure);
        }
        structureRepository.save(structure);
        return new ResponseEntity("Structure saved successfully", HttpStatus.OK);
    }
    
    @ApiOperation(value = "Update a structure")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateStructure(@PathVariable Long id, @RequestBody Structure structure){
        Structure storedStructure = structureRepository.findById(id).get();
        storedStructure.setDescription(structure.getDescription());
        storedStructure.setName(structure.getName());
        structureRepository.save(storedStructure);
        return new ResponseEntity("Structure updated successfully", HttpStatus.OK);
    }
    

    @ApiOperation(value = "Delete a structure")
    @RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity delete(@PathVariable Long id){
        structureRepository.deleteById(id);
        return new ResponseEntity("Structure deleted successfully", HttpStatus.OK);
    }
}
