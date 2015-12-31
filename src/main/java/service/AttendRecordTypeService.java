package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import entity.AttendRecordType;
import transfer.AttendRecordTypeTransfer;

public interface AttendRecordTypeService {

	public AttendRecordTypeTransfer retrieve(long id);

	public void delete(long id);

	public AttendRecordTypeTransfer save(
			AttendRecordTypeTransfer attendRecordType);

	public Page<AttendRecordTypeTransfer> findAll(
			Specification<AttendRecordType> spec, Pageable pageable);

	public AttendRecordTypeTransfer update(long id,
			AttendRecordTypeTransfer attendRecordType);

}