package dao;

import entity.Leavesetting;

public interface LeavesettingDao extends Dao<Leavesetting, Long> {

	Leavesetting findByName(String name);

}
