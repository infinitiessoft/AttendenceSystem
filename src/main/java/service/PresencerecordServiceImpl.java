package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.PresencerecordDao;
import entity.Presencerecord;
import transfer.PresencerecordTransfer;

public class PresencerecordServiceImpl implements PresencerecordService {

	private PresencerecordDao presencerecordDao;

	public PresencerecordServiceImpl(PresencerecordDao presencerecordDao) {
		this.presencerecordDao = presencerecordDao;
	}

	@Override
	public PresencerecordTransfer retrieve(long id) {
		return toPresencerecordTransfer(presencerecordDao.find(id));
	}

	@Override
	public void delete(long id) {
		presencerecordDao.delete(id);
	}

	@Override
	public PresencerecordTransfer save(Presencerecord presencerecord) {
		return toPresencerecordTransfer(presencerecordDao.save(presencerecord));
	}

	@Override
	public PresencerecordTransfer update(long id, Presencerecord presencerecord) {
		return toPresencerecordTransfer(presencerecordDao.save(presencerecord));
	}

	@Override
	public Collection<PresencerecordTransfer> findAll() {
		List<PresencerecordTransfer> rets = new ArrayList<PresencerecordTransfer>();

		for (Presencerecord presencerecord : presencerecordDao.findAll()) {
			rets.add(toPresencerecordTransfer(presencerecord));
		}
		return rets;
	}

	private PresencerecordTransfer toPresencerecordTransfer(Presencerecord presencerecord) {
		PresencerecordTransfer ret = new PresencerecordTransfer();
		ret.setId(presencerecord.getId());
		ret.setName(presencerecord.getName());

		return ret;
	}

	@Override
	public PresencerecordTransfer findByName(String name) {
		return toPresencerecordTransfer(presencerecordDao.findByName(name));
	}
}