package cn.codingguide.chatgpt4j.domain.engines;

import java.io.Serializable;

/**
 * @author itlemon
 * Created on 2023-04-29
 */
public class EngineItem implements Serializable {

    private String id;
    private String object;
    private String owner;
    private boolean ready;
    private Object permissions;
    private long created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public Object getPermissions() {
        return permissions;
    }

    public void setPermissions(Object permissions) {
        this.permissions = permissions;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "EngineItem{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", owner='" + owner + '\'' +
                ", ready=" + ready +
                ", permissions=" + permissions +
                ", created=" + created +
                '}';
    }
}
