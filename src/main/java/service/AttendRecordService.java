package service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import resources.specification.AttendRecordSpecification;
import transfer.AttendRecordReport;
import transfer.AttendRecordTransfer;

/**
 * An interface that defines what a AttendRecord Services looks like in general
 *
 */
public interface AttendRecordService {

	public AttendRecordTransfer retrieve(long id);

	public void delete(long id);

	public AttendRecordTransfer save(AttendRecordTransfer attendRecord);

	public Page<AttendRecordTransfer> findAll(AttendRecordSpecification spec,
			Pageable pageable);

	public AttendRecordTransfer update(long id,
			AttendRecordTransfer attendRecord);

	public List<AttendRecordReport> findAll(AttendRecordSpecification spec);

}