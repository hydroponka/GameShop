$(document).ready(function() {
    $(".add-product-form").submit(function(event) {
        event.preventDefault();
        var form = $(this);
        var url = form.attr("action");
        var method = form.attr("method")
        var data = form.serialize();
        $.ajax({
            url: url,
            type: method,
            data: data,
            success: function(response) {
                console.log(response)
                $('.toast-body').html(response);
                $('.toast').addClass('bg-success').toast('show');
            },
            error: function(error) {
                console.log(error);
                $('.toast-body').html(error.responseText);
                $('.toast').addClass('bg-danger').toast('show');
            }
        });
    });
});