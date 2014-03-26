<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ page import="java.util.Collection,
                 java.util.Iterator,
                 java.util.ArrayList"%>
                 
<%@ page import="POJO.Question" %>
<%@ page import="POJO.Answer" %>
<%@ page import="POJO.Topic" %>

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
	
	ArrayList<Integer> topicsId = new ArrayList<Integer>();
	
	if(select0 != null && select1 != null && select2 != null){
		topicsId.add(Integer.parseInt(select0));
		topicsId.add(Integer.parseInt(select1));
		topicsId.add(Integer.parseInt(select2));
	}
	
	System.out.println("Select: " + topicsId.size());
	
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

	
	System.out.println("results size: " + results.size());
	
	if(results.size() > 0){
		%>
		<form id="formNewTest" method="post" action="/MCQ_Project_AnastasiaLopez/jsp/result.jsp">
		<p>
			Test Module: 
			<input type="text" name="module" id="module" size="20">
			Test Date: <input type="date" id="date" name="date">
		</p>
		<div class="container">
			<div class="listContainer">
				<ul id="expList">
				<%
					for(Question q : results){
						ArrayList<Answer> answers = new ArrayList<Answer>(db.loadAllAnswersToQuestion(q.getId()));
						%>
							<li class="title_answer" style="width: 450px"><%= q.getTitle() %>
								<input type="checkbox" id="<%=q.getId()%>" name="addQuestion" value="<%=q.getId()%>-<%=q.getTitle() %>" style="float: right; margin-right: -5em;">
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
			<div class="addedQuestions" id="addedQuestions">
				<h2>Questions Added</h2>
			</div>
			
		</div>
		</form>
		<div><input class="button" style="margin-bottom: 1em;" type="submit" id="submitTest" value="Add Test" /></div>
		<div id="error"></div>
	<%
	}
	%>
	<jsp:include page="footer.jsp"></jsp:include>
</html>