package kr.co.strato.migrationcore.core.resource.controller;

import io.fabric8.kubernetes.api.model.Namespace;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.strato.migrationcore.gloabal.api.model.ResponseMessage;
import kr.co.strato.migrationcore.gloabal.api.model.ResponseMessageProvider;
import kr.co.strato.migrationcore.gloabal.api.model.ResponseWrapper;
import kr.co.strato.migrationcore.core.kubeAdoptor.service.KubeAdaptorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "Resource", description = "Kubernetes Resource API")
@RequestMapping("/v1/resource")
public class ResourceController {
    @Autowired
    KubeAdaptorService kubeAdaptorService;


    @GetMapping("/namespace/list")
    @Operation(summary="namespace 목록", description="namespace 상세 정보 포함 목록")
    public ResponseWrapper<List<Namespace>> getNamespaceList(@RequestParam(value="clusterIdx") Long clusterIdx){
        List<Namespace> results = kubeAdaptorService.getNamespaceList(clusterIdx);
        return new ResponseWrapper<>(results, ResponseMessageProvider.getResponseMessage("10001"));
    }

    @GetMapping("/namespace/list/name")
    @Operation(summary="namespace 이름 목록", description="namespace 이름 목록")
    public ResponseWrapper<List<String>> getNamespaceNameList(@RequestParam(value="clusterIdx") Long clusterIdx){
        List<String> results = kubeAdaptorService.getNamespaceNameList(clusterIdx);
        return new ResponseWrapper<>(results, ResponseMessageProvider.getResponseMessage("10001"));
    }
}
