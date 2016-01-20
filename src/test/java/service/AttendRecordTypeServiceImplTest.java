package service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import resources.specification.AttendRecordTypeSpecification;
import resources.specification.SimplePageRequest;
import service.impl.AttendRecordTypeServiceImpl;
import transfer.AttendRecordTypeTransfer;
import dao.AttendRecordTypeDao;
import entity.AttendRecordType;

public class AttendRecordTypeServiceImplTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
		}
	};

	private AttendRecordTypeDao rttendRecordTypeDao;
	private AttendRecordTypeServiceImpl roleService;

	private AttendRecordType role;

	@Before
	public void setUp() throws Exception {
		rttendRecordTypeDao = context.mock(AttendRecordTypeDao.class);
		roleService = new AttendRecordTypeServiceImpl(rttendRecordTypeDao);
		role = new AttendRecordType();
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
				exactly(1).of(rttendRecordTypeDao).findOne(1l);
				will(returnValue(role));
			}
		});
		AttendRecordTypeTransfer ret = roleService.retrieve(1);
		assertEquals(1, ret.getId().longValue());
		assertEquals("demo", ret.getName());

	}

	@Test
	public void testDelete() {
		context.checking(new Expectations() {

			{
				exactly(1).of(rttendRecordTypeDao).delete(1L);
			}
		});
		roleService.delete(1l);
	}

	@Test
	public void testSave() {
		final AttendRecordTypeTransfer newEntry = new AttendRecordTypeTransfer();
		newEntry.setName("name");

		context.checking(new Expectations() {

			{
				exactly(1).of(rttendRecordTypeDao).save(
						with(any(AttendRecordType.class)));
				will(new CustomAction("save  role") {

					@Override
					public Object invoke(Invocation invocation)
							throws Throwable {
						AttendRecordType e = (AttendRecordType) invocation
								.getParameter(0);
						e.setId(2L);
						return e;
					}
				});
			}
		});
		AttendRecordTypeTransfer ret = roleService.save(newEntry);
		assertEquals(2l, ret.getId().longValue());
		assertEquals("name", ret.getName());

	}

	@Test
	public void testUpdate() {
		final AttendRecordTypeTransfer newEntry = new AttendRecordTypeTransfer();
		newEntry.setName("name");

		context.checking(new Expectations() {

			{
				exactly(1).of(rttendRecordTypeDao).findOne(1l);
				will(returnValue(role));

				exactly(1).of(rttendRecordTypeDao).save(role);
				will(returnValue(role));
			}
		});
		AttendRecordTypeTransfer ret = roleService.update(1l, newEntry);
		assertEquals(1, ret.getId().longValue());
		assertEquals("name", ret.getName());

	}

	@Test
	public void testFindAll() {
		final AttendRecordTypeSpecification spec = new AttendRecordTypeSpecification();
		final SimplePageRequest pageable = new SimplePageRequest(0, 20, "id",
				"ASC");
		List<AttendRecordType> roles = new ArrayList<AttendRecordType>();
		roles.add(role);
		final Page<AttendRecordType> page = new PageImpl<AttendRecordType>(
				roles);
		context.checking(new Expectations() {

			{
				exactly(1).of(rttendRecordTypeDao).findAll(spec, pageable);
				will(returnValue(page));
			}
		});
		Page<AttendRecordTypeTransfer> rets = roleService.findAll(spec,
				pageable);
		assertEquals(1, rets.getTotalElements());
		AttendRecordTypeTransfer ret = rets.iterator().next();
		assertEquals(1l, ret.getId().longValue());
		assertEquals("demo", ret.getName());
	}

}
