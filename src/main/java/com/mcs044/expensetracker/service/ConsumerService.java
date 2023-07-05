package com.mcs044.expensetracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcs044.expensetracker.entity.Consumer;
import com.mcs044.expensetracker.entity.Role;
import com.mcs044.expensetracker.repository.ConsumerRepository;
import com.mcs044.expensetracker.repository.RoleRepository;
import com.mcs044.expensetracker.utility.EmailUtility;
import com.mcs044.expensetracker.utility.PasswordUtility;

@Service
public class ConsumerService {

	@Autowired
	private ConsumerRepository consumerRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordUtility passwordUtility;

	@Autowired
	private EmailUtility emailUtility;

	@Autowired
	private ReportService reportService;

	@Autowired
	private BudgetService budgetService;

	public Consumer createUser(Consumer consumer) throws Exception {
		String username = consumer.getUsername(),
				emailAddress = consumer.getEmailAddress(),
				firstName = consumer.getFirstName(),
				lastName = consumer.getLastName(),
				password = consumer.getPassword();
		Long phoneNumber = consumer.getPhoneNumber();
		double defaultBudget = consumer.getDefaultBudget() == 0 ? 50000 : consumer.getDefaultBudget();
		int roleId = 0;
		if (consumer.getId() != null && !consumer.getId().equals(0L)) { // editUserCall
			roleId = consumer.getRole().getRoleId().intValue();
		} else {
			roleId = consumer.getRoleIdInt().intValue();
		}
		switch(roleId) {
			case 1: {
				Role role = new Role();
				role.setRoleId(1L);
				role.setRoleName("Administrator");
				roleRepository.save(role);
				consumer.setRole(role);
				break;
			}
			case 2: {
				Role role = new Role();
				role.setRoleId(2L);
				role.setRoleName("User");
				roleRepository.save(role);
				consumer.setRole(role);
				break;
			}
		}

		if (!emailUtility.isValidEmail(emailAddress))
		throw new Exception("Invalid Email ID. Please try with a different email");

		if (consumer.getId() != null && !consumer.getId().equals(0L)) { // editUserCall
			Consumer editedUser = getUserById(consumer.getId());
			if (!editedUser.getEmailAddress().equals(consumer.getEmailAddress()) && getUserByEmailAddress(emailAddress) != null)
				throw new Exception("Email is already registered. Please try with a different email");
			if (!editedUser.getUsername().equals(consumer.getUsername()) && getUserByUsername(username) != null)
				throw new Exception("Username already exists. Please try with a different username");
			editedUser.setUsername(username);
			editedUser.setFirstName(firstName);
			editedUser.setLastName(lastName);
			editedUser.setPhoneNumber(phoneNumber);
			editedUser.setEmailAddress(emailAddress);
			editedUser.setDefaultBudget(defaultBudget);
			editedUser.setRole(consumer.getRole());
			return consumerRepository.save(editedUser);
		}

		if (getUserByUsername(username) != null)
			throw new Exception(
					"Username already exists. Please try with a different username");
		
		if (getUserByEmailAddress(emailAddress) != null)
			throw new Exception(
					"Email is already registered. Please try with a different email");

		Consumer createdUser = new Consumer();
		createdUser.setUsername(username);
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
		createdUser.setPhoneNumber(phoneNumber);
		createdUser.setEmailAddress(emailAddress);
		createdUser.setDefaultBudget(defaultBudget);
		createdUser.setRole(consumer.getRole());

		byte[] salt = passwordUtility.getSalt();
		String hashPassword = passwordUtility.generatePasswordHash(password, salt);

		createdUser.setPassword(hashPassword);
		createdUser.setSalt(salt);

		Consumer result = consumerRepository.save(createdUser);
		budgetService.saveInitialBudget(result);
		reportService.saveInitialReport(result);
		emailUtility.sendMail(username, emailAddress, "CashFlow Manager account created.", "The account for " + username + " has been created." );
		return result;
    }

