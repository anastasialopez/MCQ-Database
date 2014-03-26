function prepareList() {
	    jQuery("#expList ul").hide();

	    $("#expList li").each(function() {
	        var handleSpan = jQuery("<span></span>");
	        handleSpan.addClass("handle");
	        handleSpan.prependTo(this);

	        if(jQuery(this).has("ul").size() > 0) {
	            handleSpan.addClass("collapsed");
	            handleSpan.click(function() {
	                var clicked = jQuery(this);
	                clicked.toggleClass("collapsed expanded");
	                clicked.siblings("ul").toggle();
	            });
	        }
	    });
    
};

function checkBox(){
	var globalDiv = document.getElementById("addedQuestions");
	var value = $(this).val();
	value = value.split('-');
	var id = value[0];
	var title = value[1];
	if($('input[id=' + id +']').is(':checked')){
		var div = document.createElement("div");
		div.setAttribute("id", id);
		globalDiv.appendChild(div);
		
		$('<input>').attr({
		    type: 'hidden',
		    value: id,
		    name: 'questions'
		}).appendTo(div);
		
		$('<p>').attr({
		    class: 'addedQuestion',
		}).text(title).appendTo(div);
		
		$.post('addTest/addQuestionsAdded', {question_id: id, question_title: title}, function(response) {
		});
		
	}
	
	else{
		$( "div" ).remove( "#"+id );
		$.post('addTest/removeQuestionsAdded', {question_id: id, question_title: title}, function(response) {
		});
	}
}

function prepareDIV(){
	$('.header').click(function(){
		$(this).closest('.container').toggleClass('collapsedDiv');
	});	  
}

$(document).ready( function() {
    prepareList();
    prepareDIV();
    addQuestions();
    $( "input[type=checkbox]" ).click(checkBox);
    $( "input[type=image]" ).click(remove);
    $( ".c" ).click(changeColor);
    prepareDragDrop();
});

function validateForm(){
	var items = new Array();
	$('input[type=checkbox]:checked').each(function() {
		var value = $(this).val();
		value = value.split('-');
	    items.push(value[0]);
	});
	if(items.length < 1){
		$('<p>You have to select at least one question</p>').appendTo( '#error' );
		return false;
	}
	var date = $("#date").val();
	if(date == null || date == ""){
		$('<p>You have to insert a date</p>').appendTo( '#error' );
		return false;
	}
	if($("#module").val() == null || $("#module").val() == ""){
		$('<p>You have to insert the module code</p>').appendTo( '#error' );
		return false;
	}
	return true;
}

function validateQuestionForm(){
	$('#error').empty();

	if($("#question_title").val() == null || $("#question_title").val() == ""){
		$('<p>You must introduce a question statement</p>').appendTo( '#error' );
		return false;
	}
	
	var answers = new Array();
	$('input[name=title]').each(function() {
		answers.push($(this).val());
	});
	
	for(var i = 0; i < answers.length; ++i){
		if(answers[i] == null || answers[i] == ""){
			$('<p>You must introduce four answers statement</p>').appendTo( '#error' );
			return false;
		}
	}
	
	var correct = $('input:radio[name=correct]:checked').val();
	
	if(correct == null || correct == ""){
		$('<p>You must introduce a correct answer</p>').appendTo( '#error' );
		return false;
	}
	return true;
		
}

function addQuestions(){
	$.post('addTest/getQuestionsTitleAdded', {question: 1}, function(titles) {
		$.post('addTest/getQuestionsIdAdded', {question: 1}, function(ids) {
			for(var i = 0; i < titles.length; ++i){
				var globalDiv = document.getElementById("addedQuestions");
				if(globalDiv != null){
					var div = document.createElement("div");
					div.setAttribute("id", ids[i]);
					globalDiv.appendChild(div);
					
					$('<input>').attr({
					    type: 'hidden',
					    value: ids[i],
					    name: 'questions'
					}).appendTo(div);
					
					$('<p>').attr({
					    class: 'addedQuestion',
					}).text(titles[i]).appendTo(div);
					
					$('#'+ids[i]).attr('checked',true);
				}
			}
		});
	});
}

function prepareDragDrop(){
	$("div[name='answerSlots']").each(function(){
		var inputValueId = $(this).attr("id");
		$('#' + inputValueId).droppable( {
			accept: '#addedAnswers div',
			hoverClass: 'hovered',
			drop: handleAnswerDrop,
			disabled: true
		});
		
	});
	
	$("div[name='all']").each(function(){
		var valueId = $(this).attr("id");
		$('#' + valueId).draggable( {
			containment: '#content',
			stack: '#addedAnswers div',
			cursor: 'move',
			revert: true
		});
	});
	
}

function makeDroppable(id){
	$('#answerSlot' + id).droppable(  "enable"  );
}

function handleAnswerDrop( event, ui ) {
	var answer = ui.draggable.attr("id");
	var answerId = answer.replace("addedAnswer", "");
	  
	var slot =  $(this).attr("id");
	var slotId = slot.replace("answerSlot", "");
	
	//disable drag and drop
	//ui.draggable.draggable( 'disable' );
	$(this).droppable( 'disable' );
	
	//append to the drop div
	$('#addedAnswer' + answerId).appendTo( '#' + slotId );
	$('#addedAnswer' + answerId).css( "position", "absolute");
	$('#addedAnswer' + answerId).css( "left", "0");
	$('#addedAnswer' + answerId).css( "top", "0");
}

function remove(){
	var id = $(this).val();
	var divId = $('#' + id).children("div[name='all']").attr("id");

	//Remove p
	$('#' + divId).css( "position", "relative");
	$('#' + divId).draggable(  "enable"  );
	$('#' + divId).appendTo( '#addedAnswers' );
	
	makeDroppable(id);
}

function changeColor(){
	$(".correct").css("background-color", "#fff");
	$('.c').children("div[name='all']").attr("class", "");
	$(this).children("div[name='all']").attr("class", "correct");
	$(this).children("div[name='all']").css("background-color", "#ACEC88");
}

function validateEditForm(){
	$('#error').empty();
	if($('.c').children("div[name='all']").length != 4){
		$('<p>There has to be 4 answers</p>').appendTo( '#error' );
		return false;
	}
		
	else if($('.c').children('.correct').attr("id") == null){
		 $('<p>You have to select a correct answer</p>').appendTo( '#error' );
		 return false;
	}
	
	else if($("#question_title").val() == null || $("#question_title").val() == ""){
		 $('<p>You have to insert a question statement</p>').appendTo( '#error' );
		 return false;
	}
	else 
		return true;
}

function hey(){
	alert("hey");
}