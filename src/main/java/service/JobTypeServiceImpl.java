package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.JobTypeDao;
import entity.JobType;
import transfer.JobTypeTransfer;

public class JobTypeServiceImpl implements JobTypeService {

	private JobTypeDao jobtypeDao;

	public JobTypeServiceImpl(JobTypeDao jobtypeDao) {
		this.jobtypeDao = jobtypeDao;
	}

	@Override
	public JobTypeTransfer retrieve(long id) {
		return toJobTypeTransfer(jobtypeDao.find(id));
	}

	@Override
	public void delete(long id) {
		jobtypeDao.delete(id);
	}

	@Override
	public JobTypeTransfer save(JobType jobtype) {
		return toJobTypeTransfer(jobtypeDao.save(jobtype));
	}

	@Override
	public JobTypeTransfer update(long id, JobType jobtype) {
		return toJobTypeTransfer(jobtypeDao.save(jobtype));
	}

	@Override
	public Collection<JobTypeTransfer> findAll() {
		List<JobTypeTransfer> rets = new ArrayList<JobTypeTransfer>();

		for (JobType jobtype : jobtypeDao.findAll()) {
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

	@Override
	public JobTypeTransfer findByName(String name) {
		return toJobTypeTransfer(jobtypeDao.findByName(name));
	}
}