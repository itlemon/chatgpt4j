package cn.codingguide.chatgpt4j.domain.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * ChatGPT模型
 *
 * @author itlemon
 * Created on 2023-04-04
 */
public class Model implements Serializable {

    /**
     * 模型ID
     */
    private String id;

    /**
     * 模型类型，通常为model
     */
    private String object;

    /**
     * 时间戳，单位s，注意需要设置时区，映射到国内的时间
     */
    private long created;

    /**
     * owner
     */
    @SerializedName("owned_by")
    private String ownedBy;

    /**
     * 权限列表
     */
    private List<Permission> permission;

    /**
     * root
     */
    private String root;

    /**
     * 未知类型字段，先使用Object来代替
     */
    private Object parent;

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

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    public List<Permission> getPermission() {
        return permission;
    }

    public void setPermission(List<Permission> permission) {
        this.permission = permission;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", created=" + created +
                ", ownedBy='" + ownedBy + '\'' +
                ", permission=" + permission +
                ", root='" + root + '\'' +
                ", parent=" + parent +
                '}';
    }
}
