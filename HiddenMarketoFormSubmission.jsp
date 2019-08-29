<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

<script src="//app-sj08.marketo.com/js/forms2/js/forms2.min.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
$(document).ready(function(){
  $("form").submit(function(){
	  
	  var myForm = MktoForms2.allForms()[0];
	  myForm.addHiddenFields({
	  	//These are the values which will be submitted to Marketo
	  	"Email":($("#email").val()),
	  	"FirstName":($("#firstname").val()),
	  	"LastName":($("#lastname").val()),
	  	"Country":($("#country").val()),
	  	"Company":($("#company").val())
	  	});
	  myForm.submit();
	  
  });
});
</script>

</head>
<body>


<form id="mktoForm_2199" style="display:none"></form>
<script>MktoForms2.loadForm("//app-sj08.marketo.com", "876-SWJ-450", 2199);</script> <!-- Change the Instance id and Form Id -->

<br/>
<div class = "container-fluid">
	<form class="form-horizontal">
<fieldset>

<!-- Form Name -->

<legend>Registration Form </legend>

<!-- First Name-->
<div class="form-group">
  <label class="col-md-4 control-label" for="firstname">First Name</label>  
  <div class="col-md-4">
  <input id="firstname" name="firstname" type="text" placeholder="" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Last Name-->
<div class="form-group">
  <label class="col-md-4 control-label" for="lastname">Last Name</label>  
  <div class="col-md-4">
  <input id="lastname" name="lastname" type="text" placeholder="" class="form-control input-md">
    
  </div>
</div>

<!-- Email-->
<div class="form-group">
  <label class="col-md-4 control-label" for="email">Email</label>  
  <div class="col-md-4">
  <input id="email" name="email" type="email" placeholder="" class="form-control input-md">
    
  </div>
</div>
<!-- Company-->
<div class="form-group">
  <label class="col-md-4 control-label" for="company">Company</label>  
  <div class="col-md-4">
  <input id="company" name="company" type="text" placeholder="" class="form-control input-md">
    
  </div>
</div>
<!-- Country Select -->
<div class="form-group">
  <label class="col-md-4 control-label" for="country">Country</label>
  <div class="col-md-4">
    <select id="country" name="country" class="form-control">
      <option value="India">India</option>
      <option value="USA">USA</option>
      <option value="UK">UK</option>
      <option value="Canada">Canada</option>
      <option value="Australia">Australia</option>
    </select>
  </div>
</div>
</fieldset>
<!-- Button -->
<div class="form-group">
  <div class="col-md-4">
    <input type = "submit" id="submit" name="submit" class="btn btn-primary"/>
  </div>
</div>

</form>
	</div>
</body>
</html>