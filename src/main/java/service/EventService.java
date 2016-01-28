package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import resources.specification.EventSpecification;
import transfer.EventTransfer;
import transfer.Metadata;

public interface EventService {

	public EventTransfer retrieve(long id);

	public void delete(long id);

	public EventTransfer save(EventTransfer event);

	public Page<EventTransfer> findAll(EventSpecification spec,
			Pageable pageable);

	public EventTransfer update(long id, EventTransfer event);

	Metadata retrieveMetadataByEmployeeId(long id);
}
