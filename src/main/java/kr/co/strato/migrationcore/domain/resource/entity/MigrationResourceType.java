package kr.co.strato.migrationcore.domain.resource.entity;

import kr.co.strato.migrationcore.gloabal.common.util.ResourceType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "k8s_migration_resource_type")
public class MigrationResourceType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="resource_type_idx")
    private Long resource_type_idx;

    @Column(name = "resource_type")
    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resourceType", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @NotFound(action = NotFoundAction.IGNORE)
    private List<MigrationResource> migrationResources = new ArrayList<>();

    @Column(name="name")
    private String name;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "k8s_migration_resource_type", joinColumns = @JoinColumn(name = "resource_type"))
    @Column(name="related_resources")
    private List<String> relatedResources = new ArrayList<>();

    @Column(name="order")
    private int order;

    @Column(name="description")
    private String description;

    @Column(name="use_yn")
    private Character useYn;
}
