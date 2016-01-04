package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import transfer.LeavesettingTransfer;
import dao.LeavesettingDao;
import entity.Leavesetting;
import exceptions.LeavesettingNotFoundException;

public class LeavesettingServiceImpl implements LeavesettingService {

	private LeavesettingDao leavesettingDao;

	public LeavesettingServiceImpl(LeavesettingDao leavesettingDao) {
		this.leavesettingDao = leavesettingDao;
	}

	@Override
	public LeavesettingTransfer retrieve(long id) {
		Leavesetting leavesetting = leavesettingDao.findOne(id);
		if (leavesetting == null) {
			throw new LeavesettingNotFoundException(id);
		}
		return toLeavesettingTransfer(leavesetting);
	}

	@Override
	public void delete(long id) {
		try {
			leavesettingDao.delete(id);
		} catch (NullPointerException e) {
			throw new LeavesettingNotFoundException(id);
		}
	}

	@Override
	public LeavesettingTransfer save(LeavesettingTransfer leavesetting) {
		leavesetting.setId(null);
		Leavesetting newEntry = new Leavesetting();
		setUpLeavesetting(leavesetting, newEntry);
		return toLeavesettingTransfer(leavesettingDao.save(newEntry));
	}
	
	private void setUpLeavesetting(LeavesettingTransfer transfer, Leavesetting newLeavesetting) {
		if (transfer.isNameSet()) {
			newLeavesetting.setName(transfer.getName());
		}
		if (transfer.isPersonalSet()) {
			newLeavesetting.setPersonal(transfer.getPersonal());
		}
		if (transfer.isPersonalUsedSet()) {
			newLeavesetting.setPersonalUsed(transfer.getPersonalUsed());
		}
		if (transfer.isSickSet()) {
			newLeavesetting.setSick(transfer.getSick());
		}
		if (transfer.isSickUsedSet()) {
			newLeavesetting.setSickUsed(transfer.getSickUsed());
		}
		if (transfer.isAnnualSet()) {
			newLeavesetting.setAnnual(transfer.getAnnual());
		}
		if (transfer.isAnnualUsedSet()) {
			newLeavesetting.setAnnualUsed(transfer.getAnnualUsed());
		}
	}

	@Override
	public LeavesettingTransfer update(long id, LeavesettingTransfer updated) {
		Leavesetting leavesetting = leavesettingDao.findOne(id);
		if (leavesetting == null) {
			throw new LeavesettingNotFoundException(id);
		}
		setUpLeavesetting(updated, leavesetting);
		return toLeavesettingTransfer(leavesettingDao.save(leavesetting));
	}

	@Override
	public Page<LeavesettingTransfer> findAll(Pageable pageable) {
		List<LeavesettingTransfer> transfers = new ArrayList<LeavesettingTransfer>();
		Page<Leavesetting> leavesettings = leavesettingDao.findAll(pageable);
		for (Leavesetting leavesetting : leavesettings) {
			transfers.add(toLeavesettingTransfer(leavesetting));
		}
		Page<LeavesettingTransfer> rets = new PageImpl<LeavesettingTransfer>(transfers, pageable, leavesettings.getTotalElements());
		return rets;
	}

	private LeavesettingTransfer toLeavesettingTransfer(
			Leavesetting leavesetting) {
		LeavesettingTransfer ret = new LeavesettingTransfer();
		ret.setId(leavesetting.getId());
		ret.setName(leavesetting.getName());
		ret.setPersonal(leavesetting.getPersonal());
		ret.setPersonalUsed(leavesetting.getPersonalUsed());
		ret.setSick(leavesetting.getSick());
		ret.setSickUsed(leavesetting.getSickUsed());
		ret.setAnnual(leavesetting.getAnnual());
		ret.setAnnualUsed(leavesetting.getAnnualUsed());
		return ret;
	}

}