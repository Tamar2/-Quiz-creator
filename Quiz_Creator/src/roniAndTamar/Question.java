package roniAndTamar;

import java.io.Serializable;

public class Question implements Serializable {
	private static int currentNumber = 1;
	private int number;
	private String text;
	protected Answer[] answers;
	protected int answersCount = 0;
	protected boolean[] correctAnswer;
	protected Difficulty difficulty;
	
	
	public Question(String text, Difficulty difficulty) {
		this.number = Question.currentNumber;
		Question.currentNumber++;
		this.text = text;
		this.difficulty= difficulty;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}
	
	public int getNumber() {
		return number;
	}

	public int getAnswersCount() {
		return answersCount;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public Answer[] getAnswers() {
		return answers;
	}

	public void AddAnswer(Answer answer, boolean isCorrect) {
		answers[answersCount] = answer;
		correctAnswer[answersCount] = isCorrect;
		answersCount++;

	}

	public boolean answerCorrect(int index) {
		return correctAnswer[index];
	}

	public String GetFullString(boolean isWithSolution) {
		String fullString = "";
		fullString += getText() + " (" + getDifficulty() + ")";
		fullString += "\n";
		for (int i = 0; i < answersCount; i++) {
			fullString += (i + 1) + "- ";
			fullString += answers[i].getText();
			if (isWithSolution) {
				fullString += ": " + correctAnswer[i];
			}
			fullString += "\n";
		}
		return fullString;
	}

	public void deleteAnswer(int answerIdx) {
		for (int i = 0; i < answersCount - 1; i++) {
			if (i >= answerIdx) {
				answers[i] = answers[i + 1];
			}
		}
		answersCount--;
	}

}
