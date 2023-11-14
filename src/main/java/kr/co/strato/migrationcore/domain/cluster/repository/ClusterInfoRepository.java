package kr.co.strato.migrationcore.domain.cluster.repository;


import kr.co.strato.migrationcore.domain.cluster.entity.ClusterInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterInfoRepository extends JpaRepository<ClusterInfo,Long> {
    ClusterInfo findByClusterIdx(Long clusterIdx);
}
