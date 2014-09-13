var Configuration = (function($) {

    var self = {};

    var _validateFields = function(fields) {
        var valid = true;
        $.each(fields, function(i, val) {
            // If the field is blank, focus the field and add has error class to parent
            if ($.trim($(val).val()) === "") {
                $(val).focus();
                $(val).parent().addClass("has-error");
                valid = false;
                return false;
            } else {
                $(val).parent().removeClass("has-error");
            }
        });
        return valid;
    };

    var _clearFields = function() {
        $("#new-config-key").val("");
        $("#new-config-value").val("");
        $("#new-config-description").val("");
    };

    var _appendToTable = function(data) {
        var html = '' +
            '<tr>' +
                '<td><strong>' + data.description + '</strong></td>' +
                '<td><strong>' + data.key + '</strong></td>' +
                '<td>' + data.value + '</td>' +
                '<td>' + data.type + '</td>' +
            '</tr>';
        $("#config-table-body").append(html);
    };

    var _create = function() {
        if (_validateFields(["#new-config-key", "#new-config-value"])) {
            var createButton = $("#configuration-create-button");
            var buttonText = createButton.html();
            var url = createButton.attr("data-url");
            var data = {
                key: $("#new-config-key").val(),
                value: $("#new-config-value").val(),
                type: $("#new-config-type").val(),
                description: $("#new-config-description").val()
            };

            createButton.attr("disabled", "disabled");
            createButton.html("Saving...");

            $.ajax({
                url: url,
                data: data,
                type: 'POST'
            }).done(function() {
                $("#no-config-records").hide();
                $("#config-table").show();
                _clearFields();
                _appendToTable(data);
                createButton.removeAttr("disabled");
                createButton.html(buttonText);
            }).fail(function(response) {
                alert(response.responseJSON.message);
                createButton.removeAttr("disabled");
                createButton.html(buttonText);
            });
        }
    };

    self.init = function() {
        $("#configuration-create-button").click(function() {
            _create();
        })
    };

    return self;

}(jQuery));