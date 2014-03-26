<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
	<jsp:include page="header.jsp"></jsp:include>
	<body>
		
		<%String n = request.getParameter("num");
		int num = 0;
		if(n != null)
			num = Integer.parseInt(n);
		else
			num = 2;
		%>
		<h1>Create a new Question</h1>
		<hr class="menu">
		<jsp:include page="menu.jsp"></jsp:include>
		<div class="box">
			<form id="newQuestion" method="post" action="/MCQ_Project_AnastasiaLopez/jsp/result.jsp">
				Insert the statement of the question: <input type="text" name="question_title" id="question_title" size="40">
				<input type="hidden" name="id" id="id" value="1">
				<input type="hidden" name="correct_answer_id" id="correct_answer_id">
				<br>
			</form>
			<table class="table">
				<tr>
					<th> ID </th> <th> TITLE </th> <th> CORRECT ANSWER </th>
				</tr>
				<form id="newAnswer1">
					
						<tr>
							<td>A:
							<input type="hidden" name="id" id="id" value="1"/></td>
							<td><input type="text" name="title" id="title" /></td>
							<td><input type="radio" name="correct" value="A" id="radio1" onclick="changeValue('radio1')"/></td>
						</tr>
			    </form>
		    
			    <form id="newAnswer2">
						<tr>
							<td>B:
							<input type="hidden" name="id" id="id" value="1"/></td>
							<td><input type="text" name="title" id="title" /></td>
							<td><input type="radio" name="correct" value="B" id="radio2" onclick="changeValue('radio2')"/></td>
						</tr>
			    </form>
		    
			     <form id="newAnswer3">
						<tr>
							<td>C:
							<input type="hidden" name="id" id="id" value="1"/></td>
							<td><input type="text" name="title" id="title" /></td>
							<td><input type="radio" name="correct" value="C" id="radio3" onclick="changeValue('radio3')"/></td>
						</tr>
			    </form>
		    
			     <form id="newAnswer4">
						<tr>
							<td>D:
							<input type="hidden" name="id" id="id" value="1"/></td>
							<td><input type="text" name="title" id="title" /></td>
							<td><input type="radio" name="correct" value="D" id="radio4" onclick="changeValue('radio4')"/></td>
						</tr>
			    </form>
	    	</table>
		    <form id="topics">
		    	<p>Please insert the related topics separated by a coma ','</p>
				<input type="text" name="topic" id="topic" size="30" />
			</form>
			<button class="button" style="margin-left: 0;" id="submit">Add Question</button>
			<div id="error"></div>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</body>
</html>