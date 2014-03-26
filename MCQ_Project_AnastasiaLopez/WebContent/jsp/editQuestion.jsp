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
	<jsp:include page="header.jsp"></jsp:include>
	<body>
		<%
		String questionId = request.getParameter("id");
		String answer = request.getParameter("answer_title");
		String checkAnswer = request.getParameter("answer");
		
		Question question = new Question();
		if(questionId != null){
			question = db.loadQuestion(Integer.parseInt(questionId));
		}
		ArrayList<Answer> question_answers = new ArrayList<Answer>(db.loadAllAnswersToQuestion(question.getId()));
		
		ArrayList<Answer> answers = new ArrayList<Answer>();
		if(checkAnswer != null){
			if(checkAnswer.equals("no"))
				answers.addAll(db.loadAnswersNotLikeTitle(answer));
			else
				answers.addAll(db.loadAnswersLikeTitle(answer));
		}
		else
			answers.addAll(db.loadAnswersLikeTitle(answer));
		
		System.out.println("Tamaño antes " + answers.size() + "....");

		%>
		<h1>Edit Question</h1>
		<hr class="menu">
		<jsp:include page="menu.jsp"></jsp:include>
		<div class="box">
			<p style="font-weight: bold;">Question Statement  <input style="margin-left: 1em;" type="text" name="question_title" id="question_title" size="40" value="<%=question.getTitle()%>"></p>
			<input type="hidden" name="id" id="id" value="1">
			<p>Select the correct answer by clicking on it</p>
			<div id="content">
					<div id="answers">
					<%	
						int count = 1;
						for(Answer a : question_answers){
						%>
							<div class="c" id="<%=count%>">
								<div id="answerSlot<%=count%>" name="answerSlots">
									<p>Answer <%= count %></p>
								</div>
								<div id="addedAnswer<%=a.getId()%>" name="all" style="position: absolute; z-index: 10">
									<p><%=a.getTitle()%></p>
								</div>
								<input type="image" src="../img/delete.png" alt="Submit" width="25" height="25"  name="remove" value="<%=count%>" style="float: right; margin: 6px -20px;">
							</div>
						<%
							++count;
						}
					%>
					</div>
				<form id="editQuestion" method="post" action="/MCQ_Project_AnastasiaLopez/jsp/result.jsp" >
					<input type="hidden" name="edit" id="edit" value="<%=questionId %>">
				</form>
				<button class="button" style="margin: 1em 0;" id="submitQuestion">Add Question</button>
				<div id="error"></div>
				<hr class="separator" style="width: 112%; margin-left: -5em;">
				<form method="post">
					<h2 style="margin-left: -2em;">Search Answers</h2>
					<div class="s1">
						<p>The answers's statement</p>
						<p style="margin-top: 10px;">	
							should contain<input type="radio" name="answer" value="yes" id="questionYes" style="margin-left: 28px;"/>
							shouldn't contain<input type="radio" name="answer" value="no" id="questionNo" style="margin-left: 8px;"/>
						</p>
						<input type="text" name="answer_title" id="answer_title" size="20" style = "margin: 20px 0px 16px 25px;">
						<input style="font-size: 15px;" class="button" type="submit" value="Search" />
					</div>
				</form>
				<div id="addedAnswers">
				<%
					for(Answer a : answers){
					%>
						<div id="addedAnswer<%=a.getId()%>" name="all">
							<p><%=a.getTitle()%></p>
						</div>
						
					<%
					}
				%>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</body>
</html>