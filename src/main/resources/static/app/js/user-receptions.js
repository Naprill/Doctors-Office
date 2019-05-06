function adminFilterButton() {
    let date = jQuery('#date').val();
    let user = jQuery('#user').val();
    let baseUrl = window.location.origin;
    window.location.href = baseUrl + '/user-receptions?date=' + date + "&user=" + user;
}

function resetFilterButton() {
    let date = moment().format("YYYY-MM-DD");
    let baseUrl = window.location.origin;
    window.location.href = baseUrl + '/user-receptions?date=' + date;
}

window.onload = function () {
// update User Filter On Page Load
    var url_string = window.location.href;
    var url = new URL(url_string);
    var user = url.searchParams.get("user");
    if(user == null){
        jQuery('#user').val(1);
    }
};