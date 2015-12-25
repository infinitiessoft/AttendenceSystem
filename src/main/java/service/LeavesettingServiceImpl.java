package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.LeavesettingDao;
import entity.Leavesetting;
import transfer.LeavesettingTransfer;

public class LeavesettingServiceImpl implements LeavesettingService {

	private LeavesettingDao leavesettingDao;

	public LeavesettingServiceImpl(LeavesettingDao leavesettingDao) {
		this.leavesettingDao = leavesettingDao;
	}

	@Override
	public LeavesettingTransfer retrieve(long id) {
		return toLeavesettingTransfer(leavesettingDao.find(id));
	}

	@Override
	public void delete(long id) {
		leavesettingDao.delete(id);
	}

	@Override
	public LeavesettingTransfer save(Leavesetting leavesetting) {
		return toLeavesettingTransfer(leavesettingDao.save(leavesetting));
	}

	@Override
	public LeavesettingTransfer update(long id, Leavesetting leavesetting) {
		return toLeavesettingTransfer(leavesettingDao.save(leavesetting));
	}

	@Override
	public Collection<LeavesettingTransfer> findAll() {
		List<LeavesettingTransfer> rets = new ArrayList<LeavesettingTransfer>();

		for (Leavesetting leavesetting : leavesettingDao.findAll()) {
			rets.add(toLeavesettingTransfer(leavesetting));
		}
		return rets;
	}

	private LeavesettingTransfer toLeavesettingTransfer(Leavesetting leavesetting) {
		LeavesettingTransfer ret = new LeavesettingTransfer();
		ret.setId(leavesetting.getId());
		ret.setName(leavesetting.getName());

		return ret;
	}

	@Override
	public LeavesettingTransfer findByName(String name) {
		return toLeavesettingTransfer(leavesettingDao.findByName(name));
	}
}