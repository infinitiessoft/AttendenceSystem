package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import transfer.MemberTransfer;
import dao.MemberDao;
import entity.Member;

public class MemberServiceImpl implements MemberService {

	private MemberDao memberDao;

	public MemberServiceImpl(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	public MemberTransfer retrieve(long id) {
		return toMemberTransfer(memberDao.find(id));
	}

	@Override
	public void delete(long id) {
		memberDao.delete(id);
	}

	@Override
	public MemberTransfer save(Member member) {
		return toMemberTransfer(memberDao.save(member));
	}

	@Override
	public MemberTransfer update(long id, Member member) {
		return toMemberTransfer(memberDao.save(member));
	}

	@Override
	public Collection<MemberTransfer> findAll() {
		List<MemberTransfer> rets = new ArrayList<MemberTransfer>();

		for (Member member : memberDao.findAll()) {
			rets.add(toMemberTransfer(member));
		}
		return rets;
	}

	private MemberTransfer toMemberTransfer(Member member) {
		MemberTransfer ret = new MemberTransfer();
		ret.setId(member.getId());
		ret.setUsername(member.getUsername());

		return ret;
	}

}