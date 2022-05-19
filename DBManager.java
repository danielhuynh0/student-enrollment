package application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.Course;
import application.Graduate;
import application.Student;

public class DBManager {

	private final static String DB_URL = "jdbc:derby:StudentDB";
	
	//Connection conn;
	//Statement stmt;
	
	//conn = DriverManager.getConnection(DB_URL);
	
	//Saved list of courses, students, and grad students. Everytime program is run, the method reloadData() reloads
	//database values into these ArrayLists.
	private ArrayList<Course> courses = new ArrayList<Course>();
	private ArrayList<Student> students = new ArrayList<Student>();
	private ArrayList<Graduate> grads = new ArrayList<Graduate>();
	
	public ArrayList<Student> getStudentList()
	{
		return students;
	}
	
	public ArrayList<Course> getCoursesList()
	{
		
		return courses;
	}
	
	public ArrayList<Graduate> getGraduateList()
	{
		return grads;
	}
	
	public void addStudent(Student s) throws SQLException
	{
		Connection conn = DriverManager.getConnection(DB_URL);
		Statement stmt = conn.createStatement();
		
		students.add(s);
		
		stmt.executeUpdate("INSERT INTO Students VALUES ('" + s.getName() + "', " + s.getGrade() + ", '" + s.getCourse().getCourseName() + "', " + s.getStudNum() + ", null)");
	}
	
	public void addGradStudent(Graduate g) throws SQLException
	{
		Connection conn = DriverManager.getConnection(DB_URL);
		Statement stmt = conn.createStatement();
		
		grads.add(g);
		
		stmt.executeUpdate("INSERT INTO Students VALUES ('"+ g.getName() + "', " + g.getGrade() + ", '" + g.getCourse().getCourseName() + "', " + g.getStudNum() + ", '" + g.getDegree() + "')");
	}
	
	public void addCourse(Course c) throws SQLException
	{
		Connection conn = DriverManager.getConnection(DB_URL);
		Statement stmt = conn.createStatement();
		
		courses.add(c);
		
		stmt.executeUpdate("INSERT INTO Courses VALUES ('" + c.getCourseName() + "', '" + c.getInstructor() + "')");
	}
	
	public void removeStudent(Student s) throws SQLException
	{
		Connection conn = DriverManager.getConnection(DB_URL);
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("DELETE FROM Students WHERE StudentName = '" + s.getName() + "'");
		
	}
	
	public void removeCourse(Course c) throws SQLException
	{
		Connection conn = DriverManager.getConnection(DB_URL);
		Statement stmt = conn.createStatement();
		
		stmt.executeUpdate("DELETE FROM Courses WHERE CourseName = '" + c.getCourseName() + "'");
	}
	
	//This method is used to reload data from the databases each time the application is run.
	public void reloadData() throws SQLException
	{
		
		String DB_URL2 = "jdbc:derby:StudentDB";
		
		ArrayList<String> courseList = new ArrayList<String>();
		ArrayList<String> courseInstructorList = new ArrayList<String>();
		
		ArrayList<String> studList = new ArrayList<String>();
		ArrayList<Course> studCourseList = new ArrayList<Course>();
		ArrayList<Integer> studGradeList = new ArrayList<Integer>();
		ArrayList<String> studDegreeList = new ArrayList<String>();
		ArrayList<Integer> studNumList = new ArrayList<Integer>();
		
		Connection conn = DriverManager.getConnection(DB_URL2);
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		ResultSet resultCourse = stmt.executeQuery("SELECT * FROM Courses");
		//ResultSet resultStud = stmt.executeQuery("SELECT * FROM Students ORDER BY StudentNum");

		//Add ResultSet of Course data to temporary ArrayLists, to reload data to main ArrayLists.
		while(resultCourse.next())
		{
			courseList.add(resultCourse.getString("CourseName"));
		}
		resultCourse.beforeFirst();
		
		
		while(resultCourse.next())
		{
			courseInstructorList.add(resultCourse.getString("Instructor"));
		}
		resultCourse.beforeFirst();
		//Reload data by creating Course objects
		for(int i = 0; i < courseList.size(); i++)
		{
			courses.add(new Course(courseList.get(i), courseInstructorList.get(i)));
		}
		
		//.........................................................................................
		ResultSet resultStud = stmt.executeQuery("SELECT * FROM Students ORDER BY StudentNum");	
		//Add ResultSet of Student data to temporary ArrayLists, to reload data into main ArrayLists.
		while(resultStud.next())
		{
			studList.add(resultStud.getString("StudentName"));
		}
		resultStud.beforeFirst();
		while(resultStud.next())
		{
			//Find course with same name and add Course object into temporary ArrayList
			String course1 = resultStud.getString("Course");
			for(int i = 0; i < courses.size(); i++)
			{
				if (course1.equals(courses.get(i).getCourseName()))
					studCourseList.add(courses.get(i));
			}
		}
		resultStud.beforeFirst();
		while(resultStud.next())
		{
			studGradeList.add(resultStud.getInt("Grade"));
		}
		resultStud.beforeFirst();
		while(resultStud.next())
		{
			studDegreeList.add(resultStud.getString("Degree"));
		}
		resultStud.beforeFirst();
		while(resultStud.next())
		{
			studNumList.add(resultStud.getInt("StudentNum"));
		}
		//Reload data by creating Student objects
		for(int i = 0; i < studList.size(); i++)
		{
			if(studDegreeList.get(i) == null)
			{
				students.add(new Student(studList.get(i), studCourseList.get(i), studGradeList.get(i), studNumList.get(i)));
			}
			else
			{
				grads.add(new Graduate(studList.get(i), studCourseList.get(i), studGradeList.get(i), studNumList.get(i), studDegreeList.get(i)));
			}
		}
	}
}
