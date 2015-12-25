package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import transfer.RoleTransfer;
import dao.RoleDao;
import entity.Role;
import exceptions.RoleNotFoundException;

public class RoleServiceImpl implements RoleService {

	private RoleDao roleDao;

	public RoleServiceImpl(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public RoleTransfer retrieve(long id) {
		Role role = roleDao.find(id);
		if (role == null) {
			throw new RoleNotFoundException(id);
		}
		return toRoleTransfer(role);
	}

	@Override
	public void delete(long id) {
		try {
			roleDao.delete(id);
		} catch (NullPointerException e) {
			throw new RoleNotFoundException(id);
		}
	}

	@Override
	public RoleTransfer save(Role role) {
		role.setId(null);
		return toRoleTransfer(roleDao.save(role));
	}

	@Override
	public RoleTransfer update(long id, Role updated) {
		Role role = roleDao.find(id);
		if (role == null) {
			throw new RoleNotFoundException(id);
		}
		updated.setId(role.getId());
		return toRoleTransfer(roleDao.save(updated));
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

}