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

import dao.MemberDao;
import entity.Member;
import transfer.MemberTransfer;

public class MemberServiceImplTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
		}
	};

	private MemberDao memberDao;
	private MemberServiceImpl memberService;

	private Member member;

	@Before
	public void setUp() throws Exception {
		memberDao = context.mock(MemberDao.class);
		memberService = new MemberServiceImpl(memberDao);
		member = new Member();
		member.setId(1L);
		member.setUsername("demo");

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetrieve() {
		context.checking(new Expectations() {

			{
				exactly(1).of(memberDao).find(1L);
				will(returnValue(member));
			}
		});
		MemberTransfer ret = memberService.retrieve(1);
		assertEquals("1", ret.getId());
		assertEquals("demo", ret.getUsername());
		assertEquals("demo", ret.getPassword());
		assertEquals("demo", ret.getEmail());
		assertEquals("2015-01-01", ret.getLastlogin());

	}

	@Test
	public void testDelete() {
		context.checking(new Expectations() {

			{
				exactly(1).of(memberDao).delete(1L);
			}
		});
		memberService.delete(1l);
	}

	@Test
	public void testSave() {
		final Member newEntry = new Member();
		newEntry.setUsername("name");

		context.checking(new Expectations() {

			{
				exactly(1).of(memberDao).save(newEntry);
				will(new CustomAction("save member") {

					@Override
					public Object invoke(Invocation invocation) throws Throwable {
						Member e = (Member) invocation.getParameter(0);
						e.setId(2L);
						return e;
					}
				});
			}
		});
		MemberTransfer ret = memberService.save(newEntry);
		assertEquals("2", ret.getId());
		assertEquals("name", ret.getUsername());
		assertEquals("demo", ret.getPassword());
		assertEquals("demo", ret.getEmail());
		assertEquals("2015-01-01", ret.getLastlogin());

	}

	@Test
	public void testUpdate() {
		member.setUsername("name");
		context.checking(new Expectations() {

			{
				exactly(1).of(memberDao).save(member);
				will(returnValue(member));
			}
		});
		MemberTransfer ret = memberService.update(1l, member);
		assertEquals("1", ret.getId());
		assertEquals("name", ret.getUsername());
		assertEquals("demo", ret.getPassword());
		assertEquals("demo", ret.getEmail());
		assertEquals("2015-01-01", ret.getLastlogin());

	}

	@Test
	public void testFindAll() {
		final List<Member> members = new ArrayList<Member>();
		members.add(member);
		context.checking(new Expectations() {

			{
				exactly(1).of(memberDao).findAll();
				will(returnValue(members));
			}
		});
		Collection<MemberTransfer> rets = memberService.findAll();
		assertEquals(1, rets.size());
		MemberTransfer ret = rets.iterator().next();
		assertEquals("1", ret.getId());
		assertEquals("demo", ret.getUsername());
		assertEquals("demo", ret.getPassword());
		assertEquals("demo", ret.getEmail());
		assertEquals("2015-01-01", ret.getLastlogin());

	}

	@Test
	public void testFindByUsername() {
		context.checking(new Expectations() {

			{
				exactly(1).of(memberDao).findByName("demo");
				will(returnValue(member));
			}
		});
		MemberTransfer ret = memberService.findByUsername("demo");
		assertEquals("1", ret.getId());
		assertEquals("demo", ret.getUsername());
		assertEquals("demo", ret.getPassword());
		assertEquals("demo", ret.getEmail());
		assertEquals("2015-01-01", ret.getLastlogin());

	}
}
