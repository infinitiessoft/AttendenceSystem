package dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import entity.Leavesetting;

public interface LeavesettingDao extends PagingAndSortingRepository<Leavesetting, Long>, JpaSpecificationExecutor<Leavesetting> {

}
