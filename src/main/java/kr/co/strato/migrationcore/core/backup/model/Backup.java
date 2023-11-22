package kr.co.strato.migrationcore.core.backup.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

@Group("velero.io")
@Version("v1")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Backup extends CustomResource<BackupSpec, BackupStatus> implements Namespaced { }
