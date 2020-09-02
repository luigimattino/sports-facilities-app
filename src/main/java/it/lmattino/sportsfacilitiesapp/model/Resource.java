/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author Mattinò
 */
@SqlResultSetMapping(
        name = "resources",
        entities = {
            @EntityResult(
                    entityClass = Resource.class
            )
        }
)
@NamedNativeQuery(
        name = "Resource.myFindById",
        resultSetMapping = "resources",
        query = "WITH RECURSIVE nodes\n"
        + "     AS (\n"
        + "	SELECT \n"
        + "		r.id, \n"
        + "		r.name, \n"
        + "		r.description,\n"
        + "		r.parent_id,\n"
        + "		r.type_id\n"
        + "	FROM resource r\n"
        + "	WHERE r.id = :id\n"
        + "		UNION\n"
        + "	SELECT\n"
        + "		r1.id, \n"
        + "		r1.name, \n"
        + "		r1.description,\n"
        + "		r1.parent_id,\n"
        + "		r1.type_id\n"
        + "	FROM resource r1\n"
        + "     JOIN nodes n ON (n.id = r1.parent_id) \n"
        + ")\n"
        + "SELECT id,\n"
        + "	name,\n"
        + "	description,\n"
        + "	parent_id,\n"
        + "	type_id\n"
        + "     FROM nodes;"
)
@Entity
@Data
@EqualsAndHashCode
@Table(name = "resource")
@NoArgsConstructor
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id")
    @EqualsAndHashCode.Exclude
    @JsonFilter("type")
    private ResourceType type;

    @OneToMany(
            mappedBy = "resource",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @EqualsAndHashCode.Exclude
    private List<ResourceProperty> properties = new ArrayList<>();

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    private Set<Resource> childs = new HashSet<>();

    @ManyToOne@JoinColumn(name = "parent_id")
    @JsonBackReference@EqualsAndHashCode.Exclude
    private Resource parent;

    public void addProperty(ResourceProperty property) {
        properties.add(property);
        property.setResource(this);
    }

    public void removeProperty(ResourceProperty property) {
        properties.remove(property);
        property.setResource(null);
    }

    public void addChildren(Resource child) {
        childs.add(child);
        child.setParent(this);
    }

    public void removeChildren(Resource child) {
        childs.remove(child);
        child.setParent(null);
    }
}
