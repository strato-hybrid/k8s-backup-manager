package kr.co.strato.migrationcore.domain.restore.service;

import kr.co.strato.migrationcore.domain.backup.entity.MigrationBackup;
import kr.co.strato.migrationcore.domain.restore.entity.MigrationRestore;
import kr.co.strato.migrationcore.domain.restore.repository.MigrationRestoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MigrationRestoreDomainService {

    @Autowired
    MigrationRestoreRepository migrationRestoreRepository;

    @Transactional
    public Long register(MigrationRestore migrationRestore) {
        migrationRestoreRepository.save(migrationRestore);
        return migrationRestore.getRestoreIdx();
    }

    @Transactional
    public Long updateMigrationRestore(MigrationRestore migrationRestore) {
        migrationRestoreRepository.save(migrationRestore);
        return migrationRestore.getRestoreIdx();
    }
}
