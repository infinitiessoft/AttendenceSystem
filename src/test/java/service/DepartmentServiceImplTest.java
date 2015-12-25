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

import transfer.DepartmentTransfer;
import dao.DepartmentDao;
import entity.Department;

public class DepartmentServiceImplTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
		}
	};

	private DepartmentDao departmentDao;
	private DepartmentServiceImpl departmentService;

	private Department department;

	@Before
	public void setUp() throws Exception {
		departmentDao = context.mock(DepartmentDao.class);
		departmentService = new DepartmentServiceImpl(departmentDao);
		department = new Department();
		department.setId(1L);
		department.setName("demo");
		department.setManager_id(1L);
		department.setResponseto(1L);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetrieve() {
		context.checking(new Expectations() {

			{
				exactly(1).of(departmentDao).find(1L);
				will(returnValue(department));
			}
		});
		DepartmentTransfer ret = departmentService.retrieve(1);
		assertEquals(1l, ret.getId());
		assertEquals("D_demo", ret.getName());
		assertEquals("1", ret.getManager_id());
		assertEquals("2", ret.getResponseto());

	}

	@Test
	public void testDelete() {
		context.checking(new Expectations() {

			{
				exactly(1).of(departmentDao).delete(1L);
			}
		});
		departmentService.delete(1l);
	}

	@Test
	public void testSave() {
		final Department newEntry = new Department();
		newEntry.setName("D_name");

		context.checking(new Expectations() {

			{
				exactly(1).of(departmentDao).save(newEntry);
				will(new CustomAction("save department") {

					@Override
					public Object invoke(Invocation invocation)
							throws Throwable {
						Department e = (Department) invocation.getParameter(0);
						e.setId(2L);
						return e;
					}
				});
			}
		});
		DepartmentTransfer ret = departmentService.save(newEntry);
		assertEquals(2l, ret.getId());
		assertEquals("D_name", ret.getName());
		assertEquals("1", ret.getManager_id());
		assertEquals("2", ret.getResponseto());

	}

	@Test
	public void testUpdate() {
		department.setName("name");
		context.checking(new Expectations() {

			{
				exactly(1).of(departmentDao).save(department);
				will(returnValue(department));
			}
		});
		DepartmentTransfer ret = departmentService.update(1l, department);
		assertEquals(1l, ret.getId());
		assertEquals("D_name", ret.getName());
		assertEquals("1", ret.getManager_id());
		assertEquals("2", ret.getResponseto());

	}

	@Test
	public void testFindAll() {
		final List<Department> departments = new ArrayList<Department>();
		departments.add(department);
		context.checking(new Expectations() {

			{
				exactly(1).of(departmentDao).findAll();
				will(returnValue(departments));
			}
		});
		Collection<DepartmentTransfer> rets = departmentService.findAll();
		assertEquals(1, rets.size());
		DepartmentTransfer ret = rets.iterator().next();
		assertEquals(1l, ret.getId());
		assertEquals("Ddemo", ret.getName());
		assertEquals("1", ret.getManager_id());
		assertEquals("2", ret.getResponseto());

	}
}
