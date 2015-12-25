package service;

import java.util.Collection;

import entity.Role;
import transfer.RoleTransfer;

public interface RoleService {

	public RoleTransfer retrieve(long id);

	public RoleTransfer findByName(String name);

	public void delete(long id);

	public RoleTransfer save(Role role);

	public Collection<RoleTransfer> findAll();

	public RoleTransfer update(long id, Role role);

}