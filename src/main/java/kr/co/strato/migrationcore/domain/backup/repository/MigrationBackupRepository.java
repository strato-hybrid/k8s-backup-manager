package kr.co.strato.migrationcore.domain.backup.repository;


import kr.co.strato.migrationcore.domain.backup.entity.MigrationBackup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MigrationBackupRepository extends JpaRepository<MigrationBackup,Long> {
    @Transactional
    MigrationBackup findByName(String name);
}
