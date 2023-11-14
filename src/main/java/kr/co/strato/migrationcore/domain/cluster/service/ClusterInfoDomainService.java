package kr.co.strato.migrationcore.domain.cluster.service;

import kr.co.strato.migrationcore.domain.cluster.entity.ClusterInfo;
import kr.co.strato.migrationcore.domain.cluster.repository.ClusterInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClusterInfoDomainService {
    @Autowired
    ClusterInfoRepository clusterInfoRepository;

    public ClusterInfo getClusterInfo(Long clusterIdx){
        return clusterInfoRepository.findByClusterIdx(clusterIdx);
    }
    public String getKubeConfigByClusterIdx(Long clusterIdx) {
        ClusterInfo clusterInfo = clusterInfoRepository.findByClusterIdx(clusterIdx);
        return clusterInfo.getKubeConfig();
    }
}
