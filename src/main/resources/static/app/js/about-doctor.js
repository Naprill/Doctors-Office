
function showUpdateChapterModal(id) {

    jQuery.ajax({
        url: '/about-doctor/' + id,
        method: 'GET',
        success: function (object) {
            jQuery("#chapter-id-update").val(object.id);
            jQuery("#chapter-title-update").val(object.title);
            jQuery("#chapter-text-update").val(object.text);

            jQuery('#chapter-update-modal').modal("show");
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}


function showDeleteChapterModal(id) {
    jQuery("#chapter-id-delete").val(id);
    jQuery('#chapter-delete-modal').modal("show");
}


jQuery('#chapter-delete-submit').on('click', function (e) {
    //Prevent default submission of form
    e.preventDefault();

    var id = jQuery("#chapter-id-delete").val();
    jQuery.ajax({
        url: '/about-doctor/' + id,
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