<%@ page language="java" contentType="text/html; charset=utf-8"
 pageEncoding="utf-8"%>
 
<!-- Modal -->
<div class="modal fade" id="recipeModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel"></h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
	  	<div class='row'>
			<div class='col-4'>
				<img class='modal-img'>
			</div>
			<div class='col-8'>
				<div class='row'>
					Aprašymas:
					<p class='ml-3 modal-description'></p>
				</div>
				<div class='row'>
					Tipas:
					<p class='ml-3 modal-mealTypes'></p>
				</div>
				<div class='row'>
					Porcijos:
					<p class='ml-3 modal-servings'></p>
				</div>
			<div class='row mt-2 modal-buttons'></div>
		</div>
		</div>
	 		<div class='row'>
			  	<div class='col'>
			  		Reikės:
			  		<div class= 'modal-ingredients'>
			  		</div>
			  	</div>    
			  	<div class='col'>
			  		Paruošimo būdas:
			  		<div class= 'modal-preparations'>
			  		</div>
		  		</div>  
	 		</div>
      </div>
    </div>
  </div>
</div>