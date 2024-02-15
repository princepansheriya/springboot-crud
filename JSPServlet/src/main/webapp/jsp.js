var isEmailValid = false;
var isContactNumberValid = false;
var uni = 0;
var uni1 = 0;

function enableSubmitButton() {
	$('#submitBtn').prop('disabled', false).removeClass('disabled-button');
}

function disableSubmitButton() {
	$('#submitBtn').prop('disabled', true).addClass('disabled-button');
}

function validateMobileNumber() {
	var contactNoInput = $('#contactNo');
	var contactNoError = $('#contactNoError');

	// Remove non-digit characters from the input value
	var sanitizedValue = contactNoInput.val().replace(/\D/g, '');
	contactNoInput.val(sanitizedValue);

	// Check if the value is a valid 10-digit number
	if (/^\d{10}$/.test(sanitizedValue)) {
		contactNoError.text('');
		contactNoInput.removeClass('error-border');
		checkDuplicateContactNo();
		enableSubmitButton();
		return true;
	} else {
		if (sanitizedValue.length === 0) {
			contactNoError.text('Contact number is required.');
		} else if (sanitizedValue.length < 10) {
			contactNoError.text('Please enter at least 10 digits.');
		} else {
			contactNoError.text('Please enter a valid 10-digit mobile number.');
		}
		contactNoInput.addClass('error-border');
		disableSubmitButton();
		return false;
	}
}

function validateEmail() {
	var email = $('#email').val().trim();
	if (email === '') {
		showRequiredMessage('email', 'Email is required.');
		disableSubmitButton();
		isEmailValid = false;
		return false;
	} else if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
		checkDuplicateEmail();
	} else {
		showRequiredMessage('email', 'Please enter a valid email address.');
		disableSubmitButton();
		isEmailValid = false;
		return false;
	}
	return new Promise(function(resolve) {
		checkDuplicateEmail(resolve);
	});
}

function validateBirthdate() {
	var birthdate = $('#birthdate').val().trim();
	if (birthdate === '') {
		showRequiredMessage('birthdate', 'Birthdate is required.');
		disableSubmitButton();
		return false;
	}

	var birthdateMoment = moment(birthdate, 'DD/MM/YYYY');
	if (!birthdateMoment.isValid() || birthdateMoment.isAfter(moment())) {
		showRequiredMessage('birthdate', 'Please enter a valid birthdate.');
		disableSubmitButton();
		return false;
	}

	clearErrorMessage('birthdateError');
	return true;
}

function validateField(fieldName) {
	var fieldValue = $('#' + fieldName).val().trim();
	if (fieldValue === '') {
		showRequiredMessage(fieldName, capitalizeFirstLetter(fieldName) + ' is required.');
		disableSubmitButton();
		return false;
	}
	clearErrorMessage(fieldName + "Error");
	checkAllFields();
	return true;
}

function checkDuplicateContactNo(resolve) {
	var contactNo = $("#contactNo").val();
	var contactNoError = $("#contactNoError");
	var contactNoInput = $("#contactNo");

	if (contactNo.trim() !== "") {
		$.ajax({
			url: "checkDuplicateServlet",
			method: "GET",
			data: {
				contactNo: contactNo
			},
			success: function(response) {
				if (response === "true") {
					contactNoError.text("Contact number already exists.");
					disableSubmitButton();
					uni1 = 1;
					isContactNumberValid = false;
					contactNoInput.addClass('error-border');
				} else {
					uni1 = 2;
					contactNoError.text("");
					isContactNumberValid = true;
					contactNoInput.removeClass('error-border');
				}
				checkAllFields(resolve);
			}
		});
	} else {
		contactNoError.text("This field is required.");
		disableSubmitButton();
		isContactNumberValid = false;
		contactNoInput.addClass('error-border');
		checkAllFields(resolve);
	}
}

function checkDuplicateEmail(resolve) {
	var email = $("#email").val();
	var emailError = $("#emailError");
	var emailInput = $("#email");

	if (email.trim() !== "") {
		$.ajax({
			url: "checkDuplicateServlet",
			method: "GET",
			data: {
				email: email
			},
			success: function(response) {
				if (response === "true") {
					emailError.text("Email already exists.");
					disableSubmitButton();
					isEmailValid = false;
					uni = 1;
					emailInput.addClass('error-border');
				} else {
					uni = 2;
					emailError.text("");
					isEmailValid = true;
					emailInput.removeClass('error-border');
				}
				checkAllFields(resolve);
			}
		});
	} else {
		emailError.text("This field is required.");
		disableSubmitButton();
		isEmailValid = false;
		emailInput.addClass('error-border');
		checkAllFields(resolve);
	}
}

