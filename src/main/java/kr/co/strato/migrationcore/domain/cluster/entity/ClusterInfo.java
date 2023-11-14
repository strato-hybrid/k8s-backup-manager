package kr.co.strato.migrationcore.domain.cluster.entity;

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
@Table(name = "k8s_cluster_info")
public class ClusterInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cluster_idx", updatable=false)
    private Long clusterIdx;

    @Column(name="name", updatable=false)
    private String name;

    @Column(name="description", updatable=false)
    private String description;

    @Column(name="cluster_version", updatable=false)
    private String clusterVersion;

    @Column(name="kube_config", updatable=false)
    private String kubeConfig;

    @Column(name="use_yn", updatable=true)
    private Character useYn;
}
