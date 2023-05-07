package cn.codingguide.chatgpt4j.domain.finetune;

import java.io.Serializable;
import java.util.Arrays;

import cn.codingguide.chatgpt4j.constant.FineTuneModel;
import org.jetbrains.annotations.NotNull;

import com.google.gson.annotations.SerializedName;

import cn.codingguide.chatgpt4j.constant.ModelSelector;

/**
 * 创建一个从给定数据集微调指定模型的作业。
 *
 * @author itlemon
 * Created on 2023-04-24
 */
public class FineTuneRequest implements Serializable {

    private final transient Builder builder;

    @SerializedName("training_file")
    private final String trainingFile;

    @SerializedName("validation_file")
    private final String validationFile;

    private final String model;

    @SerializedName("n_epochs")
    private final Integer nEpochs;

    @SerializedName("batch_size")
    private final Integer batchSize;

    @SerializedName("learning_rate_multiplier")
    private final Double learningRateMultiplier;

    @SerializedName("prompt_loss_weight")
    private final Double promptLossWeight;

    @SerializedName("compute_classification_metrics")
    private final boolean computeClassificationMetrics;

    @SerializedName("classification_n_classes")
    private final Integer classificationNClasses;

    @SerializedName("classification_positive_class")
    private final String classificationPositiveClass;

    @SerializedName("classification_betas")
    private final double[] classificationBetas;

    private final String suffix;

    private FineTuneRequest(Builder builder) {
        this.builder = builder;
        this.trainingFile = builder.trainingFile;
        this.validationFile = builder.validationFile;
        this.model = builder.model.getModel();
        this.nEpochs = builder.nEpochs;
        this.batchSize = builder.batchSize;
        this.learningRateMultiplier = builder.learningRateMultiplier;
        this.promptLossWeight = builder.promptLossWeight;
        this.computeClassificationMetrics = builder.computeClassificationMetrics;
        this.classificationNClasses = builder.classificationNClasses;
        this.classificationPositiveClass = builder.classificationPositiveClass;
        this.classificationBetas = builder.classificationBetas;
        this.suffix = builder.suffix;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder;
    }

    public String getTrainingFile() {
        return trainingFile;
    }

    public String getValidationFile() {
        return validationFile;
    }

    public String getModel() {
        return model;
    }

