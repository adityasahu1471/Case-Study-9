import java.util.ArrayList;
import java.util.Collections;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainApp extends Application {
    private FeedbackSystem system;
    private Admin admin;
    private Stage mainStage;
    private Button activeNav;

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        system = buildSampleData();

        stage.setTitle("Case Study 9 Course Feedback System");
        stage.setMinWidth(1060);
        stage.setMinHeight(720);
        showLogin();
        stage.show();
    }

    private FeedbackSystem buildSampleData() {
        FeedbackSystem sys = new FeedbackSystem();
        admin = new Admin(1, "Mr.Kalyan", "admin@university.edu");

        sys.addStudent(new Student("Aditya Sahu", "001", "Computer Science", 2));
        sys.addStudent(new Student("M.Bhargav", "002", "Computer Science", 2));
        sys.addStudent(new Student("V.Chetan reddy", "003", "Computer Science", 2));
        sys.addStudent(new Student("Zaheer syed", "004", "Computer Science", 2));

        sys.addFaculty(new Faculty("F001", "Dr.Sarath ", "sarath@amrita.edu", "Computer Science"));
        sys.addFaculty(new Faculty("F002", "Dr. Meena Pillai", "meena@amrita.edu", "Electronics"));
        sys.addFaculty(new Faculty("F003", "Dr. Suresh Iyer", "suresh@amrita.edu", "Mechanical"));

        Course c1 = new Course("23CSE111", "Object Orientated Programming", "Dr.Sarath", "Computer Science", "F001");
        Course c2 = new Course("23EAC201", "Digital Circuits", "Dr. Meena Pillai", "Electronics", "F002");
        Course c3 = new Course("23MEC101", "Engineering Mechanics", "Dr. Suresh Iyer", "Mechanical", "F003");
        sys.addCourse(c1);
        sys.addCourse(c2);
        sys.addCourse(c3);

        Question q1 = new Question(1, "How clear was the course content?");
        Question q2 = new Question(2, "How effective was the teaching methodology?");
        Question q3 = new Question(3, "How well were doubts addressed?");
        sys.addQuestion(q1);
        sys.addQuestion(q2);
        sys.addQuestion(q3);

        addSampleFeedback(sys, sys.getStudents().get(0), c1, new int[] {5, 4, 5}, "Excellent course! Very well structured.");
        addSampleFeedback(sys, sys.getStudents().get(1), c1, new int[] {3, 4, 3}, "Good course but could use more examples.");
        addSampleFeedback(sys, sys.getStudents().get(2), c2, new int[] {4, 5, 4}, "Very engaging lectures and lab sessions.");
        return sys;
    }

    private void addSampleFeedback(FeedbackSystem sys, Student student, Course course, int[] ratings, String comment) {
        Feedback feedback = new Feedback(student, course, sys.getQuestions());
        for (int rating : ratings) {
            feedback.addRating(rating);
        }
        feedback.addComment(comment);
        sys.addFeedback(feedback);
    }

    private void showLogin() {
        activeNav = null;
        BorderPane root = new BorderPane();
        root.getStyleClass().add("app-root");

        VBox brand = new VBox(18);
        brand.getStyleClass().add("brand-panel");
        brand.setPadding(new Insets(36));
        brand.setPrefWidth(440);

        Label kicker = new Label("CASE STUDY 9");
        kicker.getStyleClass().add("brand-kicker");
        Label title = new Label("Course Feedback\nSystem");
        title.getStyleClass().add("brand-title");
        Label copy = new Label("A dashboard for students to submit course feedback and for teachers to review, receive, and verify the responses.");
        copy.getStyleClass().add("brand-copy");
        copy.setWrapText(true);
        HBox metrics = new HBox(12,
                metricCard(String.valueOf(system.getStudents().size()), "Students"),
                metricCard(String.valueOf(system.getFaculties().size()), "Faculty"),
                metricCard(String.valueOf(system.getCourses().size()), "Courses"));
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        brand.getChildren().addAll(kicker, title, copy, metrics, spacer);

        VBox card = new VBox(15);
        card.getStyleClass().add("login-card");
        card.setPadding(new Insets(34));
        card.setMaxWidth(450);

        Label heading = heading("Login");

        ToggleGroup roleGroup = new ToggleGroup();
        RadioButton studentRole = new RadioButton("Student");
        RadioButton adminRole = new RadioButton("Admin");
        studentRole.setToggleGroup(roleGroup);
        adminRole.setToggleGroup(roleGroup);
        studentRole.setSelected(true);

        HBox roles = new HBox(14, studentRole, adminRole);
        Label idLabel = fieldLabel("Student ID");
        TextField idField = input("Enter your student ID...");
        Label secretLabel = fieldLabel("Full name");
        TextField secretField = input("Enter your full name...");
        Label status = new Label();

        roleGroup.selectedToggleProperty().addListener((obs, oldValue, newValue) -> {
            boolean isAdmin = adminRole.isSelected();
            idLabel.setText(isAdmin ? "Admin email" : "Student ID");
            idField.setPromptText(isAdmin ? "Enter admin email..." : "Enter your student ID...");
            secretLabel.setText(isAdmin ? "Password" : "Full name");
            secretField.setPromptText(isAdmin ? "Enter admin password..." : "Enter your full name...");
            idField.clear();
            secretField.clear();
            status.setText("");
        });

        Button login = primaryButton("Login");
        login.setMaxWidth(Double.MAX_VALUE);
        login.setOnAction(event -> {
            if (studentRole.isSelected()) {
                Student student = authenticateStudent(idField.getText(), secretField.getText());
                if (student == null) {
                    showMessage(status, "Invalid student details.", false);
                    return;
                }
                showStudentDashboard(student);
            } else {
                if (!authenticateAdmin(idField.getText(), secretField.getText())) {
                    showMessage(status, "Invalid admin details.", false);
                    return;
                }
                showAdminDashboard();
            }
        });

        card.getChildren().addAll(heading, roles, idLabel, idField, secretLabel, secretField, login, status);

        HBox shell = new HBox(26, brand, card);
        shell.setAlignment(Pos.CENTER);
        shell.setPadding(new Insets(42));
        root.setCenter(shell);
        setAppScene(root);
    }

    private Student authenticateStudent(String id, String name) {
        for (Student student : system.getStudents()) {
            if (student.getStudentId().equalsIgnoreCase(id.trim())
                    && student.getName().equalsIgnoreCase(name.trim())) {
                return student;
            }
        }
        return null;
    }

    private boolean authenticateAdmin(String email, String password) {
        return admin.getAdminMail().equalsIgnoreCase(email.trim()) && "admin123".equals(password.trim());
    }

    private void showStudentDashboard(Student student) {
        activeNav = null;
        BorderPane root = dashboardRoot("Student Portal", student.getName(), event -> showLogin());
        StackPane content = contentArea();

        Button navFeedback = sidebarButton("Submit Feedback");
        Button navHistory = sidebarButton("My Feedbacks");
        Button navProfile = sidebarButton("Profile");

        navFeedback.setOnAction(event -> switchContent(content, navFeedback, createStudentFeedbackPane(student)));
        navHistory.setOnAction(event -> switchContent(content, navHistory, createStudentHistoryPane(student)));
        navProfile.setOnAction(event -> switchContent(content, navProfile, createStudentProfilePane(student)));

        root.setLeft(sidebar("Student", student.getStudentId(), navFeedback, navHistory, navProfile));
        root.setCenter(content);
        switchContent(content, navFeedback, createStudentFeedbackPane(student));
        setAppScene(root);
    }

    private void showAdminDashboard() {
        activeNav = null;
        BorderPane root = dashboardRoot("Admin Dashboard", admin.getAdminName(), event -> showLogin());
        StackPane content = contentArea();

        Button navDashboard = sidebarButton("Dashboard");
        Button navStudents = sidebarButton("Students");
        Button navFaculty = sidebarButton("Faculty");
        Button navCourses = sidebarButton("Courses");
        Button navFeedback = sidebarButton("Feedback");
        Button navReports = sidebarButton("Reports");

        navDashboard.setOnAction(event -> switchContent(content, navDashboard, createAdminDashboardPane()));
        navStudents.setOnAction(event -> switchContent(content, navStudents, createStudentsPane()));
        navFaculty.setOnAction(event -> switchContent(content, navFaculty, createFacultyPane()));
        navCourses.setOnAction(event -> switchContent(content, navCourses, createCoursesPane()));
        navFeedback.setOnAction(event -> switchContent(content, navFeedback, createFeedbackResultsPane()));
        navReports.setOnAction(event -> switchContent(content, navReports, createReportsPane()));

        root.setLeft(sidebar("Admin", admin.getAdminMail(), navDashboard, navStudents, navFaculty, navCourses, navFeedback, navReports));
        root.setCenter(content);
        switchContent(content, navDashboard, createAdminDashboardPane());
        setAppScene(root);
    }

    private Node createAdminDashboardPane() {
        VBox content = pageBox();
        content.getChildren().add(heading("Dashboard"));

        HBox stats = new HBox(12,
                metricCard(String.valueOf(system.getStudents().size()), "Students"),
                metricCard(String.valueOf(system.getFaculties().size()), "Faculty"),
                metricCard(String.valueOf(system.getCourses().size()), "Courses"),
                metricCard(String.valueOf(system.getFeedbacks().size()), "Feedbacks"));

        VBox chartCard = cardBox();
        chartCard.getChildren().addAll(subHeading("Average Rating By Course"), ratingChart());

        VBox recentCard = cardBox();
        recentCard.getChildren().add(subHeading("Recent Feedback"));
        recentCard.getChildren().add(recentFeedbackList());

        content.getChildren().addAll(stats, chartCard, recentCard);
        return scroll(content);
    }

    private Node createStudentFeedbackPane(Student student) {
        VBox content = pageBox();
        content.getChildren().add(heading("Submit Feedback"));

        ComboBox<Course> courseCombo = new ComboBox<>(FXCollections.observableArrayList(system.getCourses()));
        courseCombo.setMaxWidth(Double.MAX_VALUE);
        configureCourseCombo(courseCombo);

        GridPane selectForm = formGrid();
        selectForm.add(fieldLabel("Course"), 0, 0);
        selectForm.add(courseCombo, 1, 0);

        VBox questionsBox = cardBox();
        questionsBox.getChildren().add(subHeading("Rate Each Question"));
        questionsBox.getChildren().add(ratingScale());

        ArrayList<Slider> sliders = new ArrayList<>();
        for (Question question : system.getQuestions()) {
            HBox row = new HBox(12);
            row.setAlignment(Pos.CENTER_LEFT);

            Label questionLabel = new Label("Q" + question.getQuestionNo() + ". " + question.getQuestion());
            questionLabel.getStyleClass().add("question-label");
            questionLabel.setWrapText(true);
            questionLabel.setMinWidth(320);
            questionLabel.setMaxWidth(430);

            Slider slider = new Slider(1, 5, 3);
            slider.setShowTickMarks(true);
            slider.setShowTickLabels(true);
            slider.setSnapToTicks(true);
            slider.setMajorTickUnit(1);
            slider.setMinorTickCount(0);
            HBox.setHgrow(slider, Priority.ALWAYS);

            Label value = new Label(ratingText(3));
            value.getStyleClass().add("slider-value");
            slider.valueProperty().addListener((obs, oldValue, newValue) -> value.setText(ratingText(newValue.intValue())));

            sliders.add(slider);
            row.getChildren().addAll(questionLabel, slider, value);
            questionsBox.getChildren().add(row);
        }

        TextArea comment = new TextArea();
        comment.setPromptText("Write your comments here...");
        comment.setPrefRowCount(4);

        Label status = new Label();
        Button submit = primaryButton("Submit Feedback");
        submit.setOnAction(event -> {
            Course selectedCourse = courseCombo.getValue();
            if (selectedCourse == null) {
                showMessage(status, "Please select a course.", false);
                return;
            }

            Feedback feedback = new Feedback(student, selectedCourse, system.getQuestions());
            for (Slider slider : sliders) {
                feedback.addRating((int) slider.getValue());
            }
            if (!comment.getText().trim().isEmpty()) {
                feedback.addComment(comment.getText().trim());
            }
            system.addFeedback(feedback);
            FileManager.appendFeedback(feedback);

            courseCombo.setValue(null);
            comment.clear();
            for (Slider slider : sliders) {
                slider.setValue(3);
            }
            showMessage(status, String.format("Feedback submitted. Average %.2f / 5.00", averageOf(feedback)), true);
        });

        content.getChildren().addAll(selectForm, questionsBox, fieldLabel("Comments"), comment, submit, status);
        return scroll(content);
    }

    private Node createStudentHistoryPane(Student student) {
        VBox content = pageBox();
        TableView<Feedback> table = feedbackTable(false);
        table.setItems(FXCollections.observableArrayList(student.getFeedbacks()));
        content.getChildren().addAll(heading("My Feedbacks"), table);
        VBox.setVgrow(table, Priority.ALWAYS);
        return content;
    }

    private Node createStudentProfilePane(Student student) {
        VBox content = pageBox();
        content.getChildren().addAll(
                heading("Profile"),
                detailRow("Name", student.getName()),
                detailRow("Student ID", student.getStudentId()),
                detailRow("Department", student.getDepartment()),
                detailRow("Semester", String.valueOf(student.getSemester())),
                detailRow("Feedbacks submitted", String.valueOf(student.getFeedbacks().size()))
        );
        return scroll(content);
    }

    private Node createStudentsPane() {
        VBox content = pageBox();
        TableView<Student> table = studentTable();
        refreshStudents(table);

        TextField id = input("S005");
        TextField name = input("Student name");
        TextField dept = input("Department");
        TextField sem = input("Semester");

        GridPane form = formGrid();
        form.add(fieldLabel("Student ID"), 0, 0);
        form.add(id, 1, 0);
        form.add(fieldLabel("Name"), 0, 1);
        form.add(name, 1, 1);
        form.add(fieldLabel("Department"), 0, 2);
        form.add(dept, 1, 2);
        form.add(fieldLabel("Semester"), 0, 3);
        form.add(sem, 1, 3);

        Label status = new Label();
        Button add = primaryButton("Add Student");
        Button load = secondaryButton("Load Selected");
        Button update = secondaryButton("Update Selected");

        add.setOnAction(event -> {
            try {
                if (blank(id, name, dept, sem)) {
                    showMessage(status, "Fill all student fields.", false);
                    return;
                }
                system.addStudent(new Student(name.getText().trim(), id.getText().trim(), dept.getText().trim(), Integer.parseInt(sem.getText().trim())));
                clear(id, name, dept, sem);
                refreshStudents(table);
                showMessage(status, "Student added.", true);
            } catch (Exception ex) {
                showMessage(status, ex.getMessage(), false);
            }
        });

        load.setOnAction(event -> {
            Student selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showMessage(status, "Select a student first.", false);
                return;
            }
            id.setText(selected.getStudentId());
            id.setDisable(true);
            name.setText(selected.getName());
            dept.setText(selected.getDepartment());
            sem.setText(String.valueOf(selected.getSemester()));
        });

        update.setOnAction(event -> {
            try {
                Student selected = table.getSelectionModel().getSelectedItem();
                if (selected == null) {
                    showMessage(status, "Select a student first.", false);
                    return;
                }
                selected.setName(name.getText().trim());
                selected.setDepartment(dept.getText().trim());
                selected.setSemester(Integer.parseInt(sem.getText().trim()));
                clear(id, name, dept, sem);
                refreshStudents(table);
                showMessage(status, "Student updated.", true);
            } catch (Exception ex) {
                showMessage(status, ex.getMessage(), false);
            }
        });

        HBox actions = new HBox(10, add, load, update, status);
        actions.setAlignment(Pos.CENTER_LEFT);
        content.getChildren().addAll(heading("Students"), table, subHeading("Add Or Modify Student"), form, actions);
        VBox.setVgrow(table, Priority.ALWAYS);
        return content;
    }

    private Node createFacultyPane() {
        VBox content = pageBox();
        TableView<Faculty> table = facultyTable();
        refreshFaculty(table);

        TextField id = input("F004");
        TextField name = input("Faculty name");
        TextField mail = input("faculty@university.edu");
        TextField dept = input("Department");

        GridPane form = formGrid();
        form.add(fieldLabel("Faculty ID"), 0, 0);
        form.add(id, 1, 0);
        form.add(fieldLabel("Name"), 0, 1);
        form.add(name, 1, 1);
        form.add(fieldLabel("Email"), 0, 2);
        form.add(mail, 1, 2);
        form.add(fieldLabel("Department"), 0, 3);
        form.add(dept, 1, 3);

        Label status = new Label();
        Button add = primaryButton("Add Faculty");
        Button load = secondaryButton("Load Selected");
        Button update = secondaryButton("Update Selected");

        add.setOnAction(event -> {
            try {
                if (blank(id, name, mail, dept)) {
                    showMessage(status, "Fill all faculty fields.", false);
                    return;
                }
                system.addFaculty(new Faculty(id.getText().trim(), name.getText().trim(), mail.getText().trim(), dept.getText().trim()));
                clear(id, name, mail, dept);
                refreshFaculty(table);
                showMessage(status, "Faculty added.", true);
            } catch (Exception ex) {
                showMessage(status, ex.getMessage(), false);
            }
        });

        load.setOnAction(event -> {
            Faculty selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showMessage(status, "Select a faculty member first.", false);
                return;
            }
            id.setText(selected.getFacultyId());
            id.setDisable(true);
            name.setText(selected.getFacultyName());
            mail.setText(selected.getFacultyMail());
            dept.setText(selected.getDepartment());
        });

        update.setOnAction(event -> {
            try {
                Faculty selected = table.getSelectionModel().getSelectedItem();
                if (selected == null) {
                    showMessage(status, "Select a faculty member first.", false);
                    return;
                }
                selected.setFacultyName(name.getText().trim());
                selected.setFacultyMail(mail.getText().trim());
                selected.setDepartment(dept.getText().trim());
                clear(id, name, mail, dept);
                refreshFaculty(table);
                showMessage(status, "Faculty updated.", true);
            } catch (Exception ex) {
                showMessage(status, ex.getMessage(), false);
            }
        });

        HBox actions = new HBox(10, add, load, update, status);
        actions.setAlignment(Pos.CENTER_LEFT);
        content.getChildren().addAll(heading("Faculty"), table, subHeading("Add Or Modify Faculty"), form, actions);
        VBox.setVgrow(table, Priority.ALWAYS);
        return content;
    }

    private Node createCoursesPane() {
        VBox content = pageBox();
        TableView<Course> table = courseTable();
        refreshCourses(table);

        TextField code = input("CS401");
        TextField name = input("Course name");
        TextField instructor = input("Instructor name");
        TextField subject = input("Subject");
        TextField instructorId = input("F001");

        GridPane form = formGrid();
        form.add(fieldLabel("Course Code"), 0, 0);
        form.add(code, 1, 0);
        form.add(fieldLabel("Course Name"), 0, 1);
        form.add(name, 1, 1);
        form.add(fieldLabel("Instructor"), 0, 2);
        form.add(instructor, 1, 2);
        form.add(fieldLabel("Subject"), 0, 3);
        form.add(subject, 1, 3);
        form.add(fieldLabel("Instructor ID"), 0, 4);
        form.add(instructorId, 1, 4);

        Label status = new Label();
        Button add = primaryButton("Add Course");
        Button load = secondaryButton("Load Selected");
        Button update = secondaryButton("Update Selected");

        add.setOnAction(event -> {
            try {
                if (blank(code, name, instructor, subject, instructorId)) {
                    showMessage(status, "Fill all course fields.", false);
                    return;
                }
                system.addCourse(new Course(code.getText().trim(), name.getText().trim(), instructor.getText().trim(), subject.getText().trim(), instructorId.getText().trim()));
                clear(code, name, instructor, subject, instructorId);
                refreshCourses(table);
                showMessage(status, "Course added.", true);
            } catch (Exception ex) {
                showMessage(status, ex.getMessage(), false);
            }
        });

        load.setOnAction(event -> {
            Course selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showMessage(status, "Select a course first.", false);
                return;
            }
            code.setText(selected.getCourseCode());
            code.setDisable(true);
            name.setText(selected.getCourseName());
            instructor.setText(selected.getInstructor());
            subject.setText(selected.getSubject());
            instructorId.setText(selected.getInstructorId());
        });

        update.setOnAction(event -> {
            Course selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showMessage(status, "Select a course first.", false);
                return;
            }
            selected.setCourseName(name.getText().trim());
            selected.setInstructor(instructor.getText().trim());
            selected.setSubject(subject.getText().trim());
            selected.setInstructorId(instructorId.getText().trim());
            clear(code, name, instructor, subject, instructorId);
            refreshCourses(table);
            showMessage(status, "Course updated.", true);
        });

        HBox actions = new HBox(10, add, load, update, status);
        actions.setAlignment(Pos.CENTER_LEFT);
        content.getChildren().addAll(heading("Courses"), table, subHeading("Add Or Modify Course"), form, actions);
        VBox.setVgrow(table, Priority.ALWAYS);
        return content;
    }

    private Node createFeedbackResultsPane() {
        VBox content = pageBox();
        TableView<Feedback> table = feedbackTable(true);
        HBox stats = new HBox(12);
        Label status = new Label();

        Runnable refresh = () -> {
            table.setItems(FXCollections.observableArrayList(system.getFeedbacks()));
            stats.getChildren().setAll(
                    metricCard(String.valueOf(system.getFeedbacks().size()), "Feedbacks"),
                    metricCard(String.format("%.2f", overallAverage()), "Overall Avg"),
                    metricCard(String.valueOf(system.getCourses().size()), "Courses"));
        };
        refresh.run();

        Button reset = secondaryButton("Reset All Feedbacks");
        reset.getStyleClass().add("danger-button");
        reset.setOnAction(event -> {
            system.resetFeedbacks();
            refresh.run();
            showMessage(status, "All feedbacks were reset. The system is ready for new responses.", true);
        });

        HBox actions = new HBox(10, reset, status);
        actions.setAlignment(Pos.CENTER_LEFT);

        content.getChildren().addAll(heading("Feedback Results"), stats, table, actions);
        VBox.setVgrow(table, Priority.ALWAYS);
        return content;
    }

    private Node createReportsPane() {
        VBox content = pageBox();
        content.getChildren().add(heading("Reports"));

        ComboBox<Course> courseCombo = new ComboBox<>(FXCollections.observableArrayList(system.getCourses()));
        courseCombo.setMaxWidth(Double.MAX_VALUE);
        configureCourseCombo(courseCombo);

        GridPane form = formGrid();
        form.add(fieldLabel("Course"), 0, 0);
        form.add(courseCombo, 1, 0);

        VBox output = cardBox();
        output.getChildren().add(muted("Select a course and generate a report."));

        Button generate = primaryButton("Generate Report");
        generate.setOnAction(event -> {
            output.getChildren().clear();
            Course course = courseCombo.getValue();
            if (course == null) {
                output.getChildren().add(muted("Please select a course."));
                return;
            }
            ArrayList<Feedback> list = feedbacksForCourse(course);
            FileManager.appendCourseReport(course, list);
            output.getChildren().add(subHeading(course.getCourseName() + " Report"));
            output.getChildren().add(new HBox(12,
                    metricCard(String.valueOf(list.size()), "Responses"),
                    metricCard(String.format("%.2f", courseAverage(course)), "Course Avg"),
                    metricCard(course.getInstructor(), "Instructor")));

            if (list.isEmpty()) {
                output.getChildren().add(muted("No feedbacks for this course."));
                return;
            }
            for (Feedback feedback : list) {
                output.getChildren().add(feedbackCard(feedback));
            }
        });

        content.getChildren().addAll(form, generate, output);
        return scroll(content);
    }

    private TableView<Student> studentTable() {
        TableView<Student> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn<Student, String> id = new TableColumn<>("ID");
        id.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getStudentId()));
        TableColumn<Student, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));
        TableColumn<Student, String> dept = new TableColumn<>("Department");
        dept.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDepartment()));
        TableColumn<Student, Integer> sem = new TableColumn<>("Semester");
        sem.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getSemester()).asObject());
        table.getColumns().addAll(id, name, dept, sem);
        return table;
    }

    private TableView<Faculty> facultyTable() {
        TableView<Faculty> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn<Faculty, String> id = new TableColumn<>("ID");
        id.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFacultyId()));
        TableColumn<Faculty, String> name = new TableColumn<>("Name");
        name.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFacultyName()));
        TableColumn<Faculty, String> mail = new TableColumn<>("Email");
        mail.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getFacultyMail()));
        TableColumn<Faculty, String> dept = new TableColumn<>("Department");
        dept.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getDepartment()));
        table.getColumns().addAll(id, name, mail, dept);
        return table;
    }

    private TableView<Course> courseTable() {
        TableView<Course> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn<Course, String> code = new TableColumn<>("Code");
        code.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCourseCode()));
        TableColumn<Course, String> name = new TableColumn<>("Course");
        name.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCourseName()));
        TableColumn<Course, String> instructor = new TableColumn<>("Instructor");
        instructor.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getInstructor()));
        TableColumn<Course, String> subject = new TableColumn<>("Subject");
        subject.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getSubject()));
        TableColumn<Course, String> avg = new TableColumn<>("Avg");
        avg.setCellValueFactory(d -> new SimpleStringProperty(String.format("%.2f", courseAverage(d.getValue()))));
        table.getColumns().addAll(code, name, instructor, subject, avg);
        return table;
    }

    private TableView<Feedback> feedbackTable(boolean includeStudent) {
        TableView<Feedback> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        if (includeStudent) {
            TableColumn<Feedback, String> student = new TableColumn<>("Student");
            student.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getStudent().getName()));
            table.getColumns().add(student);
        }

        TableColumn<Feedback, String> course = new TableColumn<>("Course");
        course.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCourse().getCourseName()));
        TableColumn<Feedback, String> avg = new TableColumn<>("Avg");
        avg.setCellValueFactory(d -> new SimpleStringProperty(String.format("%.2f", averageOf(d.getValue()))));
        TableColumn<Feedback, String> ratings = new TableColumn<>("Ratings");
        ratings.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getRatings().toString()));
        TableColumn<Feedback, String> comment = new TableColumn<>("Comment");
        comment.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getComment().isEmpty() ? "N/A" : d.getValue().getComment()));

        table.getColumns().addAll(course, avg, ratings, comment);
        return table;
    }

    private void refreshStudents(TableView<Student> table) {
        table.setItems(FXCollections.observableArrayList(system.getStudents()));
    }

    private void refreshFaculty(TableView<Faculty> table) {
        table.setItems(FXCollections.observableArrayList(system.getFaculties()));
    }

    private void refreshCourses(TableView<Course> table) {
        table.setItems(FXCollections.observableArrayList(system.getCourses()));
    }

    private BarChart<String, Number> ratingChart() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(0, 5, 1);
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setAnimated(true);
        chart.setMinHeight(290);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Course course : system.getCourses()) {
            series.getData().add(new XYChart.Data<>(course.getCourseCode(), courseAverage(course)));
        }
        chart.getData().add(series);
        return chart;
    }

    private VBox recentFeedbackList() {
        VBox list = new VBox(10);
        ArrayList<Feedback> feedbacks = system.getFeedbacks();
        Collections.reverse(feedbacks);
        for (Feedback feedback : feedbacks) {
            list.getChildren().add(feedbackCard(feedback));
        }
        if (list.getChildren().isEmpty()) {
            list.getChildren().add(muted("No feedbacks submitted yet."));
        }
        return list;
    }

    private HBox feedbackCard(Feedback feedback) {
        HBox card = new HBox(12);
        card.getStyleClass().add("feedback-card");
        card.setAlignment(Pos.CENTER_LEFT);

        StackPane avatar = new StackPane();
        Circle circle = new Circle(21);
        circle.getStyleClass().add("avatar-circle");
        Text text = new Text(initials(feedback.getStudent().getName()));
        text.getStyleClass().add("avatar-text");
        avatar.getChildren().addAll(circle, text);

        VBox info = new VBox(3);
        Label name = new Label(feedback.getStudent().getName());
        name.getStyleClass().add("card-name");
        Label course = new Label(feedback.getCourse().getCourseName());
        course.getStyleClass().add("card-course");
        Label comment = new Label(feedback.getComment().isEmpty() ? "No comment" : feedback.getComment());
        comment.getStyleClass().add("card-comment");
        comment.setWrapText(true);
        info.getChildren().addAll(name, course, comment);
        HBox.setHgrow(info, Priority.ALWAYS);

        Label rating = new Label(String.format("Avg %.1f", averageOf(feedback)));
        rating.getStyleClass().add("rating-badge");

        card.getChildren().addAll(avatar, info, rating);
        return card;
    }

    private ArrayList<Feedback> feedbacksForCourse(Course course) {
        ArrayList<Feedback> list = new ArrayList<>();
        for (Feedback feedback : system.getFeedbacks()) {
            if (feedback.getCourse().getCourseCode().equalsIgnoreCase(course.getCourseCode())) {
                list.add(feedback);
            }
        }
        return list;
    }

    private void configureCourseCombo(ComboBox<Course> comboBox) {
        comboBox.setVisibleRowCount(6);
        comboBox.setCellFactory(listView -> new ListCell<Course>() {
            @Override
            protected void updateItem(Course course, boolean empty) {
                super.updateItem(course, empty);
                setText(empty || course == null ? null : courseDisplay(course));
            }
        });
        comboBox.setButtonCell(new ListCell<Course>() {
            @Override
            protected void updateItem(Course course, boolean empty) {
                super.updateItem(course, empty);
                setText(empty || course == null ? null : courseDisplay(course));
            }
        });
    }

    private String courseDisplay(Course course) {
        return course.getCourseCode() + " - " + course.getCourseName() + " (" + course.getInstructor() + ")";
    }

    private HBox ratingScale() {
        HBox scale = new HBox(10,
                ratingScaleItem("1", "Bad"),
                ratingScaleItem("2", "Okay"),
                ratingScaleItem("3", "Average"),
                ratingScaleItem("4", "Good"),
                ratingScaleItem("5", "Excellent"));
        scale.getStyleClass().add("rating-scale");
        scale.setAlignment(Pos.CENTER_LEFT);
        return scale;
    }

    private VBox ratingScaleItem(String number, String label) {
        Label num = new Label(number);
        num.getStyleClass().add("rating-scale-number");
        Label text = new Label(label);
        text.getStyleClass().add("rating-scale-label");
        VBox box = new VBox(2, num, text);
        box.getStyleClass().add("rating-scale-item");
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private String ratingText(int rating) {
        switch (rating) {
            case 1:
                return "1 - Bad";
            case 2:
                return "2 - Okay";
            case 3:
                return "3 - Average";
            case 4:
                return "4 - Good";
            case 5:
                return "5 - Excellent";
            default:
                return String.valueOf(rating);
        }
    }

    private double courseAverage(Course course) {
        ArrayList<Feedback> list = feedbacksForCourse(course);
        if (list.isEmpty()) return 0.0;
        double sum = 0;
        for (Feedback feedback : list) {
            sum += averageOf(feedback);
        }
        return sum / list.size();
    }

    private double overallAverage() {
        ArrayList<Feedback> list = system.getFeedbacks();
        if (list.isEmpty()) return 0.0;
        double sum = 0;
        for (Feedback feedback : list) {
            sum += averageOf(feedback);
        }
        return sum / list.size();
    }

    private double averageOf(Feedback feedback) {
        ArrayList<Integer> ratings = feedback.getRatings();
        if (ratings.isEmpty()) return 0.0;
        double sum = 0;
        for (int rating : ratings) {
            sum += rating;
        }
        return sum / ratings.size();
    }

    private String initials(String name) {
        StringBuilder result = new StringBuilder();
        for (String word : name.trim().split("\\s+")) {
            if (!word.isEmpty() && result.length() < 2) {
                result.append(Character.toUpperCase(word.charAt(0)));
            }
        }
        return result.length() == 0 ? "NA" : result.toString();
    }

    private BorderPane dashboardRoot(String title, String user, javafx.event.EventHandler<javafx.event.ActionEvent> logoutAction) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("app-root");
        root.setTop(dashboardHeader(title, user, logoutAction));
        return root;
    }

    private HBox dashboardHeader(String title, String user, javafx.event.EventHandler<javafx.event.ActionEvent> logoutAction) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("app-title");
        Label userLabel = new Label(user);
        userLabel.getStyleClass().add("app-subtitle");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button logout = secondaryButton("Logout");
        logout.setOnAction(logoutAction);
        HBox header = new HBox(14, titleLabel, new Separator(), userLabel, spacer, logout);
        header.getStyleClass().add("app-header");
        header.setPadding(new Insets(16, 24, 16, 24));
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    private VBox sidebar(String role, String user, Button... buttons) {
        VBox sidebar = new VBox(16);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPadding(new Insets(22));
        sidebar.setPrefWidth(235);

        Label title = new Label("Feedback Hub");
        title.getStyleClass().add("sidebar-title");
        Label roleLabel = new Label(role);
        roleLabel.getStyleClass().add("sidebar-role");
        Label userLabel = new Label(user);
        userLabel.getStyleClass().add("sidebar-user");
        userLabel.setWrapText(true);

        VBox nav = new VBox(8);
        nav.getChildren().addAll(buttons);
        for (Button button : buttons) {
            button.setMaxWidth(Double.MAX_VALUE);
        }

        sidebar.getChildren().addAll(title, roleLabel, userLabel, new Separator(), nav);
        return sidebar;
    }

    private Button sidebarButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("nav-button");
        button.setAlignment(Pos.CENTER_LEFT);
        return button;
    }

    private StackPane contentArea() {
        StackPane area = new StackPane();
        area.getStyleClass().add("content-area");
        return area;
    }

    private void switchContent(StackPane area, Button button, Node content) {
        if (activeNav != null) {
            activeNav.getStyleClass().remove("nav-active");
        }
        button.getStyleClass().add("nav-active");
        activeNav = button;

        content.setOpacity(0);
        area.getChildren().setAll(content);
        FadeTransition fade = new FadeTransition(Duration.millis(220), content);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    private VBox pageBox() {
        VBox box = new VBox(16);
        box.setPadding(new Insets(24));
        return box;
    }

    private VBox cardBox() {
        VBox box = new VBox(12);
        box.getStyleClass().add("surface-card");
        box.setPadding(new Insets(18));
        return box;
    }

    private GridPane formGrid() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("surface-card");
        grid.setPadding(new Insets(18));
        grid.setHgap(12);
        grid.setVgap(12);
        ColumnConstraints labelCol = new ColumnConstraints();
        labelCol.setMinWidth(130);
        ColumnConstraints inputCol = new ColumnConstraints();
        inputCol.setHgrow(Priority.ALWAYS);
        grid.getColumnConstraints().addAll(labelCol, inputCol);
        return grid;
    }

    private HBox detailRow(String label, String value) {
        Label left = new Label(label);
        left.getStyleClass().add("field-label");
        left.setMinWidth(170);
        Label right = new Label(value);
        right.getStyleClass().add("detail-value");
        right.setWrapText(true);
        HBox row = new HBox(12, left, right);
        row.getStyleClass().add("detail-row");
        row.setPadding(new Insets(10, 12, 10, 12));
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private VBox metricCard(String value, String label) {
        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add("metric-value");
        Label labelText = new Label(label);
        labelText.getStyleClass().add("metric-label");
        VBox card = new VBox(3, valueLabel, labelText);
        card.getStyleClass().add("metric-card");
        card.setPadding(new Insets(13, 18, 13, 18));
        card.setMinWidth(115);
        return card;
    }

    private Label heading(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("section-title");
        return label;
    }

    private Label subHeading(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("section-subtitle");
        return label;
    }

    private Label fieldLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("field-label");
        return label;
    }

    private Label muted(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("muted-label");
        label.setWrapText(true);
        return label;
    }

    private TextField input(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setMaxWidth(Double.MAX_VALUE);
        return field;
    }

    private Button primaryButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("primary-button");
        return button;
    }

    private Button secondaryButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("secondary-button");
        return button;
    }

    private ScrollPane scroll(Node node) {
        ScrollPane pane = new ScrollPane(node);
        pane.setFitToWidth(true);
        return pane;
    }

    private boolean blank(TextField... fields) {
        for (TextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void clear(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
            field.setDisable(false);
        }
    }

    private void showMessage(Label label, String message, boolean success) {
        label.setText(message);
        label.getStyleClass().removeAll("status-ok", "status-err");
        label.getStyleClass().add(success ? "status-ok" : "status-err");
    }

    private void setAppScene(BorderPane root) {
        Scene scene = new Scene(root, 1080, 720);
        java.net.URL stylesheet = getClass().getResource("app.css");
        if (stylesheet != null) {
            scene.getStylesheets().add(stylesheet.toExternalForm());
        }
        mainStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
