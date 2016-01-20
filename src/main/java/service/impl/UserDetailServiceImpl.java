package service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import security.EmployeeUserDetail;
import dao.EmployeeDao;
import entity.Employee;

public class UserDetailServiceImpl implements UserDetailsService {

	private EmployeeDao employeeDao;

	public UserDetailServiceImpl(EmployeeDao employeeDao) {
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
		return new EmployeeUserDetail(user);
	}

}
