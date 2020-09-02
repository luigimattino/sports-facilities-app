/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.repository;

import it.lmattino.sportsfacilitiesapp.configuration.RepositoryConfiguration;
import it.lmattino.sportsfacilitiesapp.model.DirModel;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
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
public class DirModelRepositoryTest {

    @Autowired
    private DirModelRepository dirModelRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    public void contextLoads() {
        assertThat(entityManager).isNotNull();
        assertThat(dirModelRepository).isNotNull();
    }

    @Test
    public void testInteract1() {
        DirModel principale = new DirModel();
        principale.setName("PRINCIPALE");

        DirModel secondaria = new DirModel();
        secondaria.setName("SECONDARIA");

        DirModel terziaria = new DirModel();
        terziaria.setName("TERZIARIA");

        principale.addChildren(secondaria);
        secondaria.addChildren(terziaria);

        dirModelRepository.save(principale);

        assertNotNull(principale.getId());

        ArrayList<DirModel> results = dirModelRepository.myFindById(principale.getId()).get();
        
        /*
        List<Object[]> results = this.entityManager.createNativeQuery(""
                + "WITH RECURSIVE nodes AS (\n"
                + "         SELECT dm.id, dm.name, dm.parent_id \n"
                + "             FROM dir_model dm \n"
                + "             WHERE dm.id = :id \n"
                + "        UNION \n"
                + "         SELECT dme.id, dme.name, dme.parent_id \n"
                + "             FROM dir_model dme \n"
                + "             JOIN nodes n ON (n.id = dme.parent_id) \n"
                + ") \n"
                + "SELECT * \n"
                + " FROM nodes;"
                + "", "DirectoriesResult").setParameter("id", 1).getResultList();
         */
        
        assertNotNull(results);
        
        DirModel fetched1DirModel = dirModelRepository.my2FindById(principale.getId()).orElse(null);
        
        assertNotNull(fetched1DirModel);
        
        DirModel fetched2DirModel = dirModelRepository.my2FindById( ((DirModel) results.get(1)).getId() ).orElse(null);
        
        assertNotNull(fetched2DirModel);
        
        DirModel fetched3DirModel = dirModelRepository.my2FindById( ((DirModel) results.get(2)).getId() ).orElse(null);
        
        assertNotNull(fetched3DirModel);
    }

}
