import java.util.ArrayList;

public class Feedback {
    private Student student;
    private Course course;
    private String comment;
    private ArrayList<Question> questions;
    private ArrayList<Integer> ratings;

    public Feedback(Student student, Course course, ArrayList<Question> questions) {
        if (student == null) throw new IllegalArgumentException("Student cannot be null.");
        if (course == null) throw new IllegalArgumentException("Course cannot be null.");
        if (questions == null || questions.isEmpty()) throw new IllegalArgumentException("Questions list cannot be null or empty.");
        this.student = student;
        this.course = course;
        this.questions = new ArrayList<>(questions);
        this.ratings = new ArrayList<>();
        this.comment = "";
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public String getComment() { return comment; }
    public ArrayList<Question> getQuestions() { return new ArrayList<>(questions); }
    public ArrayList<Integer> getRatings() { return new ArrayList<>(ratings); }

    public void setComment(String comment) { this.comment = comment; }

    public void setStudent(Student student) {
        if (student == null) throw new IllegalArgumentException("Student cannot be null.");
        this.student = student;
    }

    public void setCourse(Course course) {
        if (course == null) throw new IllegalArgumentException("Course cannot be null.");
        this.course = course;
    }

    public void addRating(int rating) {
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("Rating must be between 1 and 5.");
        ratings.add(rating);
    }

    public void addComment(String comment) {
        if (comment == null || comment.trim().isEmpty()) {
            System.out.println("Comment cannot be empty.");
            return;
        }
        this.comment = comment;
        System.out.println("Comment added: " + comment);
    }

    public double calculateAverage() {
        if (ratings.isEmpty()) {
            System.out.println("No ratings available.");
            return 0.0;
        }
        double sum = 0;
        for (int r : ratings) sum += r;
        double avg = sum / ratings.size();
        System.out.printf("Average Rating: %.2f%n", avg);
        return avg;
    }

    public void displayFeedback() {
        System.out.println("--- Feedback ---");
        System.out.println("Student  : " + student.getName() + " (" + student.getStudentId() + ")");
        System.out.println("Course   : " + course.getCourseName() + " (" + course.getCourseCode() + ")");
        System.out.println("Comment  : " + (comment.isEmpty() ? "N/A" : comment));
        System.out.println("Ratings  : " + ratings);
        System.out.printf("Average  : %.2f%n", calculateAverage());
        System.out.println("Questions:");
        for (Question q : questions) q.displayQuestion();
        System.out.println("----------------");
    }

    @Override
    public String toString() {
        return "Feedback[Student=" + student.getName() + " | Course=" + course.getCourseName() + " | Avg=" + String.format("%.2f", calculateAverage()) + "]";
    }
}

