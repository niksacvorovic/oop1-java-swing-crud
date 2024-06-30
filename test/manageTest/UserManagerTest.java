package manageTest;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.Employee;
import entity.Guest;
import entity.User;
import enums.Degree;
import enums.Gender;
import enums.Role;
import exceptions.DuplicateIDException;
import exceptions.NonexistentEntityException;
import manage.UserManager;

public class UserManagerTest{
	public static UserManager test = new UserManager();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		test.createEmployee("niksa", "niksa", "Niksa", "Cvorovic", Gender.MUSKI, LocalDate.parse("2004-11-25"), "060123456",
				Role.ADMINISTRATOR, Degree.DOKTORSKE, LocalDate.parse("2022-06-27"), 100000);
		test.createGuest("lenka", "lenka", "Lenka", "Nikolic", Gender.ZENSKI, LocalDate.parse("2004-05-29"), "060123123");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testReadUser() {
		User i = test.readUser("niksa");
		assertTrue(i != null);
	}
	
	@Test(expected = NonexistentEntityException.class)
	public void testReadUserException() {
		User i = test.readUser("milica");
	}

	@Test
	public void testCreateEmployee() {
	    test.createEmployee("bojana", "bojana", "Bojana", "Paunovic", Gender.ZENSKI, LocalDate.parse("2004-05-23"), "555555555", 
	    		Role.RECEPCIONAR, Degree.DOKTORSKE, LocalDate.parse("2023-03-03"), 40000.0);
	    Employee employee = (Employee) test.readUser("bojana");
	    assertNotNull(employee);
	    assertTrue(test.employees.contains(employee));
	    assertEquals("bojana", employee.getUsername());
	    assertEquals("bojana", employee.getPassword());
	    assertEquals("Bojana", employee.getName());
	    assertEquals("Paunovic", employee.getLastName());
	    assertEquals(Gender.ZENSKI, employee.getGender());
	    assertEquals(LocalDate.parse("2004-05-23"), employee.getBirthDate());
	    assertEquals("555555555", employee.getPhoneNumber());
	    assertEquals(Role.RECEPCIONAR, employee.getRole());
	    assertEquals(Degree.DOKTORSKE, employee.getDegree());
	    assertEquals(LocalDate.parse("2023-03-03"), employee.getEmploymentDate());
	    assertTrue(employee.getBaseSalary() == 40000);
	}

	@Test(expected = DuplicateIDException.class)
	public void testCreateEmployeeException() {
		test.createEmployee("niksa", "niksa", "Niksa", "Cvorovic", Gender.MUSKI, LocalDate.parse("2004-11-25"), "060123456",
				Role.ADMINISTRATOR, Degree.DOKTORSKE, LocalDate.parse("2022-06-27"), 100000);
	}

	@Test
	public void testCreateGuest() {
	    test.createGuest("miomir", "miomir", "Miomir", "Dujanovic", Gender.MUSKI, LocalDate.parse("2004-11-23"), "333333333");
	    Guest guest = (Guest) test.readUser("miomir");
	    assertNotNull(guest);
	    assertTrue(test.guests.contains(guest));
	    assertEquals("miomir", guest.getUsername());
	    assertEquals("miomir", guest.getPassword());
	    assertEquals("Miomir", guest.getName());
	    assertEquals("Dujanovic", guest.getLastName());
	    assertEquals(Gender.MUSKI, guest.getGender());
	    assertEquals(LocalDate.parse("2004-11-23"), guest.getBirthDate());
	    assertEquals("333333333", guest.getPhoneNumber());
	}

	@Test(expected = DuplicateIDException.class)
	public void testCreateGuestException() {
		test.createGuest("lenka", "lenka", "Lenka", "Nikolic", Gender.ZENSKI, LocalDate.parse("2004-05-29"), "060123123");
	}

	@Test
	public void testUpdateEmployee() {
		test.updateEmployee("niksa", "niksa", "Niksa", "Cvorovic", Gender.MUSKI, LocalDate.parse("2004-11-25"), "060654321",
				Degree.DOKTORSKE, LocalDate.parse("2022-06-27"), 150000.0);
	    Employee updatedEmployee = (Employee) test.readUser("niksa");
	    assertEquals("060654321", updatedEmployee.getPhoneNumber());
	    assertTrue(updatedEmployee.getBaseSalary() == 150000);
	}
	
	@Test
	public void testUpdateGuest() {
		test.updateGuest("lenka", "lenka", "Lenka", "Nikolic", Gender.ZENSKI, LocalDate.parse("2004-05-29"), "060123321");
	    Guest updatedGuest = (Guest) test.readUser("lenka");
	    assertEquals("060123321", updatedGuest.getPhoneNumber());
	}

	@Test
	public void testDeleteUser() {
		User user = test.readUser("bojana");
		test.deleteUser("bojana");
		assertTrue(test.users.contains(user) == false);
	}
}