var mnumber = false;
var memail = false;
var validate = false;
var valgender = false;
var valAdd = false;

function refreshForm() {
	document.getElementById('firstName').value = "";
	document.getElementById('lastName').value = "";
	document.getElementById('DOB').value = "";
	document.getElementById('mobileNumber').value = "";
	document.getElementById('Address1').value = "";
	document.getElementById('Address2').value = "";
	document.getElementById('genderMale').checked = false;
	document.getElementById('genderFemale').checked = false;
	document.getElementById('email').value = "";
	document.getElementById('Age').value = "0";

	clearFirstNameError();
	clearlastNameError();
	clearEmailError();
	clearMobileNumberError();
	clearDateError();
	cliaraddmess();
	updateSubmitButtonState();
}

function validateAddress() {
	var addressInput = document.getElementById("Address1");
	var addressError = document.getElementById("address1Error");
	var addressValue = addressInput.value.trim();

	if (addressValue === "") {
		addressError.textContent = "Address is required";
		addressError.style.color = "red";
		addressInput.style.borderColor = "red";

		valAdd = false;
	} else {
		valAdd = true;
	}
}

function validateAddress1() {
	// Get the address input element
	var addressInput = document.getElementById("Address1");

	// Get the value and limit it to 50 characters
	var limitedValue = addressInput.value.substring(0, 50);
	addressInput.value = limitedValue;
}

function validateAddress2() {
	var addressInput = document.getElementById("Address2");
	var limitedValue = addressInput.value.substring(0, 50);
	addressInput.value = limitedValue;
}

document.getElementById("resetBtn").addEventListener("click", function() {
	refreshForm();
});

if ($("#customerid").val()) {
	validateFirstName();
	validatelastName();
	validatemobileNumber();
	validateEmailOnBlur();
	validateGender();
	validateAddress();
	validateDate();
	calculateAge();
}

$(document).ready(function() {
	// Assume you have a variable for the button ID
	var buttonId = $("#customerid").val();

	// Function to update button title based on the ID
	function updateButtonTitle() {
		var saveButton = $('#save');

		if (buttonId) {
			saveButton.val('Update');
			saveButton.attr('title', 'Update');
		} else {
			saveButton.val('Register Now');
			saveButton.attr('title', 'Register');
		}
	}
	updateButtonTitle();
});

document.getElementById("email").addEventListener("blur", function() {
	validateEmailOnBlur();
	updateSubmitButtonState();
});

document.getElementById("email").addEventListener("click", function() {
	clearEmailError();
});
document.getElementById("lastName").addEventListener("blur", function() {
	validatelastName();
	updateSubmitButtonState();
});

document.getElementById("lastName").addEventListener("click", function() {
	validatelastNamee();
});

function clearEmailError() {
	const emailError = document.getElementById("emailError");
	const emailInput = document.getElementById("email");
	emailError.textContent = "";
	emailError.style.color = "";
	emailInput.style.borderColor = "";
}

function clearForm() {
	document.getElementById('customerid').value = "";
	document.getElementById('firstName').value = "";
	document.getElementById('lastName').value = "";
	document.getElementById('DOB').value = "";
	document.getElementById('mobileNumber').value = "";
	document.getElementById('Address1').value = "";
	document.getElementById('Address2').value = "";
	document.getElementById('genderMale').checked = false;
	document.getElementById('genderFemale').checked = false;
	document.getElementById('email').value = "";

	clearFirstNameError();
	clearlastNameError();
	clearEmailError();
	clearMobileNumberError();
	updateSubmitButtonState();
}

function checkDuplicateEmail(email, customerId) {
	// AJAX request
	var isDuplicate = false;
	$.ajax({
		type: "GET",
		async: false,
		url: "/api/customers/checkDuplicateEmail",
		data: {
			email: email,
			customerId: customerId
		},
		success: function(result) {
			isDuplicate = result;
		},
		error: function(error) {
			console.error("Error checking duplicate email", error);
		}
	});
	return isDuplicate;
}

function validateEmailOnBlur() {
	const emailInput = document.getElementById("email");
	const emailError = document.getElementById("emailError");
	const email = emailInput.value.trim();
	const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

	if (email === "") {
		emailError.textContent = "Email is required";
		emailError.style.color = "red";
		emailInput.style.borderColor = "red";
		memail = false;
	} else if (!emailRegex.test(email)) {
		emailError.textContent = "Invalid email format";
		emailError.style.color = "red";
		emailInput.style.borderColor = "red";
		memail = false;
	} else {
		const customerId = $("#customerid").val();
		const isDuplicate = checkDuplicateEmail(email, customerId);
		if (isDuplicate) {
			emailError.textContent = "Duplicate email";
			emailError.style.color = "red";
			emailInput.style.borderColor = "red";
			memail = false;
		} else {
			emailError.textContent = "";
			emailError.style.color = "";
			emailInput.style.borderColor = "";
			memail = true;
		}
	}
}

