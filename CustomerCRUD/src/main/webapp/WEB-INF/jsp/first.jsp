<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>Form Page</title>
<link rel="stylesheet" type="text/css" href="/static/css/first.css">
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

</head>

<body>
	<form id="frmCustomer" name="frmCustomer"
		onsubmit="event.preventDefault(); saveCustomer();">
		<label class="cleble">Customer Form</label> <input type="hidden"
			id="customerid" name="customerid" value="${customer.id}">
		<div class="input-box ">
			<label title="First Name:">*First Name:</label> <input type="text"
				id="firstName" name="firstName" value="${customer.firstName}"
				placeholder="Enter customer first name" autocomplete="off"
				oninput="validateFirstNamee()"> <span class="firstNameError"
				id="firstNameError"></span>
		</div>
		<div class="input-box">
			<label title="Last Name:">*Last Name:</label> <input type="text"
				id="lastName" name="lastName" value="${customer.lastName}"
				placeholder="Enter customer last name" autocomplete="off"
				oninput="validatelastNamee()"> <span class="lastNameError"
				id="lastNameError"></span>
		</div>
		<div class="input-box">
			<label title="Date of birth:">*Date of birth:</label> <input
				type="text" id="DOB" name="DOB" autocomplete="off"
				placeholder="dd/mm/yyyy" value="${customer.birthDate}"> <span
				class="dateError" id="dateError"></span>
		</div>
		<div class="input-box">
			<label title="Mobile Number:">*Mobile Number:</label> <input
				type="tel" id="mobileNumber" name="mobileNumber" pattern="[0-9]*"
				value="${customer.mobile}"
				placeholder="Enter customer mobile number" inputmode="numeric"
				autocomplete="off" oninput="validateMobileNumberr()"> <span
				class="mobileNumberError" id="mobileNumberError"></span>
		</div>

		<div class="input-box">
			<label title="Address1:">*Address1:</label> <input type="text"
				id="Address1" name="Address1" value="${customer.address1}"
				placeholder="Enter customer permanent address" autocomplete="off"
				oninput="validateAddress1()"> <span class="address1Error"
				id="address1Error"></span>
		</div>

		<div class="input-box">
			<label title="Address2:">Address2:</label> <input type="text"
				id="Address2" name="Address2" value="${customer.address2}"
				placeholder="Enter customer permanent address" autocomplete="off"
				oninput="validateAddress2()"> <span class="address2Error"
				id="address2Error"></span>
		</div>
		<div class="input-box">
			<label title="Age:">Age</label> <input type="text" id="Age"
				name="Age" value="" placeholder="Enter customer age"
				autocomplete="off" disabled="disabled">
		</div>
		<div class="input-box">
			<label title="Gender:">*Gender:</label> <input type="radio"
				id="genderMale" name="gender" value="Male"
				${customer.gender=='MALE' ? 'checked' : '' }> <span>Male</span>
			<input type="radio" id="genderFemale" name="gender" value="Female"
				${customer.gender=='FEMALE'
					? 'checked' : '' }> <span>Female</span><br>
			<span class="genderError" id="genderError"></span>
		</div>

		<div class="input-box">
			<label title="Email:">*Email:</label> <input type="email" id="email"
				name="email" placeholder="Enter customer email" autocomplete="off"
				value="${customer.email}"> <span class="emailError"
				id="emailError"></span>
		</div>
		<div class="input-box button">
			<input type="Submit" title="" id="save" value="Register Now" disabled>
			<input type="button" title="Reset Form" value="Reset Form"
				id="resetBtn" class="resetBtn"> <a href="#" id="viewPage"
				class="view-page-link" title="Back">Back</a>
		</div>
	</form>
	<script src="/static/js/first.js"></script>

</body>

</html>