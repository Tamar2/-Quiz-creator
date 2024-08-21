package roniAndTamar;

public interface Examable {
	 Exam createExam(StockPile st) throws TooMuchQuestionException, NotEnoughAnswersException;

}
