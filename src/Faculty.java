public class Faculty {
    private String facultyId;
    private String facultyName;
    private String facultyMail;
    private String department;

    public Faculty(String facultyId, String facultyName, String facultyMail, String department) {
        if (facultyId == null || facultyId.trim().isEmpty()) throw new IllegalArgumentException("Faculty ID cannot be empty.");
        if (facultyName == null || facultyName.trim().isEmpty()) throw new IllegalArgumentException("Faculty name cannot be empty.");
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.facultyMail = facultyMail;
        this.department = department;
    }

    public String getFacultyId() { 
        return facultyId;
    }
    public String getFacultyName() { 
        return facultyName;
    }
    public String getFacultyMail() { 
        return facultyMail; 
    }
    public String getDepartment() {
        return department;
    }

    public void setFacultyName(String facultyName) {
        if (facultyName == null || facultyName.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        this.facultyName = facultyName;
    }

    public void setFacultyMail(String facultyMail) { this.facultyMail = facultyMail; }
    public void setDepartment(String department) { this.department = department; }

    public void displayFaculty() {
        System.out.println("=== Faculty Details ===");
        System.out.println("Faculty ID   : " + facultyId);
        System.out.println("Name         : " + facultyName);
        System.out.println("Email        : " + facultyMail);
        System.out.println("Department   : " + department);
    }

    @Override
    public String toString() {
        return "Faculty[" + facultyId + " | " + facultyName + " | " + department + "]";
    }
}

