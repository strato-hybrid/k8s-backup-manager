package kr.co.strato.migrationcore.gloabal.common.util;

public enum ResourceType {
    ALL("all","*"),
    NAMESPACE("namespace","namespaces"),
    DEPLOYMENT("deployment","deployments"),
    DAEMONSET("daemonset","daemonsets"),
    STATEFULSET("statefulset","statefulsets"),
    REPLICASET("replicaset","replicasets"),
    POD("pod","pods"),
    PERSISTENT_VOLUME("persistent_volume","persistentvolumes"),
    PERSISTENT_VOLUME_CLAIM("Persistent_volume_claim","persistentvolumeclaims");

    String name;
    String value;

    ResourceType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static ResourceType getTypeByName(String name){
        return ResourceType.valueOf(name.toUpperCase());
    }

    public static ResourceType getTypeByValue(String value){
        return ResourceType.valueOf(value.toUpperCase());
    }
}
