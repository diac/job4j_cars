(function($) {
    $(document).ready(function() {
        $("#searchForm").on("submit", function(event ) {
            event.preventDefault();
            var searchUrlParams = $("#searchForm :input")
                .filter(function(index, element) {
                    return $(element).val() != '';
                })
                .serialize();
            location.href = "/?" + searchUrlParams;
        });
    });
}(jQuery));