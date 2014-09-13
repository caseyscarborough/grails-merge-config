var Configuration = (function($) {

    var self = {},
        configsCount = null;

    var _validateFields = function(fields) {
        var valid = true;
        $.each(fields, function(i, val) {
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
            '<tr id="config-' + data.id + '">' +
                '<td><strong>' + data.description + '</strong></td>' +
                '<td><strong>' + data.key + '</strong></td>' +
                '<td>' + data.value + '</td>' +
                '<td>' + data.type + '</td>' +
                '<td><a href="#" class="configuration-delete" onclick="Configuration.remove(' + data.id + ')">Delete</a></td>' +
            '</tr>';
        $("#config-table-body").append(html);
    };

    var _create = function() {
        if (_validateFields(["#new-config-description", "#new-config-key", "#new-config-value"])) {
            var createButton = $("#configuration-create-button");
            var buttonText = createButton.html();
            var url = $("#configuration-save-url").html();
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
            }).done(function(response) {
                configsCount++;
                data.id = response.data.config.id;
                _clearFields();
                _appendToTable(data);
                createButton.removeAttr("disabled");
                createButton.html(buttonText);

                if (configsCount > 0) {
                    $("#no-config-records").hide();
                    $("#config-table").show();
                }
            }).fail(function(response) {
                alert(response.responseJSON.message);
                createButton.removeAttr("disabled");
                createButton.html(buttonText);
            });
        }
    };

    self.remove = function(id) {
        if (confirm("Are you sure?")) {
            var url = $("#configuration-delete-url").html();
            $.ajax({
                url: url + "/" + id,
                type: 'DELETE'
            }).done(function() {
                configsCount--;
                $("#config-" + id).remove();
                if (configsCount == 0) {
                    $("#no-config-records").show();
                    $("#config-table").hide();
                }
            }).fail(function(response) {
                alert(response.responseJSON.message)
            });
        }
    };

    self.init = function(count) {
        configsCount = count;

        $("#configuration-create-button").on('click', function() {
            _create();
        });

        $(".configuration-delete").on('click', function() {
            var id = $(this).attr("data-config-id");
           _remove(id);
        });
    };

    return self;

}(jQuery));