package jp.wat.basket.Repository;

import java.util.List;

import jp.wat.basket.entity.UserNendo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNendoRepository extends JpaRepository<UserNendo, Integer>{

	/**
	 * ユーザーに紐づく年度情報を取得する
	*/
	@Query(value="select nendo from UserNendo m where userId = :userId order by nendo asc")	    // @Queryの標準の書き方（エンティティクラス名 エンティティクラスのカラム名を使用）
	public List<Integer> findById(String userId);
	
	/**
	 * 対象ユーザー、対象年度のデータを削除する
	*/
	@Modifying
	@Query(value="delete from UserNendo m where m.userId = :userId and m.nendo = :nendo")	    // @Queryの標準の書き方（エンティティクラス名 エンティティクラスのカラム名を使用）
	public void deleteUserNendo(String userId, Integer nendo);
	
	/**
	 * ユーザーIDをキーにユーザー年度情報を一括削除する
	*/
	@Modifying
	@Query(value="delete from UserNendo m where m.userId = :userId")	    // @Queryの標準の書き方（エンティティクラス名 エンティティクラスのカラム名を使用）
	public void deleteUserNendo(String userId);
	
}
