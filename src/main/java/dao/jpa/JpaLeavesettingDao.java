package dao.jpa;

import dao.LeavesettingDao;
import entity.Leavesetting;

public class JpaLeavesettingDao extends JpaDao<Leavesetting, Long> implements
		LeavesettingDao {

	public JpaLeavesettingDao() {
		super(Leavesetting.class);
	}
}
