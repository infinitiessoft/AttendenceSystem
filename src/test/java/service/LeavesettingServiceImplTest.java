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

import resources.specification.LeavesettingSpecification;
import resources.specification.SimplePageRequest;
import service.impl.LeavesettingServiceImpl;
import transfer.LeavesettingTransfer;
import dao.AttendRecordTypeDao;
import dao.LeavesettingDao;
import entity.AttendRecordType;
import entity.Leavesetting;

public class LeavesettingServiceImplTest extends ServiceTest {

	private LeavesettingDao leavesettingDao;
	private AttendRecordTypeDao attendRecordTypeDao;
	private LeavesettingServiceImpl leavesettingService;

	private AttendRecordType sick;
	private Leavesetting leavesetting;

	@Before
	public void setUp() throws Exception {
		leavesettingDao = context.mock(LeavesettingDao.class);
		attendRecordTypeDao = context.mock(AttendRecordTypeDao.class);
		leavesettingService = new LeavesettingServiceImpl(leavesettingDao,
				attendRecordTypeDao);

		sick = new AttendRecordType();
		sick.setId(2L);
		sick.setName("sick");

		leavesetting = new Leavesetting();
		leavesetting.setId(1L);
		leavesetting.setName("sick_1");
		leavesetting.setYear(1L);
		leavesetting.setDays(3d);
		leavesetting.setType(sick);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetrieve() {
		context.checking(new Expectations() {

			{
				exactly(1).of(leavesettingDao).findOne(1L);
				will(returnValue(leavesetting));
			}
		});
		LeavesettingTransfer ret = leavesettingService.retrieve(1);
		assertEquals(1l, ret.getId().longValue());
		assertEquals(leavesetting.getYear().longValue(), ret.getYear()
				.longValue());
		assertEquals(leavesetting.getDays().doubleValue(), ret.getDays()
				.doubleValue(), 0.1);
		assertEquals("sick_1", ret.getName());
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
		final LeavesettingTransfer newEntry = new LeavesettingTransfer();
		newEntry.setName("sick");
		newEntry.setDays(2d);
		newEntry.setYear(2l);
		final LeavesettingTransfer.Type type = new LeavesettingTransfer.Type();
		type.setId(1l);
		newEntry.setType(type);
		context.checking(new Expectations() {

			{
				exactly(1).of(attendRecordTypeDao).findOne(type.getId());
				will(returnValue(sick));

				exactly(1).of(leavesettingDao).save(
						with(any(Leavesetting.class)));
				will(new CustomAction("save leavesetting") {

					@Override
					public Object invoke(Invocation invocation)
							throws Throwable {
						Leavesetting e = (Leavesetting) invocation
								.getParameter(0);
						e.setId(2L);
						return e;
					}
				});
			}
		});
		LeavesettingTransfer ret = leavesettingService.save(newEntry);
		assertEquals(2l, ret.getId().longValue());
		assertEquals(newEntry.getName(), ret.getName());
	}

	@Test
	public void testUpdate() {
		final LeavesettingTransfer newEntry = new LeavesettingTransfer();
		newEntry.setName("name");
		context.checking(new Expectations() {

			{
				exactly(1).of(leavesettingDao).findOne(1L);
				will(returnValue(leavesetting));
				exactly(1).of(leavesettingDao).save(leavesetting);
				will(returnValue(leavesetting));
			}
		});
		LeavesettingTransfer ret = leavesettingService.update(1l, newEntry);
		assertEquals(leavesetting.getId(), ret.getId());
		assertEquals(newEntry.getName(), ret.getName());
		assertEquals(leavesetting.getYear().longValue(), ret.getYear()
				.longValue());
		assertEquals(leavesetting.getDays().doubleValue(), ret.getDays()
				.doubleValue(), 0.1);
	}

	@Test
	public void testFindAll() {
		final LeavesettingSpecification spec = new LeavesettingSpecification();
		final SimplePageRequest pageable = new SimplePageRequest(0, 20, "id",
				"ASC");
		final List<Leavesetting> leavesettings = new ArrayList<Leavesetting>();
		leavesettings.add(leavesetting);
		final Page<Leavesetting> page = new PageImpl<Leavesetting>(
				leavesettings);
		context.checking(new Expectations() {

			{
				exactly(1).of(leavesettingDao).findAll(spec, pageable);
				will(returnValue(page));
			}
		});
		Page<LeavesettingTransfer> rets = leavesettingService.findAll(spec,
				pageable);
		assertEquals(1, rets.getTotalElements());
		LeavesettingTransfer ret = rets.iterator().next();
		assertEquals(1l, ret.getId().longValue());
		assertEquals(leavesetting.getYear().longValue(), ret.getYear()
				.longValue());
		assertEquals(leavesetting.getDays().doubleValue(), ret.getDays()
				.doubleValue(), 0.1);
		assertEquals("sick_1", ret.getName());

	}
}
