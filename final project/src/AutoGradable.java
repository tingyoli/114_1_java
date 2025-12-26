import java.util.List;
import java.util.Map;

/* ================== 自動評分策略 ================== */
interface GradingStrategy {
    /**
     * 計算一題分數
     * @param question 要評分的題目
     * @param answer 學生作答
     * @return 該題分數
     */
    double grade(Question question, Answer answer);

    /**
     * 取得題目的正確答案（文字或集合）
     * @param question
     * @return 正確答案描述
     */
    String getCorrectAnswer(Question question);
}

/* ================== 部分給分策略 ================== */
interface PartialCreditStrategy {
    /**
     * 計算部分給分
     * @param question
     * @param answer
     * @return 部分分數
     */
    double calculatePartialScore(Question question, Answer answer);

    /**
     * 說明部分給分規則
     */
    String getPartialCreditRules(Question question);
}

/* ================== 隨機化策略 ================== */
interface Randomizable<T> {
    /**
     * 對集合進行隨機化
     * @param items 要亂序的集合
     * @param seed 隨機種子（可重現）
     */
    void shuffle(List<T> items, long seed);

    /**
     * 取得使用的種子
     */
    long getSeed();
}
interface AutoGradable {
    double grade(Answer answer);       // 自動計分
    String getCorrectAnswer();         // 正確答案
}

interface PartialCredit {
    double calculatePartialScore(Answer answer);  // 部分給分
    String getPartialCreditRules();               // 部分給分規則說明
}
