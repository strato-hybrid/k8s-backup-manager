package kr.co.strato.migrationcore.domain.resource.repository;


import kr.co.strato.migrationcore.domain.resource.entity.MigrationResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MigrationResourceRepository extends JpaRepository<MigrationResource,Long> {
}
