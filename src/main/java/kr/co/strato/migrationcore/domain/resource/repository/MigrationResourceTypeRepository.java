package kr.co.strato.migrationcore.domain.resource.repository;


import kr.co.strato.migrationcore.domain.resource.entity.MigrationResourceType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MigrationResourceTypeRepository extends JpaRepository<MigrationResourceType,Long>, CustomMigrationResourceTypeRepository {
}
