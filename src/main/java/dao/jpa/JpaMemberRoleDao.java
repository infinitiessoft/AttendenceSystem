package dao.jpa;

import dao.MemberRoleDao;
import entity.MemberRole;

public class JpaMemberRoleDao extends JpaDao<MemberRole, Long>implements MemberRoleDao {

	public JpaMemberRoleDao() {
		super(MemberRole.class);
	}
}
