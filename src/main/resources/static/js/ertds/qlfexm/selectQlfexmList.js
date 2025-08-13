document.addEventListener('DOMContentLoaded', function () {
  var items = document.querySelectorAll('.data-item');

  for (var i = 0; i < items.length; i++) {
    items[i].addEventListener('click', function () {
      var examId = this.getAttribute('data-exam-id');
      window.location.href = '/ertds/qlfexm/selectQlfexmDetail.do?examId=' + examId;
    });
  }
});
