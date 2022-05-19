package application;

public class Course {

	private String courseName, instructor;
	
	public Course()
	{
		
	}
	public Course(String name, String instructName)
	{
		courseName = name;
		instructor = instructName;
	}
	
	public void setCourseName(String name)
	{
		courseName = name;
	}
	
	public void setInstructor(String iName)
	{
		instructor = iName;
	}
	
	public String getCourseName()
	{
		return courseName;
	}
	
	public String getInstructor()
	{
		return instructor;
	}
}
