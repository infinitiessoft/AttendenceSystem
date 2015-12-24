package dao;

public class JpaLeavesettingDao extends JpaDao<Leavesetting, Long>implements LeavesettingDao {

	public JpaLeavesettingDao() {
		super(Leavesetting.class);
	}
}
