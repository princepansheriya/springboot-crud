<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Employee Registration</title>
<link rel="stylesheet" type="text/css" href="styles.css">

<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.8.0/css/pikaday.min.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/pikaday/1.8.0/pikaday.min.js"></script>

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<style>
.error-border {
	border: 1px solid red;
}

#submitBtn:disabled {
	cursor: not-allowed;
}
</style>
<script src="jsp.js"></script>
</head>
<body>
	<form action="register" method="post" id="employeeForm">
		<h2>Employee Registration Form</h2>
		<div>
			*First Name: <input type="text" name="firstName" id="firstName"
				maxlength="50" onblur="validateField('firstName')"
				onclick="clearErrorMessage('firstNameError')"> <span
				class="error-message" id="firstNameError" style="color: red;"></span>
			<br>
		</div>
		<div>
			*Last Name: <input type="text" name="lastName" id="lastName"
				maxlength="50" required onblur="validateField('lastName')"
				onclick="clearErrorMessage('lastNameError')"> <span
				class="error-message" id="lastNameError" style="color: red;"></span>
			<br>
		</div>
		<div>
			*Username: <input type="text" name="username" id="username"
				maxlength="50" required onblur="validateField('username')"
				onclick="clearErrorMessage('usernameError')"> <span
				class="error-message" id="usernameError" style="color: red;"></span>
			<br>
		</div>
		<div>
			*Email: <input type="email" name="email" id="email" maxlength="100"
				required onblur="validateField('email')"
				onclick="clearErrorMessage('emailError')"> <span
				class="error-message" id="emailError" style="color: red;"></span> <br>
		</div>
		<div>
			*Password: <input type="password" name="password" id="password"
				maxlength="15" required onblur="validateField('password')"
				onclick="clearErrorMessage('passwordError')"> <span
				class="error-message" id="passwordError" style="color: red;"></span>
			<br>
		</div>
		<div>
			*Address: <input type="text" name="address" id="address"
				maxlength="255" required onblur="validateField('address')"
				onclick="clearErrorMessage('addressError')"> <span
				class="error-message" id="addressError" style="color: red;"></span>
			<br>
		</div>
		<div>
			*Contact No: <input type="text" name="contactNo" id="contactNo"
				maxlength="10" inputmode="numeric" pattern="[0-9]*" required
				onblur="validateMobileNumber()"
				onclick="clearErrorMessage('contactNoError')"> <span
				class="error-message" id="contactNoError" style="color: red;"></span>
			<br>
		</div>

		<div>
			*Birth Date: <input type="text" name="birthdate" id="birthdate"
				maxlength="10" class="datepicker" placeholder="dd/mm/yyyy" required
				readonly onblur="validateField('birthdate')"
				onclick="showRequiredMessage('birthdate')"> <span
				class="error-message" id="birthdateError" style="color: red;"></span>
			<br>
		</div>
		<button type="button" value="Register" id="submitBtn" disabled>Submit</button>
	</form>


</body>
</html>
