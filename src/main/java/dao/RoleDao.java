package dao;

import entity.Role;

public interface RoleDao extends Dao<Role, Long> {

	Role findByName(String name);

}
