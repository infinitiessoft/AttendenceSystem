package service;

import java.util.Collection;

import entity.MemberRole;
import transfer.MemberRoleTransfer;

public interface MemberRoleService {

	public MemberRoleTransfer retrieve(long id);

	// public MemberRoleTransfer findByUsername(String username);

	public void delete(long id);

	public MemberRoleTransfer save(MemberRole memberrole);

	public Collection<MemberRoleTransfer> findAll();

	public MemberRoleTransfer update(long id, MemberRole memberrole);

}