function checkAllFields(resolve) {
	var firstName = $('#firstName').val().trim();
	var lastName = $('#lastName').val().trim();
	var username = $('#username').val().trim();
	var password = $('#password').val().trim();
	var address = $('#address').val().trim();
	var birthdate = $('#birthdate').val().trim();

	if (isEmailValid && isContactNumberValid && firstName !== '' && lastName !== '' && username !== '' && password !== '' && address !== '' && birthdate !== '') {

		enableSubmitButton();

		if (resolve) {
			resolve(true);

		}
	} else {
		disableSubmitButton();
		if (resolve) {
			resolve(false);
		}
	}
}

function clearErrorMessage(errorId) {
	var fieldName = errorId.replace("Error", "");
	$('#' + errorId).text('');
	$('#' + fieldName).removeClass('error-border');
}

function showRequiredMessage(fieldName, message) {
	var fieldElement = $('#' + fieldName);
	var errorElement = $('#' + fieldName + "Error");

	fieldElement.addClass('error-border');
	errorElement.text(message || (fieldName + " is required."));
}

function capitalizeFirstLetter(string) {
	return string.charAt(0).toUpperCase() + string.slice(1);
}

function clearForm() {
	// Clear all form fields
	$('#employeeForm')[0].reset();

	// Clear any error messages
	$('.error-message').text('');
	$('.error-border').removeClass('error-border');

	// Reset validation flags
	isEmailValid = false;
	isContactNumberValid = false;

	// Disable submit button
	disableSubmitButton();
}

function submitForm() {
	var servletEndpoint = 'register';
	var formData = $('#employeeForm').serialize();
	if (uni == 1 || uni1 == 1) {
		uni = 0;
		uni1 = 0;
		disableSubmitButton();
		return; // Use 'return' to exit the function early
	}

	$.ajax({
		type: 'POST',
		url: servletEndpoint,
		data: formData,
		success: function(response) {
			if (response === 'success') {
				alert('Registration successful!');
				clearForm(); // Call the clearForm function
			} else {
				alert('Registration failed: ' + response);
				enableSubmitButton();
			}
		},
		error: function(xhr, status, error) {
			alert('Error: AJAX request failed. ' + status + ' - ' + error);
			enableSubmitButton();
		}
	});
}

$(document).ready(function() {
	var birthdatePicker = new Pikaday({
		field: document.getElementById('birthdate'),
		format: 'DD/MM/YYYY',
		maxDate: new Date(),
		yearRange: [1900, moment().year()],
		position: 'top left'
	});

	$('#birthdate').on('click', function() {
		birthdatePicker.show();
		clearErrorMessage('birthdateError');
	});

	$('#birthdate').on('blur', function() {
		validateField('birthdate');
	});
	$('#contactNo').on('input', function() {
		var sanitizedValue = $(this).val().replace(/\D/g, '');
		$(this).val(sanitizedValue);
		if (sanitizedValue.length === 10) {
			validateMobileNumber();
		} else {
			clearErrorMessage('contactNoError');
			$(this).removeClass('error-border');
		}
	});

	$('#contactNo').on('blur', function() {
		validateMobileNumber();
	});

	$('#contactNo').on('click', function() {
		clearErrorMessage('contactNoError');
	});

	$('#contactNo').on('focus', function() {
		clearErrorMessage('contactNoError');
	});

	$('#email').on('blur', function() {
		validateEmail();
	});

	$('#email').on('click', function() {
		clearErrorMessage('emailError');
	});

	$('#email').on('focus', function() {
		clearErrorMessage('emailError');
	});

	$('#submitBtn').on('click', function() {

		var isFormValid = true;
		if (!validateBirthdate()) {
			isFormValid = false;
		}

		// Validate first name
		if (!validateField('firstName')) {
			isFormValid = false;
		}

		// Validate last name
		if (!validateField('lastName')) {
			isFormValid = false;
		}

		// Validate username
		if (!validateField('username')) {
			isFormValid = false;
		}

		// Validate email
		if (!validateEmail()) {
			isFormValid = false;
		}

		if (!validateField('address')) {
			isFormValid = false;
		}

		// Validate password
		if (!validateField('password')) {
			isFormValid = false;
		}

		// Validate contact number
		if (!validateMobileNumber()) {
			isFormValid = false;
		}

		if (isFormValid) {
			submitForm();
		}

	});
}); 