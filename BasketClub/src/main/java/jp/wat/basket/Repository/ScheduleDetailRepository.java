package jp.wat.basket.Repository;

import java.util.List;

import jp.wat.basket.entity.ScheduleDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleDetailRepository extends JpaRepository<ScheduleDetail, Integer> {
	
	/**
	 * スケジュール情報を全件取得する
	*/
	@Query(value="select s from ScheduleDetail s")	    // @Queryの標準の書き方（エンティティクラス名 エンティティクラスのカラム名を使用）
	public List<ScheduleDetail> findAllData();

	/**
	 * 年度と月をキーにスケジュール情報を取得する
	*/
	@Query(value="select s from ScheduleDetail s where nendo = :nendo and month = :month")	
	public List<ScheduleDetail> findByNengetsu(@Param("nendo") Integer nendo, @Param("month") Integer month);

	/**
	 * 最後にInsertされたAUTO_INCREMENTの値を取得する
	*/
	@Query(value="select LAST_INSERT_ID()", nativeQuery = true)
	public Integer getLastSeq();
	
}
