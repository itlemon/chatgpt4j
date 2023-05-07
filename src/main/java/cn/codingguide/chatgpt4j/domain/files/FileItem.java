package cn.codingguide.chatgpt4j.domain.files;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * @author itlemon
 * Created on 2023-04-24
 */
public class FileItem implements Serializable {

    /**
     * 文件ID，例如：file-ccdDZrC3iZVNiQVeEA6Z66wf
     */
    private String id;

    /**
     * 指示对象类型（例如，"file"）的字符串
     */
    private String object;

    /**
     * 字节数
     */
    private long bytes;

    /**
     * 创建时间戳，单位s
     */
    @SerializedName("created_at")
    private long createdAt;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 文件用途：官网默认值是fine-tune
     */
    private String purpose;

    /**
     * 上传状态，例如uploaded,processed
     */
    private String status;

    /**
     * 详细信息
     */
    @SerializedName("status_details")
    private Object statusDetails;

    /**
     * 是否已经删除
     */
    private boolean deleted;

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

    public long getBytes() {
        return bytes;
    }

    public void setBytes(long bytes) {
        this.bytes = bytes;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getStatusDetails() {
        return statusDetails;
    }

    public void setStatusDetails(Object statusDetails) {
        this.statusDetails = statusDetails;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "FileItem{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", bytes=" + bytes +
                ", createdAt=" + createdAt +
                ", filename='" + filename + '\'' +
                ", purpose='" + purpose + '\'' +
                ", status='" + status + '\'' +
                ", statusDetails=" + statusDetails +
                ", deleted=" + deleted +
                '}';
    }
}
