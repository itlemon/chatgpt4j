package cn.codingguide.chatgpt4j.domain.moderations;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author itlemon
 * Created on 2023-04-29
 */
public class ModerationResult implements Serializable {

    private Categories categories;

    @SerializedName("category_scores")
    private CategoryScores categoryScores;

    private boolean flagged;

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public CategoryScores getCategoryScores() {
        return categoryScores;
    }

    public void setCategoryScores(CategoryScores categoryScores) {
        this.categoryScores = categoryScores;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    @Override
    public String toString() {
        return "ModerationResult{" +
                "categories=" + categories +
                ", categoryScores=" + categoryScores +
                ", flagged=" + flagged +
                '}';
    }
}
