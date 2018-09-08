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

    if(jQuery('#file-input').val() == ''){
        return;
    }
    var files = jQuery('#file-input')[0].files;
    var filesArr = [];
    Object.keys(files).forEach(function(key){
        filesArr.push(files[key]);
    });
    var data = new FormData();
    for (var i = 0; i < filesArr.length ; i++) {
        data.append("uploadingFiles", filesArr[i]);
    }

    jQuery.ajax({
        type: "POST",
        url: "/uploadFiles",
        data: data,
        contentType: false,
        processData: false,
        cache: false,
        success: function (status) {
            console.log("SUCCESS: ", status);
            swal({
                type: 'success',
                title: 'Готово!',
                text: 'Успішно завантажено файли.',
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
                text: 'Не вдалось завантажити файли',
                timer: 5000
            });
        }
    });


});