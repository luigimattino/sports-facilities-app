/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import it.lmattino.sportsfacilitiesapp.configuration.RepositoryConfiguration;
import it.lmattino.sportsfacilitiesapp.model.Resource;
import it.lmattino.sportsfacilitiesapp.model.ResourceProperty;
import it.lmattino.sportsfacilitiesapp.model.ResourcePropertyType;
import it.lmattino.sportsfacilitiesapp.model.ResourceType;
import java.beans.FeatureDescriptor;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Mattinò
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RepositoryConfiguration.class})
public class ResourceRepositoryTest {

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;
    @Autowired
    private ResourcePropertyTypeRepository resourcePropertyTypeRepository;
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private ResourcePropertyRepository resourcePropertyRepository;

    @Test
    public void interactWithResources() throws JsonProcessingException {
        ResourceType dojo = new ResourceType();
        dojo.setLabel("DOJO");
        dojo.addPropertyType(ResourcePropertyType.builder().label("LUNGHEZZA").build());
        dojo.addPropertyType(ResourcePropertyType.builder().label("LARGHEZZA").build());
        dojo.addPropertyType(ResourcePropertyType.builder().label("MTQUADRI").build());
        dojo.addPropertyType(ResourcePropertyType.builder().label("NUMMAXPERS").build());

        resourceTypeRepository.save(dojo);
        assertNotNull(dojo.getId()); //not null after save

        ResourceType tatami = new ResourceType();
        tatami.setLabel("TATAMI");
        tatami.addPropertyType(ResourcePropertyType.builder().label("LUNGHEZZA").build());
        tatami.addPropertyType(ResourcePropertyType.builder().label("LARGHEZZA").build());
        tatami.addPropertyType(ResourcePropertyType.builder().label("MTQUADRI").build());
        tatami.addPropertyType(ResourcePropertyType.builder().label("NUMMAXPERS").build());

        resourceTypeRepository.save(tatami);
        assertNotNull(tatami.getId()); //not null after save

        ResourceType tournament = new ResourceType();
        tournament.setLabel("TOURNAMENT");
        tournament.addPropertyType(ResourcePropertyType.builder().label("DATAINIZIO").build());
        tournament.addPropertyType(ResourcePropertyType.builder().label("DATAFINE").build());
        tournament.addPropertyType(ResourcePropertyType.builder().label("PARTECIPANTI").build());

        resourceTypeRepository.save(tournament);
        assertNotNull(tournament.getId()); //not null after save

        ResourceType corso2 = new ResourceType();
        corso2.setLabel("CORSO");
        corso2.addPropertyType(ResourcePropertyType.builder().label("DATAINIZIO").build());
        corso2.addPropertyType(ResourcePropertyType.builder().label("DATAFINE").build());
        corso2.addPropertyType(ResourcePropertyType.builder().label("NUMMAXPERS").build());

        resourceTypeRepository.save(corso2);
        assertNotNull(corso2.getId()); //not null after save

        Resource rDojo = new Resource();
        rDojo.setName("DOJO TRIESTE A1");
        rDojo.setDescription("Dojo[1] (道場 dōjō?) è un termine giapponese che "
                + "indica il luogo dove si svolgono gli allenamenti alle arti "
                + "marziali. Etimologicamente significa \"luogo (jō) dove si "
                + "segue la via (dō)\". In origine il termine, ereditato dalla "
                + "tradizione buddhista cinese, indicava il luogo in cui il "
                + "Buddha ottenne il risveglio e per estensione i luoghi "
                + "deputati alla pratica religiosa nei templi buddhisti. Il "
                + "termine venne poi adottato nel mondo militare e nella pratica"
                + " del bujutsu, che durante il periodo Edo fu influenzata dalla"
                + " tradizione Zen, perciò è a tutt'oggi diffuso nell'ambiente "
                + "delle arti marziali.");
        rDojo.setType(dojo);
        for (ResourcePropertyType rpt : dojo.getPropertyTypes()) {
            if (StringUtils.equals("MTQUADRI", rpt.getLabel())) {
                rDojo.addProperty(ResourceProperty.builder().type(rpt).value("500").build());
            } else if (StringUtils.equals("NUMMAXPERS", rpt.getLabel())) {
                rDojo.addProperty(ResourceProperty.builder().type(rpt).value("50").build());
            }
        }

        Resource rTatami = new Resource();
        rTatami.setName("TATAMI PRIMO");
        rTatami.setDescription("Il tatami (畳?) è una tradizionale pavimentazione"
                + " giapponese composta da pannelli rettangolari modulari, "
                + "costruiti con un telaio di legno o altri materiali rivestito "
                + "da paglia intrecciata e pressata.\n"
                + "Nella nomenclatura giapponese, la stanza con pavimento di "
                + "questo tipo viene chiamata washitsu "
                + "(和室? \"stanza giapponese\"), in contrapposizione alla "
                + "yōshitsu (洋室? \"stanza occidentale\") che presenta un "
                + "qualunque altro tipo di pavimento.");
        rTatami.setType(tatami);
        for (ResourcePropertyType rpt : tatami.getPropertyTypes()) {
            if (StringUtils.equals("MTQUADRI", rpt.getLabel())) {
                rTatami.addProperty(ResourceProperty.builder().type(rpt).value("250").build());
            } else if (StringUtils.equals("NUMMAXPERS", rpt.getLabel())) {
                rTatami.addProperty(ResourceProperty.builder().type(rpt).value("25").build());
            }
        }
        rDojo.addChildren(rTatami);

        resourceRepository.save(rDojo);
        assertNotNull(rDojo.getId()); //not null after save

        //fetch from DB
        ArrayList<Object[]> results = resourceRepository.myFindById(rDojo.getId()).orElse(null);
        //should not be null
        assertNotNull(results);

        Resource fetchedRDojo = resourceRepository.findByIdWithChildsAndProperties(rDojo.getId()).orElse(null);

        for (ResourceProperty rp : fetchedRDojo.getProperties()) {
            if (StringUtils.equals("MTQUADRI", rp.getType().getLabel())) {
                assertEquals("500", rp.getValue());
            } else if (StringUtils.equals("NUMMAXPERS", rp.getType().getLabel())) {
                assertEquals("50", rp.getValue());
            }
        }

        assertEquals(fetchedRDojo.getChilds().size(), 1);

        Long rTatamiId = fetchedRDojo.getChilds().iterator().next().getId();

        Resource fetchedRTatami = resourceRepository.findByIdWithChildsAndProperties(rTatamiId).orElse(null);
        //should not be null
        assertNotNull(fetchedRTatami);

        Resource rTournament = new Resource();
        rTournament.setName("Campionato Europeo di MMA IMMAF");
        rTournament.setDescription("Mixed martial arts (MMA) sometimes referred "
                + "to as cage fighting,[1] is a full-contact combat sport based "
                + "on striking, grappling and ground fighting, made up from "
                + "various combat sports and martial arts from around the world."
                + "The first documented use of the term mixed martial arts was "
                + "in a review of UFC 1 by television critic Howard Rosenberg in "
                + "1993. The question of who actually coined the term is subject "
                + "to debate");
        rTournament.setType(tournament);
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (ResourcePropertyType rpt : tournament.getPropertyTypes()) {
            if (StringUtils.equals("DATAINIZIO", rpt.getLabel())) {
                rTournament.addProperty(ResourceProperty.builder().type(rpt).value(LocalDate.of(2020, 10, 4).format(pattern)).build());
            } else if (StringUtils.equals("DATAFINE", rpt.getLabel())) {
                rTournament.addProperty(ResourceProperty.builder().type(rpt).value(LocalDate.of(2020, 12, 13).format(pattern)).build());
            } else if (StringUtils.equals("PARTECIPANTI", rpt.getLabel())) {
                rTournament.addProperty(ResourceProperty.builder().type(rpt).value("12").build());
            }
        }

        Resource fetchedRTournament = resourceRepository.saveWithParent(rTournament, fetchedRTatami).orElse(null);

        assertEquals(fetchedRTatami.getChilds().size(), 1);

        //should not be null
        assertNotNull(fetchedRTournament);

        Long rTournamentId = fetchedRTatami.getChilds().iterator().next().getId();

        Resource myRTournament = resourceRepository.findByIdWithChildsAndProperties(rTournamentId).orElse(null);
        ObjectMapper mapper = new ObjectMapper();
        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("type",
                SimpleBeanPropertyFilter.filterOutAllExcept("id", "label"));
        mapper.setFilterProvider(filterProvider);
        String result = mapper.writeValueAsString(myRTournament);

        assertThat(result, containsString("type"));
        assertThat(result, containsString("name"));

        result = StringUtils.replace(result, "2020-10-04", "2020-10-03");
        result = StringUtils.replace(result, "Campionato Europeo di MMA IMMAF", "Campionato di MMA IMMAF Europeo");

        Map rTournamentMap = mapper.readValue(result, HashMap.class);

        Map parent = new HashMap();
        parent.put("id", myRTournament.getParent().getId());
        rTournamentMap.put("parent", parent);

        result = mapper.writeValueAsString(rTournamentMap);

        Resource deserializedRTournament = mapper.readValue(result, Resource.class);

        assertEquals(deserializedRTournament.getName(), "Campionato di MMA IMMAF Europeo");

        BeanUtils.copyProperties(deserializedRTournament, myRTournament, getNullPropertyNames(deserializedRTournament));
        if ( myRTournament.getProperties() != null && !myRTournament.getProperties().isEmpty() ) {
            for (ResourceProperty rp : myRTournament.getProperties()) {
                rp.setResource(myRTournament);
            }
        }
        resourceRepository.save(myRTournament);

        assertNotNull(deserializedRTournament.getParent().getId());
        
        int deleteresult = resourceRepository.deleteIfHasNoChildById(myRTournament.getId());
    
        assertEquals(deleteresult, 1);
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

}
