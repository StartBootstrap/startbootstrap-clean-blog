package tech.daniellas.p3;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CalculateSalaryValid {

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

	Optional<BigDecimal> calculateSalary(String employeeId) {
		return findEmployee(employeeId)
		    .map(e -> e.getSalary().subtract(tax(e.getCountryCode())));
	}
}
