package service;

import java.util.Collection;

import entity.Leavesetting;
import transfer.LeavesettingTransfer;

public interface LeavesettingService {

	public LeavesettingTransfer retrieve(long id);

	public LeavesettingTransfer findByName(String name);

	public void delete(long id);

	public LeavesettingTransfer save(Leavesetting leavesetting);

	public Collection<LeavesettingTransfer> findAll();

	public LeavesettingTransfer update(long id, Leavesetting leavesetting);

}