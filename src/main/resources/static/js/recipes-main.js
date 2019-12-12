$(document).ready(function() {
  $('li.active').removeClass('active');
  $('a[href="' + location.pathname + '"]').closest('li').addClass('active'); 
});

$( ".remove-ingredient" ).click(function() {
	  $(this).parent().remove();
	  return false;
});