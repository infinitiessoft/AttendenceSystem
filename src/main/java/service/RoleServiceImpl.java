package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.RoleDao;
import entity.Role;
import transfer.RoleTransfer;

public class RoleServiceImpl implements RoleService {

	private RoleDao roleDao;

	public RoleServiceImpl(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public RoleTransfer retrieve(long id) {
		return toRoleTransfer(roleDao.find(id));
	}

	@Override
	public void delete(long id) {
		roleDao.delete(id);
	}

	@Override
	public RoleTransfer save(Role role) {
		return toRoleTransfer(roleDao.save(role));
	}

	@Override
	public RoleTransfer update(long id, Role role) {
		return toRoleTransfer(roleDao.save(role));
	}

	@Override
	public Collection<RoleTransfer> findAll() {
		List<RoleTransfer> rets = new ArrayList<RoleTransfer>();

		for (Role role : roleDao.findAll()) {
			rets.add(toRoleTransfer(role));
		}
		return rets;
	}

	private RoleTransfer toRoleTransfer(Role role) {
		RoleTransfer ret = new RoleTransfer();
		ret.setId(role.getId());
		ret.setName(role.getName());

		return ret;
	}

	@Override
	public RoleTransfer findByName(String name) {
		return toRoleTransfer(roleDao.findByName(name));
	}
}