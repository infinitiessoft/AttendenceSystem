package service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import resources.specification.RoleSpecification;
import service.RoleService;
import transfer.RoleTransfer;
import dao.RoleDao;
import entity.Role;
import exceptions.RemovingIntegrityViolationException;
import exceptions.RoleNotFoundException;

public class RoleServiceImpl implements RoleService {

	private RoleDao roleDao;

	public RoleServiceImpl(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public RoleTransfer retrieve(long id) {
		Role role = roleDao.findOne(id);
		if (role == null) {
			throw new RoleNotFoundException(id);
		}
		return toRoleTransfer(role);
	}

	@Override
	public void delete(long id) {
		try {
			roleDao.delete(id);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new RemovingIntegrityViolationException(Role.class);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			throw new RoleNotFoundException(id);
		}
	}

	@Override
	public RoleTransfer save(RoleTransfer transfer) {
		transfer.setId(null);
		Role role = new Role();
		setUpRole(transfer, role);
		return toRoleTransfer(roleDao.save(role));
	}

	@Override
	public RoleTransfer update(long id, RoleTransfer updated) {
		Role role = roleDao.findOne(id);
		if (role == null) {
			throw new RoleNotFoundException(id);
		}
		updated.setId(role.getId());
		setUpRole(updated, role);
		return toRoleTransfer(roleDao.save(role));
	}

	@Override
	public Page<RoleTransfer> findAll(RoleSpecification spec, Pageable pageable) {
		List<RoleTransfer> transfers = new ArrayList<RoleTransfer>();
		Page<Role> roles = roleDao.findAll(spec, pageable);
		for (Role role : roles) {
			transfers.add(toRoleTransfer(role));
		}
		Page<RoleTransfer> rets = new PageImpl<RoleTransfer>(transfers,
				pageable, roles.getTotalElements());
		return rets;
	}

	public static RoleTransfer toRoleTransfer(Role role) {
		RoleTransfer ret = new RoleTransfer();
		ret.setId(role.getId());
		ret.setName(role.getName());

		return ret;
	}

	private void setUpRole(RoleTransfer transfer, Role role) {
		if (transfer.isNameSet()) {
			role.setName(transfer.getName());
		}
	}

}