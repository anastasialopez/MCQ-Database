package POJO;

public class Topic {
	private int id;
	private String topic;
	
	public Topic(){
		
	}
	public Topic(int id, String topic){
		this.id = id;
		this.topic = topic;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public int getID(){
		return id;
	}
	
	public void setTopic(String topic){
		this.topic = topic;
	}
	
	public String getTopic(){
		return topic;
	}

}
