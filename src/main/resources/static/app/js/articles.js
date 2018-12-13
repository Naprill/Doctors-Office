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

function showUpdateArticleModal(id) {

    jQuery.ajax({
        url: '/articles/' + id,
        method: 'GET',
        success: function (object) {
            jQuery("#article-id-update").val(object.id);
            jQuery("#article-title-update").val(object.title);
            jQuery("#article-link-update").val(object.link);
            jQuery("#article-description-update").val(object.description);

            jQuery('#article-update-modal').modal("show");
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}


function showDeleteArticleModal(id) {
    jQuery("#article-id-delete").val(id);
    jQuery('#article-delete-modal').modal("show");
}


jQuery('#article-delete-submit').on('click', function (e) {
    //Prevent default submission of form
    e.preventDefault();

    var id = jQuery("#article-id-delete").val();
    jQuery.ajax({
        url: '/articles/' + id,
        method: 'DELETE',
        success: function () {
            console.log("Success");
            location.reload();
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });

});