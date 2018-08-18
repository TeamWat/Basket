package jp.wat.basket.Repository;

import java.util.List;

import jp.wat.basket.entity.Member;
import jp.wat.basket.entity.UserMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMemberRepository extends JpaRepository<UserMember, Integer>{

	/**
	 * ユーザーに紐づくメンバー情報を取得する
	*/
	@Query(value="select m from UserMember m where userId = :userId order by nendo asc")	    // @Queryの標準の書き方（エンティティクラス名 エンティティクラスのカラム名を使用）
	public List<UserMember> findById(String userId);
	
	/**
	 * ユーザーに紐づくメンバー情報を取得する（ユーザーID、年度指定）
	*/
	@Query(value="select memberId from UserMember m where userId = :userId and nendo = :nendo order by nendo asc")	    // @Queryの標準の書き方（エンティティクラス名 エンティティクラスのカラム名を使用）
	public List<Integer> findByIdNendo(String userId, Integer nendo);
	
	/**
	 * 対象ユーザー、対象年度、対象メンバーのデータを削除する
	*/
	@Modifying
	@Query(value="delete from UserMember m where m.userId = :userId and m.nendo = :nendo and m.memberId = :memberId")	    // @Queryの標準の書き方（エンティティクラス名 エンティティクラスのカラム名を使用）
	public void deleteUserMember(String userId, Integer nendo, Integer memberId);

	/**
	 * ユーザーに紐づくメンバー情報を取得する（メンバー情報をJoinして返却する）
	 * @param userId
	 * @return
	 */
//	@Query(value="select m from UserMember um "
//			+ "inner join member m on um.memberId = m.memberId and m.deleteFlg = 0 "
//			+ "where um.userId = :userId "
//			+ "order by m.nendo asc, m.no asc")
	@Query(value="select um from UserMember um "
			+ "join fetch um.member where um.userId = :userId and um.member.deleteFlg = 0 order by um.nendo asc, um.member.no asc")
	public List<UserMember> findByUserId(String userId);
	
}
