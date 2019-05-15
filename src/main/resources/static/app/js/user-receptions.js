function adminFilterButton() {
    let date = jQuery('#date').val();
    let user = jQuery('#user').val();
    let baseUrl = window.location.origin;
    window.location.href = baseUrl + '/user-receptions?date=' + date + "&user=" + user;
}

function resetFilterButton() {
    let baseUrl = window.location.origin;
    window.location.href = baseUrl + '/user-receptions';
}