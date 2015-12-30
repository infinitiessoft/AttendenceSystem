//package service;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import transfer.AttendRecordTransfer;
//import dao.AttendRecordDao;
//import entity.AttendRecord;
//
//public class AttendRecordServiceImpl implements AttendRecordService {
//
//	@Autowired
//	private AttendRecordDao attendrecordDao;
//
//	public AttendRecordServiceImpl(AttendRecordDao attendrecordDao) {
//		this.attendrecordDao = attendrecordDao;
//	}
//
//	@Override
//	public AttendRecordTransfer retrieve(long id) {
//		return toAttendRecordTransfer(attendrecordDao.findOne(id));
//	}
//
//	@Override
//	public void delete(long id) {
//		attendrecordDao.delete(id);
//	}
//
//	@Override
//	public AttendRecordTransfer save(AttendRecord attendrecord) {
//		return toAttendRecordTransfer(attendrecordDao.save(attendrecord));
//	}
//
//	@Override
//	public AttendRecordTransfer update(long id, AttendRecord attendrecord) {
//		return toAttendRecordTransfer(attendrecordDao.save(attendrecord));
//	}
//
//	@Override
//	public Collection<AttendRecordTransfer> findAll() {
//		List<AttendRecordTransfer> rets = new ArrayList<AttendRecordTransfer>();
//		for (AttendRecord attendrecord : attendrecordDao.findAll()) {
//			rets.add(toAttendRecordTransfer(attendrecord));
//		}
//		return rets;
//	}
//
//	private AttendRecordTransfer toAttendRecordTransfer(
//			AttendRecord attendrecord) {
//		AttendRecordTransfer ret = new AttendRecordTransfer();
//		ret.setId(attendrecord.getId());
//		ret.setModifypermit(attendrecord.getModifypermit());
//		ret.setBook_date(attendrecord.getBook_date());
//		ret.setDateinterval(attendrecord.getDateinterval());
//		ret.setDuration(attendrecord.getDuration());
//		ret.setEmployee_id(attendrecord.getEmployee_id());
//		ret.setEnd_date(attendrecord.getEnd_date());
//		ret.setModify_end_date(attendrecord.getModify_end_date());
//		ret.setModify_date(attendrecord.getModify_date());
//		ret.setModify_start_date(attendrecord.getModify_start_date());
//		ret.setReason(attendrecord.getReason());
//		ret.setPeriod(attendrecord.getPeriod());
//		ret.setPermic_person_id(attendrecord.getPermic_person_id());
//		ret.setPermic_person_id2(attendrecord.getPermic_person_id2());
//		ret.setPermit(attendrecord.getPermit());
//		ret.setPermit2(attendrecord.getPermit2());
//		ret.setStart_date(attendrecord.getStart_date());
//		ret.setEnd_date(attendrecord.getEnd_date());
//		ret.setEndperiod(attendrecord.getEndperiod());
//		ret.setModifyendperiod(attendrecord.getModifyendperiod());
//		ret.setModifypermit2(attendrecord.getModifypermit2());
//		ret.setModifyreason(attendrecord.getModifyreason());
//		ret.setModifytype(attendrecord.getModifytype());
//		ret.setStartperiod(attendrecord.getStartperiod());
//		ret.setModifystartperiod(attendrecord.getModifystartperiod());
//		ret.setModifydateinterval(attendrecord.getModifydateinterval());
//
//		return ret;
//
//	}
//
//}