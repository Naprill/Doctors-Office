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

    if (entireSize > 2097152) {
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
        url: "/uploadFiles",
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
                content += response[i].fileName;
                content += " <p class='bg-primary text-light'>Дата: " +
                    response[i].date.dayOfMonth + "." + response[i].date.monthValue + "." + response[i].date.year +
                    "</p>";
                content += "<a class='btn btn-success float-right' href='" + response[i].fileDownloadUri + "' >Скачати</a></p>";
                content += "</strong> </div> <div class='card-body'></div> </div>";
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
    var status = decodeURIComponent(getParameterVal('status'));
    if (status === "success") {
        swal({
            type: 'success',
            title: 'Готово!',
            text: 'Успішно оновлено профіль',
            showConfirmButton: false,
            timer: 2500
        });
    }
});
