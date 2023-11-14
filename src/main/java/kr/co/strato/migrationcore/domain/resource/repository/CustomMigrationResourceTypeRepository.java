package kr.co.strato.migrationcore.domain.resource.repository;


import kr.co.strato.migrationcore.domain.resource.entity.MigrationResourceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomMigrationResourceTypeRepository {
    public List<MigrationResourceType> getMigrationResourceTypeList();
}
