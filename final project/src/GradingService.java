import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* ================== 計分服務 ================== */
public class GradingService {

    /**
     * 給定學生答案，計算每題分數
     *
     * @param answers Map<Question, Answer>
     * @return Map<Question, Double> 每題得分
     */
    public Map<Question, Double> grade(Map<Question, Answer> answers) {
        Map<Question, Double> result = new HashMap<>();

        for (Map.Entry<Question, Answer> entry : answers.entrySet()) {
            Question q = entry.getKey();
            Answer a = entry.getValue();
            double score = 0;

            // 若題目可自動評分
            if (q instanceof AutoGradable auto) {
                score = auto.grade(a);
            }
            // 簡答題或待人工批改答案保持 score = 0

            result.put(q, score);
        }

        return result;
    }

    /* ================== 成績報告 ================== */
    public static class GradeReport {
        private final Student student;
        private double totalScore;

        public GradeReport(Student student) {
            this.student = student;
            this.totalScore = 0;
        }

        /**
         * 使用 GradingService 計算出的 Map<Question, Double>
         */
        public void generateReport(Map<Question, Double> scores) {
            totalScore = scores.values().stream().mapToDouble(Double::doubleValue).sum();
        }

        public double getTotalScore() {
            return totalScore;
        }

        public Student getStudent() {
            return student;
        }

        /** 可選：列印各題成績明細 */
        public void printDetail(Map<Question, Double> scores, List<Question> questionOrder) {
            System.out.println("學生: " + student.getName());
            System.out.println("總分: " + getTotalScore());
            System.out.println("各題成績明細:");

            for (Question q : questionOrder) {
                if (q instanceof ShortAnswerQuestion) {
                    System.out.println(q.getQuestionId() + " : 待人工批改 / " + q.getScore());
                } else {
                    System.out.println(q.getQuestionId() + " : " + scores.get(q) + " / " + q.getScore());
                }
            }
        }


    }
}




