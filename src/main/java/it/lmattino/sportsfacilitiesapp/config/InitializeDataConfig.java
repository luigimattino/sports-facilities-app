/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.config;

import it.lmattino.sportsfacilitiesapp.model.Address;
import it.lmattino.sportsfacilitiesapp.model.Resource;
import it.lmattino.sportsfacilitiesapp.model.ResourceProperty;
import it.lmattino.sportsfacilitiesapp.model.ResourcePropertyType;
import it.lmattino.sportsfacilitiesapp.model.ResourceType;
import it.lmattino.sportsfacilitiesapp.model.Structure;
import it.lmattino.sportsfacilitiesapp.repository.ResourceRepository;
import it.lmattino.sportsfacilitiesapp.repository.ResourceTypeRepository;
import it.lmattino.sportsfacilitiesapp.repository.StructureRepository;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 *
 * @author Mattinò
 */
@Slf4j
@Configuration
public class InitializeDataConfig {

    @Bean
    CommandLineRunner initStructures(StructureRepository repository) {
        return args -> {

            Structure structure = new Structure();
            structure.setDescription("BigGym FITNESS & WELLNESS - Sala pesi - Corsi Fitness - WELLNESS & SPA");
            structure.setName("BigGym FITNESS & WELLNESS, Fit It a.r.l.");

            Address address = new Address();
            address.setStreet("Via Ada Negri");
            address.setStreetNo(2);
            address.setLocale("Abbiategrasso");
            address.setCity("Milano");
            address.setZipCode(19999);
            address.setCountry("Italia");

            address.setStructure(structure);
            structure.setAddress(address);

            repository.save(structure);
        };
    }

    @Bean
    CommandLineRunner initResourceTypes(ResourceTypeRepository repository) {
        return args -> {
            ResourceType salaPesi = ResourceType.builder().label("Sala Pesi").propertyTypes(new ArrayList<>()).build();
            salaPesi.addPropertyType(ResourcePropertyType.builder().label("MTQUADRI").build());
            salaPesi.addPropertyType(ResourcePropertyType.builder().label("POSTAZIONI").build());
            salaPesi.addPropertyType(ResourcePropertyType.builder().label("NMAX_PERSONE").build());
            repository.save(salaPesi);

            ResourceType solarium = ResourceType.builder().label("Solarium").propertyTypes(new ArrayList<>()).build();
            solarium.addPropertyType(ResourcePropertyType.builder().label("DIMENSIONI").build());
            solarium.addPropertyType(ResourcePropertyType.builder().label("TEMPO_TRATTAMENTO").build());
            solarium.addPropertyType(ResourcePropertyType.builder().label("NPERSONE").build());
            repository.save(solarium);

            ResourceType spa = ResourceType.builder().label("SPA").propertyTypes(new ArrayList<>()).build();
            spa.addPropertyType(ResourcePropertyType.builder().label("MTQUADRI").build());
            spa.addPropertyType(ResourcePropertyType.builder().label("MIN_ORE_PRENOTABILI").build());
            spa.addPropertyType(ResourcePropertyType.builder().label("MAX_ORE_PRENOTABILI").build());
            spa.addPropertyType(ResourcePropertyType.builder().label("NMAX_PERSONE").build());
            repository.save(spa);

            ResourceType vasca = ResourceType.builder().label("Vasca Idromassaggio").propertyTypes(new ArrayList<>()).build();
            vasca.addPropertyType(ResourcePropertyType.builder().label("DIMENSIONE").build());
            vasca.addPropertyType(ResourcePropertyType.builder().label("N_ORE_PRENOTABILI").build());
            vasca.addPropertyType(ResourcePropertyType.builder().label("NMAX_PERSONE").build());
            repository.save(vasca);

            ResourceType bagnoTurco = ResourceType.builder().label("Bagno Turco").propertyTypes(new ArrayList<>()).build();
            bagnoTurco.addPropertyType(ResourcePropertyType.builder().label("DIMENSIONE").build());
            bagnoTurco.addPropertyType(ResourcePropertyType.builder().label("N_ORE_PRENOTABILI").build());
            bagnoTurco.addPropertyType(ResourcePropertyType.builder().label("NMAX_PERSONE").build());
            repository.save(bagnoTurco);

            ResourceType sauna = ResourceType.builder().label("Sauna").propertyTypes(new ArrayList<>()).build();
            sauna.addPropertyType(ResourcePropertyType.builder().label("DIMENSIONE").build());
            sauna.addPropertyType(ResourcePropertyType.builder().label("MIN_ORE_PRENOTABILI").build());
            sauna.addPropertyType(ResourcePropertyType.builder().label("MAX_ORE_PRENOTABILI").build());
            sauna.addPropertyType(ResourcePropertyType.builder().label("NMAX_PERSONE").build());
            repository.save(sauna);

        };
    }

