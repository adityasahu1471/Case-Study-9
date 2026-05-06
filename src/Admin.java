public class Admin {
    private int adminId;
    private String adminName;
    private String adminMail;

    public Admin(int adminId, String adminName, String adminMail) {
        if (adminId<=0) throw new IllegalArgumentException("Admin ID must be positive.");
        if (adminName==null || adminName.trim().isEmpty()) throw new IllegalArgumentException("Admin name cannot be empty.");
        this.adminId=adminId;
        this.adminName=adminName;
        this.adminMail=adminMail;
    }

    public int getAdminId() { 
        return adminId; 
    }
    public String getAdminName() { 
        return adminName; 
    }
    public String getAdminMail() { 
        return adminMail;
    }

    public void setAdminName(String adminName) {
        if (adminName==null || adminName.trim().isEmpty()) throw new IllegalArgumentException("Admin name cannot be empty.");
        this.adminName=adminName;
    }

    public void setAdminMail(String adminMail) { this.adminMail = adminMail; }

    public void displayAdmin() {
        System.out.println(" Admin Details ");
        System.out.println("Admin ID : " + adminId);
        System.out.println("Name: " + adminName);
        System.out.println("Email: " + adminMail);
    }

    @Override
    public String toString() {
        return "Admin[" + adminId + " | " + adminName + "]";
    }
}