function validateFirstName() {
	const firstNameInput = document.getElementById("firstName");
	const firstNameError = document.getElementById("firstNameError");

	if (firstNameInput.value.trim() === "") {
		firstNameError.textContent = "First Name is required";
		firstNameError.style.color = "red";
		firstNameInput.style.borderColor = "red";
		validate = false;
	} else {
		firstNameError.textContent = "";
		firstNameError.style.color = "";
		firstNameInput.style.borderColor = "";
		validate = true;
	}
}

function validatelastName() {
	const firstNameInput = document.getElementById("lastName");
	const firstNameError = document.getElementById("lastNameError");

	if (firstNameInput.value.trim() === "") {
		firstNameError.textContent = "Last Name is required";
		firstNameError.style.color = "red";
		firstNameInput.style.borderColor = "red";
		validate = false;
	} else {
		firstNameError.textContent = "";
		firstNameError.style.color = "";
		firstNameInput.style.borderColor = "";
		validate = true;
	}
}

function clearFirstNameError() {
	const firstNameError = document.getElementById("firstNameError");
	const firstNameInput = document.getElementById("firstName");
	firstNameError.textContent = "";
	firstNameError.style.color = "";
	firstNameInput.style.borderColor = "";

}

function clearlastNameError() {
	const firstNameError = document.getElementById("lastNameError");
	const firstNameInput = document.getElementById("lastName");
	firstNameError.textContent = "";
	firstNameError.style.color = "";
	firstNameInput.style.borderColor = "";

}

function validateFirstNamee() {
	const firstNameInput = document.getElementById("firstName");
	let sanitizedValue = firstNameInput.value.replace(/[^a-zA-Z]/g, '');
	const limitedValue = sanitizedValue.substring(0, 50);

	// Capitalize the first character
	sanitizedValue = limitedValue.charAt(0).toUpperCase() + limitedValue.slice(1);

	if (firstNameInput.value !== sanitizedValue) {
		firstNameInput.value = sanitizedValue;
	}

	if (firstNameInput.value.trim() === "") {
		clearFirstNameError();
	}
}

function validatelastNamee() {
	const lastNameInput = document.getElementById("lastName");
	let sanitizedValue = lastNameInput.value.replace(/[^a-zA-Z]/g, '');
	const limitedValue = sanitizedValue.substring(0, 50);

	sanitizedValue = limitedValue.charAt(0).toUpperCase() + limitedValue.slice(1);

	if (lastNameInput.value !== sanitizedValue) {
		lastNameInput.value = sanitizedValue;
	}

	if (lastNameInput.value.trim() === "") {
		clearlastNameError();
	}
}

function validatemobileNumber() {
	const mobileNumberInput = document.getElementById("mobileNumber");
	const mobileNumberError = document.getElementById("mobileNumberError");

	if (mobileNumberInput.value.trim() === "") {
		mobileNumberError.textContent = "Mobile Number is required";
		mobileNumberError.style.color = "red";
		mobileNumberInput.style.borderColor = "red";
		mnumber = false;
	} else if (mobileNumberInput.value.length !== 10) {
		mobileNumberError.textContent = "Enter a valid 10-digit number";
		mobileNumberError.style.color = "red";
		mobileNumberInput.style.borderColor = "red";
		mnumber = false;
	} else {
		checkDuplicateContact(function(isDuplicate) {
			if (isDuplicate) {
				mobileNumberError.textContent = "Duplicate mobile number";
				mobileNumberError.style.color = "red";
				mobileNumberInput.style.borderColor = "red";
				mnumber = false;
			} else {
				mobileNumberError.textContent = "";
				mobileNumberError.style.color = "";
				mobileNumberInput.style.borderColor = "";
				mnumber = true;
			}
		});
	}
}

function clearMobileNumberError() {
	const mobileNumberError = document.getElementById("mobileNumberError");
	const mobileNumberInput = document.getElementById("mobileNumber");
	mobileNumberError.textContent = "";
	mobileNumberError.style.color = "";
	mobileNumberInput.style.borderColor = "";
}

function validateMobileNumberr() {
	const mobileNumberInput = document.getElementById("mobileNumber");
	mobileNumberInput.value = mobileNumberInput.value.replace(/\D/g, '');
	const limitedValue = mobileNumberInput.value.substring(0, 10);
	mobileNumberInput.value = limitedValue;

	if (mobileNumberInput.value.trim() === "" || mobileNumberInput.value.length !== 10) {
		clearMobileNumberError();
	}
}

