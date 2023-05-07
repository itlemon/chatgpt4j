package cn.codingguide.chatgpt4j.domain.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * ChatGPT模型权限
 *
 * @author itlemon
 * Created on 2023-04-04
 */
public class Permission implements Serializable {

    private String id;

    private String object;

    private long created;

    @SerializedName("allow_create_engine")
    private boolean allowCreateEngine;

    @SerializedName("allow_sampling")
    private boolean allowSampling;

    @SerializedName("allow_logprobs")
    private boolean allowLogProbs;

    @SerializedName("allow_search_indices")
    private boolean allowSearchIndices;

    @SerializedName("allow_view")
    private boolean allowView;

    @SerializedName("allow_fine_tuning")
    private boolean allowFineTuning;

    private String organization;

    /**
     * 未知类型字段，先使用Object来代替
     */
    private Object group;

    @SerializedName("is_blocking")
    private boolean isBlocking;

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

    public boolean isAllowCreateEngine() {
        return allowCreateEngine;
    }

    public void setAllowCreateEngine(boolean allowCreateEngine) {
        this.allowCreateEngine = allowCreateEngine;
    }

    public boolean isAllowSampling() {
        return allowSampling;
    }

    public void setAllowSampling(boolean allowSampling) {
        this.allowSampling = allowSampling;
    }

    public boolean isAllowLogProbs() {
        return allowLogProbs;
    }

    public void setAllowLogProbs(boolean allowLogProbs) {
        this.allowLogProbs = allowLogProbs;
    }

    public boolean isAllowSearchIndices() {
        return allowSearchIndices;
    }

    public void setAllowSearchIndices(boolean allowSearchIndices) {
        this.allowSearchIndices = allowSearchIndices;
    }

    public boolean isAllowView() {
        return allowView;
    }

    public void setAllowView(boolean allowView) {
        this.allowView = allowView;
    }

    public boolean isAllowFineTuning() {
        return allowFineTuning;
    }

    public void setAllowFineTuning(boolean allowFineTuning) {
        this.allowFineTuning = allowFineTuning;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Object getGroup() {
        return group;
    }

    public void setGroup(Object group) {
        this.group = group;
    }

    public boolean isBlocking() {
        return isBlocking;
    }

    public void setBlocking(boolean blocking) {
        isBlocking = blocking;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", created=" + created +
                ", allowCreateEngine=" + allowCreateEngine +
                ", allowSampling=" + allowSampling +
                ", allowLogProbs=" + allowLogProbs +
                ", allowSearchIndices=" + allowSearchIndices +
                ", allowView=" + allowView +
                ", allowFineTuning=" + allowFineTuning +
                ", organization='" + organization + '\'' +
                ", group=" + group +
                ", isBlocking=" + isBlocking +
                '}';
    }
}
