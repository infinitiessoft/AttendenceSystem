package dao.jpa;

import dao.JobTypeDao;
import entity.JobType;

public class JpaJobTypeDao extends JpaDao<JobType, Long> implements JobTypeDao {

	public JpaJobTypeDao() {
		super(JobType.class);
	}
}
