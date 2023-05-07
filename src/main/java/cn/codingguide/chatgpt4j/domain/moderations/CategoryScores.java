package cn.codingguide.chatgpt4j.domain.moderations;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author itlemon
 * Created on 2023-04-28
 */
public class CategoryScores implements Serializable {

    private BigDecimal hate;

    @SerializedName("hate/threatening")
    private BigDecimal hateThreatening;

    @SerializedName("self-harm")
    private BigDecimal selfHarm;

    private BigDecimal sexual;

    @SerializedName("sexual/minors")
    private BigDecimal sexualMinors;

    private BigDecimal violence;

    @SerializedName("violence/graphic")
    private BigDecimal violenceGraphic;

    public BigDecimal getHate() {
        return hate;
    }

    public void setHate(BigDecimal hate) {
        this.hate = hate;
    }

    public BigDecimal getHateThreatening() {
        return hateThreatening;
    }

    public void setHateThreatening(BigDecimal hateThreatening) {
        this.hateThreatening = hateThreatening;
    }

    public BigDecimal getSelfHarm() {
        return selfHarm;
    }

    public void setSelfHarm(BigDecimal selfHarm) {
        this.selfHarm = selfHarm;
    }

    public BigDecimal getSexual() {
        return sexual;
    }

    public void setSexual(BigDecimal sexual) {
        this.sexual = sexual;
    }

    public BigDecimal getSexualMinors() {
        return sexualMinors;
    }

    public void setSexualMinors(BigDecimal sexualMinors) {
        this.sexualMinors = sexualMinors;
    }

    public BigDecimal getViolence() {
        return violence;
    }

    public void setViolence(BigDecimal violence) {
        this.violence = violence;
    }

    public BigDecimal getViolenceGraphic() {
        return violenceGraphic;
    }

    public void setViolenceGraphic(BigDecimal violenceGraphic) {
        this.violenceGraphic = violenceGraphic;
    }

    @Override
    public String toString() {
        return "CategoryScores{" +
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