function validateGender() {
	const genderError = document.getElementById("genderError");
	const maleChecked = document.getElementById("genderMale").checked;
	const femaleChecked = document.getElementById("genderFemale").checked;
	if (!maleChecked && !femaleChecked) {
		genderError.textContent = "Please select a gender";
		genderError.style.color = "red";
		valgender = false;
	} else {
		genderError.textContent = "";
		valgender = true;
	}
}

function areAllFieldsValid() {
	const firstNameInput = document.getElementById("firstName");
	const mobileNumberInput = document.getElementById("mobileNumber");
	return (
		firstNameInput.value.trim() !== "" &&
		mobileNumberInput.value.trim() !== "" &&
		mobileNumberInput.value.length === 10 && validate && memail && mnumber && valgender && valAdd
	);
}

// Function to enable/disable the submit button based on field validity
function updateSubmitButtonState() {
	const submitButton = document.getElementById("save");
	const isValid = areAllFieldsValid();
	submitButton.disabled = !isValid;

	if (isValid) {
		submitButton.classList.remove("disabled");
		submitButton.classList.add("enabled");
	} else {
		submitButton.classList.remove("enabled");
		submitButton.classList.add("disabled");
	}
}

document.getElementById("mobileNumber").addEventListener("blur", function() {
	validatemobileNumber();
	updateSubmitButtonState();
});

document.getElementById("mobileNumber").addEventListener("click", function() {
	validateMobileNumberr();
});

function checkDuplicateContact(callback) {
	// Get the mobile number value from the form
	var mobileNumber = $("#mobileNumber").val();

	// Optionally, you can get the customer ID if it exists
	var customerId = $("#customerid").val();

	// AJAX request
	$.ajax({
		type: "GET",
		url: "/api/customers/checkDuplicateContact",
		data: {
			mobile: mobileNumber,
			customerId: customerId
		},
		success: function(isDuplicate) {
			// isDuplicate is a boolean indicating whether the mobile number is duplicate or not
			callback(isDuplicate);
		},
		error: function(error) {
			// Handle error, maybe show an error message
			console.error("Error checking duplicate mobile number", error);
			callback(false); // Assume it's not a duplicate in case of an error
		}
	});
}

function cliaraddmess() {
	const addressInput = document.getElementById("Address1");
	const addressError = document.getElementById("address1Error");
	addressError.textContent = "";
	addressError.style.color = "";
	addressInput.style.borderColor = "";
}


document.getElementById("firstName").addEventListener("blur", function() {
	validateFirstName();
	updateSubmitButtonState();
});

document.getElementById("firstName").addEventListener("click", function() {
	validateFirstNamee();

});
document.getElementById("Address1").addEventListener("blur", function() {
	validateAddress();
	updateSubmitButtonState();
});

document.getElementById("Address1").addEventListener("click", function() {
	cliaraddmess();

});

$(document).ready(function() {
	// Enable jQuery datepicker for the Date of Birth field
	$("#DOB").datepicker({
		dateFormat: "dd/mm/yy",
		changeMonth: true,
		changeYear: true,
		yearRange: "-100:+0", // Set the year range as per your requirements
		maxDate: 0, // Disable dates after the current date
		onSelect: function(dateText, inst) {
			validateDate();
			calculateAge();
		},
		onClose: function(selectedDate) {
			validateDate();
			calculateAge();
		}
	});

	// Disable manual input for the date field
	$("#DOB").on("keydown", function(e) {
		e.preventDefault();
	});
	isEditMode = $("#customerid").val() !== "";

	if (isEditMode) {
		const birthDateValue = $("#DOB").val();
		const convertedDate = convertDateFormat(birthDateValue);
		console.log("convertedDate: " + convertedDate);
		$("#DOB").val(convertedDate);
		calculateAge();
	}
});

function convertDateFormat(originalDate) {
	const dateObject = new Date(originalDate);
	const day = ('0' + dateObject.getDate()).slice(-2);
	const month = ('0' + (dateObject.getMonth() + 1)).slice(-2);
	const year = dateObject.getFullYear();
	return day + '/' + month + '/' + year; // Change the format to dd/mm/yyyy
}

function validateDate() {
	const dateError = document.getElementById("dateError");
	const inputDate = $("#DOB").val();

	if (inputDate.trim() === "") {
		dateError.textContent = "Date is required";
		dateError.style.color = "red";
		$("#DOB").css("border-color", "red");
	} else {
		dateError.textContent = "";
		$("#DOB").css("border-color", ""); // Reset border color
	}
}

