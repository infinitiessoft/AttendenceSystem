package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import entity.Leavesetting;
import resources.specification.LeavesettingSpecification;
import transfer.LeavesettingTransfer;

public interface LeavesettingService {

	public LeavesettingTransfer retrieve(long id);

	public void delete(long id);

	public LeavesettingTransfer save(LeavesettingTransfer leavesetting);

	public Page<LeavesettingTransfer> findAll(LeavesettingSpecification spec, Pageable pageable);

	public LeavesettingTransfer update(long id, LeavesettingTransfer leavesetting);
	
	public LeavesettingTransfer findByTypeIdAndYear(long typeId, long year);

}