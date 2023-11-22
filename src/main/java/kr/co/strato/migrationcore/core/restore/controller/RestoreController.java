package kr.co.strato.migrationcore.core.restore.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.strato.migrationcore.core.restore.model.Restore;
import kr.co.strato.migrationcore.core.restore.model.RestoreRequestDto;
import kr.co.strato.migrationcore.core.restore.model.RestoreStatus;
import kr.co.strato.migrationcore.gloabal.api.model.ResponseMessage;
import kr.co.strato.migrationcore.gloabal.api.model.ResponseMessageProvider;
import kr.co.strato.migrationcore.gloabal.api.model.ResponseWrapper;
import kr.co.strato.migrationcore.core.restore.service.RestoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "Restore", description = "Restore API")
@RequestMapping("/v1/restore")
public class RestoreController {

    @Autowired
    RestoreService restoreService;

    @GetMapping("")
    @Operation(summary="restore 상세 조회", description="restore 상세 조회")
    public ResponseWrapper<Restore> getRestoreDetail(@RequestParam(value="name") String name, @RequestParam(value="clusterIdx") Long clusterIdx) {
        Restore result = restoreService.getRestoreDetail(clusterIdx, name);
        return new ResponseWrapper<>(result, ResponseMessageProvider.getResponseMessage("10001"));
    }

    @PostMapping("")
    @Operation(summary="restore 실행", description="restore 실행")
    public ResponseWrapper<Void> executeBackup(@RequestBody RestoreRequestDto restoreRequestDto) {
        restoreService.execute(restoreRequestDto);
        return new ResponseWrapper<>(ResponseMessageProvider.getResponseMessage("10001"));
    }

    @GetMapping("/status")
    @Operation(summary="restore 상태 조회", description="restore 상태 조회")
    public ResponseWrapper<RestoreStatus> checkRestoreStatus(@RequestParam(value="name") String name, @RequestParam(value="clusterIdx") Long clusterIdx) {
        RestoreStatus result = restoreService.checkRestoreStatus(clusterIdx, name);
        return new ResponseWrapper<>(result, ResponseMessageProvider.getResponseMessage("10001"));
    }

    @GetMapping("/list")
    @Operation(summary="restore 목록", description="restore 상세 정보 포함 목록")
    public ResponseWrapper<List<Restore>> getNamespaceList(@RequestParam(value="clusterIdx") Long clusterIdx){
        List<Restore> results = restoreService.getRestoreList(clusterIdx);
        return new ResponseWrapper<>(results, ResponseMessageProvider.getResponseMessage("10001"));
    }

    @GetMapping("/list/name")
    @Operation(summary="restore 이름 목록", description="backup 이름 목록")
    public ResponseWrapper<List<String>> getNamespaceNameList(@RequestParam(value="clusterIdx") Long clusterIdx){
        List<String> results = restoreService.getRestoreNameList(clusterIdx);
        return new ResponseWrapper<>(results, ResponseMessageProvider.getResponseMessage("10001"));
    }
}
