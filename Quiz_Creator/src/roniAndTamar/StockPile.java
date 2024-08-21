package roniAndTamar;

import java.io.Serializable;

public class StockPile implements Serializable {
	private final static int MAX_QUESTIONS = 100;
	private final static int MAX_ANSWERS=100;
	private Question[] questions;
	private Answer[] answers;
	private int questionsCount = 0;
	private int answersCount = 0;

	public StockPile() {
		questions = new Question[MAX_QUESTIONS];
		answers = new Answer[MAX_ANSWERS];
	}

	public void AddAnswer(Answer answer) {
		answers[answersCount] = answer;
		answersCount++;
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
	public Answer[] getAnswers() {
		return answers;
	}
	
	public int getAnswersCount() {
		return answersCount;
	}
	public void deleteQuestion(int questionIdx) {
		for (int i = 0; i < questionsCount-1; i++) {
			if (i >= questionIdx) {
				questions[i] = questions[i + 1];
			}
		}
		questionsCount--;
			
		}
			public String toString() {
				String fullString = "";
				for (int i = 0; i <questionsCount ; i++) {
					fullString += questions[i].getText();
				
				fullString += "\n";
				}
			return fullString;
					
				}
			}
		
