package service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import resources.specification.EmployeeSpecification;
import resources.specification.SimplePageRequest;
import service.impl.EmployeeServiceImpl;
import transfer.EmployeeTransfer;
import dao.DepartmentDao;
import dao.EmployeeDao;
import entity.Department;
import entity.Employee;
import entity.Role;

public class EmployeeServiceImplTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
		}
	};

	private EmployeeDao employeeDao;
	private DepartmentDao departmentDao;
	private PasswordEncoder passwordEncoder;
	private EmployeeServiceImpl employeeService;

	private Department department;
	private Employee employee;
	private Role admin;

	@Before
	public void setUp() throws Exception {
		passwordEncoder = context.mock(PasswordEncoder.class);
		employeeDao = context.mock(EmployeeDao.class);
		departmentDao = context.mock(DepartmentDao.class);
		employeeService = new EmployeeServiceImpl(employeeDao, departmentDao,
				passwordEncoder);
		employee = new Employee();
		employee.setId(1L);
		employee.setUsername("demo");
		admin = new Role();
		admin.setName("admin");
		department = new Department();
		department.setId(1L);
		department.setName("sale");
		employee.setDepartment(department);
		// employee.getRoles().add(admin);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetrieve() {
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeDao).findOne(1L);
				will(returnValue(employee));
			}
		});
		EmployeeTransfer ret = employeeService.retrieve(1);
		assertEquals(1l, ret.getId().longValue());
		assertEquals(employee.getName(), ret.getName());
		assertEquals(department.getId(), ret.getDepartment().getId());
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
		final EmployeeTransfer newEntry = new EmployeeTransfer();
		newEntry.setName("name");
		EmployeeTransfer.Department dep = new EmployeeTransfer.Department();
		dep.setId(1L);
		newEntry.setDepartment(dep);
		context.checking(new Expectations() {

			{
				exactly(1).of(departmentDao).findOne(1L);
				will(returnValue(department));

				exactly(1).of(employeeDao).save(with(any(Employee.class)));
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
		assertEquals(2l, ret.getId().longValue());
		assertEquals(newEntry.getName(), ret.getName());
	}

	@Test
	public void testUpdate() {
		EmployeeTransfer newEntry = new EmployeeTransfer();
		newEntry.setName("name");
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeDao).findOne(employee.getId());
				will(returnValue(employee));

				exactly(1).of(employeeDao).save(employee);
				will(returnValue(employee));
			}
		});
		EmployeeTransfer ret = employeeService.update(1l, newEntry);
		assertEquals(1l, ret.getId().longValue());
		assertEquals(newEntry.getName(), ret.getName());
	}

	@Test
	public void testFindAll() {
		final EmployeeSpecification spec = new EmployeeSpecification();
		final SimplePageRequest pageable = new SimplePageRequest(0, 20, "id",
				"ASC");
		final List<Employee> employees = new ArrayList<Employee>();
		employees.add(employee);
		final Page<Employee> page = new PageImpl<Employee>(employees);
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeDao).findAll(spec, pageable);
				will(returnValue(page));
			}
		});
		Page<EmployeeTransfer> rets = employeeService.findAll(spec, pageable);
		assertEquals(1, rets.getTotalElements());
		EmployeeTransfer ret = rets.iterator().next();
		assertEquals(1l, ret.getId().longValue());
		assertEquals(employee.getUsername(), ret.getUsername());
	}

	@Test
	public void testFindByUsername() {
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeDao).findByUsername(
						employee.getUsername());
				will(returnValue(employee));
			}
		});
		EmployeeTransfer ret = employeeService.findByUsername(employee
				.getUsername());
		assertEquals(1l, ret.getId().longValue());
		assertEquals(employee.getUsername(), ret.getUsername());
	}
}
