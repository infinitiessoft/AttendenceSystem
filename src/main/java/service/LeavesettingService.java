package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import transfer.LeavesettingTransfer;

public interface LeavesettingService {

	public LeavesettingTransfer retrieve(long id);

	public void delete(long id);

	public LeavesettingTransfer save(LeavesettingTransfer leavesetting);

	public Page<LeavesettingTransfer> findAll(Pageable pageable);

	public LeavesettingTransfer update(long id, LeavesettingTransfer leavesetting);

}