package dao.jpa;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import dao.EmployeeDao;
import dao.UserDetailDao;
import entity.Employee;

public class JpaUserDetailDao implements UserDetailDao {

	private EmployeeDao employeeDao;

	public JpaUserDetailDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Employee user = employeeDao.findByUsername(username);
		if (null == user) {
			throw new UsernameNotFoundException("The employee with username "
					+ username + " was not found");
		}
		return user;
	}

}
