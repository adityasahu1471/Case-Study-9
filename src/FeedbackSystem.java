import java.util.ArrayList;

public class FeedbackSystem {
    private ArrayList<Student> students;
    private ArrayList<Faculty> faculties;
    private ArrayList<Course> courses;
    private ArrayList<Question> questions;
    private ArrayList<Feedback> feedbacks;

    public FeedbackSystem() {
        this.students = new ArrayList<>();
        this.faculties = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students); 
    }
    public ArrayList<Faculty> getFaculties() {
        return new ArrayList<>(faculties);
    }
    public ArrayList<Course> getCourses() { 
        return new ArrayList<>(courses); 
    }
    public ArrayList<Question> getQuestions() { 
        return new ArrayList<>(questions); 
    }
    public ArrayList<Feedback> getFeedbacks() {
        return new ArrayList<>(feedbacks); 
    }

    public void addStudent(Student s) {
        if (s == null) throw new IllegalArgumentException("Student cannot be null.");
        for (Student existing : students) {
            if (existing.getStudentId().equals(s.getStudentId())) {
                System.out.println("Student with ID " + s.getStudentId() + " already exists.");
                return;
            }
        }
        students.add(s);
        System.out.println("Student added: " + s.getName());
    }

    public void addFaculty(Faculty f) {
        if (f == null) throw new IllegalArgumentException("Faculty cannot be null.");
        for (Faculty existing : faculties) {
            if (existing.getFacultyId().equals(f.getFacultyId())) {
                System.out.println("Faculty with ID " + f.getFacultyId() + " already exists.");
                return;
            }
        }
        faculties.add(f);
        System.out.println("Faculty added: " + f.getFacultyName());
    }

    public void addCourse(Course c) {
        if (c == null) throw new IllegalArgumentException("Course cannot be null.");
        for (Course existing : courses) {
            if (existing.getCourseCode().equals(c.getCourseCode())) {
                System.out.println("Course with code " + c.getCourseCode() + " already exists.");
                return;
            }
        }
        courses.add(c);
        System.out.println("Course added: " + c.getCourseName());
    }

    public void addQuestion(Question q) {
        if (q == null) throw new IllegalArgumentException("Question cannot be null.");
        questions.add(q);
        System.out.println("Question added: Q" + q.getQuestionNo());
    }

    public void addFeedback(Feedback f) {
        if (f == null) throw new IllegalArgumentException("Feedback cannot be null.");
        feedbacks.add(f);
        f.getStudent().addFeedback(f);
        f.getCourse().addFeedback(f);
        System.out.println("Feedback recorded for course: " + f.getCourse().getCourseName());
    }

    public void resetFeedbacks() {
        feedbacks.clear();
        for (Student student : students) {
            student.clearFeedbacks();
        }
        for (Course course : courses) {
            course.clearFeedbacks();
        }
        System.out.println("All feedbacks have been reset.");
    }

    public Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getStudentId().equals(id)) return s;
        }
        System.out.println("Student not found: " + id);
        return null;
    }

    public Course findCourseByCode(String code) {
        for (Course c : courses) {
            if (c.getCourseCode().equals(code)) return c;
        }
        System.out.println("Course not found: " + code);
        return null;
    }

    public void showAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        System.out.println("\n======= All Students =======");
        for (Student s : students) s.displayStudent();
        System.out.println("============================\n");
    }

    public void showAllCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        System.out.println("\n======= All Courses =======");
        for (Course c : courses) c.displayCourse();
        System.out.println("===========================\n");
    }

    public void showAllQuestions() {
        if (questions.isEmpty()) {
            System.out.println("No questions found.");
            return;
        }
        System.out.println("\n======= All Questions =======");
        for (Question q : questions) q.displayQuestion();
        System.out.println("=============================\n");
    }

    public void showAllFeedbacks() {
        if (feedbacks.isEmpty()) {
            System.out.println("No feedbacks submitted yet.");
            return;
        }
        System.out.println("\n======= All Feedbacks =======");
        for (Feedback f : feedbacks) f.displayFeedback();
        System.out.println("=============================\n");
    }

    public void generateCourseReport(int courseId) {
        if (courseId < 1 || courseId > courses.size()) {
            System.out.println("Invalid course ID: " + courseId);
            return;
        }

        Course targetCourse = courses.get(courseId - 1);
        ArrayList<Feedback> courseFeedbacks = new ArrayList<>();

        for (Feedback f : feedbacks) {
            if (f.getCourse().getCourseCode().equals(targetCourse.getCourseCode())) {
                courseFeedbacks.add(f);
            }
        }

        System.out.println("\n========== Course Report ==========");
        System.out.println("Course      : " + targetCourse.getCourseName() + " (" + targetCourse.getCourseCode() + ")");
        System.out.println("Instructor  : " + targetCourse.getInstructor());
        System.out.println("Total Feedbacks: " + courseFeedbacks.size());

        if (courseFeedbacks.isEmpty()) {
            System.out.println("No feedbacks submitted for this course.");
        } else {
            double totalAvg = 0;
            for (Feedback f : courseFeedbacks) {
                System.out.println("\nFrom: " + f.getStudent().getName());
                System.out.println("  Comment : " + f.getComment());
                double avg = f.calculateAverage();
                totalAvg += avg;
            }
            System.out.printf("%nOverall Course Average Rating : %.2f / 5.00%n", totalAvg / courseFeedbacks.size());
        }
        System.out.println("===================================\n");
    }
}
