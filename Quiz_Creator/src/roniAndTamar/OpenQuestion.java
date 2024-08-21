package roniAndTamar;

public class OpenQuestion extends Question {

	public OpenQuestion(String text,  Difficulty difficulty) {
		super(text, difficulty);
		answers = new Answer[1];
		correctAnswer = new boolean[1];

	}

	@Override
	public String GetFullString(boolean isWithSolution) {
		String fullString = "";
		fullString += getText() + " (" + getDifficulty() + ")";
		fullString += "\n";
		if (isWithSolution) {
			if (getAnswersCount()>0) {
			fullString += "The answer is " + "- ";
			fullString += answers[0].getText();
		}}
		fullString += "\n";
		return fullString;
	}
}
