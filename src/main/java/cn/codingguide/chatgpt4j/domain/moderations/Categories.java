package cn.codingguide.chatgpt4j.domain.moderations;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author itlemon
 * Created on 2023-04-28
 */
public class Categories implements Serializable {

    /**
     * 是否包含：表达、煽动或宣扬基于种族、性别、民族、宗教、国籍、性取向、残疾等仇恨的内容
     */
    private boolean hate;

    /**
     * 是否包含：仇恨内容、对弱势群体的胁迫、暴力或严重伤害
     */
    @SerializedName("hate/threatening")
    private boolean hateThreatening;

    /**
     * 是否包含：宣扬、鼓励或描绘自残行为（例如自杀、自我伤害）的内容
     */
    @SerializedName("self-harm")
    private boolean selfHarm;

    /**
     * 是否包含：旨在引起性兴奋的内容，例如对性活动的描述，或宣传性服务（不包括性教育和健康）的内容
     */
    private boolean sexual;

    /**
     * 是否包含：未满 18 周岁的个人的色情内容
     */
    @SerializedName("sexual/minors")
    private boolean sexualMinors;

    /**
     * 是否包含：宣扬或美化暴力、 歌颂他人遭受苦难或羞辱的内容
     */
    private boolean violence;

    /**
     * 是否包含：以极端血腥细节描绘死亡、暴力或严重身体伤害的暴力内容
     */
    @SerializedName("violence/graphic")
    private boolean violenceGraphic;

    public boolean isHate() {
        return hate;
    }

    public void setHate(boolean hate) {
        this.hate = hate;
    }

    public boolean isHateThreatening() {
        return hateThreatening;
    }

    public void setHateThreatening(boolean hateThreatening) {
        this.hateThreatening = hateThreatening;
    }

    public boolean isSelfHarm() {
        return selfHarm;
    }

    public void setSelfHarm(boolean selfHarm) {
        this.selfHarm = selfHarm;
    }

    public boolean isSexual() {
        return sexual;
    }

    public void setSexual(boolean sexual) {
        this.sexual = sexual;
    }

    public boolean isSexualMinors() {
        return sexualMinors;
    }

    public void setSexualMinors(boolean sexualMinors) {
        this.sexualMinors = sexualMinors;
    }

    public boolean isViolence() {
        return violence;
    }

    public void setViolence(boolean violence) {
        this.violence = violence;
    }

    public boolean isViolenceGraphic() {
        return violenceGraphic;
    }

    public void setViolenceGraphic(boolean violenceGraphic) {
        this.violenceGraphic = violenceGraphic;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "hate=" + hate +
                ", hateThreatening=" + hateThreatening +
                ", selfHarm=" + selfHarm +
                ", sexual=" + sexual +
                ", sexualMinors=" + sexualMinors +
                ", violence=" + violence +
                ", violenceGraphic=" + violenceGraphic +
                '}';
    }
}
