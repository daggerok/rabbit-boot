(function app() {
  $(document).ready(function ready() {
    var base = $('meta[name=base]').attr("content");
    $('#send-message-form').on('click', function submitForm(event) {
      event.preventDefault();
      var $input = $(this).find('input[name=message]');
      $.post(base + '/message/send1', { message: $input.val() })
        .always(function always(data) {
          $input.val('');
        })
        .fail(function always(data) {
          console.info(data.statusText, data.status);
        });
    })
  });
})();
