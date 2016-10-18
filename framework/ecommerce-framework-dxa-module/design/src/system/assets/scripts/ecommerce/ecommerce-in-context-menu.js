$(document).ready(function() {
    //
    // Move XPM create new menu to the header (temporary workaround)
    //
    if ($(".xpm-create-new-menu").length > 0) {
        $("#page-header .page-border").css("position", "relative");
        $(".xpm-create-new-menu").detach().appendTo("#page-header .page-border");
    }
});