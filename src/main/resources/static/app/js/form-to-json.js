/**
 * Serialize form to Json
 * @param form - jquery form
 * @returns {string} payload
 */
function formToJSON(form) {
    var formData = form.serializeArray();
    var data = {};
    formData.forEach(function (obj) {
        data[obj.name] = obj.value;
    });
    return JSON.stringify(data);
}