package service;

import java.util.Collection;

import entity.Presencerecord;
import transfer.PresencerecordTransfer;

public interface PresencerecordService {

	public PresencerecordTransfer retrieve(long id);

	public PresencerecordTransfer findByName(String name);

	public void delete(long id);

	public PresencerecordTransfer save(Presencerecord presencerecord);

	public Collection<PresencerecordTransfer> findAll();

	public PresencerecordTransfer update(long id, Presencerecord presencerecord);

}