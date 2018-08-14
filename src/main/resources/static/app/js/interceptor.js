/**
 * Add csrf token to header of each ajax request
 */
(function() {
    console.log("interceptor call");
    var send = XMLHttpRequest.prototype.send;
    XMLHttpRequest.prototype.send = function(data) {
        this.setRequestHeader('X-CSRF-Token', jQuery("meta[name='_csrf']").attr("content"));
        return send.apply(this, arguments);
    };
}());
