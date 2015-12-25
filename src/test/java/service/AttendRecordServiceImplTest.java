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

import dao.AttendRecordDao;
import transfer.AttendRecordTransfer;

public class AttendRecordServiceImplTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
		}
	};

	private AttendRecordDao attendrecordDao;
	private AttendRecordServiceImpl attendrecordService;

	private AttendRecord attendrecord;
	// private Role admin;

	@Before
	public void setUp() throws Exception {
		attendrecordDao = context.mock(AttendRecordDao.class);
		attendrecordService = new AttendRecordServiceImpl(attendrecordDao);
		attendrecord = new AttendRecord();
		attendrecord.setId(1L);
		attendrecord.setName("demo");
		attendrecord.setModify_end_date("2015-01-01");
		attendrecord.setModify_start_date("2015-01-01");
		attendrecord.setModify_date("2015-01-01");
		attendrecord.setEmployee_id("1");
		attendrecord.setPermit("demo");
		attendrecord.setReason("demo");
		attendrecord.setDuration("2015-01-01");
		attendrecord.setEnd_date("2015-01-01");
		attendrecord.setBook_date("2015-01-01");
		attendrecord.setPermic_person_id("1");
		attendrecord.setStart_date("2015-01-01");
		attendrecord.setDateinterval("2015-01-01");
		attendrecord.setEndperiod("2015-01-01");
		attendrecord.setModifyendperiod("2015-01-01");
		attendrecord.setModifystartperiod("2015-01-01");
		attendrecord.setModifypermit("demo");
		attendrecord.setStartperiod("2015-01-01");
		attendrecord.setPeriod("2015-01-01");
		attendrecord.setPermit2("demo");
		attendrecord.setModifypermit2("demo");
		attendrecord.setModifyreason("demo");
		attendrecord.setModifytype("demo");
		attendrecord.setPermic_person_id2("1");

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetrieve() {
		context.checking(new Expectations() {

			{
				exactly(1).of(attendrecordDao).find(1L);
				will(returnValue(attendrecord));
			}
		});
		AttendRecordTransfer ret = attendrecordService.retrieve(1);
		assertEquals(1l, ret.getId());
		assertEquals("demo", ret.getName());
		assertEquals("2015-01-01", ret.getModify_end_date());
		assertEquals("2015-01-01", ret.getModify_start_date());
		assertEquals("2015-01-01", ret.getModify_date());
		assertEquals("1l", ret.getEmployee_id());
		assertEquals("demo", ret.getPermit());
		assertEquals("demo", ret.getReason());
		assertEquals("2015-01-01", ret.getDuration());
		assertEquals("2015-01-01", ret.getEnd_date());
		assertEquals("2015-01-01", ret.getBook_date());
		assertEquals("1L", ret.getPermic_person_id());
		assertEquals("2015-01-01", ret.getStart_date());
		assertEquals("2015-01-01", ret.getDateinterval());
		assertEquals("2015-01-01", ret.getEndperiod());
		assertEquals("2015-01-01", ret.getModifyendperiod());
		assertEquals("2015-01-01", ret.getModifystartperiod());
		assertEquals("demo", ret.getModifypermit());
		assertEquals("2015-01-01", ret.getStartperiod());
		assertEquals("2015-01-01", ret.getPeriod());
		assertEquals("demo", ret.getPermit2());
		assertEquals("demo", ret.getModifypermit2());
		assertEquals("demo", ret.getModifyreason());
		assertEquals("demo", ret.getModifytype());
		assertEquals("1L", ret.getPermic_person_id2());

	}

	@Test
	public void testDelete() {
		context.checking(new Expectations() {

			{
				exactly(1).of(attendrecordDao).delete(1L);
			}
		});
		attendrecordService.delete(1l);
	}

	@Test
	public void testSave() {
		final AttendRecord newEntry = new AttendRecord();
		newEntry.setUsername("name");
		context.checking(new Expectations() {

			{
				exactly(1).of(attendrecordDao).save(newEntry);
				will(new CustomAction("save attendrecord") {

					@Override
					public Object invoke(Invocation invocation) throws Throwable {
						AttendRecord e = (AttendRecord) invocation.getParameter(0);
						e.setId(2L);
						return e;
					}
				});
			}
		});
		AttendRecordTransfer ret = attendrecordService.save(newEntry);
		assertEquals(2l, ret.getId());
		assertEquals("name", ret.getName());
		assertEquals("2015-01-01", ret.getModify_end_date());
		assertEquals("2015-01-01", ret.getModify_start_date());
		assertEquals("2015-01-01", ret.getModify_date());
		assertEquals("1", ret.getEmployee_id());
		assertEquals("demo", ret.getPermit());
		assertEquals("demo", ret.getReason());
		assertEquals("2015-01-01", ret.getDuration());
		assertEquals("2015-01-01", ret.getEnd_date());
		assertEquals("2015-01-01", ret.getBook_date());
		assertEquals("1", ret.getPermic_person_id());
		assertEquals("2015-01-01", ret.getStart_date());
		assertEquals("2015-01-01", ret.getDateinterval());
		assertEquals("2015-01-01", ret.getEndperiod());
		assertEquals("2015-01-01", ret.getModifyendperiod());
		assertEquals("2015-01-01", ret.getModifystartperiod());
		assertEquals("demo", ret.getModifypermit());
		assertEquals("2015-01-01", ret.getStartperiod());
		assertEquals("2015-01-01", ret.getPeriod());
		assertEquals("demo", ret.getPermit2());
		assertEquals("demo", ret.getModifypermit2());
		assertEquals("demo", ret.getModifyreason());
		assertEquals("demo", ret.getModifytype());
		assertEquals("1", ret.getPermic_person_id2());

	}

	@Test
	public void testUpdate() {
		attendrecord.setUsername("name");
		context.checking(new Expectations() {

			{
				exactly(1).of(attendrecordDao).save(attendrecord);
				will(returnValue(attendrecord));
			}
		});
		AttendRecordTransfer ret = attendrecordService.update(1l, attendrecord);
		assertEquals(1l, ret.getId());
		assertEquals("name", ret.getName());
		assertEquals("2012-01-01", ret.getModify_end_date());
		assertEquals("2012-01-01", ret.getModify_start_date());
		assertEquals("2012-01-01", ret.getModify_date());
		assertEquals("1", ret.getEmployee_id());
		assertEquals("demo1", ret.getPermit());
		assertEquals("demo1", ret.getReason());
		assertEquals("2012-01-01", ret.getDuration());
		assertEquals("2012-01-01", ret.getEnd_date());
		assertEquals("2012-01-01", ret.getBook_date());
		assertEquals("1", ret.getPermic_person_id());
		assertEquals("2012-01-01", ret.getStart_date());
		assertEquals("2012-01-01", ret.getDateinterval());
		assertEquals("2012-01-01", ret.getEndperiod());
		assertEquals("2012-01-01", ret.getModifyendperiod());
		assertEquals("2012-01-01", ret.getModifystartperiod());
		assertEquals("demo1", ret.getModifypermit());
		assertEquals("2012-01-01", ret.getStartperiod());
		assertEquals("2012-01-01", ret.getPeriod());
		assertEquals("demo1", ret.getPermit2());
		assertEquals("demo1", ret.getModifypermit2());
		assertEquals("demo1", ret.getModifyreason());
		assertEquals("demo1", ret.getModifytype());
		assertEquals("1", ret.getPermic_person_id2());

	}

	@Test
	public void testFindAll() {
		final List<AttendRecord> attendrecords = new ArrayList<AttendRecord>();
		employees.add(attendrecord);
		context.checking(new Expectations() {

			{
				exactly(1).of(attendrecordDao).findAll();
				will(returnValue(attendrecords));
			}
		});
		Collection<AttendRecordTransfer> rets = attendrecordService.findAll();
		assertEquals(1, rets.size());
		AttendRecordTransfer ret = rets.iterator().next();
		assertEquals(1l, ret.getId());
		assertEquals("demo", ret.getName());
		assertEquals("2015-01-01", ret.getModify_end_date());
		assertEquals("2015-01-01", ret.getModify_start_date());
		assertEquals("2015-01-01", ret.getModify_date());
		assertEquals("1", ret.getEmployee_id());
		assertEquals("demo", ret.getPermit());
		assertEquals("demo", ret.getReason());
		assertEquals("2015-01-01", ret.getDuration());
		assertEquals("2015-01-01", ret.getEnd_date());
		assertEquals("2015-01-01", ret.getBook_date());
		assertEquals("1", ret.getPermic_person_id());
		assertEquals("2015-01-01", ret.getStart_date());
		assertEquals("2015-01-01", ret.getDateinterval());
		assertEquals("2015-01-01", ret.getEndperiod());
		assertEquals("2015-01-01", ret.getModifyendperiod());
		assertEquals("2015-01-01", ret.getModifystartperiod());
		assertEquals("demo", ret.getModifypermit());
		assertEquals("2015-01-01", ret.getStartperiod());
		assertEquals("2015-01-01", ret.getPeriod());
		assertEquals("demo", ret.getPermit2());
		assertEquals("demo", ret.getModifypermit2());
		assertEquals("demo", ret.getModifyreason());
		assertEquals("demo", ret.getModifytype());
		assertEquals("1", ret.getPermic_person_id2());

	}

	@Test
	public void testFindByName() {
		context.checking(new Expectations() {

			{
				exactly(1).of(attendrecordDao).findByName("demo");
				will(returnValue(attendrecord));
			}
		});
		AttendRecordTransfer ret = attendrecordService.findByUsername("demo");
		assertEquals(1l, ret.getId());
		assertEquals("demo", ret.getName());
		assertEquals("2015-01-01", ret.getModify_end_date());
		assertEquals("2015-01-01", ret.getModify_start_date());
		assertEquals("2015-01-01", ret.getModify_date());
		assertEquals("1", ret.getEmployee_id());
		assertEquals("demo", ret.getPermit());
		assertEquals("demo", ret.getReason());
		assertEquals("2015-01-01", ret.getDuration());
		assertEquals("2015-01-01", ret.getEnd_date());
		assertEquals("2015-01-01", ret.getBook_date());
		assertEquals("1", ret.getPermic_person_id());
		assertEquals("2015-01-01", ret.getStart_date());
		assertEquals("2015-01-01", ret.getDateinterval());
		assertEquals("2015-01-01", ret.getEndperiod());
		assertEquals("2015-01-01", ret.getModifyendperiod());
		assertEquals("2015-01-01", ret.getModifystartperiod());
		assertEquals("demo", ret.getModifypermit());
		assertEquals("2015-01-01", ret.getStartperiod());
		assertEquals("2015-01-01", ret.getPeriod());
		assertEquals("demo", ret.getPermit2());
		assertEquals("demo", ret.getModifypermit2());
		assertEquals("demo", ret.getModifyreason());
		assertEquals("demo", ret.getModifytype());
		assertEquals("1", ret.getPermic_person_id2());

	}
}
