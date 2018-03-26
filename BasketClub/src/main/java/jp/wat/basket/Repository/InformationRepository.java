package jp.wat.basket.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jp.wat.basket.entity.Information;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRepository extends JpaRepository<Information, Integer> {
	
	/**
	 * expireDate をキーにインフォメーション情報を取得する
	*/
	@Query(value="select s from Information s where expireDate >= :now")	    // @Queryの標準の書き方（エンティティクラス名 エンティティクラスのカラム名を使用）
	public List<Information> findByExpireDate(Timestamp now);
	
//	@Query(value="select s from Information s where expireDate >= '2014-07-21 14:05:31'")	   // @Queryの標準の書き方（エンティティクラス名 エンティティクラスのカラム名を使用）
//	public List<Information> find();

}
