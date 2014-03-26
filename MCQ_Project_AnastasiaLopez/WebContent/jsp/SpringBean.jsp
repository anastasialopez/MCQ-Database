<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Insert title here</title>
</head>
<body>
<form:form method="post" action="../MCQ/addAnswer" commandName="answer">
		<table>
			<tr>
				<td>Id</td>
				<td><form:input type="text" path="id" size="16" /></td>
				<td></td>
			</tr>
			<tr>
				<td>Title</td>
				<td><form:input type="text" path="title" size="40" /></td>
				<td></td>
			</tr>			
			<tr>
				<td><input type="submit" name="addAuthor" value="Submit" /> <input
					type="reset"></td>
			</tr>
		</table>
		<p>${message}</p>
		<p>
			<form:errors path="*" cssClass="error" />
		</p>
	</form:form>
</body>
</html>