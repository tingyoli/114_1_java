import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalTime;

class Exam {
    private String examId;
    private String title;
    private List<Question> questions;

    public Exam(String examId, String title) {
        this.examId = examId;
        this.title = title;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void removeQuestion(String questionId) {
        questions.removeIf(q -> q.getQuestionId().equals(questionId));
    }

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    public double getTotalScore() {
        return questions.stream().mapToDouble(Question::getScore).sum();
    }

    public String getExamId() {
        return examId;
    }

    public String getTitle() {
        return title;
    }

}
class ExamSession {
    private final Exam exam;
    private final Student student;
    private final Map<Question, Answer> answers = new HashMap<>();
    private final LocalTime startTime = LocalTime.now();
    private LocalTime endTime;

    public ExamSession(Exam exam, Student student) {
        this.exam = exam;
        this.student = student;
    }

    public void submitAnswer(Question question, Answer answer) {
        answers.put(question, answer);
    }

    public void finishExam() {
        this.endTime = LocalTime.now();
    }

    public Map<Question, Answer> getAnswers() {
        return answers;
    }

    // ✅ 新增 Getter
    public Exam getExam() { return exam; }
    public Student getStudent() { return student; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
}