public class Question {
    private int questionNo;
    private String question;

    public Question(int questionNo, String question) {
        if (questionNo < 1) throw new IllegalArgumentException("Question number must be positive.");
        if (question == null || question.trim().isEmpty()) throw new IllegalArgumentException("Question text cannot be empty.");
        this.questionNo = questionNo;
        this.question = question;
    }

    public int getQuestionNo() { return questionNo; }
    public String getQuestion() { return question; }

    public void setQuestionNo(int questionNo) {
        if (questionNo < 1) throw new IllegalArgumentException("Question number must be positive.");
        this.questionNo = questionNo;
    }

    public void setQuestion(String question) {
        if (question == null || question.trim().isEmpty()) throw new IllegalArgumentException("Question text cannot be empty.");
        this.question = question;
    }

    public void displayQuestion() {
        System.out.println("Q" + questionNo + ": " + question);
    }

    @Override
    public String toString() {
        return "Q" + questionNo + ": " + question;
    }
}

