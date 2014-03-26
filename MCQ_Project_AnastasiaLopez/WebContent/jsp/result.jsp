<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
		<h1>Result</h1>
		<hr class="menu">
		<jsp:include page="menu.jsp"></jsp:include>
		<%
		Thread.sleep(1000);
		String title = request.getParameter("question_title");
		String edit = request.getParameter("edit");
		System.out.println(title);
		String module = request.getParameter("module");
		String date = request.getParameter("date");
		
		if(module != null && date != null){
			Test test = db.loadTest(module, date);
		%>
			<div class="container">
				<div class="listContainer" style="border: none; overflow: inherit;">
					<p>Test Module Code: <b> <%=module %></b></p>
					<p>Test Date: <b> <%=date %></b></p>
					<p>Test Questions</p>
					<ul id="expList">
					<%
						for(Question q : test.getQuestions()){
							//ArrayList<Answer> answers = new ArrayList<Answer>(db.loadAllAnswersToQuestion(q.getId()));
							%>
								<li class="title_answer"><%= q.getTitle() %>
									<ul>
										<%
										for(Answer a: q.getAnswers()){
											if(a.getId() == q.getCorrectAnswer()){
											%>
												<li class="answer">
													<span style="margin-left: 1em;"><%= a.getTitle() %></span>
													<img src="../img/correct.png" width="20" height="20"/>
												</li>
											<%
											}
											else{
											%>
												<li class="answer">
													<span style="margin-left: 1em;"><%= a.getTitle() %></span>
												</li>
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
		<%
		}
		
		else if(title != null){
			Question question = db.loadLastQuestion();
			%>
			<div class="container">
				<p>Question Statement: <b><%= question.getTitle() %></b></p>
				<ul style="margin-left: 7em;">
				<%
					ArrayList<Answer> answers = new ArrayList<Answer>(db.loadAllAnswersToQuestion(question.getId()));
					System.out.println("Size a: " + answers.size());
					for(Answer a: answers){
						if(a.getId() == question.getCorrectAnswer()){
						%>
							<li class="answer">
								<span style="margin-left: 1em;"><%= a.getTitle() %></span>
								<img src="../img/correct.png" width="20" height="20"/>
							</li>
						<%
						}
						else{
						%>
							<li class="answer">
								<span style="margin-left: 1em;"><%= a.getTitle() %></span>
							</li>
						<%
						}
					}
				%>
				</ul>
				<ul>
				<%
					ArrayList<Topic> topics = new ArrayList<Topic>(db.loadAllTopicsToQuestion(question.getId()));
					System.out.println("Topics size: " + topics.size());
					if(topics.size() >= 1){
					%>
						<p><b>With topics:</b></p>
					<%
						for(Topic t : topics ){
						%>
							<li><%= t.getTopic() %></li>
						<%
						}
					}else{
					%>
						<p><b>With no topics</b></p>
					<%
					}
					
				%>
				</ul>
				</div>
			<%
		}
		
		else if(edit != null){
			Question question = db.loadLastQuestion();
			ArrayList<Topic> topics = new ArrayList<Topic>(db.loadAllTopicsToQuestion(question.getId()));
			ArrayList<Topic> topicsPrevious = new ArrayList<Topic>(db.loadAllTopicsToQuestion(Integer.parseInt(edit)));
			if(topics.size() < 1){
				System.out.println("Here");
				topics.addAll(topicsPrevious);
				question.setTopics(topics);
				db.storeTopicsToQuestion(question);
			}		
			System.out.println("ID: " + question.getId());
			%>
			<div class="container">
				<p>Question Statement: <b><%= question.getTitle() %></b></p>
						<ul style="margin-left: 7em;">
						<%
							ArrayList<Answer> answers = new ArrayList<Answer>(db.loadAllAnswersToQuestion(question.getId()));
							System.out.println("Size a: " + answers.size());
							for(Answer a: answers){
								if(a.getId() == question.getCorrectAnswer()){
								%>
									<li class="answer">
										<span style="margin-left: 1em;"><%= a.getTitle() %></span>
										<img src="../img/correct.png" width="20" height="20"/>
									</li>
								<%
								}
								else{
								%>
									<li class="answer">
										<span style="margin-left: 1em;"><%= a.getTitle() %></span>
									</li>
								<%
								}
							}
						%>
						</ul>
						<ul>
						<%
						if(topics.size() >= 1){
						%>
							<p><b>With topics:</b></p>
						<%
							for(Topic t : topics ){
							%>
								<li><%= t.getTopic() %></li>
							<%
							}
						}else{
						%>
							<p><b>With no topics</b></p>
						<%
						}
						%>
						</ul>
					</div>
				<%
			}
			%>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</body>
</html>