/**
 * Add or replace parameter of query string in current url
 * @param paramName
 * @param paramValue
 */
function addParameter(paramName, paramValue) {
    var parameter = paramName + '=' + paramValue;
    var loc = location.href.toString();
    if (hasParameter(paramName)) {
        var paramArr = getParametersArray();
        var query = '?';
        for (var i = 0; i < paramArr.length; i++) {
            var pair = paramArr[i].split('=');
            if (pair[0] !== paramName) {
                query = query + paramArr[i] + '&';
            }
        }
        var newLoc = loc.substring(0, loc.indexOf('?'));
        location.href = newLoc + query + parameter;
    } else {
        if (loc.indexOf('?') >= 0) {
            location.href = loc + '&' + parameter;
        } else {
            location.href = loc + '?' + parameter;
        }
    }
}

/**
 * Check whether parameter is present in query string
 * @param parameter to check
 * @returns {boolean} true if present
 */
function hasParameter(parameter) {
    var parameters = getParametersArray();
    for (var i = 0; i < parameters.length; i++) {
        var pair = parameters[i].split('=');
        if (pair[0] === parameter) {
            return true;
        }
    }
    return false;
}

/**
 * Get parameter value from query string
 * @param parameter
 * @returns {string} value
 */
function getParameterVal(parameter) {
    var parameters = getParametersArray();
    for (var i = 0; i < parameters.length; i++) {
        var pair = parameters[i].split('=');
        if (pair[0] === parameter) {
            return pair[1];
        }
    }
    return "";
}

/**
 * Get array of all query parameters with values
 * @returns {string[]} - 'param=value' items
 */
function getParametersArray() {
    var query = window.location.search.substring(1);
    return query.split("&");
}

/**
 * Remove all query parameters
 */
function reset() {
    var loc = location.href.toString();
    location.href = loc.substring(0, loc.indexOf('?'));
}