var entireSize = 0;

jQuery('#file-input').change(function () {
    var length = this.files.length;
    var fileInfo = "Ви збираєтесь завантажити:";

    entireSize = 0;
    for (var i = 0; i <= length - 1; i++) {

        var fname = this.files[i].name;      // THE NAME OF THE FILE.
        var fsize = this.files[i].size;      // THE SIZE OF THE FILE.

        entireSize += fsize;
        fileInfo += '<br/> <b>' + i + ')</b> ' + fname + ' (<b>' + fsize / 1000000. + '</b> MB)';
    }

    // SHOW THE EXTRACTED DETAILS OF THE FILES.
    document.getElementById('file-info').innerHTML = fileInfo +
        "<br/> Всього " + entireSize / 1000000 + " з доступних 2.097152";

    if (entireSize > 2097152) { //TODO function checkSize(entireSize){ return true}
        swal({
            type: 'error',
            title: 'Завеликий розмір файлів',
            text: 'Максимальний розмір завантажуваних за один раз файлів: 2MB',
            timer: 5000
        });
    }

});

jQuery('#file-upload-submit').on('click', function (e) {
    //Prevent default submission of form
    e.preventDefault();

    if (entireSize > 2097152) {
        swal({
            type: 'error',
            title: 'Завеликий розмір файлів',
            text: 'Максимальний розмір завантажуваних за один раз файлів: 2MB',
            timer: 5000
        });
        return;
    }

    if (jQuery('#file-input').val() == '') {
        return;
    }
    var files = jQuery('#file-input')[0].files;
    var filesArr = [];
    Object.keys(files).forEach(function (key) {
        filesArr.push(files[key]);
    });
    var data = new FormData();
    for (var i = 0; i < filesArr.length; i++) {
        data.append("uploadingFiles", filesArr[i]);
    }

    jQuery.ajax({
        type: "POST",
        url: "/" + userId + "/uploadFiles",
        data: data,
        contentType: false,
        processData: false,
        cache: false,
        success: function (response) {
            console.log("SUCCESS: ", response);
            swal({
                type: 'success',
                title: 'Готово!',
                text: 'Успішно завантажено файли.',
                showConfirmButton: false,
                timer: 2500
            });
            var content = "";
            for (var i = 0; i < response.length; i++) {
                content += "<div class='card'> <div class='card-header'> <strong class='card-title'>";
                content += " <span class='btn btn-info'>Дата: " +
                    response[i].date.dayOfMonth + "." + response[i].date.monthValue + "." + response[i].date.year +
                    "</span>&#010;";
                content += "<a class='btn btn-success' href='" + response[i].fileDownloadUri + "' >Скачати</a>";
                content += "</strong> </div> <div class='card-body'><span>";
                content += response[i].fileName;
                content += "</span> </div> </div>";
            }
            var div = document.querySelector('#filesForDownload');
            div.innerHTML = content + div.innerHTML;
        },
        error: function (e) {
            console.log("ERROR : ", e);
            swal({
                type: 'error',
                title: 'Щось пішло не так...',
                text: 'Не вдалось завантажити файли',
                timer: 5000
            });
        }
    });
});

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

function showUpdateTherapyModal(id) {

    jQuery.ajax({
        url: '/profile/therapy/' + id,
        method: 'GET',
        success: function (object) {
            jQuery("#therapy-id-update").val(object.id);
            jQuery("#therapy-text-update").val(object.text);
            jQuery("#therapy-patient-update").val(object.patient);

            jQuery('#therapy-update-modal').modal("show");
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}