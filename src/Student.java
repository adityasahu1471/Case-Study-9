import java.util.ArrayList;

public class Student {
    private String name;
    private String studentId;
    private String department;
    private int semester;
    private ArrayList<Feedback> feedbacks;

    public Student(String name, String studentId, String department, int semester) {
        this.name = name;
        this.studentId = studentId;
        this.department = department;
        this.semester = semester;
        this.feedbacks = new ArrayList<>();
    }

    public String getName() { 
        return name; 
    }
    public String getStudentId() {
        return studentId;
    }
    public String getDepartment() {
        return department; 
    }
    public int getSemester() {
        return semester; 
    }
    public ArrayList<Feedback> getFeedbacks() { 
        return new ArrayList<>(feedbacks); 
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        this.name = name;
    }

    public void setDepartment(String department) { this.department = department; }

    public void setSemester(int semester) {
        if (semester < 1) throw new IllegalArgumentException("Semester must be positive.");
        this.semester = semester;
    }

    public void addFeedback(Feedback f) {
        if (f == null) throw new IllegalArgumentException("Feedback cannot be null.");
        feedbacks.add(f);
        System.out.println("Feedback added for student: " + name);
    }

    public void clearFeedbacks() {
        feedbacks.clear();
    }

    public void displayStudent() {
        System.out.println("=== Student Details ===");
        System.out.println("Name       : " + name);
        System.out.println("Student ID : " + studentId);
        System.out.println("Department : " + department);
        System.out.println("Semester   : " + semester);
        System.out.println("Feedbacks  : " + feedbacks.size());
    }

    public void viewFeedbacks() {
        if (feedbacks.isEmpty()) {
            System.out.println("No feedbacks found for " + name);
            return;
        }
        System.out.println("=== Feedbacks by " + name + " ===");
        for (Feedback f : feedbacks) {
            f.displayFeedback();
        }
    }

    @Override
    public String toString() {
        return "Student[" + studentId + " | " + name + " | " + department + " | Sem " + semester + "]";
    }
}
