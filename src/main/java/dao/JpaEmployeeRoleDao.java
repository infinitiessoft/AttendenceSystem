package dao;

import entity.EmployeeRole;

public class JpaEmployeeRoleDao extends JpaDao<EmployeeRole, Long>implements EmployeeRoleDao {

	public JpaEmployeeRoleDao() {
		super(EmployeeRole.class);
	}
}
