import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class FileManager {
    private static final String DEFAULT_FILE = "feedback_output.txt";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static boolean consoleLoggingStarted = false;

    private FileManager() {
    }

    public static void appendOutput(String output) {
        appendOutput(DEFAULT_FILE, output);
    }

    public static void appendOutput(String fileName, String output) {
        try (FileOutputStream writer = new FileOutputStream(fileName, true)) {
            writer.write(output.getBytes(StandardCharsets.UTF_8));
            writer.write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));
        } catch (IOException ex) {
            System.err.println("Unable to write to file: " + ex.getMessage());
        }
    }

    public static void appendFeedback(Feedback feedback) {
        StringBuilder output = new StringBuilder();
        output.append(System.lineSeparator());
        output.append("========== Feedback Submitted ==========").append(System.lineSeparator());
        output.append("Time     : ").append(LocalDateTime.now().format(DATE_FORMAT)).append(System.lineSeparator());
        output.append("Student  : ").append(feedback.getStudent().getName())
                .append(" (").append(feedback.getStudent().getStudentId()).append(")").append(System.lineSeparator());
        output.append("Course   : ").append(feedback.getCourse().getCourseName())
                .append(" (").append(feedback.getCourse().getCourseCode()).append(")").append(System.lineSeparator());
        output.append("Ratings  : ").append(feedback.getRatings()).append(System.lineSeparator());
        output.append("Average  : ").append(String.format("%.2f", averageOf(feedback))).append(" / 5.00").append(System.lineSeparator());
        output.append("Comment  : ").append(feedback.getComment().isEmpty() ? "N/A" : feedback.getComment()).append(System.lineSeparator());
        output.append("========================================");
        appendOutput(output.toString());
    }

    public static void appendCourseReport(Course course, ArrayList<Feedback> feedbacks) {
        StringBuilder output = new StringBuilder();
        output.append(System.lineSeparator());
        output.append("========== Course Report ==========").append(System.lineSeparator());
        output.append("Time            : ").append(LocalDateTime.now().format(DATE_FORMAT)).append(System.lineSeparator());
        output.append("Course          : ").append(course.getCourseName())
                .append(" (").append(course.getCourseCode()).append(")").append(System.lineSeparator());
        output.append("Instructor      : ").append(course.getInstructor()).append(System.lineSeparator());
        output.append("Total Feedbacks : ").append(feedbacks.size()).append(System.lineSeparator());

        if (feedbacks.isEmpty()) {
            output.append("No feedbacks submitted for this course.").append(System.lineSeparator());
        } else {
            double totalAverage = 0;
            for (Feedback feedback : feedbacks) {
                double feedbackAverage = averageOf(feedback);
                totalAverage += feedbackAverage;
                output.append(System.lineSeparator());
                output.append("From     : ").append(feedback.getStudent().getName()).append(System.lineSeparator());
                output.append("Ratings  : ").append(feedback.getRatings()).append(System.lineSeparator());
                output.append("Average  : ").append(String.format("%.2f", feedbackAverage)).append(" / 5.00").append(System.lineSeparator());
                output.append("Comment  : ").append(feedback.getComment().isEmpty() ? "N/A" : feedback.getComment()).append(System.lineSeparator());
            }
            output.append(System.lineSeparator());
            output.append("Overall Course Average Rating : ")
                    .append(String.format("%.2f", totalAverage / feedbacks.size()))
                    .append(" / 5.00")
                    .append(System.lineSeparator());
        }

        output.append("===================================");
        appendOutput(output.toString());
    }

    public static void startConsoleLogging() {
        if (consoleLoggingStarted) {
            return;
        }

        try {
            PrintStream currentOut = System.out;
            PrintStream currentErr = System.err;
            FileOutputStream fileOutput = new FileOutputStream(DEFAULT_FILE, true);
            PrintStream teeOut = new PrintStream(new TeeOutputStream(currentOut, fileOutput), true, StandardCharsets.UTF_8.name());
            PrintStream teeErr = new PrintStream(new TeeOutputStream(currentErr, fileOutput), true, StandardCharsets.UTF_8.name());
            System.setOut(teeOut);
            System.setErr(teeErr);
            consoleLoggingStarted = true;
        } catch (IOException ex) {
            System.err.println("Unable to start file logging: " + ex.getMessage());
        }
    }

    private static double averageOf(Feedback feedback) {
        ArrayList<Integer> ratings = feedback.getRatings();
        if (ratings.isEmpty()) {
            return 0.0;
        }

        double sum = 0;
        for (int rating : ratings) {
            sum += rating;
        }
        return sum / ratings.size();
    }

    private static class TeeOutputStream extends OutputStream {
        private final OutputStream first;
        private final OutputStream second;

        private TeeOutputStream(OutputStream first, OutputStream second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public void write(int value) throws IOException {
            first.write(value);
            second.write(value);
        }

        @Override
        public void write(byte[] bytes, int offset, int length) throws IOException {
            first.write(bytes, offset, length);
            second.write(bytes, offset, length);
        }

        @Override
        public void flush() throws IOException {
            first.flush();
            second.flush();
        }
    }
}
