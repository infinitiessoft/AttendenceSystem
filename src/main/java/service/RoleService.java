package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import resources.specification.RoleSpecification;
import transfer.RoleTransfer;

public interface RoleService {

	public RoleTransfer retrieve(long id);

	public void delete(long id);

	public RoleTransfer save(RoleTransfer role);

	public Page<RoleTransfer> findAll(RoleSpecification spec, Pageable pageable);

	public RoleTransfer update(long id, RoleTransfer role);

}