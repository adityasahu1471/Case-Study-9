import java.io.*;
import java.util.ArrayList;

public class FileManager {

    public static void saveFeedbacks(String fileName, ArrayList<Feedback> feedbacks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedback.txt"))) {

            for (Feedback f : feedbacks) {
                writer.write(formatFeedback(f));
                writer.newLine();
            }

            System.out.println("Feedbacks saved successfully.");

        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }


    public static void appendFeedback(Feedback f) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedback.txt", true))) {

            writer.write(formatFeedback(f));
            writer.newLine();

            System.out.println("Feedback appended successfully.");

        } catch (IOException e) {
            System.out.println("Error appending file: " + e.getMessage());
        }
    }

    public static void appendCourseReport(Course course, ArrayList<Feedback> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("course_report.txt", true))) {

            writer.write("===== Course Report =====");
            writer.newLine();
            writer.write("Course: " + course.getCourseName() + " (" + course.getCourseCode() + ")");
            writer.newLine();
            writer.write("Instructor: " + course.getInstructor());
            writer.newLine();
            writer.write("Total Feedbacks: " + list.size());
            writer.newLine();

            double total = 0;

            for (Feedback f : list) {
                writer.write("Student: " + f.getStudent().getName());
                writer.newLine();
                writer.write("Ratings: " + f.getRatings());
                writer.newLine();
                writer.write("Comment: " + f.getComment());
                writer.newLine();

                total += f.calculateAverage();
                writer.write("----------------------");
                writer.newLine();
            }

            if (!list.isEmpty()) {
                writer.write("Overall Average: " + (total / list.size()));
                writer.newLine();
            }

            writer.write("=========================");
            writer.newLine();
            writer.newLine();

            System.out.println("Course report appended successfully.");

        } catch (IOException e) {
            System.out.println("Error writing course report: " + e.getMessage());
        }
    }

    public static void readFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;
            System.out.println("\n=== File Content ===");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    public static void startConsoleLogging() {
        try {
            PrintStream console = System.out;
            PrintStream fileOut = new PrintStream(new FileOutputStream("console_log.txt", true));

            PrintStream multi = new PrintStream(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    console.write(b);
                    fileOut.write(b);
                }
            });

            System.setOut(multi);
            System.out.println("Console + File logging started...");

        } catch (IOException e) {
            System.out.println("Error starting console logging: " + e.getMessage());
        }
    }
    private static String formatFeedback(Feedback f) {
        return "Student: " + f.getStudent().getName() +
                " | Course: " + f.getCourse().getCourseName() +
                " | Ratings: " + f.getRatings() +
                " | Comment: " + f.getComment();
    }
}
