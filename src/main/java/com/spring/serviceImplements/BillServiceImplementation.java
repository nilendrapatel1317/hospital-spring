package com.spring.serviceImplements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spring.models.Bill;
import com.spring.models.Doctor;
import com.spring.models.Patient;
import com.spring.repositories.BillRepository;
import com.spring.repositories.DoctorRepository;
import com.spring.repositories.PatientRepository;
import com.spring.services.BillService;

@Service
public class BillServiceImplementation implements BillService {

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private PatientRepository patientRepository;

	// Fetch All Bills
	@Override
	public List<Bill> getAllBills() {
		return billRepository.findAll();
	}

	@Override
	public List<Bill> getAllBillsSortBy(String sortBy) {
		return billRepository.findAll(Sort.by(sortBy));
	}

	// Fetch Bill by ID
	@Override
	public Optional<Bill> getBillById(String id) {
		return billRepository.findById(id);
	}

	// Add Bill
	@Override
	public Bill addBill(Bill newBill) {
		try {
			System.out.println("===================================================================");
			System.out.println("===================================================================");
			String patientId = newBill.getPatient().getId();
			Patient patient = patientRepository.findById(patientId).orElse(null);
			if (patient != null) {
				System.out.println(patient);
				Bill oldBill = patient.getBill();
				if (oldBill != null) {
					System.out.println("Bill Present");
					double totalAmount = oldBill.getTotalAmount() + newBill.getTotalAmount();
					double paidAmount = oldBill.getPaidAmount() + newBill.getPaidAmount();
					double remainingAmount = totalAmount - paidAmount;
					
					if(remainingAmount>0) {
						oldBill.setStatus(false);
					}
					else {
						oldBill.setStatus(true);
					}

					oldBill.setTotalAmount(totalAmount);
					oldBill.setPaidAmount(paidAmount);
					oldBill.setRemainingAmount(remainingAmount);

					billRepository.save(oldBill);
					return oldBill;
				} else {
//	        		 1. Generate unique ID manually
					newBill.setId(generateID());

					// 2. Validation: paidAmount should not exceed totalAmount
					if (newBill.getTotalAmount() < newBill.getPaidAmount()) {
						return null;
					}

					// 3. Fetch Patient from DB
					String patient_Id = newBill.getPatient().getId();
					Patient patient2 = patientRepository.findById(patient_Id).orElse(null);
					if (patient2 == null)
						return null;

					// 4. Set patient in bill (owning side)
					newBill.setPatient(patient2);

					// 5. Set remaining amount and status
					newBill.setRemainingAmount(newBill.getTotalAmount() - newBill.getPaidAmount());
					newBill.setStatus(newBill.getRemainingAmount() == 0);

					// 6. Save bill first (avoid attaching unsaved bill to patient directly)
					Bill savedBill = billRepository.save(newBill);

					// 7. Attach saved bill to patient side
					patient2.setBill(savedBill);

					return savedBill;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	private String generateID() {
		String lastIdStr = billRepository.findLastId().orElse("BILL-100");
		int lastNum = Integer.parseInt(lastIdStr.split("-")[1]);
		String newId = "BILL-" + (lastNum + 1);
		return newId;
	}

	// Edit Bill by ID
	@Override
	public Bill updateBill(String id, Bill updateBill) {
		try {
			if (billRepository.existsById(id)) {
				Bill existBill = billRepository.findById(id)
						.orElseThrow(() -> new RuntimeException("Bill not found !!"));

				if (existBill.getRemainingAmount() > 0) {
					existBill.setPaidAmount(existBill.getPaidAmount() + updateBill.getRemainingAmount());
					existBill.setRemainingAmount(existBill.getRemainingAmount() - updateBill.getRemainingAmount());
					if (existBill.getTotalAmount() <= existBill.getPaidAmount()) {
						existBill.setStatus(true);
					} else {
						existBill.setStatus(false);
					}
				}
				return billRepository.save(existBill);
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("Error message: " + e.getMessage());
			return null;
		}
	}

	// Delete Bill by ID
	@Override
	public String deleteBillById(String id) {
		System.out.println("==========================================");
		try {

			Bill bill = billRepository.findById(id).orElse(null);
			System.out.println(bill.getPatient().getBill());
			if (bill != null) {
				Patient patient = bill.getPatient();
				patient.setBill(null);
				billRepository.delete(bill);
				return "Bill deleted successfully.";
			}
			return "Bill not found.";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	// Delete All Bills
	@Override
	public String deleteAllBill() {
		try {
			billRepository.deleteAll();
			return "All Bills Deleted Successfully !!";
		} catch (Exception e) {
			System.out.println("Error Message: " + e.getMessage());
			return e.getMessage();
		}
	}

	@Override
	public List<Bill> getBillById(Bill bill) {
		Bill bill2 = billRepository.findById(bill.getId()).orElse(null);
		List<Bill> list = new ArrayList<>();
		if(bill2 != null) {
			list.add(bill2);
		}
			
		return list;
	}

}
