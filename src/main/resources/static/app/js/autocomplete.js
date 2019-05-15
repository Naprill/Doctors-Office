
jQuery(document).ready(function () {

    jQuery('.autocomplete').autocomplete({
        serviceUrl: '/patients/search',
        paramName: "fragment",
        minLength: 1,
        delay: 500,
        showNoSuggestionNotice: true,
        transformResult: function (response) {
            return {
                //must convert json to javascript object before process
                suggestions: jQuery.map(jQuery.parseJSON(response), function (item) {
                    return {value: item.name, data: item.id};
                })
            };
        },
        onSelect: function (suggestion) {
            console.log("on select")
            jQuery('.userId').val(suggestion.data);
        }
    });
});