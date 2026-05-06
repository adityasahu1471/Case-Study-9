import java.util.ArrayList;

public class Course {
    private String courseCode;
    private String courseName;
    private String instructor;
    private String subject;
    private String instructorId;
    private ArrayList<Feedback> feedbacks;

    public Course(String courseCode, String courseName, String instructor, String subject, String instructorId) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.instructor = instructor;
        this.subject = subject;
        this.instructorId = instructorId;
        this.feedbacks = new ArrayList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }
    public String getCourseName() {
        return courseName; 
    }
    public String getInstructor() {
        return instructor;
    }
    public String getSubject() { 
        return subject; 
    }
    public String getInstructorId() {
        return instructorId;
    }
    public ArrayList<Feedback> getFeedbacks() { 
        return new ArrayList<>(feedbacks);
    }

    public void setCourseCode(String courseCode) { 
        this.courseCode = courseCode;
    }
    public void setCourseName(String courseName) { 
        this.courseName = courseName; 
    }
    public void setInstructor(String instructor) { 
        this.instructor = instructor; 
    }
    public void setSubject(String subject) { 
        this.subject = subject;
    }
    public void setInstructorId(String instructorId) { 
        this.instructorId = instructorId;
    }

    public void addFeedback(Feedback f) {
        if (f == null) throw new IllegalArgumentException("Feedback cannot be null.");
        feedbacks.add(f);
        System.out.println("Feedback added to course: " + courseName);
    }

    public void clearFeedbacks() {
        feedbacks.clear();
    }

    public void displayCourse() {
        System.out.println("Course Details ");
        System.out.println("Course Code: " + courseCode);
        System.out.println("Course Name: " + courseName);
        System.out.println("Subject: " + subject);
        System.out.println("Instructor: " + instructor + " (ID: " + instructorId + ")");
        System.out.println("Feedbacks: " + feedbacks.size());
    }

    public void viewCourseFeedbacks() {
        if (feedbacks.isEmpty()) {
            System.out.println("No feedbacks found for course: " + courseName);
            return;
        }
        System.out.println(" Feedbacks for Course: " + courseName );
        for (Feedback f : feedbacks) {
            f.displayFeedback();
        }
    }

    public void instructorDetails() {
        System.out.println("Instructor Details");
        System.out.println("Instructor Name : " + instructor);
        System.out.println("Instructor ID: " + instructorId);
        System.out.println("Course Taught: " + courseName + " (" + courseCode + ")");
    }

    @Override
    public String toString() {
        return "Course[" + courseCode + " | " + courseName + " | " + instructor + "]";
    }
}
