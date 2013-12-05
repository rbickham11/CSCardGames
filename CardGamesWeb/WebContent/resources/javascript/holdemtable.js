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
                    startGame();
            },
            error: function(xhr, status, error) {
                    alert(error);
                    alert(xhr.responseText);
            }
        });
		e.preventDefault();
	});
});

function startGame() {
	initPlayers();
	
}

function initPlayers() {
	$.getJSON("/api/holdemtable/getallplayers", function(data) {
		$.each(data, function(key, val) {
			$("#player" + val.seatNumber + "name").html("Name: " + val.username);
			$("#player" + val.seatNumber + "chips").html("Chips: " + val.chips);
		});
	});
}
