package dao.jpa;

import dao.JobDao;
import entity.Job;

public class JpaJobDao extends JpaDao<Job, Long> implements JobDao {

	public JpaJobDao() {
		super(Job.class);
	}
}
