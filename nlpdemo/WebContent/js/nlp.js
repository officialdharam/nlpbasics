$(document).ready(function() {

	$("#spaceTokenizer").click(function() {
		var textToTokenize = $("#textToTokenize").val();
		var tokenizationMethod = 'space';
		var myObject = new Object();
		myObject.textToTokenize = textToTokenize;
		myObject.tokenizationMethod = tokenizationMethod;
		var data = JSON.stringify(myObject);
		makePOSTCall("/nlpdemo/testWordTokenize", data, handleTokenization);
	});

	$("#splTokenizer").click(function() {
		var textToTokenize = $("#textToTokenize").val();
		var tokenizationMethod = 'splchars';
		var myObject = new Object();
		myObject.textToTokenize = textToTokenize;
		myObject.tokenizationMethod = tokenizationMethod;
		var data = JSON.stringify(myObject);
		makePOSTCall("/nlpdemo/testWordTokenize", data, handleTokenization);
	});

	handleTokenization = function(data) {
		var result = data.result;
		var m = '';
		jQuery.each(result, function(i, val) {
			m = m.concat('#' + (i + 1)).concat(" - " + val + '\n');
		});

		BootstrapDialog.show({
			title : 'Word Tokenization Results',
			message : m
		});
	}

	$("#dtSegm").click(function() {
		var paragraphToSegment = $("#paragraphToSegment").val();
		var tokenizationMethod = 'decisionTree';
		var myObject = new Object();
		myObject.docToSegment = paragraphToSegment;
		myObject.segmentationMethod = tokenizationMethod;
		var data = JSON.stringify(myObject);
		makePOSTCall("/nlpdemo/testSentenceSegmentation", data, handleSegmentation);
	});

	$("#eosSegm").click(function() {
		var paragraphToSegment = $("#paragraphToSegment").val();
		var tokenizationMethod = 'eos';
		var myObject = new Object();
		myObject.docToSegment = paragraphToSegment;
		myObject.segmentationMethod = tokenizationMethod;
		var data = JSON.stringify(myObject);
		makePOSTCall("/nlpdemo/testSentenceSegmentation", data, handleSegmentation);
	});

	handleSegmentation = function(data) {
		var result = data.result;
		var m = '';
		jQuery.each(result, function(i, val) {
			m = m.concat('#' + (i + 1)).concat(" - " + val + '\n');
		});

		BootstrapDialog.show({
			title : 'Sentence Segmentation Results',
			message : m
		});
	}
	
	$("#spellErrors").click(function() {
		var textToCorrect = $("#spellingCorrectionText").val();
		var myObject = new Object();
		myObject.docToCorrect = textToCorrect;
		var data = JSON.stringify(myObject);
		makePOSTCall("/nlpdemo/spellCorrect", data, handleSpellCorrect);
	});

	handleSpellCorrect = function(data) {
		var result = data.result;
		var m = '';
		jQuery.each(result, function(i, val) {
			m = m.concat('\n#' + (i + 1)).concat(' -> '+val.error+' [\t');
			jQuery.each(val.corrections, function(j, xval) {
				m = m.concat(xval+', ')
			});
			if(val.corrections.length == 0){
				m = m.concat('<div class="btn-group"><button class="btn btn-sm btn-primary" onClick="addToDictionary(\''+val.error.substr(0,val.error.indexOf('('))+'\')">Add to Dictionary</button></div>');
			}
			m = m.concat(' ]\n');
			
		});
		
		if(m == ''){
			m = 'No non-word errors found!!';
		}

		BootstrapDialog.show({
			title : 'Spelling Correction Results',
			message : m
		});
	}

	addToDictionary = function(data){
		var myObject = new Object();
		myObject.wordToAdd = data;
		var data = JSON.stringify(myObject);
		makePOSTCall("/nlpdemo/addToDictionary", data, handleAddToDictionary);
	}
	
	handleAddToDictionary = function(data) {
		var result = data.result;
		BootstrapDialog.show({
			title : 'Success',
			message : "Word added successfully!!"
		});
	}
	
	$("#storyWritingBtn").click(function() {
		makeGETCall("/nlpdemo/sentenceCreation", handleStoryWriting);
	});
	

	$("#phraseRearrangementBtn").click(function() {
		var paragraphToSegment = $("#sentenceCreation").val();
		var myObject = new Object();
		myObject.words = paragraphToSegment;
		var data = JSON.stringify(myObject);
		makePOSTCall("/nlpdemo/sentenceCreation", data, handleStoryWriting);
	});
	
	handleStoryWriting = function(data) {
		var result = data.result;
		
		var m = '';
		
		jQuery.each(result, function(i, val) {
			m = m.concat('<b>'+val[0]+' : </b>' + val[1]);
			m = m.concat('\n\n\n');
		});
		
		BootstrapDialog.show({
			title : 'Story Writing using Language Model',
			message : m
		});
	}
	
	$("#movieReviewBtn").click(function() {
		var review = $("#reviewContent").val();
		var myObject = new Object();
		myObject.review = review;
		myObject.type = "movie";
		var data = JSON.stringify(myObject);
		makePOSTCall("/nlpdemo/sentimentAnalysis", data, sentimentAnalysis);
	});
	
	
	$("#headlineClassification").click(function() {
		var review = $("#reviewContent").val();
		var myObject = new Object();
		myObject.review = review;
		myObject.type = "news";
		var data = JSON.stringify(myObject);
		makePOSTCall("/nlpdemo/sentimentAnalysis", data, sentimentAnalysis);
	});
	
	sentimentAnalysis = function(data) {
		var result = data.result;
		
		var m = 'The class of the subject is '+result[0]+' with a log probability of '+result[1];
		
		BootstrapDialog.show({
			title : 'Sentiment Analysis',
			message : m
		});
	}
	
	$("#showDecisionTreeImage").click(function(){
		 var $textAndPic = $('<div></div>');
        $textAndPic.append('<img src="./images/decisiontree.png" />');
        
        BootstrapDialog.show({
            title: 'Decision Tree for Sentence Segmentation',
            size: BootstrapDialog.SIZE_WIDE,
            message: $textAndPic,	           
        });
	});
	
	makePOSTCall = function(url, data, handler) {

		// submit using AJAX.
		$.ajax({
			type : "POST",
			url : url,
			data : data,
			success : handler,
			dataType : "json",
			contentType : "application/json"
		});
	};
	
	makeGETCall = function(url, handler) {

		// submit using AJAX.
		$.ajax({
			type : "GET",
			url : url,
			success : handler,
			dataType : "json",
			contentType : "application/json"
		});
	};

});