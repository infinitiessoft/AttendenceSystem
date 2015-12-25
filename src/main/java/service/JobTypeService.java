package service;

import java.util.Collection;

import entity.JobType;
import transfer.JobTypeTransfer;

public interface JobTypeService {

	public JobTypeTransfer retrieve(long id);

	public JobTypeTransfer findByName(String name);

	public void delete(long id);

	public JobTypeTransfer save(JobType jobtype);

	public Collection<JobTypeTransfer> findAll();

	public JobTypeTransfer update(long id, JobType jobtype);

}