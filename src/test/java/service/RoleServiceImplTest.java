package service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.api.Invocation;
import org.jmock.lib.action.CustomAction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import resources.specification.RoleSpecification;
import resources.specification.SimplePageRequest;
import service.impl.RoleServiceImpl;
import transfer.RoleTransfer;
import dao.RoleDao;
import entity.Role;

public class RoleServiceImplTest extends ServiceTest {

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
				exactly(1).of(roleDao).findOne(1l);
				will(returnValue(role));
			}
		});
		RoleTransfer ret = roleService.retrieve(1);
		assertEquals(1, ret.getId().longValue());
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
		final RoleTransfer newEntry = new RoleTransfer();
		newEntry.setName("name");

		context.checking(new Expectations() {

			{
				exactly(1).of(roleDao).save(with(any(Role.class)));
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
		assertEquals(2l, ret.getId().longValue());
		assertEquals("name", ret.getName());

	}

	@Test
	public void testUpdate() {
		final RoleTransfer newEntry = new RoleTransfer();
		newEntry.setName("name");

		context.checking(new Expectations() {

			{
				exactly(1).of(roleDao).findOne(1l);
				will(returnValue(role));

				exactly(1).of(roleDao).save(role);
				will(returnValue(role));
			}
		});
		RoleTransfer ret = roleService.update(1l, newEntry);
		assertEquals(1, ret.getId().longValue());
		assertEquals("name", ret.getName());

	}

	@Test
	public void testFindAll() {
		final RoleSpecification spec = new RoleSpecification();
		final SimplePageRequest pageable = new SimplePageRequest(0, 20, "id",
				"ASC");
		List<Role> roles = new ArrayList<Role>();
		roles.add(role);
		final Page<Role> page = new PageImpl<Role>(roles);
		context.checking(new Expectations() {

			{
				exactly(1).of(roleDao).findAll(spec, pageable);
				will(returnValue(page));
			}
		});
		Page<RoleTransfer> rets = roleService.findAll(spec, pageable);
		assertEquals(1, rets.getTotalElements());
		RoleTransfer ret = rets.iterator().next();
		assertEquals(1l, ret.getId().longValue());
		assertEquals("demo", ret.getName());
	}

}
