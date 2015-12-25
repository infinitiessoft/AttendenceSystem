package service;

import java.util.Collection;

import entity.Member;
import transfer.MemberTransfer;

public interface MemberService {

	public MemberTransfer retrieve(long id);

	public MemberTransfer findByUsername(String username);

	public void delete(long id);

	public MemberTransfer save(Member member);

	public Collection<MemberTransfer> findAll();

	public MemberTransfer update(long id, Member member);

}