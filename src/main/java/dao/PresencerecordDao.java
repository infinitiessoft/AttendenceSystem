package dao;

import entity.Presencerecord;

public interface PresencerecordDao extends Dao<Presencerecord, Long> {

	Presencerecord findByName(String name);

}
