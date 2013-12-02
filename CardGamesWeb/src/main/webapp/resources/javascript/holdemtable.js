$(function() {
	$("#chatButton").click(function () {
		var chatMessage = $("#chatInput").val();
		var data = JSON.stringify({bigBlind: chatMessage});
		$.ajax({
			type: "POST",
			url: "localhost:8080/cardgamesapi/table",
			data: data,
			contentType: "application/json; charset=utf-8;",
			success: function(data) {
				alert("Success!");
			},
			error: function(xhr, status, error) {
				alert(error);
				alert(xhr.responseText);
			}
		});
	});
});