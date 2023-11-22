package kr.co.strato.migrationcore.domain.restore.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import kr.co.strato.migrationcore.gloabal.common.util.PhaseType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Getter
@Setter
@TypeDef(name="json", typeClass = JsonType.class)
@Table(name = "k8s_migration_restore")
public class MigrationRestore implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="restore_idx", updatable=false)
    private Long restoreIdx;

    @Column(name="name", updatable=false)
    private String name;

    @Column(name="backup_name", updatable=false)
    private String backup_name;

    @Column(name="backup_idx", updatable=false)
    private Long backupIdx;

    @Column(name="description", updatable=false)
    private String description;

    @Column(name="restore_cluster_idx", updatable=false)
    private Long restoreClusterIdx;

    @Column(name="restore_cluster_name", updatable=false)
    private String restoreClusterName;

    @Column(name="restore_cluster_version", updatable=false)
    private String restoreClusterVersion;

    @Column(name="backup_cluster_idx", updatable=false)
    private Long backupClusterIdx;

    @Column(name="backup_cluster_name", updatable=false)
    private String backupClusterName;

    @Column(name="backup_cluster_version", updatable=false)
    private String backupClusterVersion;

    @Type(type="json")
    @Column(name="spec", columnDefinition = "longtext", updatable = false)
    private Map<String,Object> spec;

    @Column(name="phase")
    @Enumerated(EnumType.STRING)
    private PhaseType phase;

    @CreationTimestamp
    @Column(name="created_at", updatable=false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at", insertable=false)
    private LocalDateTime updatedAt;
}
