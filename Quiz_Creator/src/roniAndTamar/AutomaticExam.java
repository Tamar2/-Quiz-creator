package roniAndTamar;

import java.util.Random;
import java.util.Scanner;

public class AutomaticExam implements Examable {

	@Override
	public Exam createExam(StockPile st) {
		Random rd = new Random();
		Scanner scan = new Scanner(System.in);
		System.out.println("How many questions do you want to have on the exam?");
		int examQ = scan.nextInt();
		scan.nextLine();
		Exam ex1 = new Exam(examQ);
		for (int i = 0; i < examQ; i++) {
			if (!isThereValidQ(ex1,st)) {
				System.out.println("There are not enough valid questions");
				System.out.println("There are only " + ex1.getQuestionsCount() + " valid questions");
				return null;
			}
			int qidx = rd.nextInt(st.getQuestionsCount());
			Question q = st.getQuestions()[qidx];
			boolean isQExsits = isQuestionAlreayExists(ex1, q);
			if (isQExsits) {
				i--;
				continue;
			}
			if (q instanceof MultQuestion) {
				if (!isQuestionValid(q)) {
					i--;
					continue;
				}
				MultQuestion exQ = new MultQuestion(q.getText(), q.getDifficulty());
				boolean isCorrectAdded = false;
				for (int j = 0; j < 4; j++) {
					int aidx = rd.nextInt(q.getAnswersCount());
					Answer ans = q.getAnswers()[aidx];
					boolean isAExsits = isAnswerAlreayExists(exQ, ans);
					if (isAExsits) {
						j--;
						continue;
					}
					if (isCorrectAdded && q.answerCorrect(aidx)) {
						j--;
						continue;
					}
					exQ.AddAnswer(ans, q.answerCorrect(aidx));
					if (q.answerCorrect(aidx)) {
						isCorrectAdded = true;
					}
				}
				calcFinalAnswer2(exQ);
				ex1.AddNewQuestion(exQ);
			} else {
				ex1.AddNewQuestion(q);
			}
		}
		return ex1;
	}

	public boolean isQuestionAlreayExists(Exam ex, Question q) {
		for (int i = 0; i < ex.getQuestionsCount(); i++) {
			if (ex.getQuestions()[i].getText() == q.getText()) {
				return true;
			}

		}
		return false;

	}

	public boolean isAnswerAlreayExists(Question q, Answer a) {
		for (int i = 0; i < q.answersCount; i++) {
			if (q.getAnswers()[i].getText() == a.getText()) {
				return true;
			}

		}
		return false;

	}

	public boolean isAtLeastThreeWrongAnswers(Question q) {
		int count = 0;
		for (int i = 0; i < q.answersCount; i++) {
			if (!(q.answerCorrect(i))) {
				count++;
			}
		}
		if (count >= 3)
			return true;
		return false;
	}

	public void calcFinalAnswer2(Question q) {
		Answer noneCorrect = new Answer("No answer is correct");
		for (int i = 0; i < q.answersCount; i++) {
			if (q.correctAnswer[i] == true) {
				q.AddAnswer(noneCorrect, false);
				return;
			}
		}
		q.AddAnswer(noneCorrect, true);
	}
	public boolean isQuestionValid(Question q) {
		if (q.answersCount >= 4 && isAtLeastThreeWrongAnswers(q)) {
			return true;
		}
		return false;
	}
	public boolean isThereValidQ(Exam ex, StockPile st) {
		for (int i = 0; i < st.getQuestionsCount(); i++) {
			if (isQuestionValid(st.getQuestions()[i]) && !isQuestionAlreayExists(ex,st.getQuestions()[i])) {
				return true;
			}
			
		}
		return false;
	}
}
