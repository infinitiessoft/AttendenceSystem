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

import transfer.PresencerecordTransfer;
import dao.PresencerecordDao;
import entity.Presencerecord;

public class PresencerecordServiceImplTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
		}
	};

	private PresencerecordDao presencerecordDao;
	private PresencerecordServiceImpl presencerecordService;

	private Presencerecord presencerecord;

	@Before
	public void setUp() throws Exception {
		presencerecordDao = context.mock(PresencerecordDao.class);
		presencerecordService = new PresencerecordServiceImpl(presencerecordDao);
		presencerecord = new Presencerecord();
		presencerecord.setId(1L);
		presencerecord.setName("demo");

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetrieve() {
		context.checking(new Expectations() {

			{
				exactly(1).of(presencerecordDao).find(1L);
				will(returnValue(presencerecord));
			}
		});
		PresencerecordTransfer ret = presencerecordService.retrieve(1);
		assertEquals("1", ret.getId());
		assertEquals("demo", ret.getName());
		assertEquals("2015-01-01", ret.getAttenddate());
		assertEquals("2015-01-01", ret.getBookdate());
		assertEquals("11", ret.getBook_person_id());
		assertEquals("111", ret.getEmployee_id());

	}

	@Test
	public void testDelete() {
		context.checking(new Expectations() {

			{
				exactly(1).of(presencerecordDao).delete(1L);
			}
		});
		presencerecordService.delete(1l);
	}

	@Test
	public void testSave() {
		final Presencerecord newEntry = new Presencerecord();
		newEntry.setName("name");

		context.checking(new Expectations() {

			{
				exactly(1).of(presencerecordDao).save(newEntry);
				will(new CustomAction("save presencerecord") {

					@Override
					public Object invoke(Invocation invocation)
							throws Throwable {
						Presencerecord e = (Presencerecord) invocation
								.getParameter(0);
						e.setId(2L);
						return e;
					}
				});
			}
		});
		PresencerecordTransfer ret = presencerecordService.save(newEntry);
		assertEquals("2", ret.getId());
		assertEquals("name", ret.getName());
		assertEquals("2015-01-01", ret.getAttenddate());
		assertEquals("2015-01-01", ret.getBookdate());
		assertEquals("12", ret.getBook_person_id());
		assertEquals("112", ret.getEmployee_id());

	}

	@Test
	public void testUpdate() {
		presencerecord.setName("name");
		context.checking(new Expectations() {

			{
				exactly(1).of(presencerecordDao).save(presencerecord);
				will(returnValue(presencerecord));
			}
		});
		PresencerecordTransfer ret = presencerecordService.update(1l,
				presencerecord);
		assertEquals("1", ret.getId());
		assertEquals("name", ret.getName());
		assertEquals("2015-01-01", ret.getAttenddate());
		assertEquals("2015-01-01", ret.getBookdate());
		assertEquals("11", ret.getBook_person_id());
		assertEquals("111", ret.getEmployee_id());

	}

	@Test
	public void testFindAll() {
		final List<Presencerecord> presencerecords = new ArrayList<Presencerecord>();
		presencerecords.add(presencerecord);
		context.checking(new Expectations() {

			{
				exactly(1).of(presencerecordDao).findAll();
				will(returnValue(presencerecords));
			}
		});
		Collection<PresencerecordTransfer> rets = presencerecordService
				.findAll();
		assertEquals(1, rets.size());
		PresencerecordTransfer ret = rets.iterator().next();
		assertEquals("1", ret.getId());
		assertEquals("demo", ret.getName());
		assertEquals("2015-01-01", ret.getAttenddate());
		assertEquals("2015-01-01", ret.getBookdate());
		assertEquals("11", ret.getBook_person_id());
		assertEquals("111", ret.getEmployee_id());

	}

}
