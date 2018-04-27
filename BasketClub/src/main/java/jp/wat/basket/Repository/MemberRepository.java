package jp.wat.basket.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jp.wat.basket.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
	
	/**
	 * メンバー情報を全件取得する
	 * ソート順　背番号（No）の昇順
	*/
	@Query(value="select m from Member m where deleteFlg = '0' order by no")	    // @Queryの標準の書き方（エンティティクラス名 エンティティクラスのカラム名を使用）
	public List<Member> findAllByOrderByIdAsc();

	/**
	 * メンバーIDをキーにメンバー情報を取得する
	*/
	@Query(value="select m from Member m where memberId = :memberId")	
	public Member findById(Integer memberId);
	
	/**
	 * 背番号(No)をキーにメンバー情報を取得する
	*/
	@Query(value="select m from Member m where no = :no")	
	public Member findByNo(Integer no);
	
}
