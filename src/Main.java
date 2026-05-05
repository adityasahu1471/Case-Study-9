import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        FileManager.startConsoleLogging();

        FeedbackSystem system = new FeedbackSystem();
        Admin admin = new Admin(1, "Dr. Alex Kumar", "admin@university.edu");
        admin.displayAdmin();

        Student s1 = new Student("Arjun Nair", "S001", "Computer Science", 5);
        Student s2 = new Student("Priya Menon", "S002", "Electronics", 3);
        system.addStudent(s1);
        system.addStudent(s2);

        Faculty f1 = new Faculty("F001", "Dr. Rahul Sharma", "rahul@university.edu", "Computer Science");
        system.addFaculty(f1);

        Course c1 = new Course("CS301", "Data Structures", "Dr. Rahul Sharma", "Computer Science", "F001");
        Course c2 = new Course("EC201", "Digital Circuits", "Dr. Meena Pillai", "Electronics", "F002");
        system.addCourse(c1);
        system.addCourse(c2);

        Question q1 = new Question(1, "How clear was the course content?");
        Question q2 = new Question(2, "How effective was the teaching methodology?");
        Question q3 = new Question(3, "How well were doubts addressed?");
        system.addQuestion(q1);
        system.addQuestion(q2);
        system.addQuestion(q3);

        ArrayList<Question> questionList = new ArrayList<>();
        questionList.add(q1);
        questionList.add(q2);
        questionList.add(q3);

        Feedback fb1 = new Feedback(s1, c1, questionList);
        fb1.addRating(5);
        fb1.addRating(4);
        fb1.addRating(5);
        fb1.addComment("Excellent course! Very well structured.");
        system.addFeedback(fb1);

        Feedback fb2 = new Feedback(s2, c1, questionList);
        fb2.addRating(3);
        fb2.addRating(4);
        fb2.addRating(3);
        fb2.addComment("Good course but could use more examples.");
        system.addFeedback(fb2);

        system.showAllStudents();
        system.showAllCourses();
        system.showAllQuestions();
        system.showAllFeedbacks();
        system.generateCourseReport(1);
        s1.viewFeedbacks();
        c1.viewCourseFeedbacks();
        c1.instructorDetails();
    }
}
