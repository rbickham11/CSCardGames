$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

var myId, myUsername, mySeatNumber, myChips;
var lastPlayer = 0;

$(function() {
	$('#modal').modal({
		  backdrop: 'static',
		  keyboard: false
	});
	
	$(window).load(function() {
		$('#modal').modal('show');
	});
	
	$('#modal-close').click(function() {
		window.location = "/tables.html";
	});
	
	$("#addPlayerSubmit").click(function() {
		$("#addPlayerForm").submit();
	});
	
	$("#addPlayerForm").submit(function(e) {
		var action = $(this).attr("action");
		var dataObj = $(this).serializeObject();
		myId = dataObj.userId;
		myUsername = dataObj.username;
		mySeatNumber = dataObj.seatNumber;
		myChips = dataObj.startingChips;
		inGame = true;
		var data = JSON.stringify(dataObj);
        $.ajax({
            type: "POST",
            url: action,
            data: data,
            contentType: "application/json; charset=utf-8;",
            success: function(data) {
                    $('#modal').modal('hide');
                    initPlayers();
            },
            error: function(xhr, status, error) {
                    alert(xhr.responseText);
            }
        });
		e.preventDefault();
	});
	
	$('#checkcallbtn').click(function() {
		var action;
		if($(this).html() == "Call") {
			action = "CALL";
		}
		else {
			action = "CHECK";
		}
		takeAction(action);
	});
	
	$('#betraisebtn').click(function() {
		var action;
		if($(this).html() == "Bet") {
			action = "BET";
		}
		else {
			action = "RAISE";
		}
		takeAction(action);
	});
	
	$('#foldbtn').click(function() {
		var action;
		action = "FOLD";
		takeAction(action);
	});
	
	$('#standupbtn').click(function() {
		
	});
});

var enoughPlayers = 0;
function initPlayers() {
	$.getJSON("/api/holdemtable/getallplayers", function(data) {
		$.each(data, function(key, val) {
			$("#bottomName").html(myUsername);
			$("#player" + val.seatNumber + "name").html("Name: " + val.username);
			$("#player" + val.seatNumber + "chips").html("Chips: " + val.chips);
		});
		if(data.length == 1) { 
			if(enoughPlayers == 0) {
				alert("Waiting for another player");
			}
			enoughPlayers = 1;
			setTimeout(initPlayers, 5000);
		}
		else if(enoughPlayers == 0 && data.length == 2) {
			$.getJSON("/api/holdemtable/starthand");
			startGameWait();
		}
		else {
			startGameWait();
		}
	});
}

function startGameWait() {
	setButtonsInactive();
	var data = JSON.stringify({mySeatNumber: mySeatNumber, lastPlayer: lastPlayer});
	$.ajax({
		type: "POST",
		url: "/api/holdemtable/gettableinfo",
		data: data,
		contentType: "application/json; charset=utf-8;",
		timeout:60000,
		success: function(data) {
			displayCards(data);
			lastPlayer = data.lastPlayer;
			if(data.playerActive == true) {
				setButtonsActive();
				if(data.currentBet > 0) {
					$('#checkcallbtn').html('Call');
					$('#betraisebtn').html('Raise');
				}
				else {
					$('#checkcallbtn').html('Check');
					$('#betraisebtn').html('Bet');
				}
			}
			setTimeOut(startGameWait, 1000);
		},
		error: function(xhr, status, error){
			alert(status);
			alert(error);
			alert(xhr.responseText);
		}
	});	
}

function displayCards(data) {
	var HtmlString = "";
	for(var i = 1; i < 10; i++) {
		$("#player" + i + "cards").html("");
	}
	$.each(data.activePlayers, function(key, value) {
		HtmlString = '<img src = "/resources/images/cards/cardback.png" class = "smallCard">';
		HtmlString += '<img src = "/resources/images/cards/cardback.png" class = "smallCard">';
		if(value.userId == myId) {
			HtmlString = '<img src = "/resources/images/cards/' + value.hand[0] + '.png" class = "smallCard">';
			HtmlString += '<img src = "/resources/images/cards/' + value.hand[1] + '.png" class = "smallCard">';
			$("#player" + value.seatNumber + "cards").html(HtmlString);
			HtmlString = '<img src = "/resources/images/cards/' + value.hand[0] + '.png">';
			HtmlString += '<img src = "/resources/images/cards/' + value.hand[1] + '.png">';
			$("#yourhand").html(HtmlString);			
		}
		else {
			$("#player" + value.seatNumber + "cards").html(HtmlString);
		}
	});
};

function setButtonsInactive() {
	$('#checkcallbtn').prop('disabled', true);
	$('#betraisebtn').prop('disabled', true);
	$('#foldbtn').prop('disabled', true);
};

function setButtonsActive() {
	$('#checkcallbtn').prop('disabled', false);
	$('#betraisebtn').prop('disabled', false);
	$('#foldbtn').prop('disabled', false);
};

function takeAction(action) {
	var chipAmount = 0;
	if((action == "BET") || (action == "RAISE")){
		chipAmount = $("#betraiseamt").val();
	}
	var data = JSON.stringify({action: action, chipAmount: chipAmount});
    $.ajax({
        type: "POST",
        url: "/api/holdemtable/takebettingaction",
        data: data,
        contentType: "application/json; charset=utf-8;",
        success: function(data) {
                $('#modal').modal('hide');
                initPlayers();
        },
        error: function(xhr, status, error) {
                alert(xhr.responseText);
        }
    });
}

