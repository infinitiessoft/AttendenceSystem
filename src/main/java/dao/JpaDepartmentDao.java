package dao;

public class JpaDepartmentDao extends JpaDao<Department, Long>implements DepartmentDao {

	public JpaDepartmentDao() {
		super(Department.class);
	}
}
