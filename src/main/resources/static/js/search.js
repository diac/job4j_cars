(function($) {
    $(document).ready(function() {
        $("#searchForm").on("submit", function(event) {
            event.preventDefault();
            var searchUrlParams = $("#searchForm :input")
                .filter(function(index, element) {
                    return $(element).val() != '';
                })
                .serialize();
            location.href = "/?" + searchUrlParams;
        });

        $("#searchForm #expandControl").click(function() {
            if ($("#searchForm .expandable").hasClass("collapsed")) {
                $("#searchForm .expandable").slideDown(function() {
                    $("#searchForm .expandable").removeClass("collapsed");
                    $("#expandControl .caption").html("Скрыть");
                });
            } else {
                $("#searchForm .expandable").slideUp(function() {
                    $("#searchForm .expandable").addClass("collapsed");
                    $("#expandControl .caption").html("Показать");
                });
            }
        });
    });
}(jQuery));