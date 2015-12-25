package service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Invocation;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.action.CustomAction;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import transfer.EmployeeTransfer;
import dao.EmployeeDao;
import entity.Employee;
import entity.Role;

public class EmployeeServiceImplTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
		}
	};

	private EmployeeDao employeeDao;
	private EmployeeServiceImpl employeeService;

	private Employee employee;
	private Role admin;

	@Before
	public void setUp() throws Exception {
		employeeDao = context.mock(EmployeeDao.class);
		employeeService = new EmployeeServiceImpl(employeeDao);
		employee = new Employee();
		employee.setId(1L);
		employee.setUsername("demo");
		admin = new Role();
		admin.setName("admin");
		employee.getRoles().add(admin);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetrieve() {
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeDao).find(1L);
				will(returnValue(employee));
			}
		});
		EmployeeTransfer ret = employeeService.retrieve(1);
		assertEquals(1l, ret.getId());
		assertEquals("demo", ret.getName());
		assertEquals(1, ret.getRoles().size());
		assertTrue(ret.getRoles().get("admin"));
	}

	@Test
	public void testDelete() {
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeDao).delete(1L);
			}
		});
		employeeService.delete(1l);
	}

	@Test
	public void testSave() {
		final Employee newEntry = new Employee();
		newEntry.setUsername("name");
		newEntry.getRoles().add(admin);
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeDao).save(newEntry);
				will(new CustomAction("save employee") {

					@Override
					public Object invoke(Invocation invocation)
							throws Throwable {
						Employee e = (Employee) invocation.getParameter(0);
						e.setId(2L);
						return e;
					}
				});
			}
		});
		EmployeeTransfer ret = employeeService.save(newEntry);
		assertEquals(2l, ret.getId());
		assertEquals("name", ret.getName());
		assertEquals(1, ret.getRoles().size());
		assertTrue(ret.getRoles().get("admin"));
	}

	@Test
	public void testUpdate() {
		employee.setUsername("name");
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeDao).save(employee);
				will(returnValue(employee));
			}
		});
		EmployeeTransfer ret = employeeService.update(1l, employee);
		assertEquals(1l, ret.getId());
		assertEquals("name", ret.getName());
		assertEquals(1, ret.getRoles().size());
		assertTrue(ret.getRoles().get("admin"));
	}

	@Test
	public void testFindAll() {
		final List<Employee> employees = new ArrayList<Employee>();
		employees.add(employee);
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeDao).findAll();
				will(returnValue(employees));
			}
		});
		Collection<EmployeeTransfer> rets = employeeService.findAll();
		assertEquals(1, rets.size());
		EmployeeTransfer ret = rets.iterator().next();
		assertEquals(1l, ret.getId());
		assertEquals("demo", ret.getName());
		assertEquals(1, ret.getRoles().size());
		assertTrue(ret.getRoles().get("admin"));
	}

	@Test
	public void testFindByUsername() {
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeDao).findByName("demo");
				will(returnValue(employee));
			}
		});
		EmployeeTransfer ret = employeeService.findByUsername("demo");
		assertEquals(1l, ret.getId());
		assertEquals("demo", ret.getName());
		assertEquals(1, ret.getRoles().size());
		assertTrue(ret.getRoles().get("admin"));
	}
}
