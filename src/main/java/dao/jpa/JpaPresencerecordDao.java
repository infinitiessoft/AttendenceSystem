package dao.jpa;

import dao.PresencerecordDao;
import entity.Presencerecord;

public class JpaPresencerecordDao extends JpaDao<Presencerecord, Long>
		implements PresencerecordDao {

	public JpaPresencerecordDao() {
		super(Presencerecord.class);
	}
}
