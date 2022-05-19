package application;

public class Student {

	private String name;
	private Course course;
	private int grade;
	private int studentNum;
	
	public Student()
	{
		name = "";
	}
	public Student(String name1, Course course1, int grade1, int studNum)
	{
		name = name1;
		course = course1;
		grade = grade1;
		studentNum = studNum;
	}
	
	public void setName(String name1)
	{
		name = name1;
	}
	
	public void setCourse(Course course1)
	{
		course = course1;
	}
	
	public void setGrade(int grade1)
	{
		grade = grade1;
	}
	
	public void setStudNum(int studNum)
	{
		studentNum = studNum;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Course getCourse()
	{
		return course;
	}
	
	public int getGrade()
	{
		return grade;
	}
	
	public int getStudNum()
	{
		return studentNum;
	}
}
