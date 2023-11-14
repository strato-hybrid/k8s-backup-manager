package kr.co.strato.migrationcore.domain.resource.repository;


import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.strato.migrationcore.domain.resource.entity.MigrationResourceType;
import kr.co.strato.migrationcore.domain.resource.entity.QMigrationResourceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CustomMigrationResourceTypeRepositoryImpl implements CustomMigrationResourceTypeRepository {

    JPAQueryFactory jpaQueryFactory;

    public CustomMigrationResourceTypeRepositoryImpl(JPAQueryFactory jpaQueryFactory){ this.jpaQueryFactory = jpaQueryFactory; }

    @Override
    public List<MigrationResourceType> getMigrationResourceTypeList() {
        QMigrationResourceType qMigrationResourceType = QMigrationResourceType.migrationResourceType;
        QueryResults<MigrationResourceType> results = jpaQueryFactory
                .select(qMigrationResourceType)
                .from(qMigrationResourceType)
                .fetchResults();

        return results.getResults();
    }
}
