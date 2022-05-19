package application;
import java.sql.*;

public class CreateStudentDirectory {

	public static void main(String[] args) {
		
		final String CREATE_CITY = "jdbc:derby:StudentDB;create=true";

		try {
			Connection conn = DriverManager.getConnection(CREATE_CITY);
			
			Statement stmt = conn.createStatement();

			
			stmt.execute("DROP TABLE Students");
			stmt.execute("DROP TABLE Courses");
			
			
			System.out.println("Creating the Students table...");
	         stmt.execute("CREATE TABLE Students ("    +
	                      "StudentName VARCHAR(25) NOT NULL PRIMARY KEY, "   +
	                      "Grade DOUBLE, Course VARCHAR(25), StudentNum DOUBLE, Degree VARCHAR(25))");
	                      
	         System.out.println("Creating the Courses table...");
	         stmt.execute("CREATE TABLE Courses ("    +
	                      "CourseName VARCHAR(25) NOT NULL PRIMARY KEY, "   +
	                      "Instructor VARCHAR(25))");            	

			stmt.executeUpdate("INSERT INTO Students VALUES('Terrance', 98, 'CIS 111B', 234, null)");
			
			
			stmt.executeUpdate("INSERT INTO Courses VALUES('CIS 111B', 'Kelly')");
			
			ResultSet resultName;
			
			System.out.println("Students:");
			resultName = stmt.executeQuery("SELECT * FROM Students");		
			while(resultName.next())
			{
				System.out.println(resultName.getString("StudentName"));
			}
			
			resultName = stmt.executeQuery("SELECT * FROM Courses");		
			while(resultName.next())
			{
				System.out.println(resultName.getString("CourseName"));
			}
			
	         stmt.close();
	         conn.close();
	         System.out.println("Done");
		}
		catch(Exception e)
		{
			System.out.println("Error: " + e.getMessage());
		}
		
	}

}
