package tech.daniellas.p3;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CalculateSalary {

	@RequiredArgsConstructor
	@Getter
	static class Employee {
		final String countryCode;
		final BigDecimal salary;
	}

	Optional<Employee> findEmployee(String name) {
		return Optional.empty();
	}

	BigDecimal tax(String countryCode) {
		return BigDecimal.ZERO;
	}

	BigDecimal calculateSalary(String employeeId) {
		Optional<Employee> employee = findEmployee(employeeId);

		if (employee.isPresent()) {
			String countryCode = employee.get().getCountryCode();
			BigDecimal salary = employee.get().getSalary();

			return salary.subtract(tax(countryCode));
		}

		return null;
	}
}
