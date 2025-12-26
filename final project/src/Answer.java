import java.time.LocalTime;
import java.util.Collections;
import java.util.Set;

/* ================== 抽象父類別 ================== */
abstract class Answer {
    protected final LocalTime answerTime;

    public Answer() {
        this.answerTime = LocalTime.now();
    }

    public LocalTime getAnswerTime() {
        return answerTime;
    }
}

/* ================== 單選答案 ================== */
class SingleChoiceAnswer extends Answer {
    private final String selectedOption;

    public SingleChoiceAnswer(String selectedOption) {
        if (selectedOption == null || selectedOption.isEmpty()) {
            throw new IllegalArgumentException("selectedOption cannot be null or empty");
        }
        this.selectedOption = selectedOption;
    }

    public String getSelectedOption() {
        return selectedOption;
    }
}

/* ================== 複選答案 ================== */
class MultipleChoiceAnswer extends Answer {
    private final Set<String> selectedOptions;

    public MultipleChoiceAnswer(Set<String> selectedOptions) {
        if (selectedOptions == null || selectedOptions.isEmpty()) {
            throw new IllegalArgumentException("selectedOptions cannot be null or empty");
        }
        this.selectedOptions = Set.copyOf(selectedOptions);
    }

    public Set<String> getSelectedOptions() {
        return Collections.unmodifiableSet(selectedOptions);
    }
}

/* ================== 文字答案 ================== */
class TextAnswer extends Answer {
    private final String text;

    public TextAnswer(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

/* ================== 待人工批改答案 ================== */
class PendingAnswer extends Answer {
    private final String text;

    public PendingAnswer(String text) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
