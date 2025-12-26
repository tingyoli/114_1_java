import java.util.*;
import java.util.stream.Collectors;

public class OnlineExamInteractiveDemo {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1️⃣ 學生登入
        System.out.print("請輸入學生ID: ");
        String studentId = scanner.nextLine().trim();
        while (studentId.isEmpty()) {
            System.out.print("學生ID不可為空，請重新輸入: ");
            studentId = scanner.nextLine().trim();
        }

        System.out.print("請輸入學生姓名: ");
        String studentName = scanner.nextLine().trim();
        while (studentName.isEmpty()) {
            System.out.print("學生姓名不可為空，請重新輸入: ");
            studentName = scanner.nextLine().trim();
        }

        Student student = new Student(studentId, studentName);

        // 2️⃣ 建立題庫
        List<Question> questions = new ArrayList<>();
        questions.add(new SingleChoiceQuestion("Q1", "Java 是什麼語言？\nA. 物件導向\nB. 函數式\nC. 結構化\nD. 標記語言", 5, "A"));
        questions.add(new SingleChoiceQuestion("Q2", "下列哪個是 Java 的資料型別？\nA. int\nB. string\nC. char\nD. number", 5, "A"));
        questions.add(new SingleChoiceQuestion("Q3", "Java 中哪個關鍵字表示繼承？\nA. extends\nB. implements\nC. inherit\nD. super", 5, "A"));
        questions.add(new MultipleChoiceQuestion("Q4", "下列哪些是 Java 的存取修飾子？\nA. public\nB. private\nC. protected\nD. static", 10, new HashSet<>(Arrays.asList("A","B","C"))));
        questions.add(new MultipleChoiceQuestion("Q5", "下列哪些是基本資料型別？\nA. int\nB. boolean\nC. String\nD. double", 10, new HashSet<>(Arrays.asList("A","B","D"))));
        questions.add(new TrueFalseQuestion("Q6", "Java 是平台無關的語言？", 5, true));
        questions.add(new TrueFalseQuestion("Q7", "Java 允許多重繼承（class 多繼承）？", 5, false));
        questions.add(new TrueFalseQuestion("Q8", "interface 可以有實作方法？", 5, true));
        questions.add(new FillInBlankQuestion("Q9", "填空題：Java 中的字串類別是_____", 5, new HashSet<>(Arrays.asList("String"))));
        questions.add(new ShortAnswerQuestion("Q10", "請簡述物件導向特性", 15));

        Exam exam = new Exam("EX10", "10題程式語言測驗");
        questions.forEach(exam::addQuestion);
        ExamSession session = new ExamSession(exam, student);

        System.out.println("\n=== 開始作答 ===");

        // 3️⃣ 逐題作答
        for (Question q : questions) {
            System.out.println("\n題目 " + q.getQuestionId() + ": " + q.getContent());
            Answer ans = null;

            if (q instanceof SingleChoiceQuestion) {
                String input;
                while (true) {
                    System.out.print("請輸入答案（單選 A/B/C/D）：");
                    input = scanner.nextLine().trim().toUpperCase();
                    if (input.isEmpty()) {
                        System.out.println("請作答！");
                        continue;
                    }
                    if (!Set.of("A","B","C","D").contains(input)) {
                        System.out.println("輸入錯誤，請填寫 A/B/C/D");
                        continue;
                    }
                    break;
                }
                ans = new SingleChoiceAnswer(input);

            } else if (q instanceof MultipleChoiceQuestion) {
                Set<String> selected;
                while (true) {
                    System.out.print("請輸入答案（複選，以逗號分隔，例如 A,B）：");
                    String input = scanner.nextLine().trim().toUpperCase();
                    if (input.isEmpty()) {
                        System.out.println("請作答！");
                        continue;
                    }
                    selected = Arrays.stream(input.split(","))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty())
                            .collect(Collectors.toSet());
                    if (!selected.stream().allMatch(s -> Set.of("A","B","C","D").contains(s))) {
                        System.out.println("輸入錯誤，請只填寫 A/B/C/D，用逗號分隔");
                        continue;
                    }
                    break;
                }
                ans = new MultipleChoiceAnswer(selected);

            } else if (q instanceof TrueFalseQuestion) {
                String input;
                while (true) {
                    System.out.print("請輸入答案（是/否）：");
                    input = scanner.nextLine().trim();
                    if (input.isEmpty()) {
                        System.out.println("請作答！");
                        continue;
                    }
                    if (!input.equals("是") && !input.equals("否")) {
                        System.out.println("輸入錯誤，請填寫 是 或 否");
                        continue;
                    }
                    break;
                }
                ans = new SingleChoiceAnswer(input.equals("是") ? "true" : "false");

            } else if (q instanceof FillInBlankQuestion) {
                String input;
                while (true) {
                    System.out.print("請輸入答案：");
                    input = scanner.nextLine().trim();
                    if (input.isEmpty()) {
                        System.out.println("請作答！");
                        continue;
                    }
                    break;
                }
                ans = new TextAnswer(input);

            } else if (q instanceof ShortAnswerQuestion) {
                String input;
                while (true) {
                    System.out.print("請輸入答案（簡答，待人工批改）：");
                    input = scanner.nextLine().trim();
                    if (input.isEmpty()) {
                        System.out.println("請作答！");
                        continue;
                    }
                    break;
                }
                ans = new PendingAnswer(input);
            }

            session.submitAnswer(q, ans);
        }

        session.finishExam();

        // 4️⃣ 計算分數
        GradingService gradingService = new GradingService();
        Map<Question, Double> scores = gradingService.grade(session.getAnswers());
        GradingService.GradeReport report = new GradingService.GradeReport(student);
        report.generateReport(scores);

        // 5️⃣ 顯示成績
        System.out.println("\n=== 作答結束，成績報告 ===");
        report.printDetail(scores, exam.getQuestions());


        scanner.close();
    }
}
