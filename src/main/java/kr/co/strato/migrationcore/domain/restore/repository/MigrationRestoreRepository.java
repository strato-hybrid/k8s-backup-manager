package kr.co.strato.migrationcore.domain.restore.repository;

import kr.co.strato.migrationcore.domain.restore.entity.MigrationRestore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MigrationRestoreRepository extends JpaRepository<MigrationRestore,Long> {
}
