package Database;

import java.sql.SQLException;
import java.util.List;

import POJO.Answer;
import POJO.Question;
import POJO.Test;
import POJO.Topic;

public interface MCQdb {
	
	public Boolean isQuestionInDB(int id) throws SQLException;
	public int storeQuestion(Question question) throws Exception;
	public Question loadQuestion(int id) throws SQLException;
	public Question loadQuestion(Question question) throws SQLException;
	public int deleteQuestion(int id) throws Exception;
	public int storeQuestions(Iterable<Question> questions) throws Exception;
	public List<Question> loadAllQuestions() throws Exception;
	public Question loadLastQuestion() throws SQLException;
	public List<Question> loadLastThreeQuestion() throws SQLException;
	
	public Boolean isAnswerInDB(Answer a) throws SQLException;	
	public int storeAnswer(Answer answer) throws SQLException;
	public Answer loadAnswer(int id) throws SQLException;
	public Answer loadAnswer(Answer a) throws SQLException;
	public int deleteAnswer(int id) throws Exception;
	public int storeAnswers(List<Answer> answers) throws Exception;
	public List<Answer> loadAllAnswers() throws Exception;
	
	public int storeTopic(Topic topic) throws Exception;
	public Topic loadTopic(int id) throws SQLException;
	public int deleteTopic(int id) throws Exception;
	public int storeTopics(List<Topic> topics) throws Exception;
	public List<Topic> loadAllTopics() throws Exception;
	
	public int storeAnswerToQuestion(int question_id, int answer_id) throws Exception;
	public int storeAnswersToQuestion(Question question) throws Exception;
	public List<Answer> loadAllAnswersToQuestion(int question_id) throws Exception;
	public int deleteAnswerToQuestion(int question_id) throws Exception;
	public int deleteAnswersToQuestion(Question question) throws Exception;
	
	public int storeTopicToQuestion(int question_id, int topic_id) throws Exception;
	public int storeTopicsToQuestion(Question question) throws Exception;
	public int deleteTopicToQuestion(int topic_id) throws Exception;
	public int deleteTopicsToQuestion(Question question) throws Exception;
	public List<Topic> loadAllTopicsToQuestion(int question_id) throws Exception;
	public Topic loadTopic(Topic p) throws SQLException;
	public Boolean isTopicInDB(Topic a) throws SQLException;
	public List<Question> searchQuestions(String title) throws SQLException;
	public List<Question> loadAllQuestionsToTopics(List<Integer> topics) throws SQLException;
	
	public List<Question> searchQuestionsTopicsString(List<Integer> topics, String title) throws SQLException;
	public List<Question> searchQuestionsTopicsStringAnswer(List<Integer> topics, String title, String answer) throws SQLException;
	public List<Question> searchQuestionsTopicsNotTitleAnswer(List<Integer> topics, String title, String answer) throws SQLException;
	public List<Question> searchQuestionsTopicsTitleNotAnswer(List<Integer> topics, String title, String answer) throws SQLException;
	public List<Question> searchQuestionsTopicsNotTitleNotAnswer(List<Integer> topics, String title, String answer) throws SQLException;
	
	public int storeTestAllQuestions(List<Integer> questionsId, String module, String date) throws SQLException;
	public int storeTestQuestion(int questionId, String module, String date) throws SQLException;
	public Boolean isTestInDB(String module, String date) throws SQLException;
	public Test loadTest(String module, String date) throws Exception;
	public List<Test> loadAllTests() throws Exception;
	public List<Integer> loadQuestionTest(String module, String date) throws Exception;
	public List<Integer> loadQuestionsInTests(List<Test> tests) throws Exception;
	public List<Answer> loadAnswersNotLikeTitle(String title) throws Exception;
	public List<Answer> loadAnswersLikeTitle(String title) throws Exception;
	public Test loadLastTest() throws Exception;
	
}
