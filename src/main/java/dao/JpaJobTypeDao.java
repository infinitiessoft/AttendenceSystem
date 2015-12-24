package dao;

import entity.JobType;

public class JpaJobTypeDao extends JpaDao<JobType, Long>implements JobTypeDao {

	public JpaJobTypeDao() {
		super(JobType.class);
	}
}
