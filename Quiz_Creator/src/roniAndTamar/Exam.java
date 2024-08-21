package roniAndTamar;

public class Exam {
	private Question[] questions;
	private int questionsCount = 0;

	public Exam(int questionCount) {
		questions = new Question[questionCount];

	}

	public void AddNewQuestion(Question question) {
		questions[questionsCount] = question;
		questionsCount++;

	}

	public Question[] getQuestions() {
		return questions;
	}

	public int getQuestionsCount() {
		return questionsCount;
	}
}
