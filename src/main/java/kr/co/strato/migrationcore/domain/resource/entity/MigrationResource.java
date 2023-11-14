package kr.co.strato.migrationcore.domain.resource.entity;

import kr.co.strato.migrationcore.gloabal.common.util.ResourceType;
import kr.co.strato.migrationcore.gloabal.common.util.WorkActionType;
import kr.co.strato.migrationcore.gloabal.common.util.WorkType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "k8s_migration_resource")
public class MigrationResource implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="resource_idx", updatable=false)
    private Long resourceIdx;

    @Column(name="work_type", updatable = false)
    @Enumerated(EnumType.STRING)
    private WorkType workType;

    @Column(name="work_idx", updatable=false)
    private Long workIdx;

    @Column(name="work_action", updatable=false)
    @Enumerated(EnumType.STRING)
    private WorkActionType workAction;

    @Column(name="resource_type", updatable=false)
    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;

    @Column(name="namespace",updatable=false)
    private String namespace;
}
