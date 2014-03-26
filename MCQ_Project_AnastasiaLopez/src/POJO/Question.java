package POJO;

import java.util.ArrayList;

public class Question {
	
	private int id;
	private String title;
	private int correct_answer_id;
	private ArrayList<Answer> answers;
	private ArrayList<Topic> topics;
	
	public Question(){
		answers = new ArrayList<Answer>();
	}
	
	public Question(int id, String title, int answ_id){
		this.id = id;
		this.title = title;
		this.correct_answer_id = answ_id;
	}
	
	public Question(int id, String title){
		this.id = id;
		this.title = title;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public int getCorrectAnswer(){
		return correct_answer_id;
	}
	
	public void setCorrectAnswer(int answer){
		this.correct_answer_id = answer;
	}
	
	public ArrayList<Answer> getAnswers(){
		return answers;
	}
	
	public void setAnswers(ArrayList<Answer> answers){
		this.answers = answers;
	}
	
	public void addAnswer(Answer answer){
		answers.add(answer);
	}
	
	public void deleteAnswer(Answer answer){
		answers.remove(answer);
	}
	
	public ArrayList<Topic> getTopics(){
		return topics;
	}
	
	public void setTopics(ArrayList<Topic> topics){
		this.topics = topics;
	}
	
	public void addTopic(Topic topic){
		topics.add(topic);
	}
	
	public void deleteTopic(Topic topic){
		topics.remove(topic);
	}
	
	@Override
	public String toString() {
		return "Question [id=" + id + "; title=" + title + "]";
	}

}
