$(function() {
	$("#chatButton").click(function () {
		$.getJSON("/api/holdemtable/starthand", function(data) {
			$.each(data, function(key, val) {
				alert("ID: " + val.userId + "Seat Number: " + val.seatNumber + "Chips: " + val.chips);
			});
		});
	});
});