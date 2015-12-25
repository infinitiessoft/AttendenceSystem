package dao;

import entity.Member;

public class JpaMemberDao extends JpaDao<Member, Long> implements MemberDao {

	public JpaMemberDao() {
		super(Member.class);
	}
}
