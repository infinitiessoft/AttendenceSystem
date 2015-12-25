
package service;

import java.util.Collection;

import entity.Job;
import transfer.JobTransfer;

public interface JobService {

	public JobTransfer retrieve(long id);

	public JobTransfer findByName(String name);

	public void delete(long id);

	public JobTransfer save(Job job);

	public Collection<JobTransfer> findAll();

	public JobTransfer update(long id, Job job);

}