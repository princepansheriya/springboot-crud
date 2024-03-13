$(document).ready(function() {
	// DataTable initialization
	const table = $('#customerTable').DataTable({
		"lengthMenu": [5, 10, 15, 25, 30],
		"pageLength": 5,
		"order": [
			[0, "desc"]
		], // Sort by the first column (ID) in descending order
		"columnDefs": [{
				"targets": -1,
				"orderable": false // Disable sorting for the last column
			},
			{
				"targets": 4,
				"orderable": false // Disable sorting for the last column
			},
			{
				"targets": 3,
				"render": function(data, type, row) {
					if (type === 'display' || type === 'filter') {
						return moment(data).format('DD/MM/YYYY');
					} else if (type === 'sort') {
						// Sort using the original date value
						return data;
					} else {
						// For other types, return the original data
						return data;
					}
				},
				"createdCell": function(td, cellData, rowData, row, col) {
					// Add title attribute with formatted date
					$(td).attr('title', moment(cellData).format('DD/MM/YYYY'));
				}
			},
			{
				"targets": 7, // Assuming gender column is at index 7
				"render": function(data) {
					// Capitalize the first letter and make the rest lowercase
					const formattedGender = data.charAt(0).toUpperCase() + data.slice(1).toLowerCase();

					return formattedGender;
				},
				"createdCell": function(td, cellData, rowData, row, col) {
					// Add title attribute with formatted gender
					$(td).attr('title', cellData.charAt(0).toUpperCase() + cellData.slice(1).toLowerCase());
				}
			}
		]
	});

	// Function to calculate age and populate the "Age" column
	function calculateAge() {
		$('#customerTable tbody tr').each(function() {
			const dob = $(this).find('td:eq(3)').text();
			const age = calculateAgeFromDOB(dob);
			$(this).find('.age-column').text(age);
			$(this).find('.age-column').attr('title', 'Age: ' + age);
		});
	}

	// Call the calculateAge function initially
	calculateAge();

	// Add a draw callback to recalculate age when the DataTable is redrawn
	$('#customerTable').on('draw.dt', function() {
		calculateAge();
	});

	// Function to handle delete confirmation
	function confirmDelete(customerId) {
		const result = confirm("Are you sure you want to delete?");
		if (result) {
			// If the user clicks "OK," proceed with deletion
			window.location.href = "/delete/" + customerId;
		} else {
			// If the user clicks "Cancel," do nothing
		}
	}

	// Function to handle update redirection
	function redirectToUpdate(customerId) {
		window.location.href = "/first/" + customerId;
	}

	// Event delegation for the delete buttons
	$('#customerTable tbody').on('click', '.deleteBtn', function() {
		const customerId = $(this).data('customer-id');
		confirmDelete(customerId);
	});

	// Event delegation for the update buttons
	$('#customerTable tbody').on('click', '.updateBtn', function() {
		const customerId = $(this).data('customer-id');
		redirectToUpdate(customerId);
	});

	// Function to handle redirection to another page
	function redirectToOtherPage() {
		window.location.href = '/first';
	}

	// Click event for the "New" link
	$('#formPage').click(function(e) {
		e.preventDefault();
		redirectToOtherPage();
	});

	// Reload the DataTable when the page changes to ensure event delegation
	$('#customerTable').on('draw.dt', function() {
		table.rows().every(function() {
			const data = this.data();
			// Add/update the data-customer-id attribute to each delete and update button
			$(this.node()).find('.deleteBtn').data('customer-id', data.id);
			$(this.node()).find('.updateBtn').data('customer-id', data.id);
		});
	});
});

// Function to calculate age from Date of Birth
function calculateAgeFromDOB(dob) {
	// Assuming the date format is DD/MM/YYYY
	const dobParts = dob.split("/");
	const dobDate = new Date(dobParts[2], dobParts[1] - 1, dobParts[0]);

	const currentDate = new Date();

	let ageInYears = currentDate.getFullYear() - dobDate.getFullYear();
	let ageInMonths = currentDate.getMonth() - dobDate.getMonth();

	// Check if birthday hasn't occurred yet this year
	if (
		currentDate.getMonth() < dobDate.getMonth() ||
		(currentDate.getMonth() === dobDate.getMonth() && currentDate.getDate() < dobDate.getDate())
	) {
		ageInYears--;
		ageInMonths = (12 + currentDate.getMonth() - dobDate.getMonth()) % 12;
	}
	return ageInYears + " year";
}