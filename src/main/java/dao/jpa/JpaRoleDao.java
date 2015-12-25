package dao.jpa;

import dao.RoleDao;
import entity.Role;

public class JpaRoleDao extends JpaDao<Role, Long> implements RoleDao {

	public JpaRoleDao() {
		super(Role.class);
	}
}
