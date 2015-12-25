package service;

import java.util.Collection;

import transfer.PresencerecordTransfer;
import entity.Presencerecord;

public interface PresencerecordService {

	public PresencerecordTransfer retrieve(long id);

	public void delete(long id);

	public PresencerecordTransfer save(Presencerecord presencerecord);

	public Collection<PresencerecordTransfer> findAll();

	public PresencerecordTransfer update(long id, Presencerecord presencerecord);

}