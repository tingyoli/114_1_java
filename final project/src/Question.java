import java.util.Set;

/* ================== 抽象父類別 ================== */
public abstract class Question {
    private final String questionId;
    private final String content;
    private final double score;

    public Question(String questionId, String content, double score) {
        if (questionId == null || questionId.isEmpty()) {
            throw new IllegalArgumentException("questionId is required");
        }
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("content is required");
        }
        if (score <= 0) {
            throw new IllegalArgumentException("score must be positive");
        }
        this.questionId = questionId;
        this.content = content;
        this.score = score;
    }

    public String getQuestionId() { return questionId; }
    public String getContent() { return content; }
    public double getScore() { return score; }
}

/* ================== 單選題 ================== */
class SingleChoiceQuestion extends Question implements AutoGradable {
    private final String correctAnswer;

    public SingleChoiceQuestion(String questionId, String content, double score, String correctAnswer) {
        super(questionId, content, score);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public double grade(Answer answer) {
        if (!(answer instanceof SingleChoiceAnswer)) return 0;
        SingleChoiceAnswer ans = (SingleChoiceAnswer) answer;
        return correctAnswer.equals(ans.getSelectedOption()) ? getScore() : 0;
    }

    @Override
    public String getCorrectAnswer() {
        return correctAnswer;
    }
}

/* ================== 複選題 ================== */
class MultipleChoiceQuestion extends Question implements AutoGradable, PartialCredit {
    private final Set<String> correctAnswers;

    public MultipleChoiceQuestion(String questionId, String content, double score, Set<String> correctAnswers) {
        super(questionId, content, score);
        this.correctAnswers = Set.copyOf(correctAnswers);
    }

    @Override
    public double grade(Answer answer) {
        return calculatePartialScore(answer);
    }

    @Override
    public double calculatePartialScore(Answer answer) {
        if (!(answer instanceof MultipleChoiceAnswer)) return 0;
        MultipleChoiceAnswer ans = (MultipleChoiceAnswer) answer;
        int correctCount = 0;
        for (String s : ans.getSelectedOptions()) {
            if (correctAnswers.contains(s)) correctCount++;
        }
        return getScore() * ((double) correctCount / correctAnswers.size());
    }

    @Override
    public String getPartialCreditRules() {
        return "得分依答對比例計算";
    }

    @Override
    public String getCorrectAnswer() {
        return correctAnswers.toString();
    }
}

/* ================== 是非題 ================== */
class TrueFalseQuestion extends Question implements AutoGradable {
    private final boolean correctAnswer;

    public TrueFalseQuestion(String questionId, String content, double score, boolean correctAnswer) {
        super(questionId, content, score);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public double grade(Answer answer) {
        if (!(answer instanceof SingleChoiceAnswer)) return 0;
        SingleChoiceAnswer ans = (SingleChoiceAnswer) answer;
        return Boolean.parseBoolean(ans.getSelectedOption()) == correctAnswer ? getScore() : 0;
    }

    @Override
    public String getCorrectAnswer() {
        return Boolean.toString(correctAnswer);
    }
}

/* ================== 填空題 ================== */
class FillInBlankQuestion extends Question implements AutoGradable, PartialCredit {
    private final Set<String> correctAnswers;

    public FillInBlankQuestion(String questionId, String content, double score, Set<String> correctAnswers) {
        super(questionId, content, score);
        this.correctAnswers = Set.copyOf(correctAnswers);
    }

    @Override
    public double grade(Answer answer) {
        return calculatePartialScore(answer);
    }

    @Override
    public double calculatePartialScore(Answer answer) {
        if (!(answer instanceof TextAnswer)) return 0;
        TextAnswer ans = (TextAnswer) answer;
        String[] responses = ans.getText().split(",");
        int correctCount = 0;
        for (String resp : responses) {
            for (String ca : correctAnswers) {
                if (resp.trim().equalsIgnoreCase(ca.trim())) correctCount++;
            }
        }
        return getScore() * ((double) correctCount / correctAnswers.size());
    }

    @Override
    public String getPartialCreditRules() {
        return "每個填空比對正確可得部分分";
    }

    @Override
    public String getCorrectAnswer() {
        return correctAnswers.toString();
    }
}

/* ================== 簡答題 ================== */
class ShortAnswerQuestion extends Question {
    public ShortAnswerQuestion(String questionId, String content, double score) {
        super(questionId, content, score);
    }
    // 簡答題需人工評分，不實作 AutoGradable / PartialCredit
}
