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
	<jsp:include page="header.jsp"></jsp:include>
	<body>
		<%
		int s0 = 0, s1 = 0, s2 = 0;
		String select0 = request.getParameter("select0");
		String select1 = request.getParameter("select1");
		String select2 = request.getParameter("select2");
		
		if(select0 != null) 
			s0 = Integer.parseInt(select0);
		
		if(select1 != null) 
			s1 = Integer.parseInt(select1);
		
		if(select2 != null) 
			s2 = Integer.parseInt(select2);
		
		%>
		<h1>Add new Test</h1>
		<hr class="menu">
		<jsp:include page="menu.jsp"></jsp:include>
		<form method="post" action="addTest" id="formSearchQinTest">
	
			<div class="s1">
				<p>The question's statement</p>
				<p style="margin-top: 10px;">
					should contain<input type="radio" name="question" value="yes" id="questionYes" style="margin-left: 28px;"/>
					shouldn't contain<input type="radio" name="question" value="no" id="questionNo" style="margin-left: 8px;"/>
				</p>
				<input type="text" name="title" id="title" size="20" style = "margin: 20px 0px 16px 25px;">
			</div>
			<div class="s1">
				<p>The answers's statement</p>
				<p style="margin-top: 10px;">	
					should contain<input type="radio" name="answer" value="yes" id="questionYes" style="margin-left: 28px;"/>
					shouldn't contain<input type="radio" name="answer" value="no" id="questionNo" style="margin-left: 8px;"/>
				</p>
				<input type="text" name="answer_title" id="answer_title" size="20" style = "margin: 20px 0px 16px 25px;">
			</div>
			<div class=s1>
				<p>The question has the following topics:</p>
				<br>
				<%
				ArrayList<Topic> topics = new ArrayList<Topic>(db.loadAllTopics());
				for(int i = 0; i < 3; ++i){
					%>
					<select name="select<%=i%>" id="select<%=i%>">
						<option value="0"></option>
					<%
						for(int j = 0; j < topics.size(); ++j){
							String t = topics.get(j).getTopic();
							t = t.toLowerCase();
							t = t.replaceAll(" ", "");
							
							int id = topics.get(j).getID();
	
							if((i == 0 && s0 == id) || (i == 1 && s1 ==  id) || (i == 2 && s2 == id)){
								%>
									<option value="<%=id%>" selected><%=t%></option>
								<%
							}
							else{
								%>
								<option value="<%=id%>"><%=t%></option>
								<%
							}
							
						}
					%>
					</select>
					<%
				}
				%>
			</div>
		</form>
		<input id="searchQinTest" class="button" style="margin-left: -148px; margin-top: 1em;" type="submit" value="Search" />
		<jsp:include page="searchResult.jsp"></jsp:include>
	</body>
</html>