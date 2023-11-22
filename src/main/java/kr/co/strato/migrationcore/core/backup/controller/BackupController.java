package kr.co.strato.migrationcore.core.backup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.strato.migrationcore.core.backup.model.Backup;
import kr.co.strato.migrationcore.core.backup.model.BackupRequestDto;
import kr.co.strato.migrationcore.core.backup.model.BackupStatus;
import kr.co.strato.migrationcore.core.backup.service.BackupService;
import kr.co.strato.migrationcore.gloabal.api.model.ResponseMessage;
import kr.co.strato.migrationcore.gloabal.api.model.ResponseMessageProvider;
import kr.co.strato.migrationcore.gloabal.api.model.ResponseWrapper;
import kr.co.strato.migrationcore.gloabal.common.util.PhaseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Tag(name = "Backup", description = "Backup API")
@RequestMapping("/v1/backup")
class BackupController {
    @Autowired
    BackupService backupService;

    @GetMapping("")
    @Operation(summary="backup 상세 조회", description="backup 상세 조회")
    public ResponseWrapper<Backup> getBackupDetail(@RequestParam(value="name") String name, @RequestParam(value="clusterIdx") Long clusterIdx) {
        Backup result = backupService.getBackupDetail(clusterIdx, name);
        return new ResponseWrapper<>(result, ResponseMessageProvider.getResponseMessage("10001"));
    }

    @PostMapping("")
    @Operation(summary="backup 실행", description="backup 실행")
    public ResponseWrapper<Void> executeBackup(@RequestBody BackupRequestDto backupRequestDto) {
        backupService.execute(backupRequestDto);
        return new ResponseWrapper<>(ResponseMessageProvider.getResponseMessage("10001"));
    }

    @GetMapping("/status")
    @Operation(summary="backup 상태 조회", description="backup 상태 조회")
    public ResponseWrapper<String> checkBackupStatus(@RequestParam(value="name") String name, @RequestParam(value="clusterIdx") Long clusterIdx) {
        BackupStatus result = backupService.checkBackupStatus(clusterIdx, name);
        return new ResponseWrapper<>(result.getPhase(), ResponseMessageProvider.getResponseMessage("10001"));
    }

    @GetMapping("/list")
    @Operation(summary="backup 목록", description="backup 상세 정보 포함 목록")
    public ResponseWrapper<List<Backup>> getNamespaceList(@RequestParam(value="clusterIdx") Long clusterIdx){
        List<Backup> results = backupService.getBackupList(clusterIdx);
        return new ResponseWrapper<>(results, ResponseMessageProvider.getResponseMessage("10001"));
    }

    @GetMapping("/list/name")
    @Operation(summary="backup 이름 목록", description="backup 이름 목록")
    public ResponseWrapper<List<String>> getNamespaceNameList(@RequestParam(value="clusterIdx") Long clusterIdx){
        List<String> results = backupService.getBackupNameList(clusterIdx);
        return new ResponseWrapper<>(results, ResponseMessageProvider.getResponseMessage("10001"));
    }

    @GetMapping("/test")
    @Operation(summary="backup 이름 목록", description="backup 이름 목록")
    public ResponseWrapper<Map<String,Object>> getTest(){
        Map<String,Object> results = new HashMap<>();
        results.put("phase",PhaseType.COMPLETED);
        results.put("getValue",PhaseType.COMPLETED.getValue());
        results.put("getType",PhaseType.getType("Completed2"));


        return new ResponseWrapper<>(results, ResponseMessageProvider.getResponseMessage("10001"));
    }
}
