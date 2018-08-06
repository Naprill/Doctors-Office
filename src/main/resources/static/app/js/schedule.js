
function userFilterButton() {
    let date = jQuery('#date').val();
    let interval = jQuery('#interval').val();
    let baseUrl = window.location.origin;
    console.log("baseUrl: ", baseUrl);
    window.location.href = baseUrl + '/schedule/filter?date=' + date + "&interval=" + interval;
}

function adminFilterButton() {
    let date = jQuery('#date').val();
    let interval = jQuery('#interval').val();
    let user = jQuery('#user').val();
    let baseUrl = window.location.origin;
    console.log("baseUrl: ", baseUrl);
    window.location.href = baseUrl + '/schedule/filter?date=' + date + "&interval=" + interval + "&user=" + user;
}

function resetFilterButton() {
    let date = moment().format("YYYY-MM-DD");
    jQuery('#date').val(date);
    jQuery('#interval').val(0);
    jQuery('#user').val(0);
    let baseUrl = window.location.origin;
    console.log("link: ", baseUrl);
    window.location.href = baseUrl + '/schedule/filter?date=' + date;
}