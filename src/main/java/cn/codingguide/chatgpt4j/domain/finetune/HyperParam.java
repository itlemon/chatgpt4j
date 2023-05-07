package cn.codingguide.chatgpt4j.domain.finetune;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author itlemon
 * Created on 2023-04-28
 */
public class HyperParam implements Serializable {

    @SerializedName("batch_size")
    private Integer batchSize;

    @SerializedName("learning_rate_multiplier")
    private Double learningRateMultiplier;

    @SerializedName("n_epochs")
    private Integer nEpochs;

    @SerializedName("prompt_loss_weight")
    private Double promptLossWeight;

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public Double getLearningRateMultiplier() {
        return learningRateMultiplier;
    }

    public void setLearningRateMultiplier(Double learningRateMultiplier) {
        this.learningRateMultiplier = learningRateMultiplier;
    }

    public Integer getnEpochs() {
        return nEpochs;
    }

    public void setnEpochs(Integer nEpochs) {
        this.nEpochs = nEpochs;
    }

    public Double getPromptLossWeight() {
        return promptLossWeight;
    }

    public void setPromptLossWeight(Double promptLossWeight) {
        this.promptLossWeight = promptLossWeight;
    }

    @Override
    public String toString() {
        return "HyperParam{" +
                "batchSize=" + batchSize +
                ", learningRateMultiplier=" + learningRateMultiplier +
                ", nEpochs=" + nEpochs +
                ", promptLossWeight=" + promptLossWeight +
                '}';
    }
}
