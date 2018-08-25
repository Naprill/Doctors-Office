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

    let RegisterReceptionDTO = {};
    RegisterReceptionDTO["id"] = jQuery("#item-id").html();
    registerReception(RegisterReceptionDTO);
});

jQuery('#register-admin-button-submit').on('click', function (e) {
    //Prevent default submission of form
    e.preventDefault();

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

function registerReception(RegisterReceptionDTO) {
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

function showCancelModal(id) {

    jQuery.ajax({
        url: '/schedule/' + id,
        method: 'GET',
        success: function (object) {
            jQuery("#cancel-item-id").html(object.id);
            jQuery("#cancel-user-id").html(object.userId);
            jQuery("#cancel-item-date").html(object.date.dayOfMonth + "-" + object.date.monthValue + "-" + object.date.year);
            jQuery("#cancel-item-begin").html(object.intervalStart.hour + ":" + object.intervalStart.minute);
            jQuery("#cancel-item-end").html(object.intervalEnd.hour + ":" + object.intervalEnd.minute);
            jQuery("#cancel-item-duration").html(object.duration);
            jQuery("#cancel-item-user").html(object.userFirstName + " " + object.userLastName);

            jQuery('#cancel-modal').modal("show");
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

jQuery('#cancel-button-submit').on('click', function (e) {
    //Prevent default submission of form
    e.preventDefault();

    let CancelReceptionDTO = {};
    CancelReceptionDTO["id"] = jQuery("#cancel-item-id").html();
    CancelReceptionDTO["userId"] = jQuery("#cancel-user-id").html();
    CancelReceptionDTO["message"] = jQuery("#cancel-message").val();
    if ((CancelReceptionDTO["message"]) === "") {
        swal({
            type: 'error',
            title: 'Вкажіть причину відміни',
            text: 'Не вдалось відмінити прийом',
            timer: 5000
        });
        return;
    }
    cancelReception(CancelReceptionDTO);
});

function cancelReception(CancelReceptionDTO) {
    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            "Content-Type": "application/json"
        },
        method: 'DELETE',
        url: '/schedule/',
        data: JSON.stringify(CancelReceptionDTO),
        cash: false,
        success: function (status) {
            console.log("SUCCESS: ", status);
            swal({
                type: 'success',
                title: 'Готово!',
                text: 'Успішно відмінено прийом.',
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
                text: 'Не вдалось відмінити прийом',
                timer: 5000
            });
        }
    });
}

function showSettingsModal() {

    jQuery.ajax({
        url: '/schedule/settings',
        method: 'GET',
        success: function (object) {
            jQuery("#workStart").val(object.workStart);
            jQuery("#workEnd").val(object.workEnd);
            jQuery("#receptionTimeRange").val(object.receptionTimeRange);

            jQuery('#settings-modal').modal("show");
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

jQuery('#settings-button-submit').on('click', function (e) {
    //Prevent default submission of form
    e.preventDefault();
    var form = jQuery('#settings-form');
    jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            "Content-Type": "application/json"
        },
        method: 'PUT',
        url: '/schedule/settings/',
        data: formToJSON(form),
        cash: false,
        success: function (status) {
            console.log("SUCCESS: ", status);
            swal({
                type: 'success',
                title: 'Готово!',
                text: 'Успішно збережено налаштування. Вони працюватимуть починаючи з дня в якому немає записів на прийом.',
                showConfirmButton: false,
                timer: 5000
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
                text: 'Не вдалось зберегти налаштування',
                timer: 5000
            });
        }
    });
});

