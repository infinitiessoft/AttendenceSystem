package dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import entity.Event;

public interface EventDao extends
		PagingAndSortingRepository<Event, Long>,
		JpaSpecificationExecutor<Event> {

}
