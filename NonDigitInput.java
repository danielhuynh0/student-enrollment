package application;

public class NonDigitInput extends Exception
{
	public NonDigitInput()
	{
		super("Error: Non-digit input entered. Please enter valid characters.");
	}
}