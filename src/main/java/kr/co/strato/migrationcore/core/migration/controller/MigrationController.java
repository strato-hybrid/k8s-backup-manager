package kr.co.strato.migrationcore.core.migration.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.strato.migrationcore.domain.resource.entity.MigrationResourceType;
import kr.co.strato.migrationcore.gloabal.api.model.PageRequest;
import kr.co.strato.migrationcore.gloabal.api.model.ResponseMessage;
import kr.co.strato.migrationcore.gloabal.api.model.ResponseMessageProvider;
import kr.co.strato.migrationcore.gloabal.api.model.ResponseWrapper;
import kr.co.strato.migrationcore.core.migration.model.MigrationRequestDto;
import kr.co.strato.migrationcore.core.migration.service.MigrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "Migration", description = "Migration API")
@RequestMapping("/v1/migration")
public class MigrationController {

    @Autowired
    MigrationService migrationService;

    @PostMapping("")
    @Operation(summary="migration 실행", description="migration 실행")
    public ResponseWrapper<Void> executeMigration(@RequestBody MigrationRequestDto migrationRequestDto) {
        migrationService.execute(migrationRequestDto);
        return new ResponseWrapper<>(ResponseMessageProvider.getResponseMessage("10001"));
    }

    @GetMapping("/resource/type/list")
    @Operation(summary="migration 리소스 타입 목록", description = "migration 리소스 타입 목록")
    public ResponseWrapper<List<MigrationResourceType>> getMigrationTypeList(PageRequest pageable) {
        List<MigrationResourceType> result = migrationService.getMigrationResourceTypeList();
        return new ResponseWrapper<>(result, ResponseMessageProvider.getResponseMessage("10001"));
    }
}