	public Consumer editUser(Consumer consumer) throws Exception {
		String username = consumer.getUsername(),
				emailAddress = consumer.getEmailAddress(),
				firstName = consumer.getFirstName(),
				lastName = consumer.getLastName(),
				password = consumer.getPassword();
		Long phoneNumber = consumer.getPhoneNumber();
		double defaultBudget = consumer.getDefaultBudget() == 0 ? 50000 : consumer.getDefaultBudget();

		if (!emailUtility.isValidEmail(emailAddress))
		throw new Exception("Invalid Email ID. Please try with a different email");

		if (consumer.getId() != null && !consumer.getId().equals(0L)) { // editUserCall
			Consumer editedUser = getUserById(consumer.getId());
			if (!editedUser.getEmailAddress().equals(consumer.getEmailAddress()) && getUserByEmailAddress(emailAddress) != null)
				throw new Exception("Email is already registered. Please try with a different email");
			if (!editedUser.getUsername().equals(consumer.getUsername()) && getUserByUsername(username) != null)
				throw new Exception("Username already exists. Please try with a different username");
			editedUser.setUsername(username);
			editedUser.setFirstName(firstName);
			editedUser.setLastName(lastName);
			editedUser.setPhoneNumber(phoneNumber);
			editedUser.setEmailAddress(emailAddress);
			editedUser.setDefaultBudget(defaultBudget);
			editedUser.setRole(consumer.getRole());
			return consumerRepository.save(editedUser);
		}

		if (getUserByUsername(username) != null)
			throw new Exception(
					"Username already exists. Please try with a different username");
		
		if (getUserByEmailAddress(emailAddress) != null)
			throw new Exception(
					"Email is already registered. Please try with a different email");

		Consumer createdUser = new Consumer();
		createdUser.setUsername(username);
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
		createdUser.setPhoneNumber(phoneNumber);
		createdUser.setEmailAddress(emailAddress);
		createdUser.setDefaultBudget(defaultBudget);
		createdUser.setRole(consumer.getRole());

		byte[] salt = passwordUtility.getSalt();
		String hashPassword = passwordUtility.generatePasswordHash(password, salt);

		createdUser.setPassword(hashPassword);
		createdUser.setSalt(salt);

		Consumer result = consumerRepository.save(createdUser);
		budgetService.saveInitialBudget(result);
		reportService.saveInitialReport(result);
		return result;
    }
    
	public void deleteUser(Long userId) {
        consumerRepository.delete(consumerRepository.findById(userId).get());
	}

    public Consumer getUserByUsername(String username) {
		return consumerRepository.findByUsername(username);
	}

	public Consumer getUserByEmailAddress(String username) {
		return consumerRepository.findByEmailAddress(username);
	}

	public boolean isValidEmail(String email) {
		return emailUtility.isValidEmail(email);
	}

	public Consumer userLogin(String username, String password) throws Exception {
		Consumer consumer = consumerRepository.findByUsername(username);
		if (consumer == null)
			throw new Exception("Please check if username or password is valid");
		String securedPasswordHash = consumer.getPassword();
		byte[] salt = consumer.getSalt();

		Boolean isPasswordCorrect = passwordUtility.validatePassword(password, securedPasswordHash, salt);
		if (!isPasswordCorrect)
			throw new Exception("Please check if username or password is valid");
		else {
			Consumer returned = consumerRepository.findByUsername(username);
			// Check if the user's role is authorized for accessing the system
            if (returned.getRole().getRoleName().equals("Administrator")) {
                // Perform additional operations for admin users
            } else if (returned.getRole().getRoleName().equals("User")) {
                // Perform additional operations for manager users
            }
			return returned;
		}
	}

	public boolean passwordReset(String username, String oldPassword, String newPassword) throws Exception {
		Consumer consumer = consumerRepository.findByUsername(username);
		String securedPasswordHash = consumer.getPassword();

		Boolean isPasswordCorrect = passwordUtility.validatePassword(oldPassword, securedPasswordHash,
				consumer.getSalt());
		if (!isPasswordCorrect)
			throw new Exception("Incorrect password");

		byte[] salt = passwordUtility.getSalt();
		Consumer apiUser = consumerRepository.findByUsername(username);
		if (apiUser == null)
			throw new Exception("Please check if username or password is valid");
		apiUser.setPassword(passwordUtility.generatePasswordHash(newPassword, salt));
		apiUser.setSalt(salt);
		consumerRepository.save(apiUser);
		return true;
	}

	public Consumer getUserById(Long userId) {
		return consumerRepository.findById(userId).orElse(null);
	}

	public List<Consumer> getUsers() {
		return consumerRepository.findAll();
	}

}