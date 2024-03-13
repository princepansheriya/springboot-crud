<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.example.customercrud.entity.CustomerEntity"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Customer List</title>
<!-- Include DataTables CSS -->
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.css">
<!-- Include jQuery -->
<script type="text/javascript" charset="utf8"
	src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- Include DataTables JS -->
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="/static/js/customerList.js"></script>
<link rel="stylesheet" type="text/css"
	href="/static/css/customerList.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>

</head>
<body>
	<a href="#" id="formPage" class="form-page-link">New</a>
	<h2>Customer List</h2>
	<table id="customerTable" class="display">
		<caption></caption>
		<thead>
			<tr>
				<th title="ID" class="center">ID</th>
				<th title="Name">Name</th>
				<th title="Email">Email</th>
				<th title="Date of Birth">Date of Birth</th>
				<th title="Age">Age</th>
				<th title="Mobile">Mobile</th>
				<th title="Address">Address</th>
				<th title="Gender">Gender</th>
				<th class="center" title="Action">Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${customers}" var="customer">
				<tr>
					<td class="center" title="${customer.id}">${customer.id}</td>
					<td title="${customer.firstName}  ${customer.lastName}">${customer.firstName}
						${customer.lastName}</td>
					<td title="${customer.email}">${customer.email}</td>
					<td title="${customer.birthDate}">${customer.birthDate}</td>
					<td class="age-column" title=""></td>
					<td title="${customer.mobile}">${customer.mobile}</td>
					<td class="address-cell"
						title="${customer.address1}, ${customer.address2}">${customer.address1},
						${customer.address2}</td>
					<td>${customer.gender}</td>
					<td class="center">
						<!-- Update Button -->
						<button type="button" title="Update" class="updateBtn"
							data-customer-id="${customer.id}">Update</button> <!-- Delete Button -->
						<button type="button" class="deleteBtn" title="Delete"
							data-customer-id="${customer.id}">Delete</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
