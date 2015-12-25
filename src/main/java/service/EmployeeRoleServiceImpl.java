//package service;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import dao.EmployeeRoleDao;
//import entity.EmployeeRole;
//import transfer.EmployeeRoleTransfer;
//
//public class EmployeeRoleServiceImpl implements EmployeeRoleService {
//
//	private EmployeeRoleDao employeeroleDao;
//
//	public EmployeeRoleServiceImpl(EmployeeRoleDao employeeroleDao) {
//		this.employeeroleDao = employeeroleDao;
//	}
//
//	@Override
//	public EmployeeRoleTransfer retrieve(long employee_id) {
//		return toEmployeeRoleTransfer(employeeroleDao.find(employee_id));
//	}
//
//	@Override
//	public void delete(long employee_id) {
//		employeeroleDao.delete(employee_id);
//	}
//
//	@Override
//	public EmployeeRoleTransfer save(EmployeeRole employeerole) {
//		return toEmployeeRoleTransfer(employeeroleDao.save(employeerole));
//	}
//
//	@Override
//	public EmployeeRoleTransfer update(long employee_id, EmployeeRole employeerole) {
//		return toEmployeeRoleTransfer(employeeroleDao.save(employeerole));
//	}
//
//	@Override
//	public Collection<EmployeeRoleTransfer> findAll() {
//		List<EmployeeRoleTransfer> rets = new ArrayList<EmployeeRoleTransfer>();
//
//		for (EmployeeRole employeerole : employeeroleDao.findAll()) {
//			rets.add(toEmployeeRoleTransfer(employeerole));
//		}
//		return rets;
//	}
//
//	private EmployeeRoleTransfer toEmployeeRoleTransfer(EmployeeRole employeerole) {
//		EmployeeRoleTransfer ret = new EmployeeRoleTransfer();
//		ret.setEmployee_id(employeerole.getEmployee_id());
//
//		return ret;
//	}
//
// }