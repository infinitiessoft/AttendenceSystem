package service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import transfer.AttendRecordReport;
import transfer.AttendRecordTransfer;
import transfer.Metadata;
import entity.AttendRecord;

/**
 * An interface that defines what a AttendRecord Services looks like in general
 *
 */
public interface AttendRecordService {

	public AttendRecordTransfer retrieve(long id);

	public void delete(long id, boolean force);

	public AttendRecordTransfer save(AttendRecordTransfer attendRecord);

	public Page<AttendRecordTransfer> findAll(Specification<AttendRecord> spec,
			Pageable pageable);

	public AttendRecordTransfer update(long id,
			AttendRecordTransfer attendRecord, boolean force);

	public AttendRecordTransfer update(Specification<AttendRecord> spec,
			AttendRecordTransfer attendRecord);

	public List<AttendRecordReport> findAll(Specification<AttendRecord> spec);

	public AttendRecordTransfer reject(long id);

	public AttendRecordTransfer permit(long id);

	public AttendRecordTransfer retrieve(Specification<AttendRecord> spec);

	public void delete(Specification<AttendRecord> spec);

	// public long count(Specification<AttendRecord> spec);

	public Metadata retrieveMetadataByEmployeeId(long id);

}