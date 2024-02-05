let LOCAL_URL = "http://localhost:8080/spring/";
function save_task(task_id) {

    let value_description = $("#input_description_" + task_id).val();
    let value_status = $("#select_" + task_id).val();

    $.ajax({
        url: LOCAL_URL + task_id,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        type: "POST",
        async: false,
        data: JSON.stringify(
            {
                "description": value_description,
                "status": value_status
            }
        )
    });
    setTimeout(() => {
        document.location.reload()
    }, 200);
}


function delete_task(task_id) {
    let url = LOCAL_URL + task_id;
    $.ajax({
        url: url,
        type: "DELETE",
        success: function () {
            window.location.reload();
        }
    });
}

function edit_task(task_id) {
    let description = $("#description_" + task_id);
    description.html("<input id='input_description_" + task_id + "' type='text' class='form-control' value ='" + description.text() + "' >");

    let status = $("#status_" + task_id);
    let status_value = status.text();
    status.html(descriptionInputHtml(task_id));
    $("#select_" + task_id).val(status_value);

    $("#save_" + task_id).removeAttr("hidden");
    $("#edit_" + task_id).attr("hidden", "hidden");
    $("#delete_" + task_id).attr("hidden", "hidden");
}

function add_task(page_count) {

    let value_description = $("#input_new_task_description").val();
    let value_status = $("#input_new_task_status").val();

    $.ajax({
        url: LOCAL_URL,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        type: "POST",
        async: false,
        data: JSON.stringify(
            {
                "description": value_description,
                "status": value_status
            }
        )
    });
    setTimeout(() => {
        window.location.href = LOCAL_URL + "?page=" + page_count;
    }, 200);
}

function descriptionInputHtml(task_id) {

    return "<select class=\"form-select\" id=\"select_" + task_id + "\">" +
        "<option value=\"IN_PROGRESS\">IN_PROGRESS</option>" +
        "<option value=\"DONE\">DONE</option>" +
        "<option value=\"PAUSED\">PAUSED</option>" +
        "</select>";
}