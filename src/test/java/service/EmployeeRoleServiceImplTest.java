package service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import resources.specification.EmployeeRoleSpecification;
import resources.specification.SimplePageRequest;
import service.impl.EmployeeRoleServiceImpl;
import transfer.RoleTransfer;
import dao.EmployeeDao;
import dao.EmployeeRoleDao;
import dao.RoleDao;
import entity.Employee;
import entity.EmployeeRole;
import entity.Role;

public class EmployeeRoleServiceImplTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
		}
	};

	private EmployeeDao employeeDao;
	private RoleDao roleDao;
	private EmployeeRoleDao employeeroleDao;
	private EmployeeRoleServiceImpl employeeroleService;

	private Employee employee;
	private Role admin;
	private EmployeeRole employeerole;

	@Before
	public void setUp() throws Exception {
		employeeDao = context.mock(EmployeeDao.class);
		roleDao = context.mock(RoleDao.class);
		employeeroleDao = context.mock(EmployeeRoleDao.class);
		employeeroleService = new EmployeeRoleServiceImpl(employeeDao, roleDao,
				employeeroleDao);

		employee = new Employee();
		employee.setId(1L);
		employee.setUsername("demo");
		admin = new Role();
		admin.setId(1L);
		admin.setName("admin");

		employeerole = new EmployeeRole();
		employeerole.setId(1L);
		employeerole.setEmployee(employee);
		employeerole.setRole(admin);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindByEmployeeIdAndRoleId() {
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeroleDao).findByEmployeeIdAndRoleId(
						employee.getId(), admin.getId());
				will(returnValue(employeerole));
			}
		});
		RoleTransfer ret = employeeroleService.findByEmployeeIdAndRoleId(
				employee.getId(), admin.getId());
		assertEquals(admin.getId(), ret.getId());
		assertEquals(admin.getName(), ret.getName());

	}

	@Test
	public void testDelete() {
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeroleDao).findByEmployeeIdAndRoleId(
						employee.getId(), admin.getId());
				will(returnValue(employeerole));

				exactly(1).of(employeeroleDao).delete(employeerole);
			}
		});
		employeeroleService.revokeRoleFromEmployee(employee.getId(),
				admin.getId());
	}

	@Test
	public void testSave() {
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeDao).findOne(employee.getId());
				will(returnValue(employee));

				exactly(1).of(roleDao).findOne(admin.getId());
				will(returnValue(admin));

				exactly(1).of(employeeroleDao).save(
						with(any(EmployeeRole.class)));
				will(returnValue(employeerole));
			}
		});
		employeeroleService
				.grantRoleToEmployee(employee.getId(), admin.getId());
	}

	@Test
	public void testFindAll() {
		final EmployeeRoleSpecification spec = new EmployeeRoleSpecification();
		final SimplePageRequest pageable = new SimplePageRequest(0, 20, "id",
				"ASC");
		final List<EmployeeRole> employeeroles = new ArrayList<EmployeeRole>();
		employeeroles.add(employeerole);
		final Page<EmployeeRole> page = new PageImpl<EmployeeRole>(
				employeeroles);

		context.checking(new Expectations() {

			{
				exactly(1).of(employeeroleDao).findAll(spec, pageable);
				will(returnValue(page));
			}
		});
		Page<RoleTransfer> rets = employeeroleService.findAll(spec, pageable);
		assertEquals(1, rets.getTotalElements());
		RoleTransfer ret = rets.iterator().next();
		assertEquals(admin.getId(), ret.getId());
		assertEquals(admin.getName(), ret.getName());

	}

}
