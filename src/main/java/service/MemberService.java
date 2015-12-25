package service;

import java.util.Collection;

import transfer.MemberTransfer;
import entity.Member;

public interface MemberService {

	public MemberTransfer retrieve(long id);

	public void delete(long id);

	public MemberTransfer save(Member member);

	public Collection<MemberTransfer> findAll();

	public MemberTransfer update(long id, Member member);

}