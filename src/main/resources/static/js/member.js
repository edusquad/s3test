String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};
$(".signup-form input[type=submit]").click(addMember);

function addMember(e) {
    e.preventDefault();

    var queryString = $("form[name=signup-form]").serialize();

    $.ajax({
        type : 'post',
        url : '/generateToken',
        data : queryString,
        dataType : 'json',
        error: onError,
        success : onSuccess,
    });
}