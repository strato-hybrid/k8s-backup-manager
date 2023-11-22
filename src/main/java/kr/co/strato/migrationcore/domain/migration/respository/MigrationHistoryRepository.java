package kr.co.strato.migrationcore.domain.migration.respository;


import kr.co.strato.migrationcore.domain.migration.entity.MigrationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MigrationHistoryRepository extends JpaRepository<MigrationHistory,Long> {
}
