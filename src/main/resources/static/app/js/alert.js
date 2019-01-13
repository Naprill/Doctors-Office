
jQuery(document).ready(function () {
    if (status === "success") {
        swal({
            type: 'success',
            title: 'Готово!',
            text: 'Оновлення успішне',
            timer: 2500
        });
    } else if (status === "failure") {
        swal({
            type: 'error',
            title: 'Щось пішло не так...',
            text: 'Не вдалось зберегти дані',
            timer: 5000
        });
    }
});