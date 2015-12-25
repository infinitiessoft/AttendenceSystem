package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.MemberRoleDao;
import entity.MemberRole;
import transfer.MemberRoleTransfer;

public class MemberRoleServiceImpl implements MemberRoleService {

	private MemberRoleDao memberroleDao;

	public MemberRoleServiceImpl(MemberRoleDao memberroleDao) {
		this.memberroleDao = memberroleDao;
	}

	@Override
	public MemberRoleTransfer retrieve(long id) {
		return toMemberRoleTransfer(memberroleDao.find(id));
	}

	@Override
	public void delete(long id) {
		memberroleDao.delete(id);
	}

	@Override
	public MemberRoleTransfer save(MemberRole memberrole) {
		return toMemberRoleTransfer(memberroleDao.save(memberrole));
	}

	@Override
	public MemberRoleTransfer update(long id, MemberRole memberrole) {
		return toMemberRoleTransfer(memberroleDao.save(memberrole));
	}

	@Override
	public Collection<MemberRoleTransfer> findAll() {
		List<MemberRoleTransfer> rets = new ArrayList<MemberRoleTransfer>();

		for (MemberRole memberrole : memberroleDao.findAll()) {
			rets.add(toMemberRoleTransfer(memberrole));
		}
		return rets;
	}

	private MemberRoleTransfer toMemberRoleTransfer(MemberRole memberrole) {
		MemberRoleTransfer ret = new MemberRoleTransfer();
		ret.setMember_id(memberrole.getMember_id());

		return ret;
	}

}