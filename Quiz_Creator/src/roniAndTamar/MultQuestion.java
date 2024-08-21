package roniAndTamar;

public class MultQuestion  extends Question {
	private final static int MAX_ANSWERS = 10;
	
	public MultQuestion(String text, Difficulty difficulty) {
		super(text, difficulty);
		answers = new Answer[MAX_ANSWERS];
		correctAnswer = new boolean[MAX_ANSWERS];
		
	}
	public void calcFinalAnswer() {
		Answer noneCorrect = new Answer("No answer is correct");
		Answer moreCorrect = new Answer("More than one answer is correct");
		int count = 0;
		for (int i = 0; i < answersCount; i++) {
			if (correctAnswer[i] == true)
				count++;
		}
		if (count > 1) {
			for (int i = 0; i < answersCount; i++) {
				correctAnswer[i] = false;
			}
			AddAnswer(noneCorrect, false);
			AddAnswer(moreCorrect, true);
		}
		if (count == 0) {
			AddAnswer(noneCorrect, true);
			AddAnswer(moreCorrect, false);
		}
		if (count == 1) {
			AddAnswer(noneCorrect, false);
			AddAnswer(moreCorrect, false);
		}
	}
	
	
	
	
	
}
