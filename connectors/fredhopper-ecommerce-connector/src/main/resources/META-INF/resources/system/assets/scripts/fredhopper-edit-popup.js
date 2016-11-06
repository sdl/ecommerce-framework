$(document).ready(function() {

    // Remove buttons that does not make sense from the edit popup view
    //
    $(".toolbar-button-container[title='Archive']").remove();
    $(".edit .toolbarShowLabelsDropDown").remove();
    $(".toolbar-button-container[title='Assign/remove labels']").remove();

    // Add events on the save and cancel buttons to close the popup (to avoid end up in the list view)
    //
    $("#synonymSaveButton").mouseup(function () {
        setTimeout(function() {
            window.parent.$('.mfp-close').click();
        }, 250);

    });

    $("#synonymCancelButton").mouseup(function () {
        setTimeout(function() {
            window.parent.$('.mfp-close').click();
        }, 250);
    });

    $(".toolbar-button-container[title='Delete']").mouseup(function () {
        setTimeout(function() {
            window.parent.$('.mfp-close').click();
        }, 250);
    });

});