package roniAndTamar;

import java.io.Serializable;

public class Answer implements Serializable{
	private String text;
	
	
	public Answer(String text)
	{
		this.text = text;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}

	
}
