package kr.co.strato.migrationcore.core.resource.service;

import kr.co.strato.migrationcore.core.backup.model.BackupRequestDto;
import kr.co.strato.migrationcore.core.migration.model.MigrationRequestDto;
import kr.co.strato.migrationcore.core.restore.model.RestoreRequestDto;
import kr.co.strato.migrationcore.domain.resource.entity.MigrationResource;
import kr.co.strato.migrationcore.domain.resource.service.MigrationResourceDomainService;
import kr.co.strato.migrationcore.gloabal.api.exception.MigrationException;
import kr.co.strato.migrationcore.gloabal.common.model.MigrationDtoCommonSpec;
import kr.co.strato.migrationcore.gloabal.common.util.ResourceType;
import kr.co.strato.migrationcore.gloabal.common.util.WorkActionType;
import kr.co.strato.migrationcore.gloabal.common.util.WorkType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {
    @Autowired
    MigrationResourceDomainService migrationResourceDomainService;

    public <T extends MigrationDtoCommonSpec>  void setMigrationResource(T requestDto, Long workIdx){
        // WorkType 판별
        WorkType workType = getWorkType(requestDto);

        // DB 등록
        try  {
            registerIncludedNamespace(requestDto, workIdx, workType);
            registerIncludedResources(requestDto, workIdx, workType);
            registerExcludedNamespace(requestDto, workIdx, workType);
            registerExcludedResources(requestDto, workIdx, workType);
        } catch (Exception e) {
            throw new MigrationException("2001");
        }
    }

    private <T extends MigrationDtoCommonSpec> WorkType getWorkType(T requestDto) {
        WorkType workType = null;

        if (requestDto instanceof BackupRequestDto) {
            workType = WorkType.BACKUP;
        } else if (requestDto instanceof RestoreRequestDto) {
            workType = WorkType.RESTORE;
        } else if (requestDto instanceof MigrationRequestDto) {
            workType = WorkType.MIGRATION;
        }

        return workType;
    }

    private <T extends MigrationDtoCommonSpec> void registerIncludedNamespace(T requestDto, Long workIdx, WorkType workType) {
        if(requestDto.getIncludedNamespaces() != null) {
            requestDto.getIncludedNamespaces().forEach(namespace -> {
                MigrationResource migrationResource = new MigrationResource();
                migrationResource.setWorkType(workType);
                migrationResource.setWorkAction(WorkActionType.INCLUDE);
                migrationResource.setWorkIdx(workIdx);
                migrationResource.setResourceType(ResourceType.NAMESPACE);
                migrationResource.setNamespace(namespace);

                migrationResourceDomainService.register(migrationResource);
            });
        }
    }

    private <T extends MigrationDtoCommonSpec> void registerIncludedResources(T requestDto, Long workIdx, WorkType workType) {
        if(requestDto.getIncludedResources() != null) {
            requestDto.getIncludedResources().forEach(resource -> {
                MigrationResource migrationResource = new MigrationResource();
                migrationResource.setWorkType(workType);
                migrationResource.setWorkAction(WorkActionType.INCLUDE);
                migrationResource.setWorkIdx(workIdx);
                migrationResource.setResourceType(ResourceType.valueOf(resource));

                migrationResourceDomainService.register(migrationResource);
            });
        }
    }

    private <T extends MigrationDtoCommonSpec> void registerExcludedNamespace(T requestDto, Long workIdx, WorkType workType) {
        if(requestDto.getExcludedNamespaces() != null){
            requestDto.getExcludedNamespaces().forEach(namespace -> {
                MigrationResource migrationResource = new MigrationResource();
                migrationResource.setWorkType(workType);
                migrationResource.setWorkAction(WorkActionType.EXCLUDE);
                migrationResource.setWorkIdx(workIdx);
                migrationResource.setResourceType(ResourceType.NAMESPACE);
                migrationResource.setNamespace(namespace);

                migrationResourceDomainService.register(migrationResource);
            });
        }
    }

    private <T extends MigrationDtoCommonSpec> void registerExcludedResources(T requestDto, Long workIdx, WorkType workType) {
        if(requestDto.getExcludedResources() != null) {
            requestDto.getExcludedResources().forEach(resource -> {
                MigrationResource migrationResource = new MigrationResource();
                migrationResource.setWorkType(workType);
                migrationResource.setWorkAction(WorkActionType.EXCLUDE);
                migrationResource.setWorkIdx(workIdx);
//                migrationResource.setResourceType(ResourceType.valueOf(resource));

                migrationResourceDomainService.register(migrationResource);
            });
        }
    }
}
