package com.example.customercrud.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.customercrud.entity.CustomerEntity;
import com.example.customercrud.services.CustomerService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * Controller class responsible for customer-related web requests in a Spring
 * MVC application. Methods handle requests for "/first", "/view",
 * "/first/{id}", and "/delete/{id}". Depends on {@code CustomerService} for
 * customer-related business logic.
 */
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerWebController {

	CustomerService customerService;

	/**
	 * Handles the GET request for "/first" and returns the "first" view.
	 *
	 * @return String representing the name of the view to be rendered.
	 */
	@GetMapping("/first")
	public String firstPage() {
		return "first";
	}

	/**
	 * Handles the GET request for "/view" and returns the "view" view.
	 *
	 * @return String representing the name of the view to be rendered.
	 */
	@GetMapping("/view")
	public String viewPage() {
		return "redirect:/getAllCustomers";
	}

	/**
	 * Handles the GET request for "/first/{id}" to display the "first" view with
	 * the details of the customer identified by the provided {id}.
	 *
	 * @param id    The unique identifier of the customer.
	 * @param model The Spring MVC model to add attributes for rendering in the
	 *              view.
	 * @return String representing the name of the view to be rendered.
	 */
	@GetMapping("/first/{id}")
	public String updatePage(@PathVariable Long id, Model model) {
		CustomerEntity customer = customerService.getCustomerById(id);
		model.addAttribute("customer", customer);
		return "first";
	}

	/**
	 * Handles the GET request for "/delete/{id}" to delete the customer identified
	 * by the provided {id} and redirects to the "view" view.
	 *
	 * @param id The unique identifier of the customer to be deleted.
	 * @return String representing the URL to redirect to after deletion.
	 */
	@GetMapping("/delete/{id}")
	public String deletePage(@PathVariable Long id) {
		customerService.deleteCustomer(id);
		return "redirect:/getAllCustomers";
	}

	/**
	 * Retrieves a list of all customers and populates the model with the customer
	 * data.
	 *
	 * @param model The Spring MVC model to which customer data will be added.
	 * @return The logical view name ("customerList") to render the customer list
	 *         page.
	 */
	@GetMapping("/getAllCustomers")
	public String getAllCustomers(Model model) {
		// Retrieve the list of customers from the service layer
		List<CustomerEntity> customers = customerService.getAllCustomer();
		model.addAttribute("customers", customers);
		return "customerList";
	}

}