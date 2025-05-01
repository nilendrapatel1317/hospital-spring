package com.spring.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.models.Appointment;
import com.spring.models.Bill;
import com.spring.services.BillService;
import com.spring.services.DoctorService;
import com.spring.services.PatientService;

@Controller
@RequestMapping("/bill")
public class BillController {

	@Autowired
	private BillService billService;
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private PatientService patientService;

	// Fetch All Bills
	@GetMapping
    public String getAllBills(@RequestParam(name = "sortBy", required = false) String sortBy, Model model) {
        List<Bill> bills;
        
        if (sortBy == null || sortBy.isEmpty()) {
            bills = billService.getAllBills(); // Default: ID
            model.addAttribute("sortField", "id");
        } else {
            bills = billService.getAllBillsSortBy(sortBy);
            model.addAttribute("sortField", sortBy);
        }

        model.addAttribute("bills", bills);
        return "bill/bills-list";
    }
	@PostMapping
	public String getBillById(@ModelAttribute Bill bill, Model model) {
		List<Bill> bills;
		
		if (bill.getId().trim() != "") {
			bills = billService.getBillById(bill);
			model.addAttribute("sortField", "id");
			model.addAttribute("bills", bills);
			return "bill/bills-list";
		} else {
			return "redirect:/bill";
		}
		
	}

	// open add form
	@GetMapping("/add")
	public String openAddBillForm(Model model) {
		model.addAttribute("bill", new Bill());
		System.out.println(patientService.getAllPatients());
		model.addAttribute("patients", patientService.getAllPatients());
		return "bill/bill-add";
	}

	// Handle Add Bill
	@PostMapping("/add")
	public String addBill(@ModelAttribute Bill bill) {
		billService.addBill(bill);
		return "redirect:/bill";
	}

	// Fetch Bill by ID
	@GetMapping("/find/{id}")
	public Optional<Bill> getAllBills(@PathVariable String id) {
		return billService.getBillById(id);
	}

	// Open Edit Bill Form
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable String id, Model model) {

		Optional<Bill> bill = billService.getBillById(id);
		if (bill.isPresent()) {
			model.addAttribute("bill", bill.get()); // not Optional
			model.addAttribute("doctors", doctorService.getAllDoctors());
			model.addAttribute("patients", patientService.getAllPatients());
			return "bill/bill-edit";
		} else {
			return "redirect:/bill";
		}
	}

	// Edit Bill by ID
	@PostMapping("/edit/{id}")
	public String updateBill(@PathVariable String id, @ModelAttribute Bill bill) {
		billService.updateBill(id, bill);
		return "redirect:/bill";
	}

	// Delete Bill
	@GetMapping("/delete/{id}")
	public String deleteBill(@PathVariable String id) {
		billService.deleteBillById(id);
		return "redirect:/bill";
	}

	// Delete All Bills
	@DeleteMapping("/deleteAll")
	public String deleteAllBill() {
		return billService.deleteAllBill();
	}
}
