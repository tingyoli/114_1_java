import java.util.*;

// ================== Demo ==================
public class OnlineExamDemo {
    public static void main(String[] args) {
        // 1️⃣ 建立題目
        SingleChoiceQuestion q1 = new SingleChoiceQuestion(
                "Q1", "Java 是什麼語言？", 5, "物件導向");

        Set<String> mcAnswers = new HashSet<>(Arrays.asList("A", "C"));
        MultipleChoiceQuestion q2 = new MultipleChoiceQuestion(
                "Q2", "下列哪些是程式語言？", 10, mcAnswers);

        TrueFalseQuestion q3 = new TrueFalseQuestion(
                "Q3", "地球是平的？", 5, false);

        Set<String> blanks = new HashSet<>(Arrays.asList("狗", "貓"));
        FillInBlankQuestion q4 = new FillInBlankQuestion(
                "Q4", "填空題：寵物有哪些？(以逗號分隔)", 10, blanks);

        ShortAnswerQuestion q5 = new ShortAnswerQuestion(
                "Q5", "請簡述物件導向特性", 15);

        // 2️⃣ 建立考試
        Exam exam = new Exam("EX01", "程式語言測驗");
        exam.addQuestion(q1);
        exam.addQuestion(q2);
        exam.addQuestion(q3);
        exam.addQuestion(q4);
        exam.addQuestion(q5);

        // 3️⃣ 建立學生
        Student student = new Student("S001", "小明");

        // 4️⃣ 建立考試作答階段
        ExamSession session = new ExamSession(exam, student);

        // 5️⃣ 學生作答
        session.submitAnswer(q1, new SingleChoiceAnswer("物件導向"));          // 單選答對
        session.submitAnswer(q2, new MultipleChoiceAnswer(new HashSet<>(Arrays.asList("A", "B", "C")))); // 複選部分答對
        session.submitAnswer(q3, new SingleChoiceAnswer("false"));             // 是非答對
        session.submitAnswer(q4, new TextAnswer("狗,貓,鳥"));                // 填空部分答對
        session.submitAnswer(q5, new PendingAnswer("物件導向特性說明..."));   // 簡答題待人工評分

        session.finishExam();

        // 6️⃣ 計算分數
        GradingService gradingService = new GradingService();
        Map<Question, Double> scores = gradingService.grade(session.getAnswers());

        // 7️⃣ 生成成績報告
        GradingService.GradeReport report = new GradingService.GradeReport(student);
        report.generateReport(scores);

        // 8️⃣ 顯示結果
        System.out.println("學生: " + student.getName());
        System.out.println("總分: " + report.getTotalScore() + " / " + exam.getTotalScore());
        System.out.println("\n各題成績明細:");
        for (Question q : exam.getQuestions()) {
            System.out.println(q.getQuestionId() + " : " + scores.get(q) + " / " + q.getScore());
        }
    }
}

