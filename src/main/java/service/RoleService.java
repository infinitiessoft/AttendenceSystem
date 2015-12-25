package service;

import java.util.Collection;

import transfer.RoleTransfer;
import entity.Role;

public interface RoleService {

	public RoleTransfer retrieve(long id);

	public void delete(long id);

	public RoleTransfer save(Role role);

	public Collection<RoleTransfer> findAll();

	public RoleTransfer update(long id, Role role);

}