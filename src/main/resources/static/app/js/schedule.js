function adminFilterButton() {
    let date = jQuery('#date').val();
    let baseUrl = window.location.origin;
    console.log("baseUrl: ", baseUrl);
    window.location.href = baseUrl + '/schedule/filter?date=' + date;
}