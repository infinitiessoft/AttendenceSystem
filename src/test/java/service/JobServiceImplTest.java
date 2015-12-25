//package service;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import org.jmock.Expectations;
//import org.jmock.Mockery;
//import org.jmock.api.Invocation;
//import org.jmock.integration.junit4.JUnit4Mockery;
//import org.jmock.lib.action.CustomAction;
//import org.jmock.lib.concurrent.Synchroniser;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import dao.JobDao;
//import entity.Job;
//import transfer.JobTransfer;
//
//public class JobServiceImplTest {
//
//	protected Mockery context = new JUnit4Mockery() {
//
//		{
//			setThreadingPolicy(new Synchroniser());
//		}
//	};
//
//	private JobDao jobDao;
//	private JobServiceImpl jobService;
//
//	private Job job;
//
//	@Before
//	public void setUp() throws Exception {
//		jobDao = context.mock(JobDao.class);
//		jobService = new JobServiceImpl(jobDao);
//		job = new Job();
//		job.setId(1L);
//		job.setName("demo");
//
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void testRetrieve() {
//		context.checking(new Expectations() {
//
//			{
//				exactly(1).of(jobDao).find(1L);
//				will(returnValue(job));
//			}
//		});
//		JobTransfer ret = jobService.retrieve(1);
//		assertEquals("1", ret.getId());
//		assertEquals("demo", ret.getName());
//		assertEquals("11", ret.getDepartment_id());
//		assertEquals("111", ret.getJob_type_id());
//
//	}
//
//	@Test
//	public void testDelete() {
//		context.checking(new Expectations() {
//
//			{
//				exactly(1).of(jobDao).delete(1L);
//			}
//		});
//		jobService.delete(1l);
//	}
//
//	@Test
//	public void testSave() {
//		final Job newEntry = new Job();
//		newEntry.setName("name");
//
//		context.checking(new Expectations() {
//
//			{
//				exactly(1).of(jobDao).save(newEntry);
//				will(new CustomAction("save job") {
//
//					@Override
//					public Object invoke(Invocation invocation) throws Throwable {
//						Job e = (Job) invocation.getParameter(0);
//						e.setId(2L);
//						return e;
//					}
//				});
//			}
//		});
//		JobTransfer ret = jobService.save(newEntry);
//		assertEquals("2", ret.getId());
//		assertEquals("name", ret.getName());
//		assertEquals("22", ret.getDepartment_id());
//		assertEquals("222", ret.getJob_type_id());
//
//	}
//
//	@Test
//	public void testUpdate() {
//		job.setName("name");
//		context.checking(new Expectations() {
//
//			{
//				exactly(1).of(jobDao).save(job);
//				will(returnValue(job));
//			}
//		});
//		JobTransfer ret = jobService.update(1l, job);
//		assertEquals("1", ret.getId());
//		assertEquals("name", ret.getName());
//		assertEquals("11", ret.getDepartment_id());
//		assertEquals("111", ret.getJob_type_id());
//
//	}
//
//	@Test
//	public void testFindAll() {
//		final List<Job> jobs = new ArrayList<Job>();
//		jobs.add(job);
//		context.checking(new Expectations() {
//
//			{
//				exactly(1).of(jobDao).findAll();
//				will(returnValue(jobs));
//			}
//		});
//		Collection<JobTransfer> rets = jobService.findAll();
//		assertEquals(1, rets.size());
//		JobTransfer ret = rets.iterator().next();
//		assertEquals("1", ret.getId());
//		assertEquals("demo", ret.getName());
//		assertEquals("11", ret.getDepartment_id());
//		assertEquals("111", ret.getJob_type_id());
//
//	}
//
//	@Test
//	public void testFindByName() {
//		context.checking(new Expectations() {
//
//			{
//				exactly(1).of(jobDao).findByName("demo");
//				will(returnValue(job));
//			}
//		});
//		JobTransfer ret = jobService.findByName("demo");
//		assertEquals("1", ret.getId());
//		assertEquals("demo", ret.getName());
//
//	}
//}
