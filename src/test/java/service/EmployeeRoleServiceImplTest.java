package service;

import static org.junit.Assert.assertEquals;

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

import dao.EmployeeRoleDao;
import entity.EmployeeRole;
import transfer.EmployeeRoleTransfer;

public class EmployeeRoleServiceImplTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
		}
	};

	private EmployeeRoleDao employeeroleDao;
	private EmployeeRoleServiceImpl employeeroleService;

	private EmployeeRole employeerole;

	@Before
	public void setUp() throws Exception {
		employeeroleDao = context.mock(EmployeeRoleDao.class);
		employeeroleService = new EmployeeRoleServiceImpl(employeeroleDao);
		employeerole = new EmployeeRole();
		employeerole.setEmployee_id(1L);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetrieve() {
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeroleDao).find(1L);
				will(returnValue(employeerole));
			}
		});
		EmployeeRoleTransfer ret = employeeroleService.retrieve(1);
		assertEquals("100", ret.getEmployee_id());
		assertEquals("1", ret.getRole_id());

	}

	@Test
	public void testDelete() {
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeroleDao).delete(1L);
			}
		});
		employeeroleService.delete(1l);
	}

	@Test
	public void testSave() {
		final EmployeeRole newEntry = new EmployeeRole();
		// newEntry.setEmployee_id("101");

		context.checking(new Expectations() {

			{
				exactly(1).of(employeeroleDao).save(newEntry);
				will(new CustomAction("save employeerole") {

					@Override
					public Object invoke(Invocation invocation) throws Throwable {
						EmployeeRole e = (EmployeeRole) invocation.getParameter(0);
						e.setEmployee_id(2L);
						return e;
					}
				});
			}
		});
		EmployeeRoleTransfer ret = employeeroleService.save(newEntry);
		assertEquals("200", ret.getEmployee_id());
		assertEquals("1", ret.getRole_id());

	}

	@Test
	public void testUpdate() {
		// employeerole.setRole_id("111");
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeroleDao).save(employeerole);
				will(returnValue(employeerole));
			}
		});
		EmployeeRoleTransfer ret = employeeroleService.update(1l, employeerole);
		assertEquals("100", ret.getEmployee_id());
		assertEquals("1", ret.getRole_id());
	}

	@Test
	public void testFindAll() {
		final List<EmployeeRole> employeeroles = new ArrayList<EmployeeRole>();
		employeeroles.add(employeerole);
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeroleDao).findAll();
				will(returnValue(employeeroles));
			}
		});
		Collection<EmployeeRoleTransfer> rets = employeeroleService.findAll();
		assertEquals(1, rets.size());
		EmployeeRoleTransfer ret = rets.iterator().next();
		assertEquals("100", ret.getEmployee_id());
		assertEquals("1", ret.getRole_id());

	}

}
