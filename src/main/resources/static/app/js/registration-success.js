 window.onload = function () {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var c = url.searchParams.get("success");
    if (c != null) {
        jQuery.ajax({
            url: "/registration",
            success: function () {
                swal({
                    type: 'success',
                    title: 'Вітаємо!',
                    text: 'Ви успішно зареєструвались. Перевірте вашу пошту щоб підтвердити реєстрацію',
                    showConfirmButton: false,
                    timer: 5000
                });
                setTimeout(function () {
                    location = "/login";
                }, 5000);
            }
        });
    }
};

