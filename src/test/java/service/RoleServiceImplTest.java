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

import transfer.RoleTransfer;
import dao.RoleDao;
import entity.Role;

public class RoleServiceImplTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
		}
	};

	private RoleDao roleDao;
	private RoleServiceImpl roleService;

	private Role role;

	@Before
	public void setUp() throws Exception {
		roleDao = context.mock(RoleDao.class);
		roleService = new RoleServiceImpl(roleDao);
		role = new Role();
		role.setId(1L);
		role.setName("demo");

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetrieve() {
		context.checking(new Expectations() {

			{
				exactly(1).of(roleDao).find(1L);
				will(returnValue(role));
			}
		});
		RoleTransfer ret = roleService.retrieve(1);
		assertEquals("1", ret.getId());
		assertEquals("demo", ret.getName());

	}

	@Test
	public void testDelete() {
		context.checking(new Expectations() {

			{
				exactly(1).of(roleDao).delete(1L);
			}
		});
		roleService.delete(1l);
	}

	@Test
	public void testSave() {
		final Role newEntry = new Role();
		newEntry.setName("name");

		context.checking(new Expectations() {

			{
				exactly(1).of(roleDao).save(newEntry);
				will(new CustomAction("save  role") {

					@Override
					public Object invoke(Invocation invocation)
							throws Throwable {
						Role e = (Role) invocation.getParameter(0);
						e.setId(2L);
						return e;
					}
				});
			}
		});
		RoleTransfer ret = roleService.save(newEntry);
		assertEquals("2", ret.getId());
		assertEquals("name", ret.getName());

	}

	@Test
	public void testUpdate() {
		role.setName("name");
		context.checking(new Expectations() {

			{
				exactly(1).of(roleDao).save(role);
				will(returnValue(role));
			}
		});
		RoleTransfer ret = roleService.update(1l, role);
		assertEquals("1", ret.getId());
		assertEquals("name", ret.getName());

	}

	@Test
	public void testFindAll() {
		final List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		context.checking(new Expectations() {

			{
				exactly(1).of(roleDao).findAll();
				will(returnValue(roles));
			}
		});
		Collection<RoleTransfer> rets = roleService.findAll();
		assertEquals(1, rets.size());
		RoleTransfer ret = rets.iterator().next();
		assertEquals("1", ret.getId());
		assertEquals("demo", ret.getName());

	}

}
