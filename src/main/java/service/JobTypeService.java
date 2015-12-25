package service;

import java.util.Collection;

import transfer.JobTypeTransfer;
import entity.JobType;

public interface JobTypeService {

	public JobTypeTransfer retrieve(long id);

	public void delete(long id);

	public JobTypeTransfer save(JobType jobtype);

	public Collection<JobTypeTransfer> findAll();

	public JobTypeTransfer update(long id, JobType jobtype);

}