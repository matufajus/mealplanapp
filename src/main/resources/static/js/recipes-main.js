$(document).ready(function() {
  $('li.active').removeClass('active');
  $('a[href="' + location.pathname + '"]').closest('li').addClass('active'); 
});

$( ".remove-ingredient" ).click(function() {
	  $(this).parent().remove();
	  return false;
});

function readURL(input) {
	  if (input.files && input.files[0]) {
	    var reader = new FileReader();
	    
	    reader.onload = function(e) {
	      $('#recipe-form-image').attr('src', e.target.result);
	    }
	    
	    reader.readAsDataURL(input.files[0]);
	  }
	}

$("#recipe-image-input").change(function() {
	  readURL(this);
	});