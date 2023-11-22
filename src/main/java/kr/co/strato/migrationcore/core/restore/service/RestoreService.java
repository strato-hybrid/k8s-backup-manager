package kr.co.strato.migrationcore.core.restore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import kr.co.strato.migrationcore.core.backup.model.Backup;
import kr.co.strato.migrationcore.core.resource.service.ResourceService;
import kr.co.strato.migrationcore.core.restore.model.Restore;
import kr.co.strato.migrationcore.core.restore.model.RestoreRequestDto;
import kr.co.strato.migrationcore.core.restore.model.RestoreSpec;
import kr.co.strato.migrationcore.core.restore.model.RestoreStatus;
import kr.co.strato.migrationcore.core.kubeAdoptor.service.KubeAdaptorService;
import kr.co.strato.migrationcore.domain.backup.entity.MigrationBackup;
import kr.co.strato.migrationcore.domain.backup.service.MigrationBackupDomainService;
import kr.co.strato.migrationcore.domain.cluster.entity.ClusterInfo;
import kr.co.strato.migrationcore.domain.cluster.service.ClusterInfoDomainService;
import kr.co.strato.migrationcore.domain.resource.entity.MigrationResource;
import kr.co.strato.migrationcore.domain.resource.service.MigrationResourceDomainService;
import kr.co.strato.migrationcore.domain.restore.entity.MigrationRestore;
import kr.co.strato.migrationcore.domain.restore.service.MigrationRestoreDomainService;
import kr.co.strato.migrationcore.gloabal.api.exception.MigrationException;
import kr.co.strato.migrationcore.gloabal.common.util.PhaseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RestoreService {

    @Autowired
    MigrationRestoreDomainService migrationRestoreDomainService;

    @Autowired
    MigrationBackupDomainService migrationBackupDomainService;

    @Autowired
    ClusterInfoDomainService clusterInfoDomainService;

    @Autowired
    KubeAdaptorService<Restore> kubeAdaptorService;

    @Autowired
    ResourceService resourceService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Long execute(RestoreRequestDto restoreRequestDto) {
        MigrationRestore migrationRestore = new MigrationRestore();
        RestoreStatus restoreStatus = new RestoreStatus();

        try {
            // insert
            setMigrationRestore(restoreRequestDto,migrationRestore);
            Long migrationRestoreIdx = migrationRestoreDomainService.register(migrationRestore);

            resourceService.setMigrationResource(restoreRequestDto, migrationRestoreIdx);

            Restore restoreResource = createRestoreResource(restoreRequestDto);
            applyRestoreResource(restoreRequestDto.getRestoreClusterIdx(), restoreResource);
            restoreStatus = waitFinishRestore(restoreRequestDto.getRestoreClusterIdx(), restoreRequestDto.getName());

            log.info("[RestoreService] success finish waiting restore : {}", restoreStatus.toString());


            // update status
            if(restoreStatus.getPhase() != null) {
                String phase = restoreStatus.getPhase();
                migrationRestore.setPhase(PhaseType.getType(phase));
                log.info("[RestoreService] set phase to migrationRestore : {}", migrationRestore.toString());
            } else {

            }

            migrationRestoreDomainService.updateMigrationRestore(migrationRestore);

            return migrationRestore.getRestoreIdx();
        } catch(MigrationException e){
            throw e;
        } catch(Exception e) {
            throw new MigrationException("5001");
        }
    }
    private void setMigrationRestore(RestoreRequestDto restoreRequestDto, MigrationRestore migrationRestore) {
        ClusterInfo clusterInfo = clusterInfoDomainService.getClusterInfo(restoreRequestDto.getRestoreClusterIdx());
        MigrationBackup migrationBackup = migrationBackupDomainService.getMigrationBackupFindByName(restoreRequestDto.getBackupName());

        migrationRestore.setName(restoreRequestDto.getName());
        migrationRestore.setDescription(restoreRequestDto.getDescription());
        migrationRestore.setBackupIdx(migrationBackup.getBackupIdx());
        migrationRestore.setBackup_name(restoreRequestDto.getBackupName());
        migrationRestore.setRestoreClusterIdx(restoreRequestDto.getRestoreClusterIdx());
        migrationRestore.setRestoreClusterName(clusterInfo.getName());
        migrationRestore.setRestoreClusterVersion(clusterInfo.getClusterVersion());
        migrationRestore.setBackupClusterIdx(migrationBackup.getBackupClusterIdx());
        migrationRestore.setBackupClusterName(migrationBackup.getBackupClusterName());
        migrationRestore.setBackupClusterVersion(migrationBackup.getBackupClusterVersion());

        Map<String,Object> specMap = MAPPER.convertValue(restoreRequestDto,Map.class);

        // 불필요한 항목 제거
        specMap.remove("name");
        specMap.remove("clusterIdx");
        specMap.remove("description");

        migrationRestore.setSpec(specMap);
    }

    private Restore createRestoreResource(RestoreRequestDto restoreRequestDto) {
        Restore restore = new Restore();

        // Metadata
        ObjectMeta meta = new ObjectMeta();
        meta.setName(restoreRequestDto.getName());
        meta.setNamespace("velero");

        restore.setMetadata(meta);


        //spec
        RestoreSpec restoreSpec = new RestoreSpec();
        restoreSpec.setBackupName(restoreRequestDto.getBackupName());
        restoreSpec.setExcludedResources(restoreRequestDto.getExcludedResources());
        restoreSpec.setIncludedNamespaces(restoreRequestDto.getIncludedNamespaces());

        restore.setSpec(restoreSpec);

        log.info("[RestoreService] created restore Resource Object : {}",restore.toString());
        return restore;
    }

    public void applyRestoreResource(Long clusterIdx, Restore restoreResource) {
        // context, resourceYaml 전달
        Restore result = kubeAdaptorService.applyCustomResource(Restore.class, clusterIdx, restoreResource);

        log.info("[RestoreService] applied Restore Resource : {}",result.toString());
    }

    public RestoreStatus checkRestoreStatus(Long clusterIdx, String restoreName) {
        Restore restore = kubeAdaptorService.getRestoreDetail(clusterIdx, restoreName);
        return restore.getStatus();
    }

    public Restore getRestoreDetail(Long clusterIdx, String restoreName) {
        return kubeAdaptorService.getRestoreDetail(clusterIdx, restoreName);
    }

    public List<Restore> getRestoreList(Long clusterIdx) {
        return kubeAdaptorService.getRestoreList(clusterIdx);
    }

    public List<String> getRestoreNameList(Long clusterIdx) {
        return kubeAdaptorService.getRestoreNameList(clusterIdx);
    }

    public RestoreStatus waitFinishRestore(Long clusterIdx, String restoreName) {
        long startTime = System.currentTimeMillis();
        long maxWaitTime = 60 * 1000 * 2; // 2min
        long intervalTime = 500;

        try {
            while((System.currentTimeMillis() - startTime) < maxWaitTime) {
                Thread.sleep(intervalTime);
                RestoreStatus restoreStatus = checkRestoreStatus(clusterIdx, restoreName);

                if (restoreStatus == null) continue;
                if(!restoreStatus.getPhase().equals(PhaseType.NEW.getValue())
                        && !restoreStatus.getPhase().equals(PhaseType.IN_PROGRESS.getValue())) {
                    if(!restoreStatus.getPhase().equals(PhaseType.COMPLETED.getValue())) {
                        // "Restore Not Completed"
                        throw new MigrationException("5002");
                    }

                    return restoreStatus;
                }
                log.info("[RestoreService] Wait Finish Restore : {}",restoreStatus.toString());
            }

            throw new MigrationException("5003");
        } catch(MigrationException e) {
            throw e;
        } catch(Exception e) {

            throw new MigrationException("5003");
        }
    }
}
