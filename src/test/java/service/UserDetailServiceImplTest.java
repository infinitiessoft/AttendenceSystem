package service;

import static org.junit.Assert.assertEquals;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;

import service.impl.UserDetailServiceImpl;
import dao.EmployeeDao;
import entity.Employee;

public class UserDetailServiceImplTest extends ServiceTest {

	private EmployeeDao employeeDao;
	private UserDetailServiceImpl userDetailService;
	private Employee employee;

	@Before
	public void setUp() throws Exception {
		employeeDao = context.mock(EmployeeDao.class);
		userDetailService = new UserDetailServiceImpl(employeeDao);
		employee = new Employee();
		employee.setId(1L);
		employee.setUsername("demo");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadUserByUsername() {
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeDao).findByUsername(
						employee.getUsername());
				will(returnValue(employee));
			}
		});
		UserDetails ret = userDetailService.loadUserByUsername(employee
				.getUsername());
		assertEquals(employee.getUsername(), ret.getUsername());
	}

}
