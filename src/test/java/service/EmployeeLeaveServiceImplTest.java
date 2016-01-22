package service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import resources.specification.EmployeeLeaveSpecification;
import resources.specification.SimplePageRequest;
import service.impl.EmployeeLeaveServiceImpl;
import transfer.EmployeeLeaveTransfer;
import dao.EmployeeDao;
import dao.EmployeeLeaveDao;
import dao.LeavesettingDao;
import entity.AttendRecordType;
import entity.Employee;
import entity.EmployeeLeave;
import entity.Leavesetting;

public class EmployeeLeaveServiceImplTest extends ServiceTest {

	private AttendRecordType sick;
	private Employee employee;
	private Leavesetting leavesetting;
	private LeavesettingDao leavesettingDao;
	private EmployeeDao employeeDao;
	private EmployeeLeaveDao employeeLeaveDao;
	private EmployeeLeave leave;
	private EmployeeLeaveServiceImpl employeeLeaveService;

	@Before
	public void setUp() throws Exception {
		employeeLeaveDao = context.mock(EmployeeLeaveDao.class);
		leavesettingDao = context.mock(LeavesettingDao.class);
		employeeDao = context.mock(EmployeeDao.class);
		employeeLeaveService = new EmployeeLeaveServiceImpl(employeeLeaveDao,
				employeeDao, leavesettingDao);

		sick = new AttendRecordType();
		sick.setId(1L);
		sick.setName("sick");

		leavesetting = new Leavesetting();
		leavesetting.setId(1L);
		leavesetting.setName("sick_1");
		leavesetting.setYear(1L);
		leavesetting.setDays(3d);
		leavesetting.setType(sick);

		employee = new Employee();
		employee.setId(1L);
		employee.setUsername("demo");

		leave = new EmployeeLeave();
		leave.setId(1l);
		leave.setEmployee(employee);
		leave.setLeavesetting(leavesetting);
		leave.setUsedDays(1d);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetrieve() {
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeLeaveDao).findOne(1L);
				will(returnValue(leave));
			}
		});
		EmployeeLeaveTransfer ret = employeeLeaveService.retrieve(1);
		assertEquals(leave.getId(), ret.getId());
		assertEquals(leave.getUsedDays(), ret.getUsedDays());
		assertEquals(employee.getId(), ret.getEmployee().getId());
		assertEquals(leavesetting.getId(), ret.getLeavesetting().getId());
	}

	@Test
	public void testDelete() {
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeLeaveDao).delete(leave.getId());
			}
		});
		employeeLeaveService.delete(leave.getId());
	}

	@Test
	public void testSave() {
		final EmployeeLeaveTransfer newEntry = new EmployeeLeaveTransfer();
		EmployeeLeaveTransfer.Employee employeeTransfer = new EmployeeLeaveTransfer.Employee();
		employeeTransfer.setId(employee.getId());
		EmployeeLeaveTransfer.Leavesetting settingTransfer = new EmployeeLeaveTransfer.Leavesetting();
		settingTransfer.setId(leavesetting.getId());
		newEntry.setEmployee(employeeTransfer);
		newEntry.setLeavesetting(settingTransfer);

		context.checking(new Expectations() {

			{
				exactly(1).of(leavesettingDao).findOne(
						newEntry.getLeavesetting().getId());
				will(returnValue(leavesetting));
				exactly(1).of(employeeDao).findOne(
						newEntry.getEmployee().getId());
				will(returnValue(employee));
				exactly(1).of(employeeLeaveDao).save(
						with(any(EmployeeLeave.class)));
				will(returnValue(leave));
			}
		});
		EmployeeLeaveTransfer ret = employeeLeaveService.save(newEntry);
		assertEquals(leave.getId(), ret.getId());
		assertEquals(leave.getUsedDays(), ret.getUsedDays());
		assertEquals(employee.getId(), ret.getEmployee().getId());
		assertEquals(leavesetting.getId(), ret.getLeavesetting().getId());
	}

	@Test
	public void testFindAll() {
		final EmployeeLeaveSpecification spec = new EmployeeLeaveSpecification();
		final SimplePageRequest pageable = new SimplePageRequest(0, 20, "id",
				"ASC");
		final List<EmployeeLeave> leaves = new ArrayList<EmployeeLeave>();
		leaves.add(leave);
		final Page<EmployeeLeave> page = new PageImpl<EmployeeLeave>(leaves);
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeLeaveDao).findAll(spec, pageable);
				will(returnValue(page));
			}
		});
		Page<EmployeeLeaveTransfer> rets = employeeLeaveService.findAll(spec,
				pageable);
		assertEquals(1, rets.getTotalElements());
		EmployeeLeaveTransfer ret = rets.iterator().next();
		assertEquals(leave.getId(), ret.getId());
		assertEquals(leave.getId(), ret.getId());
		assertEquals(leave.getUsedDays(), ret.getUsedDays());
		assertEquals(employee.getId(), ret.getEmployee().getId());
		assertEquals(leavesetting.getId(), ret.getLeavesetting().getId());
	}

	@Test
	public void testFindByEmployeeIdAndLeavesettingId() {
		context.checking(new Expectations() {

			{
				exactly(1).of(employeeLeaveDao)
						.findByEmployeeIdAndLeavesettingId(employee.getId(),
								leavesetting.getId());
				will(returnValue(leave));
			}
		});
		EmployeeLeaveTransfer ret = employeeLeaveService
				.findByEmployeeIdAndLeavesettingId(employee.getId(),
						leavesetting.getId());
		assertEquals(leave.getId(), ret.getId());
		assertEquals(leave.getUsedDays(), ret.getUsedDays());
		assertEquals(employee.getId(), ret.getEmployee().getId());
		assertEquals(leavesetting.getId(), ret.getLeavesetting().getId());
	}

	@Test
	public void testUpdate() {
		final EmployeeLeaveTransfer newEntry = new EmployeeLeaveTransfer();
		EmployeeLeaveTransfer.Employee employeeTransfer = new EmployeeLeaveTransfer.Employee();
		employeeTransfer.setId(employee.getId());
		EmployeeLeaveTransfer.Leavesetting settingTransfer = new EmployeeLeaveTransfer.Leavesetting();
		settingTransfer.setId(leavesetting.getId());
		newEntry.setEmployee(employeeTransfer);
		newEntry.setLeavesetting(settingTransfer);

		context.checking(new Expectations() {

			{
				exactly(1).of(employeeLeaveDao).findOne(leave.getId());
				will(returnValue(leave));
				exactly(1).of(employeeLeaveDao).save(
						with(any(EmployeeLeave.class)));
				will(returnValue(leave));
			}
		});
		EmployeeLeaveTransfer ret = employeeLeaveService.update(leave.getId(),
				newEntry);
		assertEquals(leave.getId(), ret.getId());
		assertEquals(leave.getUsedDays(), ret.getUsedDays());
		assertEquals(employee.getId(), ret.getEmployee().getId());
		assertEquals(leavesetting.getId(), ret.getLeavesetting().getId());
	}

}
