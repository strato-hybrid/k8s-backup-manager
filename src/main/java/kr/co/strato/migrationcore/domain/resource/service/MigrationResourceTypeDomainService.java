package kr.co.strato.migrationcore.domain.resource.service;


import kr.co.strato.migrationcore.domain.resource.entity.MigrationResourceType;
import kr.co.strato.migrationcore.domain.resource.repository.MigrationResourceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MigrationResourceTypeDomainService {
    @Autowired
    MigrationResourceTypeRepository migrationResourceTypeRepository;

    public List<MigrationResourceType> getMigrationResourceTypeList() {
        return migrationResourceTypeRepository.getMigrationResourceTypeList();
    }
}
