/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Mattinò
 */
@SqlResultSetMapping(
        name = "DirectoriesResult",
        entities = {
            @EntityResult(
                    entityClass = DirModel.class
            )
        }
)
@NamedNativeQuery(
        name = "DirModel.myFindById",
        resultSetMapping = "DirectoriesResult",
        query = "WITH RECURSIVE nodes AS (\n"
        + "         SELECT dm.id , dm.name , dm.parent_id \n"
        + "             FROM dir_model dm \n"
        + "             WHERE dm.id = :id \n"
        + "        UNION \n"
        + "         SELECT dme.id , dme.name , dme.parent_id \n"
        + "             FROM dir_model dme \n"
        + "             JOIN nodes n ON (n.id = dme.parent_id) \n"
        + ") \n"
        + "SELECT id, name, parent_id \n"
        + " FROM nodes;"
)
@Entity
@Data
@EqualsAndHashCode
@Table(name = "dir_model")
public class DirModel {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    @EqualsAndHashCode.Exclude
    private DirModel parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<DirModel> childs  = new HashSet<>();
    
    public void addChildren(DirModel child) {
        child.setParent(this);
        childs.add(child);
    }
    
    public void removeChildren(DirModel child) {
        child.setParent(null);
        childs.remove(child);
    }
}
