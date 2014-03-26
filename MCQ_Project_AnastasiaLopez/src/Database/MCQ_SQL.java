package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import lib.Lib;
import POJO.Answer;
import POJO.Question;
import POJO.Test;
import POJO.Topic;

public class MCQ_SQL implements MCQdb {
	
	private static Connection connection;
	public MCQ_SQL db;
	
	public static void main(String[] args) throws Exception  {
        // simple method to test that ShopDB works
        System.out.println("Got this far...");
        MCQ_SQL db = new MCQ_SQL(Lib.getConnection("MCQ_schema")); 
        try {
        	System.out.println("created shop db");
        	ArrayList<Answer> answers = new ArrayList<Answer>();
        	Answer a = new Answer(3, "2/5");
        	Answer b = new Answer(4, "4/7");
        	Answer c = new Answer(10, "4/9");
        	Answer d = new Answer(11, "5/11");
        	Answer e = new Answer(5, "6/13");
        	answers.add(a); answers.add(b); answers.add(c); answers.add(d); answers.add(e);
        	System.out.println("Size: " + answers.size());
        	db.storeAnswers(answers);
        	System.out.println("Answers added");
        	Answer answer = db.loadAnswer(4);
        	Question q = new Question(1, "Of the following, which is greater than 1/2", answer.getId());
        	q.setAnswers(answers);
        	db.storeQuestion(q);
        	//db.storeAnswersToQuestion(q);
        	System.out.println("Questions added");
        	
        	System.out.println("Trying to load a question with its answers: ");
        	q = db.loadQuestion(1);
        	answers.clear();
        	answers = new ArrayList<Answer>(db.loadAllAnswersToQuestion(q.getId()));
        	System.out.println(answers.size());
        	q.setAnswers(answers);
        	System.out.println("Question loded: " + q);
        	

        } finally {
            connection.close();
        }
        

    }
	
	public Connection getCon() throws Exception{
		return connection;
	}

	public MCQ_SQL(Connection connection) {
		MCQ_SQL.connection = connection;
	}
	
