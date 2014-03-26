package POJO;

import java.util.ArrayList;

public class Test {
	private String date;
	private String module_code;
	private ArrayList<Question> questions;
	
	public Test(){
		questions = new ArrayList<Question>();
	}
	
	public Test(String date, String module_code, ArrayList<Question>questions_id){
		this.date = date;
		this.module_code = module_code;
		this.questions = questions_id;
	}
	
	public Test(String module_code, String date){
		this.date = date;
		this.module_code = module_code;
	}
	
	public void setModuleCode(String module){
		this.module_code = module;
	}
	
	public String getModuleCode(){
		return this.module_code;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public String getDate(){
		return date;
	}
	
	public void addQuestion(Question q){
		questions.add(q);
	}
	
	public void setQuestions(ArrayList<Question> questions){
		this.questions = questions;
	}
	
	public ArrayList<Question> getQuestions(){
		return questions;
	}
	
	@Override
	public String toString(){
		return "Test: " + module_code + ", " + date;
	}

}