    public Integer getnEpochs() {
        return nEpochs;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public Double getLearningRateMultiplier() {
        return learningRateMultiplier;
    }

    public Double getPromptLossWeight() {
        return promptLossWeight;
    }

    public boolean isComputeClassificationMetrics() {
        return computeClassificationMetrics;
    }

    public Integer getClassificationNClasses() {
        return classificationNClasses;
    }

    public String getClassificationPositiveClass() {
        return classificationPositiveClass;
    }

    public double[] getClassificationBetas() {
        return classificationBetas;
    }

    public String getSuffix() {
        return suffix;
    }

    public static final class Builder {

        /**
         * 必需参数：包含训练数据的上传文件的 ID
         */
        @NotNull
        private String trainingFile;

        /**
         * 非必需参数：包含验证数据的上传文件的 ID
         */
        private String validationFile;

        /**
         * 非必需参数：选择的模型，当前稳定版本的模型：<a href="https://platform.openai.com/docs/models/model-endpoint-compatibility">链接</a>
         */
        private ModelSelector model = FineTuneModel.CURIE;

        /**
         * 非必需参数：训练模型的时期数，一个纪元指的是训练数据集的一个完整周期。默认值是4
         */
        private Integer nEpochs;

        /**
         * 非必需参数：用于训练的批量大小，批量大小是用于训练单个前向和后向传递的训练示例数。默认是null
         */
        private Integer batchSize;

        /**
         * 非必需参数：用于训练的学习率乘数，微调学习率是用于预训练的原始学习率乘以该值。默认值是null
         */
        private Double learningRateMultiplier;

        /**
         * 非必需参数：用于提示令牌损失的权重，这控制了模型尝试学习生成提示的程度（与权重始终为 1.0 的完成相比），并且可以在完成较短时为训练增加稳定效果。默认值是0.01
         */
        private Double promptLossWeight;

        /**
         * 非必需参数：如果设置，我们将在每个时期结束时使用验证集计算特定于分类的指标，例如准确性和 F-1 分数。 可以在结果文件中查看这些指标。默认值是false
         * <p>
         * 为了计算分类指标，您必须提供一个 validation_file。 此外，您必须为多类分类指定 classification_n_classes 或为二元分类指定
         * classification_positive_class。
         */
        private boolean computeClassificationMetrics;

        /**
         * 非必需参数：分类任务中的类数。多类分类需要此参数。默认值是null
         */
        private Integer classificationNClasses;

        /**
         * 非必需参数：二元分类中的正类。在进行二元分类时，需要此参数来生成精度、召回率和 F1 指标。默认值是null
         */
        private String classificationPositiveClass;

        /**
         * 非必需参数：如果提供，我们将计算指定 beta 值的 F-beta 分数。 F-beta 分数是 F-1 分数的推广。 这仅用于二进制分类。当 beta 为 1（即 F-1
         * 分数）时，精确率和召回率被赋予相同的权重。 Beta 分数越大，召回率越高，精确率越低。 Beta 分数越小，精确度越重要，召回率越低。默认值是null
         */
        private double[] classificationBetas;

        /**
         * 非必需参数：最多 40 个字符的字符串，将添加到您的微调模型名称中。例如，“custom-model-name”的后缀会生成类似
         * ada:ft-your-org:custom-model-name-2022-02-15-04-21-04 的模型名称。默认值是null
         */
        private String suffix;

        private Builder() {
        }

        public Builder trainingFile(String trainingFile) {
            this.trainingFile = trainingFile;
            return this;
        }

        public Builder validationFile(String validationFile) {
            this.validationFile = validationFile;
            return this;
        }

        public Builder model(ModelSelector model) {
            this.model = model;
            return this;
        }

        public Builder nEpochs(int nEpochs) {
            this.nEpochs = nEpochs;
            return this;
        }

        public Builder batchSize(int batchSize) {
            this.batchSize = batchSize;
            return this;
        }

        public Builder learningRateMultiplier(double learningRateMultiplier) {
            this.learningRateMultiplier = learningRateMultiplier;
            return this;
        }

        public Builder promptLossWeight(double promptLossWeight) {
            this.promptLossWeight = promptLossWeight;
            return this;
        }

        public Builder computeClassificationMetrics(boolean computeClassificationMetrics) {
            this.computeClassificationMetrics = computeClassificationMetrics;
            return this;
        }

        public Builder classificationNClasses(int classificationNClasses) {
            this.classificationNClasses = classificationNClasses;
            return this;
        }

        public Builder classificationPositiveClass(String classificationPositiveClass) {
            this.classificationPositiveClass = classificationPositiveClass;
            return this;
        }

        public Builder classificationBetas(double[] classificationBetas) {
            this.classificationBetas = classificationBetas;
            return this;
        }

        public Builder suffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        public FineTuneRequest build() {
            return new FineTuneRequest(this);
        }
    }

    @Override
    public String toString() {
        return "FineTuneRequest{" +
                "trainingFile='" + trainingFile + '\'' +
                ", validationFile='" + validationFile + '\'' +
                ", model='" + model + '\'' +
                ", nEpochs=" + nEpochs +
                ", batchSize=" + batchSize +
                ", learningRateMultiplier=" + learningRateMultiplier +
                ", promptLossWeight=" + promptLossWeight +
                ", computeClassificationMetrics=" + computeClassificationMetrics +
                ", classificationNClasses=" + classificationNClasses +
                ", classificationPositiveClass='" + classificationPositiveClass + '\'' +
                ", classificationBetas=" + Arrays.toString(classificationBetas) +
                ", suffix='" + suffix + '\'' +
                '}';
    }
}