    @Bean
    @DependsOn({"initResourceTypes"})
    CommandLineRunner initResources(ResourceTypeRepository typeRepository, ResourceRepository repository) {
        return args -> {
            ResourceType sala_pesi_type = typeRepository.findByLabel("Sala Pesi");
            Resource salaPesi = new Resource();
            salaPesi.setName("SALA PESI");
            salaPesi.setDescription("Un'area di 900 mq open space è a vostra "
                    + "disposizione con pesi e macchinari d'avanguardia per "
                    + "esercitare ogni singola parte del corpo. La sala è divisa"
                    + " in varie zone funzionali: area addominali, area cardio, "
                    + "area pesistica, zona funzionale e dedicata ai macchinari. "
                    + "Tutto il meglio per la vostra forma fisica.");
            salaPesi.setType(sala_pesi_type);

            for (ResourcePropertyType rpt : sala_pesi_type.getPropertyTypes()) {
                if (StringUtils.equals("MTQUADRI", rpt.getLabel())) {
                    salaPesi.addProperty(ResourceProperty.builder().type(rpt).value("900").build());
                } else if (StringUtils.equals("POSTAZIONI", rpt.getLabel())) {
                    salaPesi.addProperty(ResourceProperty.builder().type(rpt).value("40").build());
                } else if (StringUtils.equals("NMAX_PERSONE", rpt.getLabel())) {
                    salaPesi.addProperty(ResourceProperty.builder().type(rpt).value("50").build());
                }
            }
            
            repository.save(salaPesi);

            ResourceType spa_type = typeRepository.findByLabel("SPA");
            Resource spa = new Resource();
            spa.setName("AREA WELLNESS & SPA");
            spa.setDescription("Una nuova area pensata appositamente per "
                    + "migliorare il vostro aspetto ed il vostro stile di "
                    + "vita sostenendo l’equilibrio tra mente e corpo. Una "
                    + "moderna spa –  con sauna, bagno turco, doccia emozionale "
                    + "e idromassaggio – il solarium, esperti specialisti ed il "
                    + "nostro team di preparate beauty trainers sono a vostra "
                    + "disposizione per garantirvi il meglio in tutta sicurezza.");
            spa.setType(spa_type);

            for (ResourcePropertyType rpt : spa_type.getPropertyTypes()) {
                if (StringUtils.equals("MTQUADRI", rpt.getLabel())) {
                    spa.addProperty(ResourceProperty.builder().type(rpt).value("600").build());
                } else if (StringUtils.equals("MIN_ORE_PRENOTABILI", rpt.getLabel())) {
                    spa.addProperty(ResourceProperty.builder().type(rpt).value("1h").build());
                } else if (StringUtils.equals("MAX_ORE_PRENOTABILI", rpt.getLabel())) {
                    spa.addProperty(ResourceProperty.builder().type(rpt).value("2h").build());
                } else if (StringUtils.equals("NMAX_PERSONE", rpt.getLabel())) {
                    spa.addProperty(ResourceProperty.builder().type(rpt).value("5").build());
                }
            }
            
            repository.save(spa);
            
        };
    }

}
