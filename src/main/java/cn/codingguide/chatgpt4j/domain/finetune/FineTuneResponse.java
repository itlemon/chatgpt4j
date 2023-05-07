package cn.codingguide.chatgpt4j.domain.finetune;

import cn.codingguide.chatgpt4j.domain.files.FileItem;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author itlemon
 * Created on 2023-04-28
 */
public class FineTuneResponse implements Serializable {

    private String id;
    private String object;
    private String model;

    @SerializedName("created_at")
    private long createdAt;
    private List<FineTuneEvent> events;

    @SerializedName("fine_tuned_model")
    private String fineTunedModel;

    @SerializedName("hyperparams")
    private HyperParam hyperParams;

    @SerializedName("organization_id")
    private String organizationId;

    @SerializedName("result_files")
    private List<FileItem> resultFiles;

    private String status;

    @SerializedName("validation_files")
    private List<FileItem> validationFiles;

    @SerializedName("training_files")
    private List<FileItem> trainingFiles;

    @SerializedName("updated_at")
    private long updatedAt;

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public List<FineTuneEvent> getEvents() {
        return events;
    }

    public void setEvents(List<FineTuneEvent> events) {
        this.events = events;
    }

    public String getFineTunedModel() {
        return fineTunedModel;
    }

    public void setFineTunedModel(String fineTunedModel) {
        this.fineTunedModel = fineTunedModel;
    }

    public HyperParam getHyperParams() {
        return hyperParams;
    }

    public void setHyperParams(HyperParam hyperParams) {
        this.hyperParams = hyperParams;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public List<FileItem> getResultFiles() {
        return resultFiles;
    }

    public void setResultFiles(List<FileItem> resultFiles) {
        this.resultFiles = resultFiles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FileItem> getValidationFiles() {
        return validationFiles;
    }

    public void setValidationFiles(List<FileItem> validationFiles) {
        this.validationFiles = validationFiles;
    }

    public List<FileItem> getTrainingFiles() {
        return trainingFiles;
    }

    public void setTrainingFiles(List<FileItem> trainingFiles) {
        this.trainingFiles = trainingFiles;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "FineTuneResponse{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", model='" + model + '\'' +
                ", createdAt=" + createdAt +
                ", events=" + events +
                ", fineTunedModel='" + fineTunedModel + '\'' +
                ", hyperParams=" + hyperParams +
                ", organizationId='" + organizationId + '\'' +
                ", resultFiles=" + resultFiles +
                ", status='" + status + '\'' +
                ", validationFiles=" + validationFiles +
                ", trainingFiles=" + trainingFiles +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