function calculateAge() {
	var dob = $("#DOB").val();
	console.log("-------->" + dob);
	if (dob === '') {
		return;
	}

	// Assuming the date format is DD/MM/YYYY
	var dobParts = dob.split("/");
	var dobDate = new Date(dobParts[2], dobParts[1] - 1, dobParts[0]);

	var currentDate = new Date();

	var ageInYears = currentDate.getFullYear() - dobDate.getFullYear();

	// Check if birthday hasn't occurred yet this year
	if (
		currentDate.getMonth() < dobDate.getMonth() ||
		(currentDate.getMonth() === dobDate.getMonth() && currentDate.getDate() < dobDate.getDate())
	) {
		ageInYears--;
	}

	// Calculate age in months
	var ageInMonths = (currentDate.getMonth() - dobDate.getMonth() + 12) % 12;

	// Calculate remaining days
	var remainingDays;
	if (currentDate.getDate() < dobDate.getDate()) {
		var lastMonth = new Date(currentDate.getFullYear(), currentDate.getMonth() - 1, dobDate.getDate());
		var daysInLastMonth = Math.floor((currentDate - lastMonth) / (1000 * 60 * 60 * 24));
		remainingDays = dobDate.getDate() - daysInLastMonth;
	} else {
		remainingDays = currentDate.getDate() - dobDate.getDate();
	}

	var ageOutput = ageInYears + " years " + ageInMonths + " months ";

	$("#Age").val(ageOutput);
}

document.getElementById("DOB").addEventListener("click", function() {
	clearDateError();

});

document.getElementById("DOB").addEventListener("blur", function() {
	updateSubmitButtonState();
});

document.getElementById("Address2").addEventListener("blur", function() {
	updateSubmitButtonState();
});

function clearDateError() {
	const firstNameError = document.getElementById("dateError");
	const firstNameInput = document.getElementById("DOB");
	firstNameError.textContent = "";
	firstNameError.style.color = "";
	firstNameInput.style.borderColor = "";
}

// Attach the validation function to the click event of the radio buttons
document.getElementById("genderMale").addEventListener("blur", function() {
	console.log("blur event for gender male");
	validateGender();
	updateSubmitButtonState();
});

document.getElementById("genderFemale").addEventListener("blur", function() {
	console.log("blur event for gender female");
	validateGender();
	updateSubmitButtonState();
});

function saveCustomer() {
	if (areAllFieldsValid()) {
		var formData = {
			id: $("#customerid").val(),
			firstName: $("#firstName").val(),
			lastName: $("#lastName").val(),
			birthDate: convertToYYYYMMDD($("#DOB").val()),
			mobile: $("#mobileNumber").val(),
			address1: $("#Address1").val(),
			address2: $("#Address2").val(),
			gender: $("input[name='gender']:checked").val().toUpperCase() || '',
			email: $("#email").val()
		};

		console.log("Customer saved formData", formData);

		$.ajax({
			type: "POST",
			contentType: "application/json",
			url: "/api/customers",
			data: JSON.stringify(formData),
			success: function(data) {
				console.log("Customer saved successfully", data);
				$("#Age").val(0);
				window.location.href = "/getAllCustomers";
				clearForm();
			},
			error: function(error) {
				console.error("Error saving customer", error);
			}
		});
	}
}

function convertToYYYYMMDD(originalDate) {
	console.log("Original Date: " + originalDate);

	// Check if the original date is in "dd/MM/yyyy" format
	if (/^\d{2}\/\d{2}\/\d{4}$/.test(originalDate)) {
		var dateParts = originalDate.split('/');
		var day = parseInt(dateParts[0], 10);
		var month = parseInt(dateParts[1], 10);
		var year = parseInt(dateParts[2], 10);

		// Validate age
		var currentDate = new Date();
		var userDate = new Date(year, month - 1, day); // Month is 0-indexed
		var ageInYears = currentDate.getFullYear() - userDate.getFullYear();

		if (currentDate.getMonth() < userDate.getMonth() || (currentDate.getMonth() === userDate.getMonth() && currentDate.getDate() < userDate.getDate())) {
			ageInYears--;
		}

		if (ageInYears < -1) {
			console.error("Age should be 0 or older");
			return null;
		}
		var formattedDate = year + '-' + ('0' + month).slice(-2) + '-' + ('0' + day).slice(-2);
		console.log("Formatted Date: " + formattedDate);

		return formattedDate;
	}

	console.error("Invalid date: " + originalDate);
	return null;
}
document.getElementById("frmCustomer").addEventListener("submit", function(event) {

	if (!areAllFieldsValid()) {
		event.preventDefault();

		showFieldErrorMessages();
	}
});

function showFieldErrorMessages() {
	validateFirstName();
	validatelastName();
	validatemobileNumber();
	validateEmailOnBlur();
	validateGender();
	validateDate();
	validateAddress();
}

$(document).ready(function() {

	$("#viewPage").click(function(e) {
		e.preventDefault();
		$.ajax({
			type: "GET",
			url: "/view",
			success: function(data) {
				console.log("Success:", data);
				window.location.href = "/getAllCustomers";
			},
			error: function(error) {

				console.error("Error:", error);
			}
		});
	});
});