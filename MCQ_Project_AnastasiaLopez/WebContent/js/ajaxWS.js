$(document).ready(function() {

	$( "#submit" ).click(addNewQuestion);
	
	$("#submitTest").click(addNewTest);
	
	$("#searchQinTest").click(function() {
			$.post('addTest/getQuestionsTitleAdded', {question: 1}, function(titles) {
				$.post('addTest/getQuestionsIdAdded', {question: 1}, function(ids) {
					$('#formSearchQinTest').submit();
				});
			});

	});
	
	$( "#submitQuestion" ).click(editQuestion);
	
});

function changeValue(radio){
	if(radio != "radio1"){
		document.getElementById("radio1").checked = false;
	}
	if(radio != "radio2"){
		document.getElementById("radio2").checked = false;
	}
	if(radio != "radio3"){
		document.getElementById("radio3").checked = false;
	}
	if(radio != "radio4"){
		document.getElementById("radio4").checked = false;
	}
}

function addNewQuestion(){
	var response = validateQuestionForm();
	if(response == true){
		$.ajaxSetup({async: false});
	
		$.post('addAnswer', $("#newAnswer1").serialize(), function(response) {
		});
		$.post('addAnswer', $("#newAnswer2").serialize(), function(response) {
		});
		$.post('addAnswer', $("#newAnswer3").serialize(), function(response) {
		});
		$.post('addAnswer', $("#newAnswer4").serialize(), function(response) {
		});
		$.post('addTopic', {topic: $("#topic").val()}, function(response) {
		});
		
		$.ajaxSetup({async: true});
		var val = $('input:radio[name=correct]:checked').val();	
		if(val == "A"){
			$.post('getAnswer', $("#newAnswer1").serialize(), function(response) {
				addQuestion(response);
			});
		}
	
		if(val == "B"){
			$.post('getAnswer', $("#newAnswer2").serialize(), function(response) {
				addQuestion(response);
			});
		}
		
		if(val == "C"){
			$.post('getAnswer', $("#newAnswer3").serialize(), function(response) {
				addQuestion(response);
			});
		}
		
		if(val == "D"){
			$.post('getAnswer', $("#newAnswer4").serialize(), function(response) {
				addQuestion(response);
			});
		}
	}
}
function addQuestion(correct){
	//$("#id").val(1);
	$("#correct_answer").val(correct);
	//alert($("#title").val());
	 
	$.post('addNewQuestion', {id: $("#id").val(), title: $("#question_title").val(), correct_answer_id : correct}, function(response) {
		$('.response-headers').text("Question added with ID: " + response.id + " and title: " + response.title);
	});
	
	$('#newQuestion').submit();
	$('#newAnswer1').trigger("reset");
	$('#newAnswer2').trigger("reset");
	$('#newAnswer3').trigger("reset");
	$('#newAnswer4').trigger("reset");
	$('#newQuestion').trigger("reset");
	$('#topics').trigger("reset");
	
}

function addAnswer(){
	$.ajaxSetup({async: false});
	$('.c').children("div[name='all']").each(function(){
		var inputValueId = $(this).attr("id");
		var id = inputValueId.replace("addedAnswer", "");
		$.post('http://localhost:8080/MCQ_Project_AnastasiaLopez/MCQ/addQuestion/addAnswerById', {id: id}, function(titles) {
		});
	});
	$.ajaxSetup({async: true});
}
function addEditedQuestion(){
	$.ajaxSetup({async: false});
	var correctAnswer = $('.c').children('.correct').attr("id");
	var correctId = correctAnswer.replace("addedAnswer", "");
	
	$.post('http://localhost:8080/MCQ_Project_AnastasiaLopez/MCQ/addQuestion/addNewQuestion', {id: $("#id").val(), title: $("#question_title").val(), correct_answer_id: correctId}, function(response) {
	});
	$.ajaxSetup({async: true});
	
	$('#editQuestion').submit();
	
}

function editQuestion() {
		var response = validateEditForm();
		if(response == true)
			$.when(addAnswer()).done(addEditedQuestion);
}

function addNewTest(){
	var response = validateForm();
	if(response == true){
		$.post('addTest/newTest', {module: $("#module").val(), date: $("#date").val()}, function(response) {
			//alert(response);
			if(response == "testAdded")
				$('#formNewTest').submit();
			else
				$('#error').text(response);
			
		});
	}
}