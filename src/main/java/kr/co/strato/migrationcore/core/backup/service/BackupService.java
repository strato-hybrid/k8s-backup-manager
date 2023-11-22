package kr.co.strato.migrationcore.core.backup.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fabric8.kubernetes.api.model.LabelSelector;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.client.KubernetesClientException;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import kr.co.strato.migrationcore.core.backup.model.Backup;
import kr.co.strato.migrationcore.core.backup.model.BackupRequestDto;
import kr.co.strato.migrationcore.core.backup.model.BackupSpec;
import kr.co.strato.migrationcore.core.backup.model.BackupStatus;
import kr.co.strato.migrationcore.core.resource.service.ResourceService;
import kr.co.strato.migrationcore.domain.backup.entity.MigrationBackup;
import kr.co.strato.migrationcore.domain.backup.service.MigrationBackupDomainService;
import kr.co.strato.migrationcore.domain.cluster.entity.ClusterInfo;
import kr.co.strato.migrationcore.domain.cluster.service.ClusterInfoDomainService;
import kr.co.strato.migrationcore.domain.resource.entity.MigrationResource;
import kr.co.strato.migrationcore.domain.resource.service.MigrationResourceDomainService;
import kr.co.strato.migrationcore.gloabal.api.exception.MigrationException;
import kr.co.strato.migrationcore.gloabal.common.util.PhaseType;
import kr.co.strato.migrationcore.core.kubeAdoptor.service.KubeAdaptorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BackupService {
    @Autowired
    MigrationBackupDomainService migrationBackupDomainService;

    @Autowired
    ClusterInfoDomainService clusterInfoDomainService;

    @Autowired
    KubeAdaptorService<Backup> kubeAdaptorService;

    @Autowired
    ResourceService resourceService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Long execute(BackupRequestDto backupRequestDto) {
        try {
            MigrationBackup migrationBackup = new MigrationBackup();

            // insert
            setMigrationBackup(backupRequestDto,migrationBackup);
            Long migrationBackupIdx = migrationBackupDomainService.register(migrationBackup);
            resourceService.setMigrationResource(backupRequestDto, migrationBackupIdx);

            Backup backup = createBackupResource(backupRequestDto);
            applyBackupResource(backupRequestDto.getBackupClusterIdx(), backup);
            BackupStatus backupStatus = waitFinishBackup(backupRequestDto.getBackupClusterIdx() , backupRequestDto.getName());

            log.info("[BackupService] success finish waiting backup : {}", backupStatus.toString());

            // update status
            if(backupStatus.getPhase() != null) {
                String phase = backupStatus.getPhase();
                migrationBackup.setPhase(PhaseType.getType(phase));
                log.info("[BackupService] set phase to migrationBackup : {}", migrationBackup.toString());
            } else {

            }

            migrationBackupDomainService.updateMigrationBackup(migrationBackup);

            return migrationBackup.getBackupIdx();
        } catch(MigrationException e) {
            throw e;
        } catch(Exception e) {
            throw new MigrationException("4001");
        }
    }

    private void setMigrationBackup(BackupRequestDto backupRequestDto, MigrationBackup migrationBackup){
        ClusterInfo clusterInfo = clusterInfoDomainService.getClusterInfo(backupRequestDto.getBackupClusterIdx());

        migrationBackup.setName(backupRequestDto.getName());
        migrationBackup.setDescription(backupRequestDto.getDescription());
        migrationBackup.setBackupClusterIdx(backupRequestDto.getBackupClusterIdx());
        migrationBackup.setBackupClusterName(clusterInfo.getName());
        migrationBackup.setBackupClusterVersion(clusterInfo.getClusterVersion());
        migrationBackup.setPhase(PhaseType.START);

        // Map 변환
        Map<String,Object> specMap = MAPPER.convertValue(backupRequestDto,Map.class);

        // 불필요한 항목 제거
        specMap.remove("name");
        specMap.remove("clusterIdx");
        specMap.remove("description");

        migrationBackup.setSpec(specMap);
    }



    private Backup createBackupResource(BackupRequestDto backupRequestDto) {
        Backup backup = new Backup();

        // Metadata
        ObjectMeta meta = new ObjectMeta();
        meta.setName(backupRequestDto.getName());
        meta.setNamespace("velero");

        backup.setMetadata(meta);


        // Spec
        BackupSpec backupSpec = new BackupSpec();
        backupSpec.setIncludedNamespaces(backupRequestDto.getIncludedNamespaces());

        backup.setSpec(backupSpec);


        log.info("[BackupService] created backup Resource Object : {}",backup.toString());
        return backup;
    }

    public void applyBackupResource(Long clusterIdx, Backup backupResource) {
        // context, resourceYaml 전달
        Backup result = kubeAdaptorService.applyCustomResource(Backup.class, clusterIdx, backupResource);

        log.info("[BackupService] applied Backup Resource : {}",result.toString());
    }
    public BackupStatus checkBackupStatus(Long clusterIdx, String backupName) {
        Backup backup = kubeAdaptorService.getBackupDetail(clusterIdx, backupName);
        return backup.getStatus();
    }

    public Backup getBackupDetail(Long clusterIdx, String backupName) {
        return kubeAdaptorService.getBackupDetail(clusterIdx, backupName);
    }

    public List<Backup> getBackupList(Long clusterIdx) {
        return kubeAdaptorService.getBackupList(clusterIdx);
    }

    public List<String> getBackupNameList(Long clusterIdx) {
        return kubeAdaptorService.getBackupNameList(clusterIdx);
    }

    public BackupStatus waitFinishBackup(Long clusterIdx, String backupName) {
        long startTime = System.currentTimeMillis();
        long maxWaitTime = 60 * 1000 * 2; // 2min
        long intervalTime = 500;

        try {
            while((System.currentTimeMillis() - startTime) < maxWaitTime) {
                Thread.sleep(intervalTime);
                BackupStatus backupStatus = checkBackupStatus(clusterIdx, backupName);

                if (backupStatus == null) continue;
                if(!backupStatus.getPhase().equals(PhaseType.NEW.getValue())
                        && !backupStatus.getPhase().equals(PhaseType.IN_PROGRESS.getValue())) {
                    if(!backupStatus.getPhase().equals(PhaseType.COMPLETED.getValue())) {
                        // "Backup Not Completed"
                        throw new MigrationException("4002");
                    }

                    return backupStatus;
                }
                log.info("[BackupService] Wait Finish Backup : {}",backupStatus.toString());
            }

            throw new MigrationException("4003");
        } catch (MigrationException e) {
            throw e;
        } catch (Exception e) {
            throw new MigrationException("4003");
        }
    }

    public Backup waitFindBackup(Long clusterIdx, String backupName) {
        long startTime = System.currentTimeMillis();
        long maxWaitTime = 60 * 1000 * 2; // 2min
        long intervalTime = 500;

        try {
            while((System.currentTimeMillis() - startTime) < maxWaitTime) {
                Thread.sleep(intervalTime);
                Backup backup = kubeAdaptorService.getBackupDetail(clusterIdx, backupName);

                if(backup != null) {
                    return backup;
                }
                log.info("[BackupService] Wait Find Backup : {}",backup);
            }

            throw new MigrationException("4004");
        } catch(MigrationException e) {
            throw e;
        } catch(Exception e) {
            throw new MigrationException("4004");
        }
    }
}
