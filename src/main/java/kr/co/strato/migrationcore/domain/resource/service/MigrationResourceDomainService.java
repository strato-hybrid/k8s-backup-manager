package kr.co.strato.migrationcore.domain.resource.service;

import kr.co.strato.migrationcore.domain.resource.entity.MigrationResource;
import kr.co.strato.migrationcore.domain.resource.repository.MigrationResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MigrationResourceDomainService {
    @Autowired
    MigrationResourceRepository migrationResourceRepository;

    @Transactional
    public Long register(MigrationResource migrationResource) {
        migrationResourceRepository.save(migrationResource);
        return migrationResource.getResourceIdx();
    }
}
