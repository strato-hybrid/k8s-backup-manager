package kr.co.strato.migrationcore.domain.backup.service;

import kr.co.strato.migrationcore.core.backup.model.Backup;
import kr.co.strato.migrationcore.domain.backup.entity.MigrationBackup;
import kr.co.strato.migrationcore.domain.backup.repository.MigrationBackupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MigrationBackupDomainService {
    @Autowired
    MigrationBackupRepository migrationBackupRepository;

    @Transactional
    public Long register(MigrationBackup migrationBackup) {
        migrationBackupRepository.save(migrationBackup);
        return migrationBackup.getBackupIdx();
    }

    @Transactional
    public Long updateMigrationBackup(MigrationBackup migrationBackup) {
        migrationBackupRepository.save(migrationBackup);
        return migrationBackup.getBackupIdx();
    }

    @Transactional
    public MigrationBackup getMigrationBackupFindByName(String name) {
        return migrationBackupRepository.findByName(name);
    }
}
