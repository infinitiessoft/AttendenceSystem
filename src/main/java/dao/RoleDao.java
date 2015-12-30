package dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import entity.Role;

public interface RoleDao extends PagingAndSortingRepository<Role, Long> {

}