	public MCQ_SQL() {
		try {
			this.db = new MCQ_SQL(Lib.getConnection("MCQ_schema"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Questions methods
	
	@Override
	public Boolean isQuestionInDB(int id) throws SQLException{
		if(loadQuestion(id) != null) return true;
		else return false;
	}
	
	//To store a question we first store the related answers, and then the relation itself
	@Override
	public int storeQuestion(Question question) throws Exception {
		//if(!isQuestionInDB(question.getId())){
		
			if(question.getAnswers() != null)
				storeAnswers(question.getAnswers());
			if(question.getTopics() != null)
				storeTopics(question.getTopics());
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO questions (title, correct_answer) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, question.getTitle());
			stmt.setInt(2, question.getCorrectAnswer());
			System.out.println("Storing question ..." + stmt);
			int rows =  stmt.executeUpdate();
			System.out.println("Result rows: " + rows);
			ResultSet res = stmt.getGeneratedKeys();
			res.next();
			question.setId(res.getInt(1));
			if(question.getAnswers() != null)
				storeAnswersToQuestion(question);
			if(question.getTopics() != null)
				storeTopicsToQuestion(question);
			return rows;
		//}
		//return -1;
	}
	
	@Override
	public Question loadQuestion(int id) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("SELECT id, title, correct_answer FROM questions WHERE id = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		if (rs.next())
			return new Question(rs.getInt(1), rs.getString(2), rs.getInt(3));
		else
			return null;
	}
	
	@Override
	public Question loadQuestion(Question question) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT id, title, correct_answer FROM questions WHERE title = ?");
		stmt.setString(1, question.getTitle());
		ResultSet rs = stmt.executeQuery();
		if (rs.next())
			return new Question(rs.getInt(1), rs.getString(2), rs.getInt(3));
		else
			return null;
	}
	@Override
	public int deleteQuestion(int id) throws Exception {
		PreparedStatement stmt = connection
				.prepareStatement("DELETE FROM questions WHERE id = ?");
		stmt.setInt(1, id);
		return stmt.executeUpdate();
	}
	
	@Override
	public int storeQuestions(Iterable<Question> questions) throws Exception {
		int result = 0;
		boolean autoCommit = connection.getAutoCommit();
		connection.setAutoCommit(false);
	
		for (Question p : questions) {
			if(!isQuestionInDB(p.getId())){
				result += storeQuestion(p);
			}
		}
		connection.commit();
		connection.setAutoCommit(autoCommit);
		return result;
	}
	
	@Override
	public List<Question> loadAllQuestions() throws Exception {
		List<Question> result = new ArrayList<Question>();
		PreparedStatement stmt = connection.prepareStatement("SELECT id from questions");
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			result.add(loadQuestion(rs.getInt(1)));
		return result;
	}
	
	@Override
	public List<Question> searchQuestions(String title) throws SQLException {
		List<Question> result = new ArrayList<Question>();
		PreparedStatement stmt = connection.prepareStatement("SELECT id, title, correct_answer FROM questions WHERE title LIKE ?");
		stmt.setString(1, "%" + title + "%");
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			result.add(loadQuestion(rs.getInt(1)));
		return result;
	}
	
	@Override
	public Question loadLastQuestion() throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT id, title, correct_answer FROM questions ORDER BY id DESC LIMIT 1;");
		ResultSet rs = stmt.executeQuery();
		if (rs.next())
			return new Question(rs.getInt(1), rs.getString(2), rs.getInt(3));
		else
			return null;
	}
	
	@Override
	public List<Question> loadLastThreeQuestion() throws SQLException {
		List<Question> result = new ArrayList<Question>();
		PreparedStatement stmt = connection.prepareStatement("SELECT id, title, correct_answer FROM questions ORDER BY id DESC LIMIT 3;");
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			result.add(loadQuestion(rs.getInt(1)));
		return result;
	}
	
	//Answer methods
	
	@Override
	public Boolean isAnswerInDB(Answer a) throws SQLException{
		if(loadAnswer(a) != null) return true;
		else return false;
	}
	
	@Override
	public int storeAnswer(Answer answer) throws SQLException {
		if(!isAnswerInDB(answer)){
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO answers (title) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			//stmt.setInt(1, answer.getID());
			stmt.setString(1, answer.getTitle());
			int a = stmt.executeUpdate();
			ResultSet res = stmt.getGeneratedKeys();
			while (res.next())
			    System.out.println("Generated key: " + res.getInt(1));
			return a;
		}
		else return -1;
	}
	
	@Override
	public Answer loadAnswer(int id) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("SELECT answ_id, title FROM answers WHERE answ_id = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		if (rs.next())
			return new Answer(id, rs.getString(2));
		else
			return null;
	}
	
	@Override
	public Answer loadAnswer(Answer a) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("SELECT answ_id, title FROM answers WHERE title = ?");
		stmt.setString(1, a.getTitle());
		ResultSet rs = stmt.executeQuery();
		if (rs.next())
			return new Answer(rs.getInt(1), rs.getString(2));
		else
			return null;
	}
	
	@Override
	public int deleteAnswer(int id) throws Exception {
		PreparedStatement stmt = connection
				.prepareStatement("DELETE FROM answers WHERE answ_id = ?");
		stmt.setInt(1, id);
		return stmt.executeUpdate();
	}
	
	@Override
	public int storeAnswers(List<Answer> answers) throws Exception {
		System.out.println("Storing answers...");
		int result = 0;
		boolean autoCommit = connection.getAutoCommit();
		connection.setAutoCommit(false);
		if(answers.size() > 0){
			for (Answer a : answers) {
				int i = storeAnswer(a);
				if(i != -1)
					result += -1;
			}
			connection.commit();
			connection.setAutoCommit(autoCommit);
		}
		System.out.println("....finished");
		return result;
	}
	
	@Override
	public List<Answer> loadAllAnswers() throws Exception {
		List<Answer> result = new ArrayList<Answer>();
		PreparedStatement stmt = connection.prepareStatement("SELECT answ_id from answers");
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			result.add(loadAnswer(rs.getInt(1)));
		return result;
	}
	
	@Override
	public List<Answer> loadAnswersLikeTitle(String title) throws Exception {
		List<Answer> result = new ArrayList<Answer>();
		PreparedStatement stmt = connection.prepareStatement("SELECT answ_id from answers WHERE title LIKE ?");
		stmt.setString(1, "%" + title + "%");
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			result.add(loadAnswer(rs.getInt(1)));
		return result;
	}
	
