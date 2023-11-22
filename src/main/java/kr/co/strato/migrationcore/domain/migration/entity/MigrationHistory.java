package kr.co.strato.migrationcore.domain.migration.entity;

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
@Table(name = "k8s_migration_history")
public class MigrationHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="migration_history_idx", updatable=false)
    private Long migrationHistoryIdx;

    @Column(name="name", updatable=false)
    private String name;

    @Column(name="description", updatable=false)
    private String description;

    @Column(name="backup_idx")
    private Long backupIdx;

    @Column(name="restore_idx")
    private Long restoreIdx;

    @Type(type="json")
    @Column(name="spec", columnDefinition = "longtext", updatable = false)
    private Map<String,Object> spec;

    @CreationTimestamp
    @Column(name="created_at", updatable=false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at", insertable=false)
    private LocalDateTime updatedAt;
}
