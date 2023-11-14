package kr.co.strato.migrationcore.core.kubeAdoptor.service;

import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.client.*;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import kr.co.strato.migrationcore.core.backup.model.Backup;
import kr.co.strato.migrationcore.core.restore.model.Restore;
import kr.co.strato.migrationcore.domain.cluster.service.ClusterInfoDomainService;
import kr.co.strato.migrationcore.gloabal.api.exception.MigrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class KubeAdaptorService<T extends CustomResource> {
    @Autowired
    ClusterInfoDomainService clusterInfoDomainService;

    private Config setKubeConfig(Long clusterIdx) {
        try {
            String content = clusterInfoDomainService.getKubeConfigByClusterIdx(clusterIdx);
            Config config = Config.fromKubeconfig(content);

            return config;
        } catch(Exception e){
            throw new MigrationException("1001");
        }
    }

    private Config setKubeConfigFromFile(Long clusterIdx) {
        try {
            File file = ResourceUtils.getFile("");
            Stream<String> kubeConfigContents = Files.lines(file.toPath());
            String content = kubeConfigContents.collect(Collectors.joining(System.lineSeparator()));
            Config config = Config.fromKubeconfig(content);
            kubeConfigContents.close();

            return config;
        } catch (Exception e) {

            throw new MigrationException("1001");
        }
    }

    public T applyCustomResource(Class<T> clazz, Long clusterIdx, T resource){
        Config config = setKubeConfig(clusterIdx);

        try (final KubernetesClient client = new KubernetesClientBuilder().withConfig(config).build()) {
            MixedOperation<T,KubernetesResourceList<T>, Resource<T>> customClient = client.resources(clazz);
            T result = customClient.inNamespace("velero").resource(resource).create();

            return result;
        } catch (Exception e) {

            throw new MigrationException("1002");
        }
    }

    // 상세
    public Backup getBackupDetail(Long clusterIdx, String resourceName) {
        Config config = setKubeConfig(clusterIdx);
        try (final KubernetesClient client = new KubernetesClientBuilder().withConfig(config).build()) {
            MixedOperation<Backup,KubernetesResourceList<Backup>, Resource<Backup>> customClient = client.resources(Backup.class);
            Backup backup = customClient.inNamespace("velero").withName(resourceName).get();

            return backup;
        } catch (Exception e) {

            throw new MigrationException("1003");
        }
    }

    public Restore getRestoreDetail(Long clusterIdx, String resourceName) {
        Config config = setKubeConfig(clusterIdx);

        try (final KubernetesClient client = new KubernetesClientBuilder().withConfig(config).build()) {
            MixedOperation<Restore,KubernetesResourceList<Restore>, Resource<Restore>> customClient = client.resources(Restore.class);
            Restore restore = customClient.inNamespace("velero").withName(resourceName).get();

            return restore;
        } catch (Exception e) {

            throw new MigrationException("1003");
        }
    }


    // 목록
    public List<Namespace> getNamespaceList(Long clusterIdx) {
        Config config = setKubeConfig(clusterIdx);

        try (final KubernetesClient client = new KubernetesClientBuilder().withConfig(config).build()) {
            List<Namespace>  namespaceList = client.namespaces().list().getItems();

            return namespaceList;
        } catch(Exception e) {

            throw new MigrationException("1004");
        }
    }

    public List<String> getNamespaceNameList(Long clusterIdx) {
        Config config = setKubeConfig(clusterIdx);

        try (final KubernetesClient client = new KubernetesClientBuilder().withConfig(config).build()) {
            List<Namespace> namespaceList = client.namespaces().list().getItems();
            List<String> nameList = new ArrayList();

            for(Namespace ns : namespaceList) {
                nameList.add(ns.getMetadata().getName());
            }

            return nameList;
        } catch(Exception e) {

            throw new MigrationException("1004");
        }
    }

    public List<Backup> getBackupList(Long clusterIdx) {
        Config config = setKubeConfig(clusterIdx);

        try (final KubernetesClient client = new KubernetesClientBuilder().withConfig(config).build()) {
            MixedOperation<Backup,KubernetesResourceList<Backup>, Resource<Backup>> customClient = client.resources(Backup.class);
            List<Backup> backupList = customClient.inNamespace("velero").list().getItems();

            return backupList;
        } catch (Exception e) {

            throw new MigrationException("1005");
        }
    }

    public List<String> getBackupNameList(Long clusterIdx) {
        Config config = setKubeConfig(clusterIdx);

        try (final KubernetesClient client = new KubernetesClientBuilder().withConfig(config).build()) {
            MixedOperation<Backup,KubernetesResourceList<Backup>, Resource<Backup>> customClient = client.resources(Backup.class);
            List<Backup> backupList = customClient.inNamespace("velero").list().getItems();
            List<String> nameList = new ArrayList();

            for(Backup backup : backupList) {
                nameList.add(backup.getMetadata().getName());
            }

            return nameList;
        } catch (Exception e) {

            throw new MigrationException("1005");
        }
    }

    public List<Restore> getRestoreList(Long clusterIdx) {
        Config config = setKubeConfig(clusterIdx);

        try (final KubernetesClient client = new KubernetesClientBuilder().withConfig(config).build()) {
            MixedOperation<Restore,KubernetesResourceList<Restore>, Resource<Restore>> customClient = client.resources(Restore.class);
            List<Restore> restoreList = customClient.inNamespace("velero").list().getItems();

            return restoreList;
        } catch (Exception e) {

            throw new MigrationException("1005");
        }
    }

    public List<String> getRestoreNameList(Long clusterIdx) {
        Config config = setKubeConfig(clusterIdx);

        try (final KubernetesClient client = new KubernetesClientBuilder().withConfig(config).build()) {
            MixedOperation<Restore,KubernetesResourceList<Restore>, Resource<Restore>> customClient = client.resources(Restore.class);

            List<Restore> restoreList = customClient.inNamespace("velero").list().getItems();
            List<String> nameList = new ArrayList();

            for(Restore restore : restoreList) {
                nameList.add(restore.getMetadata().getName());
            }

            return nameList;
        } catch (Exception e) {

            throw new MigrationException("1005");
        }
    }
}
