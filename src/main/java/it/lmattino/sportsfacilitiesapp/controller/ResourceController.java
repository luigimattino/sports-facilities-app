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
import it.lmattino.sportsfacilitiesapp.model.Resource;
import it.lmattino.sportsfacilitiesapp.model.ResourceProperty;
import it.lmattino.sportsfacilitiesapp.repository.ResourceRepository;
import java.beans.FeatureDescriptor;
import java.util.stream.Stream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Mattinò
 */
@RestController
@RequestMapping("/resource")
@Api(value = "Resource", description = "Operations pertaining to Resource")
public class ResourceController {

    @Autowired
    private ResourceRepository resourceRepository;

    @ApiOperation(value = "View a list of available resource", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
        }
    )
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public Iterable<Resource> list(Model model) {
        Iterable<Resource> structureList = resourceRepository.findAll();
        return structureList;
    }

    @ApiOperation(value = "Search a resource with an ID", response = Resource.class)
    @RequestMapping(value = "/show/{id}", method= RequestMethod.GET, produces = "application/json")
    public Resource showResource(@PathVariable Long id, Model model){
       Resource resource = resourceRepository.findByIdWithChildsAndProperties(id).get();
        return resource;
    }
    
    @ApiOperation(value = "Add a resource")
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity saveResource(@RequestBody Resource resource){
        if ( resource.getProperties() != null && !resource.getProperties().isEmpty() ) {
            for (ResourceProperty rp : resource.getProperties()) {
                rp.setResource(resource);
            }
        }
        resourceRepository.save(resource);
        
        return new ResponseEntity("Resource saved successfully", HttpStatus.OK);
    }
    
    @ApiOperation(value = "Update a resource")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity updateResource(@PathVariable Long id, @RequestBody Resource resource){
        Resource storedResource = resourceRepository.findByIdWithChildsAndProperties(id).get();
        BeanUtils.copyProperties(resource, storedResource, getNullPropertyNames(resource));
        if ( storedResource.getProperties() != null && !storedResource.getProperties().isEmpty() ) {
            for (ResourceProperty rp : storedResource.getProperties()) {
                rp.setResource(storedResource);
            }
        }
        resourceRepository.save(storedResource);
        return new ResponseEntity("Resource updated successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a resource")
    @RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity delete(@PathVariable Long id){
        int result = resourceRepository.deleteIfHasNoChildById(id);
        if (result == 1)
            return new ResponseEntity("Resource deleted successfully", HttpStatus.OK);
        else return ResponseEntity.badRequest().body("Resource cannot be deleted");
    }
    
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}
