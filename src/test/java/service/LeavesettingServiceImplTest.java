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

import dao.LeavesettingDao;
import entity.Leavesetting;
import transfer.LeavesettingTransfer;

public class LeavesettingServiceImplTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
		}
	};

	private LeavesettingDao leavesettingDao;
	private LeavesettingServiceImpl leavesettingService;

	private Leavesetting leavesetting;

	@Before
	public void setUp() throws Exception {
		leavesettingDao = context.mock(LeavesettingDao.class);
		leavesettingService = new LeavesettingServiceImpl(leavesettingDao);
		leavesetting = new Leavesetting();
		leavesetting.setId(1L);
		leavesetting.setName("demo");

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetrieve() {
		context.checking(new Expectations() {

			{
				exactly(1).of(leavesettingDao).find(1L);
				will(returnValue(leavesetting));
			}
		});
		LeavesettingTransfer ret = leavesettingService.retrieve(1);
		assertEquals("1", ret.getId());
		assertEquals("demo", ret.getName());
		assertEquals("1", ret.getOfficial());
		assertEquals("11", ret.getPersonal());
		assertEquals("111", ret.getSick());
		assertEquals("1111", ret.getSpecial());

	}

	@Test
	public void testDelete() {
		context.checking(new Expectations() {

			{
				exactly(1).of(leavesettingDao).delete(1L);
			}
		});
		leavesettingService.delete(1l);
	}

	@Test
	public void testSave() {
		final Leavesetting newEntry = new Leavesetting();
		newEntry.setName("name");

		context.checking(new Expectations() {

			{
				exactly(1).of(leavesettingDao).save(newEntry);
				will(new CustomAction("save leavesetting") {

					@Override
					public Object invoke(Invocation invocation) throws Throwable {
						Leavesetting e = (Leavesetting) invocation.getParameter(0);
						e.setId(2L);
						return e;
					}
				});
			}
		});
		LeavesettingTransfer ret = leavesettingService.save(newEntry);
		assertEquals("2", ret.getId());
		assertEquals("name", ret.getName());
		assertEquals("2", ret.getOfficial());
		assertEquals("12", ret.getPersonal());
		assertEquals("112", ret.getSick());
		assertEquals("1112", ret.getSpecial());

	}

	@Test
	public void testUpdate() {
		leavesetting.setName("name");
		context.checking(new Expectations() {

			{
				exactly(1).of(leavesettingDao).save(leavesetting);
				will(returnValue(leavesetting));
			}
		});
		LeavesettingTransfer ret = leavesettingService.update(1l, leavesetting);
		assertEquals("1", ret.getId());
		assertEquals("name", ret.getName());
		assertEquals("1", ret.getOfficial());
		assertEquals("11", ret.getPersonal());
		assertEquals("111", ret.getSick());
		assertEquals("1111", ret.getSpecial());

	}

	@Test
	public void testFindAll() {
		final List<Leavesetting> leavesettings = new ArrayList<Leavesetting>();
		leavesettings.add(leavesetting);
		context.checking(new Expectations() {

			{
				exactly(1).of(leavesettingDao).findAll();
				will(returnValue(leavesettings));
			}
		});
		Collection<LeavesettingTransfer> rets = leavesettingService.findAll();
		assertEquals(1, rets.size());
		LeavesettingTransfer ret = rets.iterator().next();
		assertEquals("2", ret.getId());
		assertEquals("demo", ret.getName());
		assertEquals("1", ret.getOfficial());
		assertEquals("11", ret.getPersonal());
		assertEquals("111", ret.getSick());
		assertEquals("1111", ret.getSpecial());

	}

	@Test
	public void testFindByName() {
		context.checking(new Expectations() {

			{
				exactly(1).of(leavesettingDao).findByName("demo");
				will(returnValue(leavesetting));
			}
		});
		LeavesettingTransfer ret = leavesettingService.findByName("demo");
		assertEquals("1", ret.getId());
		assertEquals("demo", ret.getName());
		assertEquals("1", ret.getOfficial());
		assertEquals("11", ret.getPersonal());
		assertEquals("111", ret.getSick());
		assertEquals("1111", ret.getSpecial());

	}
}
