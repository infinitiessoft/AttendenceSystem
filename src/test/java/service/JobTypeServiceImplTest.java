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

import transfer.JobTypeTransfer;
import dao.JobTypeDao;
import entity.JobType;

public class JobTypeServiceImplTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
		}
	};

	private JobTypeDao jobtypeDao;
	private JobTypeServiceImpl jobtypeService;

	private JobType jobtype;

	@Before
	public void setUp() throws Exception {
		jobtypeDao = context.mock(JobTypeDao.class);
		jobtypeService = new JobTypeServiceImpl(jobtypeDao);
		jobtype = new JobType();
		jobtype.setId(1L);
		jobtype.setName("demo");

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetrieve() {
		context.checking(new Expectations() {

			{
				exactly(1).of(jobtypeDao).find(1L);
				will(returnValue(jobtype));
			}
		});
		JobTypeTransfer ret = jobtypeService.retrieve(1);
		assertEquals("1", ret.getId());
		assertEquals("demo", ret.getName());

	}

	@Test
	public void testDelete() {
		context.checking(new Expectations() {

			{
				exactly(1).of(jobtypeDao).delete(1L);
			}
		});
		jobtypeService.delete(1l);
	}

	@Test
	public void testSave() {
		final JobType newEntry = new JobType();
		newEntry.setName("name");

		context.checking(new Expectations() {

			{
				exactly(1).of(jobtypeDao).save(newEntry);
				will(new CustomAction("save jobtype") {

					@Override
					public Object invoke(Invocation invocation)
							throws Throwable {
						JobType e = (JobType) invocation.getParameter(0);
						e.setId(2L);
						return e;
					}
				});
			}
		});
		JobTypeTransfer ret = jobtypeService.save(newEntry);
		assertEquals("2", ret.getId());
		assertEquals("name", ret.getName());

	}

	@Test
	public void testUpdate() {
		jobtype.setName("name");
		context.checking(new Expectations() {

			{
				exactly(1).of(jobtypeDao).save(jobtype);
				will(returnValue(jobtype));
			}
		});
		JobTypeTransfer ret = jobtypeService.update(1l, jobtype);
		assertEquals("1", ret.getId());
		assertEquals("name", ret.getName());

	}

	@Test
	public void testFindAll() {
		final List<JobType> jobtypes = new ArrayList<JobType>();
		jobtypes.add(jobtype);
		context.checking(new Expectations() {

			{
				exactly(1).of(jobtypeDao).findAll();
				will(returnValue(jobtypes));
			}
		});
		Collection<JobTypeTransfer> rets = jobtypeService.findAll();
		assertEquals(1, rets.size());
		JobTypeTransfer ret = rets.iterator().next();
		assertEquals("1", ret.getId());
		assertEquals("demo", ret.getName());

	}
}
