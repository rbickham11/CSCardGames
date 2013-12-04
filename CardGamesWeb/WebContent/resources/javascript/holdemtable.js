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
		var data = JSON.stringify($(this).serializeObject());
		alert(data);
        $.ajax({
            type: "POST",
            url: action,
            data: data,
            contentType: "application/json; charset=utf-8;",
            success: function(data) {
                    $('#modal').modal('hide');
            },
            error: function(xhr, status, error) {
                    alert(error);
                    alert(xhr.responseText);
            }
        });
		e.preventDefault();
	});
	$("#chatButton").click(function () {
		$.getJSON("/api/holdemtable/starthand", function(data) {
			$.each(data, function(key, val) {
				alert("ID: " + val.userId + "Seat Number: " + val.seatNumber + "Chips: " + val.chips);
			});
		});
	});
});