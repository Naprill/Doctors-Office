function userFilterButton() {
    let date = jQuery('#date').val();
    let interval = jQuery('#interval').val();
    let baseUrl = window.location.origin;
    console.log("baseUrl: ", baseUrl);
    window.location.href = baseUrl + '/schedule/filter?date=' + date + "&interval=" + interval;
}

function adminFilterButton() {
    let date = jQuery('#date').val();
    let interval = jQuery('#interval').val();
    let user = jQuery('#user').val();
    let baseUrl = window.location.origin;
    console.log("baseUrl: ", baseUrl);
    window.location.href = baseUrl + '/schedule/filter?date=' + date + "&interval=" + interval + "&user=" + user;
}

function resetFilterButton() {
    let date = moment().format("YYYY-MM-DD");
    jQuery('#date').val(date);
    jQuery('#interval').val(0);
    jQuery('#user').val(0);
    let baseUrl = window.location.origin;
    console.log("link: ", baseUrl);
    window.location.href = baseUrl + '/schedule/filter?date=' + date;
}

function showRegisterModal(id) {

    jQuery.ajax({
        url: '/schedule/' + id,
        method: 'GET',
        success: function (object) {
            jQuery("#item-id").html(object.id);
            jQuery("#item-date").html(object.date.dayOfMonth + "-" + object.date.monthValue + "-" + object.date.year);
            jQuery("#item-begin").html(object.intervalStart.hour + ":" + object.intervalStart.minute);
            jQuery("#item-end").html(object.intervalEnd.hour + ":" + object.intervalEnd.minute);
            jQuery("#item-duration").html(object.duration);

            jQuery('#register-modal').modal("show");
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

jQuery('#register-user-button-submit').on('click', function (e) {
    //Prevent default submission of form
    e.preventDefault();
    //Ajax put request
    let RegisterReceptionDTO = {};
    RegisterReceptionDTO["id"] = jQuery("#item-id").html();
    registerReception(RegisterReceptionDTO);
});

jQuery('#register-admin-button-submit').on('click', function (e) {
    //Prevent default submission of form
    e.preventDefault();
    //Ajax put request
    let RegisterReceptionDTO = {};
    RegisterReceptionDTO["id"] = jQuery("#item-id").html();
    RegisterReceptionDTO["userId"] = parseInt(jQuery("#user-id").val(), 10);
    if (isNaN(RegisterReceptionDTO["userId"])) {
        swal({
            type: 'error',
            title: 'Оберіть пацієнта',
            text: 'Не вдалось записати на прийом',
            timer: 5000
        });
        return;
    }
    registerReception(RegisterReceptionDTO);
});

function registerReception(RegisterReceptionDTO){
    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            "Content-Type": "application/json"
        },
        method: 'PUT',
        url: '/schedule/',
        data: JSON.stringify(RegisterReceptionDTO),
        cash: false,
        success: function (status) {
            console.log("SUCCESS: ", status);
            swal({
                type: 'success',
                title: 'Готово!',
                text: 'Успішно записано на прийом.',
                showConfirmButton: false,
                timer: 2500
            });
            setTimeout(function () {
                location.reload();
            }, 2500);
        },
        error: function (e) {
            console.log("ERROR : ", e);
            swal({
                type: 'error',
                title: 'Щось пішло не так...',
                text: 'Не вдалось записати на прийом',
                timer: 5000
            });
        }
    });
}