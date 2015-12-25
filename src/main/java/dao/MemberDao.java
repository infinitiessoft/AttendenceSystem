package dao;

import entity.Member;

public interface MemberDao extends Dao<Member, Long> {

	Member findByName(String username);

}
