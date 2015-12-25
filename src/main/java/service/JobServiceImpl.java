package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import transfer.JobTransfer;
import dao.JobDao;
import entity.Job;

public class JobServiceImpl implements JobService {

	private JobDao jobDao;

	public JobServiceImpl(JobDao jobDao) {
		this.jobDao = jobDao;
	}

	@Override
	public JobTransfer retrieve(long id) {
		return toJobTransfer(jobDao.find(id));
	}

	@Override
	public void delete(long id) {
		jobDao.delete(id);
	}

	@Override
	public JobTransfer save(Job job) {
		return toJobTransfer(jobDao.save(job));
	}

	@Override
	public JobTransfer update(long id, Job job) {
		return toJobTransfer(jobDao.save(job));
	}

	@Override
	public Collection<JobTransfer> findAll() {
		List<JobTransfer> rets = new ArrayList<JobTransfer>();

		for (Job job : jobDao.findAll()) {
			rets.add(toJobTransfer(job));
		}
		return rets;
	}

	private JobTransfer toJobTransfer(Job job) {
		JobTransfer ret = new JobTransfer();
		ret.setId(job.getId());
		ret.setName(job.getName());
		// ret.setRoles(this.createRoleMap(job));
		return ret;
	}

}