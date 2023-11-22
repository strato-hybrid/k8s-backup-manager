package kr.co.strato.migrationcore.domain.migration.service;

import kr.co.strato.migrationcore.domain.migration.entity.MigrationHistory;
import kr.co.strato.migrationcore.domain.migration.respository.MigrationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MigrationHistoryDomainService {
    @Autowired
    MigrationHistoryRepository migrationHistoryRepository;

    @Transactional
    public Long register(MigrationHistory migrationHistory) {
        migrationHistoryRepository.save(migrationHistory);
        return migrationHistory.getMigrationHistoryIdx();
    }

    @Transactional
    public Long updateMigrationHistory(MigrationHistory migrationHistory) {
        migrationHistoryRepository.save(migrationHistory);
        return migrationHistory.getMigrationHistoryIdx();
    }
}
