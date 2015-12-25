package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import transfer.JobTypeTransfer;
import dao.JobTypeDao;
import entity.JobType;
import exceptions.JobTypeNotFoundException;

public class JobTypeServiceImpl implements JobTypeService {

	private JobTypeDao jobTypeDao;

	public JobTypeServiceImpl(JobTypeDao jobTypeDao) {
		this.jobTypeDao = jobTypeDao;
	}

	@Override
	public JobTypeTransfer retrieve(long id) {
		JobType jobType = jobTypeDao.find(id);
		if (jobType == null) {
			throw new JobTypeNotFoundException(id);
		}
		return toJobTypeTransfer(jobType);
	}

	@Override
	public void delete(long id) {
		try {
			jobTypeDao.delete(id);
		} catch (NullPointerException e) {
			throw new JobTypeNotFoundException(id);
		}
	}

	@Override
	public JobTypeTransfer save(JobType jobType) {
		jobType.setId(null);
		return toJobTypeTransfer(jobTypeDao.save(jobType));
	}

	@Override
	public JobTypeTransfer update(long id, JobType updated) {
		JobType jobType = jobTypeDao.find(id);
		if (jobType == null) {
			throw new JobTypeNotFoundException(id);
		}
		updated.setId(jobType.getId());
		return toJobTypeTransfer(jobTypeDao.save(updated));
	}

	@Override
	public Collection<JobTypeTransfer> findAll() {
		List<JobTypeTransfer> rets = new ArrayList<JobTypeTransfer>();

		for (JobType jobtype : jobTypeDao.findAll()) {
			rets.add(toJobTypeTransfer(jobtype));
		}
		return rets;
	}

	private JobTypeTransfer toJobTypeTransfer(JobType jobtype) {
		JobTypeTransfer ret = new JobTypeTransfer();
		ret.setId(jobtype.getId());
		ret.setName(jobtype.getName());
		// ret.setRoles(this.createRoleMap(jobtype));
		return ret;
	}

}