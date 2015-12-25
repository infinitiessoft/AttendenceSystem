package service;

import java.util.Collection;

import transfer.LeavesettingTransfer;
import entity.Leavesetting;

public interface LeavesettingService {

	public LeavesettingTransfer retrieve(long id);

	public void delete(long id);

	public LeavesettingTransfer save(Leavesetting leavesetting);

	public Collection<LeavesettingTransfer> findAll();

	public LeavesettingTransfer update(long id, Leavesetting leavesetting);

}