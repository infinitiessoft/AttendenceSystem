package dao;

import org.springframework.security.core.userdetails.UserDetailsService;

import entity.Job;

public interface JobDao extends Dao<Job, Long>, UserDetailsService {

	Job findByName(String name);

}
