package controllers;

import java.sql.SQLException;
import java.util.ArrayList;

import lib.Lib;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import Database.MCQ_SQL;

@Controller
@RequestMapping(value = "/addTest")
public class ControllerTest {
	
	MCQ_SQL db;
	ArrayList<Integer> ids;
	ArrayList<String> questions;
	
	ControllerTest() {
		ids = new ArrayList<Integer>();
		questions = new ArrayList<String>();
		try {
			db = new MCQ_SQL(Lib.getConnection("MCQ_schema"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String addTest(String questions) {
		return "addTest";
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String addNewTest() {
		System.out.println("TEST");
		return "addTest";
	}
	
	@RequestMapping(value = "/newTest", method = RequestMethod.POST)
	@ResponseBody
	public String newTest(@RequestParam("module") String module, @RequestParam("date") String date) {
		System.out.println("NEW TEST");
		System.out.println("ids: " + ids);
		int i = 0;
		try {
			i = db.storeTestAllQuestions(ids, module, date);
			System.out.println("Result i: " + i);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(i == -1){
			System.out.println("HERE");	
			return "A test with that module and that date already exists, please change the date or the module code.";
		}
		ids.clear();
		questions.clear();
		return "testAdded";
	}
	
	@RequestMapping(value = "/getQuestionsTitleAdded", method = RequestMethod.POST)
	public @ResponseBody ArrayList<String> getQuestionsTitleAdded(@RequestParam("question") int id) {
		System.out.println("Get title of Questions in Test");
		return questions;
	}
	
	@RequestMapping(value = "/getQuestionsIdAdded", method = RequestMethod.POST)
	public @ResponseBody ArrayList<Integer> getQuestionsIdAdded(@RequestParam("question") int id) {
		System.out.println("Get id of Questions in Test");
		return ids;
	}
	
	@RequestMapping(value = "/addQuestionsAdded", method = RequestMethod.POST)
	public @ResponseBody ArrayList<String> addQuestionsAdded(@RequestParam("question_id") int id, @RequestParam("question_title") String title) {
		System.out.println("Add Questions in Test");
		ids.add(id);
		questions.add(title);
		return questions;
	}
	
	@RequestMapping(value = "/removeQuestionsAdded", method = RequestMethod.POST)
	public @ResponseBody ArrayList<String> removeQuestionsAdded(@RequestParam("question_id") int id, @RequestParam("question_title") String title) {
		System.out.println("Remove Questions in Test");
		System.out.println(ids + "      " + id);
		ids.remove((Object)(new Integer(id)));
		questions.remove(title);
		System.out.println("questions: " + questions + "      " + "question_title: " + title);
		return questions;
	}

}
