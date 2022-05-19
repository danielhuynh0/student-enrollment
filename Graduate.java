package application;

public class Graduate extends Student{

	private String degree;
	
	public Graduate()
	{
		super();
	}
	public Graduate(String name1, Course course1, int grade1, int studNum, String degree1) {
		super(name1, course1, grade1, studNum);
		
		degree = degree1;
	}
	
	public void setDegree(String degree1)
	{
		degree = degree1;
	}
	
	public String getDegree()
	{
		return degree;
	}

}
