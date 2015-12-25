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

import dao.MemberRoleDao;
import entity.MemberRole;
import transfer.MemberRoleTransfer;

public class MemberRoleServiceImplTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
		}
	};

	private MemberRoleDao memberroleDao;
	private MemberRoleServiceImpl memberroleService;

	private MemberRole memberrole;

	@Before
	public void setUp() throws Exception {
		memberroleDao = context.mock(MemberRoleDao.class);
		memberroleService = new MemberRoleServiceImpl(memberroleDao);
		memberrole = new MemberRole();
		memberrole.setMember_id(1L);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetrieve() {
		context.checking(new Expectations() {

			{
				exactly(1).of(memberroleDao).find(1L);
				will(returnValue(memberrole));
			}
		});
		MemberRoleTransfer ret = memberroleService.retrieve(1);
		assertEquals("1", ret.getMember_id());
		assertEquals("11", ret.getRole_id());

	}

	@Test
	public void testDelete() {
		context.checking(new Expectations() {

			{
				exactly(1).of(memberroleDao).delete(1L);
			}
		});
		memberroleService.delete(1l);
	}

	@Test
	public void testSave() {
		final MemberRole newEntry = new MemberRole();

		context.checking(new Expectations() {

			{
				exactly(1).of(memberroleDao).save(newEntry);
				will(new CustomAction("save  memberrole") {

					@Override
					public Object invoke(Invocation invocation) throws Throwable {
						MemberRole e = (MemberRole) invocation.getParameter(0);
						e.setMember_id(2L);
						return e;
					}
				});
			}
		});
		MemberRoleTransfer ret = memberroleService.save(newEntry);
		assertEquals("2", ret.getMember_id());
		assertEquals("12", ret.getRole_id());

	}

	@Test
	public void testUpdate() {

		context.checking(new Expectations() {

			{
				exactly(1).of(memberroleDao).save(memberrole);
				will(returnValue(memberrole));
			}
		});
		MemberRoleTransfer ret = memberroleService.update(1l, memberrole);
		assertEquals("1", ret.getMember_id());
		assertEquals("11", ret.getRole_id());

	}

	@Test
	public void testFindAll() {
		final List<MemberRole> memberroles = new ArrayList<MemberRole>();
		memberroles.add(memberrole);
		context.checking(new Expectations() {

			{
				exactly(1).of(memberroleDao).findAll();
				will(returnValue(memberroles));
			}
		});
		Collection<MemberRoleTransfer> rets = memberroleService.findAll();
		assertEquals(1, rets.size());
		MemberRoleTransfer ret = rets.iterator().next();
		assertEquals("1", ret.getMember_id());
		assertEquals("11", ret.getRole_id());
	}

}
