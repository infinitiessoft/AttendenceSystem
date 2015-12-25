package dao;

import entity.Department;

public interface DepartmentDao extends Dao<Department, Long> {

	void findByName(String string);

}
