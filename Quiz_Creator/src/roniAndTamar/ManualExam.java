package roniAndTamar;

import java.util.Scanner;

public class ManualExam implements Examable {

	@Override
	public Exam createExam(StockPile st) throws TooMuchQuestionException, NotEnoughAnswersException {
		Scanner scan = new Scanner(System.in);
		System.out.println("How many questions do you want to have on the exam? (max 10)");
		int examQ = scan.nextInt();
		scan.nextLine();
		if (examQ > 10) {
			throw new TooMuchQuestionException("Too much");
		}
		Exam ex1 = new Exam(examQ);
		System.out.println("What questions do you want to be on the exam?");
		for (int i = 0; i < examQ; i++) {
			System.out.println("Choose a index question");
			int qix = scan.nextInt();
			scan.nextLine();
			if (qix > st.getQuestionsCount()) {
				System.out.println("Wrong number");
				return null;
			}
			Question Q = st.getQuestions()[qix - 1];
			if (Q instanceof MultQuestion) {
				if (Q.answersCount <= 3) {
					throw new NotEnoughAnswersException("Not enough answers");

				}
				MultQuestion exQ = new MultQuestion(Q.getText(), Q.getDifficulty());
				ex1.AddNewQuestion(exQ);
				System.out.println("What answers do you want to have on the exam? " + "To finish press 0");
				int aix = scan.nextInt();
				while (aix != 0) {
					if (aix > Q.getAnswersCount()) {
						System.out.println("Wrong number, try again");
					} else {
						Answer ans = Q.getAnswers()[aix - 1];
						exQ.AddAnswer(ans, Q.answerCorrect(aix - 1));
						System.out.println("Answer added :)");
					}
					aix = scan.nextInt();
				}
				exQ.calcFinalAnswer();
			} else if (Q instanceof OpenQuestion) {
				if (Q.getAnswersCount() <= 0) {
					System.out.println("You cannot add an open question without an answer");
					i--;
					continue;
				}

				else {
					ex1.AddNewQuestion(Q);
				}
			}
		}
		System.out.println("Created");
		return ex1;
	}

}
