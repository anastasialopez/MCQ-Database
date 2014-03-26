package controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.validation.Valid;

import lib.Lib;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Database.MCQ_SQL;
import POJO.Answer;
import POJO.Question;
import POJO.Topic;

@Controller
@RequestMapping(value = "/addQuestion")
public class ControllerQuestion {
	
	MCQ_SQL db; 
	ArrayList<Answer> answers;
	ArrayList<Topic> topics;
	Question question;

	ControllerQuestion() {
		answers = new ArrayList<Answer>();
		topics = new ArrayList<Topic>();
		try {
			db = new MCQ_SQL(Lib.getConnection("MCQ_schema"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    @RequestMapping(value = "/addAnswer", method = RequestMethod.POST)
	public String addAnswer(@Valid Answer answer, BindingResult result, ModelMap model) {
    	System.out.println("Add Answer post");
    	//model.addAttribute("answer", new Answer()); 
		if (!result.hasErrors()) {
			try {
				db.storeAnswer(answer);
				answer = db.loadAnswer(answer);
				if(!answers.contains(answer))
					answers.add(answer);
			} catch (SQLException e) {
			}
		}
		return "addQuestion";
	}
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
	public String addAnswer(Answer answer) {
		return "addQuestion";
	}
    
    @RequestMapping(value = "/getAnswer", method = RequestMethod.POST)
    @ResponseBody
	public Integer getAnswer(@Valid Answer answer, BindingResult result, ModelMap model) {
    	System.out.println("Get Answer");
    	//model.addAttribute("answer", new Answer()); 
		if (!result.hasErrors()) {
			try {
				answer = db.loadAnswer(answer);
				System.out.println("Answer loaded: " + answer.getId());
			} catch (SQLException e) {
			}
		}
		return answer.getId();
	}
    
    @RequestMapping(value = "/getAnswer", method = RequestMethod.GET)
    public String getAnswer(Model model) {
    	System.out.println("HELP!");
    	Answer answer = new Answer(); // declareing
    	model.addAttribute("answer", answer); // adding in model
        return "addQuestion";
    }
    
    @RequestMapping(value = "/addTopic", method = RequestMethod.POST)
	public String addTopic(@RequestParam("topic") String topic) {
    	System.out.println("Add Topic");
    	//model.addAttribute("answer", new Answer()); 	
    	topic = topic.replaceAll(" ", "");
			String[] topics = topic.split(",");
			for(String t : topics){
				try {
					t = t.toLowerCase();
					Topic p = new Topic(1, t);
					System.out.println("topic: " + p.getTopic());
					db.storeTopic(p);
					p = db.loadTopic(p);
					if(!this.topics.contains(p)){
						this.topics.add(p);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		return "addQuestion";
	}
    
    @RequestMapping(value = "/addNewQuestion", method = RequestMethod.POST)
	public @ResponseBody int addNewQuestion(@RequestParam("id") int id, @RequestParam("title") String title, @RequestParam("correct_answer_id") int correct_answer_id) {
    	System.out.println("Add new question!!");
    	//model.addAttribute("answer", new Answer()); 
		//if (!result.hasErrors()) {
    	int rows = 0;
			Question question = new Question(id, title, correct_answer_id);
			try {
				question.setAnswers(answers);
				question.setTopics(topics);
				System.out.println("here: " + title);
				rows = db.storeQuestion(question);
				System.out.println("rows affected: " + rows);
				
			} catch (Exception e) {
			}
			answers.clear();
			topics.clear();
		return rows;
	}
    
    @RequestMapping(value = "/addAnswerById", method = RequestMethod.POST)
	public String addAnswerById(@RequestParam("id") int id) {
    	System.out.println("Add Answer to arrays");
    	if(answers.size() >= 4)
    		answers.clear();
    	//model.addAttribute("answer", new Answer()); 
		try {
			Answer answer = db.loadAnswer(id);
			if(!answers.contains(answer))
				answers.add(answer);
		} catch (SQLException e) {
		}
		System.out.println("size of answers: " + answers.size());
		System.out.println("answers: " + answers);
		return "addQuestion";
	}
    
    /*@RequestMapping(value = "/addAnswerQuestion", method = RequestMethod.POST)
	public @ResponseBody String addAnswerQuestion(@Valid Answer answer, @Valid Question question, BindingResult result, ModelMap model) {
    	System.out.println("AQ!!");
    	String message = "";
    	//model.addAttribute("answer", new Answer()); 
		if (!result.hasErrors()) {
			for(Answer a : answers){
				System.out.println(a.getId());
				try {
					db.storeAnswerToQuestion(this.question.getId(), a.getId());
					message += "Answer:  " + answer + "\n";
				} catch (Exception e) {
					message = "Could not store answer " + answer + " " + "because of "
						+ e.getClass().getSimpleName();
				}
				model.addAttribute("message", message);
			}
		}
		return message;
    }*/
}
