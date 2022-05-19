package application;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RunApplication extends Application{

	//private Scene root;
	private Label welcome, editLabel, addStudLabel, addCourseLabel, loadLabel, loadStudLabel, loadCourseLabel;
	private Label removeStudLabel, removeCourseLabel, loadCourseStudList;
	private RadioButton edit, load, addStud, addCourse, removeStud, removeCourse, loadStud, loadCourse, loadStudsInCourse;
	private Button rootSelect, loadSelect, loadStudSearch, loadCourseSearch, editSelect, addStudSelect, addCourseSelect;
	private Button removeStudSearch, removeCourseSearch, studentRemovalSelect, courseRemovalSelect, studsInCourseSearch;
	private TextField enterStudentLoad, enterCourseLoad, studNameFieldS, courseNameFieldS, gradeFieldS, degreeFieldS, courseFieldC, instructorFieldC;
	private TextField remStudField, remCourseField, addStudNum, courseToFindStuds;
	private TextArea studInfoLoad, courseInfoLoad, studInfoRem, courseInfoRem, studsInCourseInfo;
	private CheckBox addGrad, remGrad;
	private boolean remCourseFound = false;
	private boolean isValidID = true;
	private boolean remStudentFound = false;
	private Course courseToRemove;
	private Student studToRemove;
	private Graduate gradToRemove;
	
	DBManager db = new DBManager();
	
	public static void main(String[] args) {
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		try {
		db.reloadData();
		System.out.println("Data reloaded successfully");
		}
		catch(Exception e)
		{
			System.out.println("Error: Unable to Reload Data." + e.getMessage());
		}
		ToggleGroup tog = new ToggleGroup();
		
		welcome = new Label("Welcome to the Student Directory");
		edit = new RadioButton("Edit Data");
		load = new RadioButton("Load Data");
		rootSelect = new Button("Select");
		Label instructions = new Label("Student database manager by Daniel Huynh.");
		Label instruct2 = new Label(" Edit or load student and course information with the choices above.");
		
		edit.setToggleGroup(tog);
		load.setToggleGroup(tog);
		edit.setSelected(true);
		
		HBox choices = new HBox(10, edit, load);
		choices.setAlignment(Pos.CENTER);
		
		welcome.setStyle("-fx-font-size: 25 pt; -fx-font-weight:bold");
		choices.setStyle("-fx-font-weight:bold");
		rootSelect.setStyle("-fx-font-weight:bold");
		
		GridPane grid = new GridPane();
		grid.add(welcome, 0, 0);
		grid.add(choices, 0, 1);
		grid.add(rootSelect, 0, 2);
		grid.add(instructions, 0, 3);
		grid.add(instruct2, 0, 4);
		grid.getColumnConstraints().add(new ColumnConstraints(480));
		grid.setHgap(20);
		grid.setVgap(20);
		
		GridPane grid2 = new GridPane();
		grid2.add(instructions, 0, 0);
		grid2.add(instruct2, 0, 1);
		grid2.getColumnConstraints().add(new ColumnConstraints(480));
		
		GridPane finalGrid = new GridPane();
		finalGrid.add(grid, 0, 0);
		finalGrid.add(grid2, 0, 1);
		finalGrid.setVgap(100);
		finalGrid.getColumnConstraints().add(new ColumnConstraints(480));
		
		GridPane.setHalignment(welcome, HPos.CENTER);
		GridPane.setHalignment(rootSelect, HPos.CENTER);
		GridPane.setHalignment(instructions, HPos.CENTER);
		GridPane.setHalignment(instruct2, HPos.CENTER);
		GridPane.setHalignment(choices, HPos.CENTER);
		
		grid.setPadding(new Insets(10));
		
		//Pane canvas = new Pane();		
		Scene root = new Scene(finalGrid, 500, 300, Color.AQUA);
		
		primaryStage.setTitle("Student Directory by Daniel Huynh");
		primaryStage.setScene(root);
		primaryStage.show();
		
		rootSelect.setOnAction(event -> {
			if(edit.isSelected())
			{
				editScene(primaryStage);
			}
			else if(load.isSelected())
			{
				loadScene(primaryStage);
			}
		});
	}
	
	public void editScene(Stage primaryStage)
	{
		Button back = new Button("Back");
		editLabel = new Label("Edit Data");
		addStud = new RadioButton("Add Student");
		addCourse = new RadioButton("Add Course");
		removeStud = new RadioButton("Remove Student");
		removeCourse = new RadioButton("Remove Course");
		editSelect = new Button("Select");
		
		editLabel.setStyle("-fx-font-size: 25 pt; -fx-font-weight:bold");
		addStud.setStyle("-fx-font-weight:bold");
		addCourse.setStyle("-fx-font-weight:bold");
		removeStud.setStyle("-fx-font-weight:bold");
		removeCourse.setStyle("-fx-font-weight:bold");
		editSelect.setStyle("-fx-font-weight:bold");
		
		ToggleGroup tog = new ToggleGroup();
		addStud.setToggleGroup(tog);
		addCourse.setToggleGroup(tog);
		removeStud.setToggleGroup(tog);
		removeCourse.setToggleGroup(tog);
		addStud.setSelected(true);
		
		GridPane grid = new GridPane();
		grid.add(editLabel, 0, 0);
		grid.add(addStud, 0, 1);
		grid.add(addCourse, 0, 2);
		grid.add(removeStud, 0, 3);
		grid.add(removeCourse, 0, 4);
		grid.add(editSelect, 0, 5);
		grid.add(back, 0, 6);
		
		grid.setPadding(new Insets(10));
		
		grid.getColumnConstraints().add(new ColumnConstraints(480));
		GridPane.setHalignment(editLabel, HPos.CENTER);
		GridPane.setHalignment(editSelect, HPos.CENTER);
		
		Scene sceneEdit = new Scene(grid, 500, 300);
		
		primaryStage.setTitle("Edit Data");
		primaryStage.setScene(sceneEdit);
		primaryStage.show();
		
		back.setOnAction(event -> {
			start(primaryStage);
		});
		
		editSelect.setOnAction(event -> {
			if(addStud.isSelected())
			{
				addStudScene(primaryStage);
			}
			else if(addCourse.isSelected())
			{
				addCourseScene(primaryStage);
			}
			else if(removeStud.isSelected())
			{
				removeStudentScene(primaryStage);
			}
			else if(removeCourse.isSelected())
			{
				removeCourseScene(primaryStage);
			}
		});
	}
	
	public void loadScene(Stage primaryStage)
	{
		Button back = new Button("Back");
		loadLabel = new Label("Load Data");
		loadStud = new RadioButton("Load Student Data");
		loadCourse = new RadioButton("Load Course Data");
		loadStudsInCourse = new RadioButton("Load Course Student Lists");
		loadSelect = new Button("Select");
		
		loadLabel.setStyle("-fx-font-size: 25 pt; -fx-font-weight:bold");
		loadStud.setStyle("-fx-font-weight:bold");
		loadCourse.setStyle("-fx-font-weight:bold");
		loadSelect.setStyle("-fx-font-weight:bold");
		loadStudsInCourse.setStyle("-fx-font-weight:bold");
		
		ToggleGroup tog = new ToggleGroup();
		loadStud.setToggleGroup(tog);
		loadCourse.setToggleGroup(tog);
		loadStudsInCourse.setToggleGroup(tog);
		loadStud.setSelected(true);
		
		GridPane grid = new GridPane();
		grid.add(loadLabel, 0, 0);
		grid.add(loadStud, 0, 1);
		grid.add(loadCourse, 0, 2);
		grid.add(loadStudsInCourse, 0, 3);
		grid.add(loadSelect, 0, 4);
		grid.add(back, 0, 5);
		grid.setPadding(new Insets(10));

		grid.getColumnConstraints().add(new ColumnConstraints(480));
		GridPane.setHalignment(loadLabel, HPos.CENTER);
		GridPane.setHalignment(loadSelect, HPos.CENTER);
		
		Scene sceneLoad = new Scene(grid, 500, 300);
		
		primaryStage.setTitle("Load Data");
		primaryStage.setScene(sceneLoad);
		primaryStage.show();
		
		loadSelect.setOnAction(event -> {
			
			if(loadStud.isSelected())
			{
				loadStudScene(primaryStage);
			}
			else if(loadCourse.isSelected())
			{
				loadCourseScene(primaryStage);
			}
			else if(loadStudsInCourse.isSelected())
			{
				loadStudentsInCourse(primaryStage);
			}
		});
		
		back.setOnAction(event -> {
			start(primaryStage);
		});
	}

	public void loadStudScene(Stage primaryStage)
	{
		ArrayList<Student> s = db.getStudentList();
		ArrayList<Graduate> g = db.getGraduateList();
		
		Button back = new Button("Back");
		CheckBox grad = new CheckBox("Check if graduate");
		loadStudLabel = new Label("Load Student Data");
		enterStudentLoad = new TextField("Enter student ID");
		loadStudSearch = new Button("Search");
		studInfoLoad = new TextArea("Student information will appear here when searched");
		enterStudentLoad.setPrefWidth(275);
		grad.setPrefWidth(275);
		studInfoLoad.setEditable(false);
		studInfoLoad.setWrapText(true);
		
		
		VBox vbox = new VBox(10, enterStudentLoad, loadStudSearch, grad);
		HBox hbox = new HBox(10, vbox, studInfoLoad);
		
		GridPane grid = new GridPane();
		grid.add(loadStudLabel, 0, 0);
		grid.add(hbox, 0, 1);
		grid.add(back, 0, 2);
		grid.setPadding(new Insets(10));
		
		grid.getColumnConstraints().add(new ColumnConstraints(480));
		GridPane.setHalignment(loadStudLabel, HPos.CENTER);
		loadStudLabel.setStyle("-fx-font-size: 25 pt; -fx-font-weight:bold");
		loadStudSearch.setStyle("-fx-font-weight:bold");
		
		Scene sceneLoadStud = new Scene(grid, 500, 300);
		
		primaryStage.setTitle("Load Student Data");
		primaryStage.setScene(sceneLoadStud);
		primaryStage.show();
		
		back.setOnAction(event -> {
			loadScene(primaryStage);
		});
		
		loadStudSearch.setOnAction(event -> {
			String input;
			input = enterStudentLoad.getText();
			boolean isValid = true;
			
			try {
			for(int i = 0; i < input.length(); i++)
			{
				if(!Character.isDigit(input.charAt(i)))
				{
					isValid = false;
					throw new NonDigitInput();
				}
			}
			}
			catch(NonDigitInput e)
			{
				studInfoLoad.setText(e.getMessage());
			}
			
			int inputID = -1;
			
			if(isValid)
			{
				inputID = Integer.parseInt(input);
			}
			
			if(isValid)
			{
				if(grad.isSelected())
				{
					studInfoLoad.setText(studentSearch(inputID, 0, g.size()-1, true));
				}
				else
				{
					studInfoLoad.setText(studentSearch(inputID, 0, s.size()-1, false));
				}
				
				
			}
		});
		
	}
	
	public void loadCourseScene(Stage primaryStage)
	{
		
		Button back = new Button("Back");
		loadCourseLabel = new Label("Load Course Data");
		enterCourseLoad = new TextField("Enter course name");
		loadCourseSearch = new Button("Search");
		courseInfoLoad = new TextArea("Course information will appear here when searched");
		courseInfoLoad.setEditable(false);
		courseInfoLoad.setWrapText(true);
		enterCourseLoad.setPrefWidth(275);
		
		
		VBox vbox = new VBox(10, enterCourseLoad, loadCourseSearch);
		HBox hbox = new HBox(10, vbox, courseInfoLoad);
		
		GridPane grid = new GridPane();
		grid.add(loadCourseLabel, 0, 0);
		grid.add(hbox, 0, 1);
		grid.add(back, 0, 2);
		grid.setPadding(new Insets(10));
		
		grid.getColumnConstraints().add(new ColumnConstraints(480));
		GridPane.setHalignment(loadCourseLabel, HPos.CENTER);
		loadCourseLabel.setStyle("-fx-font-size: 25 pt; -fx-font-weight:bold");
		loadCourseSearch.setStyle("-fx-font-weight:bold");
		
		Scene sceneLoadCourse = new Scene(grid, 500, 300);
		
		primaryStage.setTitle("Load Course Data");
		primaryStage.setScene(sceneLoadCourse);
		primaryStage.show();
		
		back.setOnAction(event -> {
			loadScene(primaryStage);
		});
		
		loadCourseSearch.setOnAction(event -> {
			String input = enterCourseLoad.getText();
			
			ArrayList<Course> course = db.getCoursesList();
			
			boolean courseFound = false;
			for(int i = 0; i < course.size(); i++)
			{
				if(input.equals(course.get(i).getCourseName()))
				{
					courseFound = true;
					courseInfoLoad.setText("Course Name: " + course.get(i).getCourseName() + "\t Instructor: " + course.get(i).getInstructor());
				}
			}
			if(courseFound == false)
			{
				courseInfoLoad.setText("Course not found.");
			}
		});
		
	}
	
	public void loadStudentsInCourse(Stage primaryStage)
	{
		Button back = new Button("Back");
		loadCourseStudList = new Label("Load Student List of a Course");
		courseToFindStuds = new TextField("Enter course name to get student list");
		studsInCourseInfo = new TextArea("Student list in course shown here when searched.");
		studsInCourseSearch = new Button("Search");
		studsInCourseInfo.setEditable(false);
		studsInCourseInfo.setWrapText(true);
		courseToFindStuds.setPrefWidth(425);
		
		VBox vbox = new VBox(10, courseToFindStuds, studsInCourseSearch);
		HBox hbox = new HBox(10, vbox, studsInCourseInfo);
		
		GridPane grid = new GridPane();
		grid.add(loadCourseStudList, 0, 0);
		grid.add(hbox, 0, 1);
		grid.add(back, 0, 2);
		grid.setPadding(new Insets(10));
		
		grid.getColumnConstraints().add(new ColumnConstraints(530));
		GridPane.setHalignment(loadCourseStudList, HPos.CENTER);
		loadCourseStudList.setStyle("-fx-font-size: 25 pt; -fx-font-weight:bold");
		studsInCourseSearch.setStyle("-fx-font-weight:bold");
		
		Scene sceneStudsInCourse = new Scene(grid, 550, 300);
		
		primaryStage.setTitle("Load Student List of a Course");
		primaryStage.setScene(sceneStudsInCourse);
		primaryStage.show();
		
		back.setOnAction(event -> {
			loadScene(primaryStage);
		});
		
		studsInCourseSearch.setOnAction(event -> {
			
			ArrayList<Course> courses = db.getCoursesList();
			ArrayList<Student> students = db.getStudentList();
			ArrayList<Graduate> grads = db.getGraduateList();
			String output = "";
			
			boolean courseIsFound = false;
			String inputCourseName = courseToFindStuds.getText();
			
			for(int i = 0; i < courses.size(); i++)
			{
				if(inputCourseName.equals(courses.get(i).getCourseName()))
				{
					courseIsFound = true;
				}		
			}
			
			if(courseIsFound)
			{
				for(int i = 0; i < students.size(); i++)
				{
					if(students.get(i).getCourse().getCourseName().equals(inputCourseName))
					{
						output += "Name: " + students.get(i).getName() + "\t Grade: " + students.get(i).getGrade() + "\n";
					}
				}
				for(int i = 0; i < grads.size(); i++)
				{
					if(grads.get(i).getCourse().getCourseName().equals(inputCourseName))
					{
						output += "Name: " + grads.get(i).getName() + "\t Grade: " + grads.get(i).getGrade() + "   (Graduate) \n";
					}
				}
				studsInCourseInfo.setText(output);
			}
			else
			{
				studsInCourseInfo.setText("Course not found. Please enter existing course name.");
			}
			
		});
	}
	
	public void addStudScene(Stage primaryStage)
	{
		Button back = new Button("Back");
		addStudLabel = new Label("Add a Student");
		studNameFieldS = new TextField("Enter student name");
		courseNameFieldS = new TextField("Enter existing course name");
		gradeFieldS = new TextField("Enter grade for course");
		addStudNum = new TextField("Enter student ID");
		degreeFieldS = new TextField("Enter degree name (Graduate Only)");
		addGrad = new CheckBox("Graduate Student");
		addStudSelect = new Button("Add");
		Label success = new Label();
		degreeFieldS.setVisible(false);
		
		courseNameFieldS.setPrefWidth(400);
		studNameFieldS.setPrefWidth(400);
		gradeFieldS.setPrefWidth(400);
		degreeFieldS.setPrefWidth(400);
		addStudNum.setPrefWidth(400);
		addGrad.setPrefWidth(400);
		
		GridPane innerGrid = new GridPane();
		innerGrid.add(studNameFieldS, 0, 0);
		innerGrid.add(courseNameFieldS, 1, 0);
		innerGrid.add(gradeFieldS, 0, 1);
		innerGrid.add(degreeFieldS, 1, 1);
		innerGrid.add(addStudNum, 0, 2);
		innerGrid.add(addGrad, 1, 2);
		
		innerGrid.setHgap(15);
		innerGrid.setVgap(15);
		
		GridPane grid = new GridPane();
		grid.add(addStudLabel, 0, 0);
		grid.add(innerGrid, 0, 1);
		grid.add(addStudSelect, 0, 2);
		grid.add(success, 0, 3);
		grid.add(back, 0, 4);
		grid.setPadding(new Insets(10));
		grid.setVgap(15);
		
		grid.getColumnConstraints().add(new ColumnConstraints(500));
		GridPane.setHalignment(addStudLabel, HPos.CENTER);
		GridPane.setHalignment(addStudSelect, HPos.CENTER);
		GridPane.setHalignment(success, HPos.CENTER);
		
		addStudLabel.setStyle("-fx-font-size: 25 pt; -fx-font-weight:bold");
		addStudSelect.setStyle("-fx-font-weight:bold");
		
		addGrad.setOnAction(event -> {
			if(addGrad.isSelected())
			{
				degreeFieldS.setVisible(true);
			}
			else if(!addGrad.isSelected())
			{
				degreeFieldS.setVisible(false);
			}
		});
		
		Scene sceneAddStud = new Scene(grid, 550, 300);
		
		primaryStage.setTitle("Add a Student");
		primaryStage.setScene(sceneAddStud);
		primaryStage.show();
		
		back.setOnAction(event -> {
			editScene(primaryStage);
		});
		
		addStudSelect.setOnAction(event -> {
			String inputName = studNameFieldS.getText();
			String inputCourse = courseNameFieldS.getText();
			String inputGrade = gradeFieldS.getText();
			String inputStudID = addStudNum.getText();
			String inputDegree = degreeFieldS.getText();
			
			boolean isValidName = true;
			boolean isValidCourse = true;
			boolean isValidDegree = true;
			boolean isValidGrade = true;
			boolean isValidID = true;
			
			for(int i = 0; i < inputName.length(); i++)
			{
				if(!Character.isLetter(inputName.charAt(i)) && !(inputName.charAt(i) == ' '))
					isValidName = false;			
			}
			for(int i = 0; i < inputCourse.length(); i++)
			{
				if(!Character.isLetter(inputCourse.charAt(i)) && !(inputCourse.charAt(i) == ' ') && !Character.isDigit(inputCourse.charAt(i)))
					isValidCourse = false;			
			}
			if(addGrad.isSelected())
			{
				for(int i = 0; i < inputDegree.length(); i++)
				{
					if(!Character.isLetter(inputDegree.charAt(i)) && !(inputDegree.charAt(i) == ' '))
						isValidDegree = false;			
				}
			}
			
			try {
			for(int i = 0; i < inputGrade.length(); i++)
			{
				if(!Character.isDigit(inputGrade.charAt(i)))
				{
					isValidGrade = false;
					throw new NonDigitInput();
				}
			}
			}
			catch(NonDigitInput e)
			{
				success.setText(e.getMessage());
			}
			
			try {
				for(int i = 0; i < inputStudID.length(); i++)
				{
					if(!Character.isDigit(inputStudID.charAt(i)))
					{
						isValidID = false;
						throw new NonDigitInput();
					}
				}
				}
				catch(NonDigitInput e)
				{
					success.setText(e.getMessage());
				}
			
			int grade = -1, ID = -1;
			
			if(isValidGrade && isValidID)
			{
				grade = Integer.parseInt(inputGrade);
				ID = Integer.parseInt(inputStudID);
			}
			
			ArrayList<Course> courses = db.getCoursesList();
			Course c = new Course();
			boolean courseFound = false;
			for(int i = 0; i < courses.size(); i++)
			{
				if(courses.get(i).getCourseName().equals(inputCourse))
				{
					c = courses.get(i);
					courseFound = true;
				}
			}
			
			if(isValidGrade && isValidID && courseFound && isValidName && isValidCourse)
			{
				if(addGrad.isSelected() && isValidDegree)
				{
					try {
						db.addGradStudent(new Graduate(inputName, c, grade, ID, inputDegree));
						success.setText("Graduate Student Added");
						System.out.println("Graduate student added successfully.");
					}
					catch (SQLException e) {
						e.printStackTrace();
					}
				}
				else
				{
					try {
						db.addStudent(new Student(inputName, c, grade, ID));
						success.setText("Student Added");
						System.out.println("Student added successfully.");
					}
					catch(SQLException e)
					{
						e.printStackTrace();
					}
				}
			}
			if(!courseFound)
			{
				success.setText("Course not found. Please enter existing course name.");
				System.out.println("Course not found.");
			}
			if(!isValidGrade)
			{
				success.setText("Invalid grade input. Please enter digits only");
			}
			if(!isValidID)
			{
				success.setText("Invalid student ID input. Please enter digits only");
			}
			if(!isValidName)
			{
				success.setText("Invalid student name input. No special characters");
			}
			if(!isValidCourse)
			{
				success.setText("Invalid course name input. No special characters");
			}
			if(!isValidDegree)
			{
				success.setText("Invalid degree name input. No special characters");
			}
		});
	}
	
	public void addCourseScene(Stage primaryStage)
	{
		Button back = new Button("Back");
		addCourseLabel = new Label("Add a Course");
		courseFieldC = new TextField("Enter course name");
		instructorFieldC = new TextField("Enter instructor name");
		addCourseSelect = new Button("Add");
		Label success = new Label();
		
		courseFieldC.setPrefWidth(275);
		instructorFieldC.setPrefWidth(275);
		
		HBox hbox = new HBox(10, courseFieldC, instructorFieldC);
		hbox.setPadding(new Insets(10));
		GridPane grid = new GridPane();
		grid.add(addCourseLabel, 0, 0);
		grid.add(hbox, 0, 1);
		grid.add(addCourseSelect, 0, 2);
		grid.add(success, 0, 3);
		grid.add(back, 0, 4);
		grid.setPadding(new Insets(10));
		
		grid.getColumnConstraints().add(new ColumnConstraints(480));
		GridPane.setHalignment(addCourseLabel, HPos.CENTER);
		GridPane.setHalignment(addCourseSelect, HPos.CENTER);
		GridPane.setHalignment(success, HPos.CENTER);
		
		addCourseLabel.setStyle("-fx-font-size: 25 pt; -fx-font-weight:bold");
		addCourseSelect.setStyle("-fx-font-weight:bold");
		
		Scene sceneAddCourse = new Scene(grid, 500, 300);
		
		primaryStage.setTitle("Add a Course");
		primaryStage.setScene(sceneAddCourse);
		primaryStage.show();
		
		back.setOnAction(event -> {
			editScene(primaryStage);
		});
		
		addCourseSelect.setOnAction(event -> {
			String inputCourse = courseFieldC.getText();
			String inputInstructor = instructorFieldC.getText();
			
			boolean isValidCourse = true;
			boolean isValidInstructor = true;
			
			for(int i = 0; i < inputCourse.length(); i++)
			{
				if(!Character.isLetter(inputCourse.charAt(i)) && inputCourse.charAt(i) != ' ' && !Character.isDigit(inputCourse.charAt(i)))
					isValidCourse = false;
			}	
			for(int i = 0; i < inputInstructor.length(); i++)
			{
				if(!Character.isLetter(inputInstructor.charAt(i)) && inputInstructor.charAt(i) != ' ')
					isValidInstructor = false;
			}
			
			try {
				
				if(isValidCourse && isValidInstructor)
				{
					db.addCourse(new Course(inputCourse, inputInstructor));
					System.out.println("Course added successfully.");
					success.setText("Course Added");
				}
				else
				{
					success.setText("Unable to add course: Course name or instructor name contains invalid special characters. Try again.");
				}
			}
			catch(Exception e)
			{
				System.out.println("Error: Unable to add course " + e.getMessage());
			}
						
		});
		
	}
	
	public void removeStudentScene(Stage primaryStage)
	{
		Button back = new Button("Back");
		Label instructions = new Label("Search for a student, then confirm to remove.");
		removeStudLabel = new Label("Remove a Student");
		remStudField = new TextField("Enter student ID");
		removeStudSearch = new Button("Search for student");
		studInfoRem = new TextArea("Student information shown here when searched.");
		studentRemovalSelect = new Button("Confirm");
		remGrad = new CheckBox("Graduate Student");
		studInfoRem.setEditable(false);
		studInfoRem.setWrapText(true);
		Label success = new Label();
		
		
		remStudField.setPrefWidth(275);
		
		VBox vbox = new VBox(10, remStudField, removeStudSearch, remGrad);
		HBox hbox = new HBox(10, vbox, studInfoRem);
		GridPane grid = new GridPane();
		grid.add(removeStudLabel, 0, 0);
		grid.add(instructions, 0, 1);
		grid.add(hbox, 0, 2);
		grid.add(success, 0, 3);
		grid.add(studentRemovalSelect, 0, 4);
		grid.add(back, 0, 5);
		grid.setPadding(new Insets(10));
		
		grid.getColumnConstraints().add(new ColumnConstraints(480));
		GridPane.setHalignment(removeStudLabel, HPos.CENTER);
		GridPane.setHalignment(hbox, HPos.CENTER);
		GridPane.setHalignment(instructions, HPos.CENTER);
		GridPane.setHalignment(studentRemovalSelect, HPos.CENTER);
		GridPane.setHalignment(success, HPos.CENTER);
		
		removeStudLabel.setStyle("-fx-font-size: 25 pt; -fx-font-weight:bold");
		removeStudSearch.setStyle("-fx-font-weight:bold");
		studentRemovalSelect.setStyle("-fx-font-weight:bold");
		
		Scene sceneRemoveStudent = new Scene(grid, 500, 300);
		
		primaryStage.setTitle("Remove a Student");
		primaryStage.setScene(sceneRemoveStudent);
		primaryStage.show();
		
		back.setOnAction(event -> {
			editScene(primaryStage);
		});
		
		removeStudSearch.setOnAction(event -> {
			
			isValidID = true;
			ArrayList<Graduate> graduates = db.getGraduateList();
			ArrayList<Student> students = db.getStudentList();
			
			String inputStringID = remStudField.getText();
			for(int i = 0; i < inputStringID.length(); i++)
			{
				if(!Character.isDigit(inputStringID.charAt(i)))
				{
					isValidID = false;
				}
			}
			
			if(isValidID)
			{
				int ID = Integer.parseInt(inputStringID);
				if(remGrad.isSelected())
				{
					Graduate g;
					for(int i = 0; i < graduates.size(); i++)
					{
						if(ID == graduates.get(i).getStudNum())
						{
							g = graduates.get(i);
							remStudentFound = true;
							studInfoRem.setText("Name: " + g.getName() + "\t Course: " + g.getCourse().getCourseName() + "\t Grade: "
									+ g.getGrade() + "\t Student ID: " + g.getStudNum() + "\t Degree: " + g.getDegree());
							gradToRemove = g;
						}
					}
				}
				else
				{
					Student s;
					for(int i = 0; i < students.size(); i++)
					{
						if(ID == students.get(i).getStudNum())
						{
							s = students.get(i);
							remStudentFound = true;
							studInfoRem.setText("Name: " + s.getName() + "\t Course: " + s.getCourse().getCourseName() + "\t Grade: "
									+ s.getGrade() + "\t Student ID: " + s.getStudNum());
							studToRemove = s;
						}
					}
				}
			}
			else
			{
				studInfoRem.setText("Invalid Student ID. Please only enter digits.");
			}
		});
		
		studentRemovalSelect.setOnAction(event -> {
			if(remStudentFound)
			{
				if(remGrad.isSelected())
				{
					try {
						db.removeStudent(gradToRemove);
						success.setText("Student removed successfully");
						System.out.println("Student removed successfully.");
					}
					catch(Exception e)
					{
						System.out.println("Unable to remove student: " + e.getMessage());
					}
				}
				else
				{
					try {
						db.removeStudent(studToRemove);
						success.setText("Student removed successfully");
						System.out.println("Student removed successfully.");
					}
					catch(Exception e)
					{
						System.out.println("Unable to remove student: " + e.getMessage());
					}
				}
			}
			else
			{
				success.setText("Unable to remove student. Student not found.");
			}
		});
	}
	
	public void removeCourseScene(Stage primaryStage)
	{
		Button back = new Button("Back");
		Label instructions = new Label("Search for a course, then confirm to remove.");
		removeCourseLabel = new Label("Remove a Course");
		remCourseField = new TextField("Enter course name");
		removeCourseSearch = new Button("Search for course");
		courseInfoRem = new TextArea("Course information shown here when searched.");
		courseRemovalSelect = new Button("Confirm");
		courseInfoRem.setEditable(false);
		courseInfoRem.setWrapText(true);
		Label success = new Label();
		
		
		ArrayList<Course> courses = db.getCoursesList();
		
		remCourseField.setPrefWidth(275);
		
		VBox vbox = new VBox(10, remCourseField, removeCourseSearch);
		HBox hbox = new HBox(10, vbox, courseInfoRem);
		hbox.setPadding(new Insets(10));
		GridPane grid = new GridPane();
		grid.add(removeCourseLabel, 0, 0);
		grid.add(instructions, 0, 1);
		grid.add(hbox, 0, 2);
		grid.add(success, 0, 3);
		grid.add(courseRemovalSelect, 0, 4);
		grid.add(back, 0, 5);
		grid.setPadding(new Insets(10));
		
		grid.getColumnConstraints().add(new ColumnConstraints(480));
		GridPane.setHalignment(removeCourseLabel, HPos.CENTER);
		GridPane.setHalignment(hbox, HPos.CENTER);
		GridPane.setHalignment(courseRemovalSelect, HPos.CENTER);
		GridPane.setHalignment(instructions, HPos.CENTER);
		GridPane.setHalignment(success, HPos.CENTER);
		
		removeCourseLabel.setStyle("-fx-font-size: 25 pt; -fx-font-weight:bold");
		removeCourseSearch.setStyle("-fx-font-weight:bold");
		courseRemovalSelect.setStyle("-fx-font-weight:bold");
		
		Scene sceneRemoveCourse = new Scene(grid, 500, 300);
		
		primaryStage.setTitle("Remove a Course");
		primaryStage.setScene(sceneRemoveCourse);
		primaryStage.show();

		back.setOnAction(event -> {
			editScene(primaryStage);
		});
		
		removeCourseSearch.setOnAction(event -> {
			String inputCourseName = remCourseField.getText();
			remCourseFound = false;
			for(int i = 0; i < courses.size(); i++)
			{
				if(courses.get(i).getCourseName().equals(inputCourseName))
				{
					courseInfoRem.setText("Course Name: " + courses.get(i).getCourseName() + "\t Instructor: " + courses.get(i).getInstructor());
					remCourseFound = true;
					courseToRemove = courses.get(i);
				}
			}
			if(!remCourseFound)
				courseInfoRem.setText("Course not found. Please enter existing course name and try again.");
		});
		
		courseRemovalSelect.setOnAction(event -> {
			
			if(remCourseFound)
			{
				try {
					db.removeCourse(courseToRemove);
					success.setText("Course removed successfully");
					System.out.println("Course removed successfully.");
				}
				catch(Exception e)
				{
					System.out.println("Error: Unable to remove course " + e.getMessage());
				}
			}
			else {
				success.setText("Unable to remove course. Course not found.");
			}
		});
		
	}
	
	
	public String studentSearch(int inputID, int first, int last, boolean isGrad)
	{
		int middle = (first+last)/2;
		ArrayList<Student> students = db.getStudentList();
		ArrayList<Graduate> graduates = db.getGraduateList();
		try {
		if(isGrad)
		{
			if(graduates.get(last).getStudNum() == inputID)
			{
				return ("Name: " + graduates.get(last).getName() + "\t Course: " + graduates.get(last).getCourse().getCourseName() + "\t Grade: " + graduates.get(last).getGrade()
						+ "\t Student ID: " + graduates.get(last).getStudNum() + "\t Degree: " + graduates.get(last).getDegree());
			}
			else if(graduates.get(middle).getStudNum() == inputID)
			{
				return ("Name: " + graduates.get(middle).getName() + "\t Course: " + graduates.get(middle).getCourse().getCourseName() + "\t Grade: " + graduates.get(middle).getGrade()
				+ "\t Student ID: " + graduates.get(middle).getStudNum() + "\t Degree: " + graduates.get(middle).getDegree());
			}
			else if(inputID < graduates.get(middle).getStudNum())
			{
				studentSearch(inputID, first, middle, isGrad);
			}
			else if(inputID > graduates.get(middle).getStudNum())
			{
				studentSearch(inputID, middle, last, isGrad);
			}
			
		}
		else
		{
			if(students.get(last).getStudNum() == inputID)
			{
				return ("Name: " + students.get(last).getName() + "\t Course: " + students.get(last).getCourse().getCourseName() + "\t Grade: " + students.get(last).getGrade()
						+ "\t Student ID: " + students.get(last).getStudNum());
			}
			else if(students.get(middle).getStudNum() == inputID)
			{
				return ("Name: " + students.get(middle).getName() + "\t Course: " + students.get(middle).getCourse().getCourseName() + "\t Grade: " + students.get(middle).getGrade()
						+ "\t Student ID: " + students.get(middle).getStudNum());
			}
			else if(inputID < students.get(middle).getStudNum())
			{
				studentSearch(inputID, first, middle, isGrad);
			}
			else if(inputID > students.get(middle).getStudNum())
			{
				studentSearch(inputID, middle, last, isGrad);							
			}
		}
		return "Student not found.";
		
		}
		catch(StackOverflowError e)
		{
			System.out.println("Student not found");
			return "Student not found. ";
		}
	}
	
	
}

