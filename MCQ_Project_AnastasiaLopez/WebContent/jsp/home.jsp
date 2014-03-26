<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
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
		ArrayList<Question> questions = new ArrayList<Question>(db.loadLastThreeQuestion());
		%>
		<h1>MCQ Home</h1>
		<hr class="menu">
		<jsp:include page="menu.jsp"></jsp:include>
		<div class="box">
			<h2>Welcome back!</h2>
			<p>Last added questions</p>
			<div class="listContainer" style="border: none; overflow: inherit;">
				<ul id="expList">
				<%
					for(Question q : questions){
						ArrayList<Answer> answers = new ArrayList<Answer>(db.loadAllAnswersToQuestion(q.getId()));
						%>
							<li class="title_answer"><%= q.getTitle() %>
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
		<jsp:include page="footer.jsp"></jsp:include>
	</body>
</html>