	@Override
	public List<Answer> loadAnswersNotLikeTitle(String title) throws Exception {
		List<Answer> result = new ArrayList<Answer>();
		PreparedStatement stmt = connection.prepareStatement("SELECT answ_id from answers WHERE title NOT LIKE ?");
		stmt.setString(1, "%" + title + "%");
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			result.add(loadAnswer(rs.getInt(1)));
		return result;
	}
	
	//Topics methods
	
	@Override
	public Boolean isTopicInDB(Topic a) throws SQLException{
		if(loadTopic(a) != null) return true;
		else return false;
	}
	
	@Override
	public int storeTopic(Topic topic) throws Exception {
		if(!isTopicInDB(topic)){
			PreparedStatement stmt = connection.prepareStatement("INSERT INTO topics (topic) VALUES (?)");
			stmt.setString(1, topic.getTopic());
			return stmt.executeUpdate();
		}
		else return -1;
	}
		
	@Override
	public Topic loadTopic(int id) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT topic_id, topic FROM topics WHERE topic_id = ?");
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		if (rs.next())
			return new Topic(rs.getInt(1), rs.getString(2));
		else
			return null;
	}
	@Override
	public Topic loadTopic(Topic p) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("SELECT topic_id, topic FROM topics WHERE topic = ?");
		stmt.setString(1, p.getTopic());
		ResultSet rs = stmt.executeQuery();
		if (rs.next()){
			return new Topic(rs.getInt(1), rs.getString(2));
		}
		else
			return null;
	}
		
	@Override
	public int deleteTopic(int id) throws Exception {
		PreparedStatement stmt = connection.prepareStatement("DELETE FROM topics WHERE topic_id = ?");
		stmt.setInt(1, id);
		return stmt.executeUpdate();
	}
		
	@Override
	public int storeTopics(List<Topic> topics) throws Exception {
		int result = 0;
		boolean autoCommit = connection.getAutoCommit();
		if(topics.size() > 0){
			connection.setAutoCommit(false);
			for (Topic t : topics) {
				int i = storeTopic(t);
				if(i != -1)
					result += i;
			}
			connection.commit();
			connection.setAutoCommit(autoCommit);
		}
		return result;
	}
		
	@Override
	public List<Topic> loadAllTopics() throws Exception {
		List<Topic> result = new ArrayList<Topic>();
		PreparedStatement stmt = connection.prepareStatement("SELECT topic_id from topics ORDER BY topic");
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			result.add(loadTopic(rs.getInt(1)));
		return result;
	}
		
	//Methods for the relation question-answers
	@Override
	public int storeAnswerToQuestion(int question_id, int answer_id) throws Exception {
		PreparedStatement stmt = connection
				.prepareStatement("INSERT INTO question_answers (question_id, answer_id) VALUES (?,?)");
		stmt.setInt(1, question_id);
		stmt.setInt(2, answer_id);
		return stmt.executeUpdate();
	}
	
	/*
	 * Save all answers related to one question.
	 * If the answer doesn't exist in the database, we store it and then store the relation with the question
	 */
	public int storeAnswersToQuestion(Question question) throws Exception {
		int result = 0;
		boolean autoCommit = connection.getAutoCommit();
		connection.setAutoCommit(false);
		for (Answer a : question.getAnswers()) {
			if(!isAnswerInDB(a)){
				result += storeAnswer(a);
			}
			result += storeAnswerToQuestion(question.getId(), a.getId());
		}
		connection.commit();
		connection.setAutoCommit(autoCommit);
		return result;
	}
	
	//Delete one answer of one question
	@Override
	public int deleteAnswerToQuestion(int answer_id) throws Exception {
		PreparedStatement stmt = connection.prepareStatement("DELETE FROM question_answers WHERE answer_id = ?");
		stmt.setInt(1, answer_id);
		return stmt.executeUpdate();
	}
	
	//Delete all answers of one question
	@Override
	public int deleteAnswersToQuestion(Question question) throws Exception {
		int result = 0;
		boolean autoCommit = connection.getAutoCommit();
		connection.setAutoCommit(false);
		for (Answer a : question.getAnswers()) {
			result += deleteAnswerToQuestion(a.getId());
		}
		connection.commit();
		connection.setAutoCommit(autoCommit);
		return result;
	}
	
	//Get all answers of one question
	@Override
	public List<Answer> loadAllAnswersToQuestion(int question_id) throws Exception {
		List<Answer> result = new ArrayList<Answer>();
		PreparedStatement stmt = connection.prepareStatement("SELECT answer_id from question_answers WHERE question_id = ?");
		stmt.setInt(1, question_id);
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			result.add(loadAnswer(rs.getInt(1)));
		return result;
	}
	
	//Methods for the relation question-topics
	@Override
	public int storeTopicToQuestion(int question_id, int topic_id) throws Exception {
		PreparedStatement stmt = connection
				.prepareStatement("INSERT INTO question_topics (question_id, topic_id) VALUES (?,?)");
		stmt.setInt(1, question_id);
		stmt.setInt(2, topic_id);
		return stmt.executeUpdate();
	}
		
	/*
	* Save all topics related to one question.
	* If the topic doesn't exist in the database, we store it and then store the relation with the question
	*/
	public int storeTopicsToQuestion(Question question) throws Exception {
		int result = 0;
		boolean autoCommit = connection.getAutoCommit();
		connection.setAutoCommit(false);
		for (Topic t : question.getTopics()) {
			if(!isTopicInDB(t)){
				result += storeTopic(t);
			}
			result += storeTopicToQuestion(question.getId(), t.getID());
		}
		connection.commit();
		connection.setAutoCommit(autoCommit);
		return result;
	}
		
	//Delete one topic of one question
	@Override
	public int deleteTopicToQuestion(int topic_id) throws Exception {
		PreparedStatement stmt = connection
				.prepareStatement("DELETE FROM question_topics WHERE topic_id = ?");
		stmt.setInt(1, topic_id);
		return stmt.executeUpdate();
	}
		
	//Delete all answers of one question
	@Override
	public int deleteTopicsToQuestion(Question question) throws Exception {
		int result = 0;
		boolean autoCommit = connection.getAutoCommit();
		connection.setAutoCommit(false);
		for (Topic p : question.getTopics()) {
			result += deleteTopicToQuestion(p.getID());
		}
		connection.commit();
		connection.setAutoCommit(autoCommit);
		return result;
	}
		
	//Get all answers of one question
	@Override
	public List<Topic> loadAllTopicsToQuestion(int question_id) throws Exception {
		List<Topic> result = new ArrayList<Topic>();
		PreparedStatement stmt = connection.prepareStatement("SELECT topic_id from question_topics WHERE question_id = ?");
		stmt.setInt(1, question_id);
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			result.add(loadTopic(rs.getInt(1)));
		return result;
	}
	
	//Load all question related to one or more topic
	@Override
	public List<Question> loadAllQuestionsToTopics(List<Integer> topics) throws SQLException{
		List<Question> result = new ArrayList<Question>();
		PreparedStatement stmt = connection.prepareStatement
				("SELECT * from MCQ_schema.Questions q WHERE id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))" +
						"AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))" + 
						"AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))");
		
		stmt.setInt(1, topics.get(0));
		stmt.setInt(2, topics.get(0));
		stmt.setInt(3, topics.get(1));
		stmt.setInt(4, topics.get(1));
		stmt.setInt(5, topics.get(2));
		stmt.setInt(6, topics.get(2));
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			result.add(loadQuestion(rs.getInt(1)));
		return result;
	}
	
	//Load all questions related to one or ore a string, and contains a given string
	
	@Override
	public List<Question> searchQuestionsTopicsString(List<Integer> topics, String title) throws SQLException{
		List<Question> result = new ArrayList<Question>();
		PreparedStatement stmt = connection.prepareStatement
				("SELECT * from MCQ_schema.Questions q WHERE (title LIKE ?) AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))" +
						"AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))" + 
						"AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))");
		stmt.setString(1, "%" + title + "%");
		stmt.setInt(2, topics.get(0));
		stmt.setInt(3, topics.get(0));
		stmt.setInt(4, topics.get(1));
		stmt.setInt(5, topics.get(1));
		stmt.setInt(6, topics.get(2));
		stmt.setInt(7, topics.get(2));
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			result.add(loadQuestion(rs.getInt(1)));
		return result;
	}
	
	@Override
	public List<Question> searchQuestionsTopicsStringAnswer(List<Integer> topics, String title, String answer) throws SQLException{
		List<Question> result = new ArrayList<Question>();
		PreparedStatement stmt = connection.prepareStatement
				("SELECT * from MCQ_schema.Questions q WHERE (title LIKE ?) AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))" +
						"AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))" + 
						"AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))" +
						"AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_answers aq, MCQ_schema.Answers a WHERE aq.answer_id = a.answ_id AND a.title LIKE ?)");
		
		stmt.setString(1, "%" + title + "%");
		stmt.setInt(2, topics.get(0));
		stmt.setInt(3, topics.get(0));
		stmt.setInt(4, topics.get(1));
		stmt.setInt(5, topics.get(1));
		stmt.setInt(6, topics.get(2));
		stmt.setInt(7, topics.get(2));
		stmt.setString(8, "%" + answer + "%");
		System.out.println("Query todo: " + stmt);
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			result.add(loadQuestion(rs.getInt(1)));
		return result;
	}
	
	@Override
	public List<Question> searchQuestionsTopicsNotTitleAnswer(List<Integer> topics, String title, String answer) throws SQLException{
		List<Question> result = new ArrayList<Question>();
		PreparedStatement stmt = connection.prepareStatement
				("SELECT * from MCQ_schema.Questions q WHERE (title NOT LIKE ?) AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))" +
						"AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))" + 
						"AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))" +
						"AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_answers aq, MCQ_schema.Answers a WHERE aq.answer_id = a.answ_id AND a.title LIKE ?)");
		
		stmt.setString(1, "%" + title + "%");
		stmt.setInt(2, topics.get(0));
		stmt.setInt(3, topics.get(0));
		stmt.setInt(4, topics.get(1));
		stmt.setInt(5, topics.get(1));
		stmt.setInt(6, topics.get(2));
		stmt.setInt(7, topics.get(2));
		stmt.setString(8, "%" + answer + "%");
		System.out.println("Query not in title: " + stmt);
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			result.add(loadQuestion(rs.getInt(1)));
		return result;
	}
	
	@Override
	public List<Question> searchQuestionsTopicsTitleNotAnswer(List<Integer> topics, String title, String answer) throws SQLException{
		List<Question> result = new ArrayList<Question>();
		PreparedStatement stmt = connection.prepareStatement
				("SELECT * from MCQ_schema.Questions q WHERE (title LIKE ?) AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))" +
						"AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))" + 
						"AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))" +
						"AND id NOT IN " +
						"(SELECT question_ID FROM MCQ_schema.question_answers aq, MCQ_schema.Answers a WHERE aq.answer_id = a.answ_id AND a.title LIKE ?)");
		
		stmt.setString(1, "%" + title + "%");
		stmt.setInt(2, topics.get(0));
		stmt.setInt(3, topics.get(0));
		stmt.setInt(4, topics.get(1));
		stmt.setInt(5, topics.get(1));
		stmt.setInt(6, topics.get(2));
		stmt.setInt(7, topics.get(2));
		stmt.setString(8, "%" + answer + "%");
		System.out.println("Query not in answer: " + stmt);
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			result.add(loadQuestion(rs.getInt(1)));
		return result;
	}
	
	@Override
	public List<Question> searchQuestionsTopicsNotTitleNotAnswer(List<Integer> topics, String title, String answer) throws SQLException{
		List<Question> result = new ArrayList<Question>();
		PreparedStatement stmt = connection.prepareStatement
				("SELECT * from MCQ_schema.Questions q WHERE (title NOT LIKE ?) AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))" +
						"AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))" + 
						"AND id IN " +
						"(SELECT question_ID FROM MCQ_schema.question_topics WHERE (NULLIF( ?, 0 ) IS NULL) OR (topic_id = ?))" +
						"AND id NOT IN " +
						"(SELECT question_ID FROM MCQ_schema.question_answers aq, MCQ_schema.Answers a WHERE aq.answer_id = a.answ_id AND a.title LIKE ?)");
		
		stmt.setString(1, "%" + title + "%");
		stmt.setInt(2, topics.get(0));
		stmt.setInt(3, topics.get(0));
		stmt.setInt(4, topics.get(1));
		stmt.setInt(5, topics.get(1));
		stmt.setInt(6, topics.get(2));
		stmt.setInt(7, topics.get(2));
		stmt.setString(8, "%" + answer + "%");
		System.out.println("Query not in any: " + stmt);
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			result.add(loadQuestion(rs.getInt(1)));
		return result;
	}
	
	//Test
	
	@Override
	public Boolean isTestInDB(String module, String date) throws SQLException{
		PreparedStatement stmt = connection
				.prepareStatement("SELECT * FROM test WHERE module_code = ? AND date = ?");
		stmt.setString(1, module);
		stmt.setString(2, date);
		ResultSet rs = stmt.executeQuery();
		if (rs.next())
			return true;
		else
			return false;
	}
	
	@Override
	public Test loadTest(String module, String date) throws Exception{
		Test test = new Test();
		test.setModuleCode(module);
		test.setDate(date);
		PreparedStatement stmt = connection.prepareStatement("SELECT test_question_id FROM test WHERE module_code = ? AND date = ?");
		stmt.setString(1, module);
		stmt.setString(2, date);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()){
			Question q = loadQuestion(rs.getInt("test_question_id"));
			q.setAnswers(new ArrayList<Answer>(loadAllAnswersToQuestion(q.getId())));
			test.addQuestion(q);
		}
		return test;
	}
	
	@Override
	public List<Integer> loadQuestionTest(String module, String date) throws Exception{
		List<Integer> questions = new ArrayList<Integer>();
		PreparedStatement stmt = connection.prepareStatement("SELECT test_question_id FROM test WHERE module_code = ? AND date = ?");
		stmt.setString(1, module);
		stmt.setString(2, date);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()){
			questions.add(rs.getInt(1));
		}
		return questions;
	}
	
	@Override
	public int storeTestQuestion(int questionId, String module, String date) throws SQLException{
		PreparedStatement stmt = connection.prepareStatement("INSERT INTO test (module_code, date, test_question_id) VALUES (?,?, ?)");
		stmt.setString(1, module);
		stmt.setString(2, date);
		stmt.setInt(3, questionId);
		return stmt.executeUpdate();
	}
	
	@Override
	public int storeTestAllQuestions(List<Integer> questionsId, String module, String date) throws SQLException{
		int result = 0;
		if(!isTestInDB(module, date)){
			boolean autoCommit = connection.getAutoCommit();
			connection.setAutoCommit(false);
			for (Integer i : questionsId) {
				System.out.println("Store test: " + i);
				result += storeTestQuestion(i, module, date);
			}
			connection.commit();
			connection.setAutoCommit(autoCommit);
			return result;
		}
		else return -1;
	}
	
	@Override
	public List<Test> loadAllTests() throws Exception {
		List<Test> result = new ArrayList<Test>();
		PreparedStatement stmt = connection.prepareStatement("SELECT DISTINCT module_code, date FROM test ORDER BY module_code");
		ResultSet rs = stmt.executeQuery();
		while (rs.next())
			result.add(loadTest(rs.getString(1), rs.getString(2)));
		return result;
	}
	
	@Override
	public Test loadLastTest() throws Exception {
		PreparedStatement stmt = connection.prepareStatement("SELECT DISTINCT module_code, date FROM test ORDER BY module_code DESC LIMIT 1;");
		ResultSet rs = stmt.executeQuery();
		if (rs.next()){
			Test test = loadTest(rs.getString(1), rs.getString(2));
			return test;
		}
		else
			return null;
	}
	
	@Override
	public List<Integer> loadQuestionsInTests(List<Test> tests) throws Exception{
		List<Integer> questions = new ArrayList<Integer>();
		for(Test test : tests){
			List<Integer> aux = loadQuestionTest(test.getModuleCode(), test.getDate());
			for(Integer i : aux){
				if(!questions.contains(i))
					questions.add(i);
			}
		}
		return questions;
		
	}
}
