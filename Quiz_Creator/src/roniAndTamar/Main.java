package roniAndTamar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		System.out.println("Choose a subject:");
		String subject = scan.nextLine();

		BinaryFileHandler bf = new BinaryFileHandler();
		bf.openInputFile(subject + ".dat");
		StockPile st = (StockPile) bf.readObject();
		if (st == null) {
			st = new StockPile();
			fillStock(st);
		}
		bf.closeFileIn();

		boolean fcountine = true;
		while (fcountine) {
			menu();
			int questionNumber = scan.nextInt();
			scan.nextLine();

			switch (questionNumber) {
			case 1:
				Question[] qs = st.getQuestions();
				for (int i = 0; i < st.getQuestionsCount(); i++) {
					System.out.println("Question:" + (i + 1));
					PrintQuestion(qs[i]);
				}
				break;
			case 2: {
				System.out.println("Adding an answer");
				String text = scan.nextLine();
				Answer a = new Answer(text);
				st.AddAnswer(a);
				break;
			}
			case 3: {
				System.out.println("Adding an answer to a question");
				System.out.println("Choose a question");
				int qw = scan.nextInt();
				scan.nextLine();
				if (qw > st.getQuestionsCount() || qw == 0) {
					System.out.println("Wrong number");
					break;
				}
				Question q = st.getQuestions()[qw - 1];
				if (q instanceof OpenQuestion && q.answersCount >= 1) {
					System.out.println("The open question already has an answer");
					break;
				}
				if (q instanceof MultQuestion && q.answersCount >= 10) {
					System.out.println("The question already has 10 answers");
					break;
				}
				System.out.println("Choose an answer:");
				printAnswers(st.getAnswers(), st.getAnswersCount());
				int aw = scan.nextInt();
				scan.nextLine();
				if (aw > st.getAnswersCount() || aw == 0) {
					System.out.println("Wrong number");
					break;
				}
				boolean isCorrect = true;
				if (q instanceof MultQuestion) {
					System.out.println("Write whether it is 'true' or 'false'");
					isCorrect = scan.nextBoolean();
					scan.nextLine();
				}
				Answer a = st.getAnswers()[aw - 1];
				st.getQuestions()[qw - 1].AddAnswer(a, isCorrect);
				break;
			}
			case 4: {
				System.out.println("1- open or 2- mult? ");
				int numChoice = scan.nextInt();
				scan.nextLine();
				System.out.println("Write your new question");
				String s = scan.nextLine();
				Difficulty dif = getDifFromUser();
				if (numChoice == 1) {
					OpenQuestion q = new OpenQuestion(s, dif);
					st.AddNewQuestion(q);
				} else {
					MultQuestion q = new MultQuestion(s, dif);
					st.AddNewQuestion(q);
				}
				break;
			}
			case 5: {
				System.out.println("Choose q to appdate");
				int qix = scan.nextInt();
				if (qix > st.getQuestionsCount() || qix == 0) {
					System.out.println("Wrong number :(");
					break;
				}
				scan.nextLine();
				System.out.println("Write a new text");
				String newText = scan.nextLine();
				st.getQuestions()[qix - 1].setText(newText);
				System.out.println("Ok, the new question has been updated :)");
				break;
			}
			case 6: {
				System.out.println("Choose question");
				int qix = scan.nextInt();
				if (qix > st.getQuestionsCount() || qix == 0) {
					System.out.println("Wrong number :(");
					break;
				}
				scan.nextLine();
				System.out.println("Choose answer to uppdate");
				int aix = scan.nextInt();
				Question Q = st.getQuestions()[qix - 1];
				if (aix > Q.getAnswersCount() || aix == 0) {
					System.out.println("Wrong number :(");
					break;
				}
				scan.nextLine();
				System.out.println("Write a new text");
				String newText = scan.nextLine();
				Q.getAnswers()[aix - 1].setText(newText);
				System.out.println("Ok, the new answer has been updated :)");
				break;
			}
			case 7: {
				System.out.println("Choose question to delete the answer");
				int qix = scan.nextInt();
				if (qix > st.getQuestionsCount() || qix == 0) {
					System.out.println("Wrong number :(");
					break;
				}
				scan.nextLine();
				Question Q = st.getQuestions()[qix - 1];
				if (Q instanceof OpenQuestion) {
					Q.deleteAnswer(0);
					System.out.println("Ok, The answer was successfully deleted!");
					break;
				}
				System.out.println("Choose answer to delete");
				int aix = scan.nextInt();
				if (aix > Q.getAnswersCount() || aix == 0) {
					System.out.println("Wrong number :(");
					break;
				}
				Q.deleteAnswer(aix - 1);
				System.out.println("Ok, The answer was successfully deleted!");
				break;
			}
			case 8: {
				System.out.println("Choose question to delete");
				int qix = scan.nextInt();
				if (qix > st.getQuestionsCount() || qix == 0) {
					System.out.println("Wrong number :(");
					break;
				}
				scan.nextLine();
				st.deleteQuestion(qix - 1);
				System.out.println("Ok, The question was successfully deleted!");
				break;
			}
			case 9: {
				Exam ex1 = null;

				try {
					ex1 = createExamChoice(st);

				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				if (ex1 == null)
					break;

				Question[] qs1 = ex1.getQuestions();
				for (int i = 0; i < qs1.length; i++) {
					System.out.println("Question:" + (i + 1));
					PrintQuestion(qs1[i]);
				}
				System.out.println("Created");

				writeExamToFile(ex1);
				writeExamToFileWithSolution(ex1);
				break;
			}
			case 0: {
				bf.openOutputFile(subject + ".dat");
				bf.writeObject(st);
				bf.closeFileOut();
				return;
			}
			default:
				System.out.println("Invaild question number");
				break;
			}

		}

		scan.close();
	}

	public static void fillStock(StockPile st) {
		Answer a1 = new Answer("10");
		Answer a2 = new Answer("12");
		Answer a3 = new Answer("15");
		Answer a4 = new Answer("20");
		Answer a5 = new Answer("7");
		Answer a6 = new Answer("25");
		Answer a7 = new Answer("50");
		Answer a8 = new Answer("30");
		Question q1 = new MultQuestion("How much is 2x10?", Difficulty.Easy);
		q1.AddAnswer(a1, false);
		q1.AddAnswer(a2, false);
		q1.AddAnswer(a3, false);
		q1.AddAnswer(a4, true);
		st.AddNewQuestion(q1);
		st.AddAnswer(a1);
		st.AddAnswer(a2);
		st.AddAnswer(a3);
		st.AddAnswer(a4);
		Question q2 = new MultQuestion("How much is 3x10?", Difficulty.Easy);
		q2.AddAnswer(a5, false);
		q2.AddAnswer(a6, false);
		q2.AddAnswer(a7, false);
		q2.AddAnswer(a8, true);
		st.AddNewQuestion(q2);
		st.AddAnswer(a5);
		st.AddAnswer(a6);
		st.AddAnswer(a7);
		st.AddAnswer(a8);
		OpenQuestion q3 = new OpenQuestion("What is the captial of Israel?", Difficulty.Hard);
		Answer a9 = new Answer("Jerusalem");
		st.AddAnswer(a9);
		st.AddNewQuestion(q3);
		q3.AddAnswer(a9, true);
	}

	public static void menu() {
		System.out.println(
				"Enter 1 - to display all the questions in the stockpile and the answers entered to the questions");
		System.out.println("Enter 2 - to add a new answer to the stockpile");
		System.out.println("Enter 3 - to add an answer from the stockpile to an existing question");
		System.out.println("Enter 4 - to add a new question to the stockpile");
		System.out.println("Enter 5 - to update the wording of an existing question");
		System.out.println("Enter 6 - to update the wording, an existing answer");
		System.out.println("Enter 7 - to delete an answer to a question from the stocpile");
		System.out.println("Enter 8 - to delete a question from the stockpile");
		System.out.println("Enter 9 - to create a exam");
		System.out.println("Enter 0 - to exit");
		System.out.println("Enter your choice:");
	}

	public static Difficulty getDifFromUser() {
		System.out.println("Choose difficulty: ");
		System.out.println("1- Easy, 2- Medium, 3- Hard");
		int choiceDif = scan.nextInt();
		scan.nextLine();
		switch (choiceDif) {
		case 1:
			return Difficulty.Easy;
		case 2:
			return Difficulty.Medium;
		case 3:
			return Difficulty.Hard;

		default:
			System.out.println("Invaild choice. easy chosen");
			return Difficulty.Easy;
		}
	}

	public static void PrintQuestion(Question q) {
		System.out.println(q.GetFullString(true));

	}

	public static void printAnswers(Answer[] answers, int answersCount) {
		for (int i = 0; i < answersCount; i++) {
			System.out.println((i + 1) + ". " + answers[i].getText());

		}
	}

	public static Exam createExamChoice(StockPile st) throws TooMuchQuestionException, NotEnoughAnswersException {
		Exam ex1 = null;
		System.out.println("Choose Manu-1 or Auto-2");
		int choice = scan.nextInt();
		scan.nextLine();
		if (choice == 1) {
			Examable examMaker = new ManualExam();
			ex1 = examMaker.createExam(st);
		} else if (choice == 2) {
			Examable examMaker = new AutomaticExam();
			ex1 = examMaker.createExam(st);
		}

		return ex1;
	}

	public static void writeExamToFile(Exam ex) throws FileNotFoundException {
		LocalDateTime dT = LocalDateTime.now();
		DateTimeFormatter examFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd_yy_HH_mm");
		String examName = "exam" + dT.format(examFormat) + ".txt";
		File f = new File(examName);
		PrintWriter pw = new PrintWriter(f);
		Question[] qs = ex.getQuestions();
		for (int i = 0; i < qs.length; i++) {
			pw.println("Question:" + (i + 1));
			pw.println(qs[i].GetFullString(false));
		}
		pw.close();
	}

	public static void writeExamToFileWithSolution(Exam ex) throws FileNotFoundException {
		LocalDateTime dT = LocalDateTime.now();
		DateTimeFormatter examFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd_yy_HH_mm");
		String solutionsName = "solutions" + dT.format(examFormat) + ".txt";
		File f2 = new File(solutionsName);
		PrintWriter pw = new PrintWriter(f2);
		Question[] qs = ex.getQuestions();
		for (int i = 0; i < qs.length; i++) {
			pw.println("Question:" + (i + 1));
			pw.println(qs[i].GetFullString(true));
		}
		pw.close();
	}

}
