<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ page import="java.util.Collection,
                 java.util.Iterator,
                 java.util.ArrayList"%>
                 
<%@ page import="POJO.Question" %>
<%@ page import="POJO.Answer" %>
<%@ page import="POJO.Topic" %>
<%@ page import="POJO.Test" %>

<jsp:useBean id='db'
             scope='session'
             class='Database.MCQ_SQL' />
             
<html>
	<%
	String title = request.getParameter("title");
	String answer = request.getParameter("answer_title");
	String select0 = request.getParameter("select0");
	String select1 = request.getParameter("select1");
	String select2 = request.getParameter("select2");
	String checkQuestion = request.getParameter("question");
	String checkAnswer = request.getParameter("answer");
	String[] checkBoxes = request.getParameterValues("addTest");
	
	
	ArrayList<Integer> topicsId = new ArrayList<Integer>();
	
	if(select0 != null && select1 != null && select2 != null){
		topicsId.add(Integer.parseInt(select0));
		topicsId.add(Integer.parseInt(select1));
		topicsId.add(Integer.parseInt(select2));
	}
	
	System.out.println("Select: " + topicsId.size());
	
	ArrayList<Test> tests = new ArrayList<Test>();
	ArrayList<Integer> testsResults = new ArrayList<Integer>();
	if(checkBoxes != null){
		for(int i = 0; i < checkBoxes.length; ++i){
			String[] s = checkBoxes[i].split("%");
			tests.add(new Test(s[0], s[1]));
		}
	}
	if(tests != null){
		testsResults.addAll(db.loadQuestionsInTests(tests));
	}
	
	
	ArrayList<Question> results = new ArrayList<Question>();
	
	if(topicsId.size() > 0){
		if(checkQuestion != null && checkAnswer != null){
			if(checkQuestion.equals("no") && checkAnswer.equals("no"))
				results.addAll(db.searchQuestionsTopicsNotTitleNotAnswer(topicsId, title, answer));
			else if(checkQuestion.equals("no"))
				results.addAll(db.searchQuestionsTopicsNotTitleAnswer(topicsId, title, answer));
			else if(checkAnswer.equals("no"))
				results.addAll(db.searchQuestionsTopicsTitleNotAnswer(topicsId, title, answer));
			else
				results.addAll(db.searchQuestionsTopicsStringAnswer(topicsId, title, answer));
		}
		else if(checkQuestion != null){
			if(checkQuestion.equals("no"))
				results.addAll(db.searchQuestionsTopicsNotTitleAnswer(topicsId, title, answer));
			else
				results.addAll(db.searchQuestionsTopicsStringAnswer(topicsId, title, answer));
		}
		else if(checkAnswer != null){
			if(checkAnswer.equals("no"))
				results.addAll(db.searchQuestionsTopicsTitleNotAnswer(topicsId, title, answer));
			else
				results.addAll(db.searchQuestionsTopicsStringAnswer(topicsId, title, answer));
		}
		else
			results.addAll(db.searchQuestionsTopicsStringAnswer(topicsId, title, answer));
	}

	ArrayList<Question> finalResults = new ArrayList<Question>();
	if(testsResults.size() > 0){
		for(Question q : results){
			if(testsResults.contains(q.getId()))
				finalResults.add(q);
		}
	}
	else
		finalResults.addAll(results);
	
	System.out.println("results size: " + finalResults.size());
	
	if(finalResults.size() > 0){
		%>
		<div class="container">
			<p style="margin-top: -1em;">To create a new question by editing one, click on the question</p>
			<div class="listContainer" style="margin-bottom: 4em;">
				<ul id="expList">
				<%
					for(Question q : finalResults){
						ArrayList<Answer> answers = new ArrayList<Answer>(db.loadAllAnswersToQuestion(q.getId()));
						%>
							<li class="title_answer">
							<a href='<%="editQuestion.jsp?id="+q.getId()%>'><%= q.getTitle() %></a>
								<ul>
									<%
									for(Answer a: answers){
										if(a.getId() == q.getCorrectAnswer()){
										%>
											<li class="answer">
												<span><%= a.getTitle() %></span>
												<img src="../img/correct.png" width="20" height="20"/>
											</li>
										<%
										}
										else{
										%>
											<li class="answer"><%= a.getTitle() %></li>
										<%
										}
									}
									%>
								</ul>
							</li>
						<%
					}
				%>
				</ul>
			</div>

			
		</div>
		<div id="message"></div>
	<%
	}
	%>
	<jsp:include page="footer.jsp"></jsp:include>
</html>