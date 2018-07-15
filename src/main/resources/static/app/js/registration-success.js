(function(){

 window.onload = function () {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var c = url.searchParams.get("success");
    if (c != null) {
        $.ajax({
            url: "/registration", success: function (result) {
                swal({
                    type: 'success',
                    title: 'Success!',
                    text: 'You have successfully registered',
                    showConfirmButton: false,
                    timer: 2500
                });
            }
        });
    }
};

})();

