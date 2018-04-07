package jp.wat.basket.Repository;

import java.util.List;

import jp.wat.basket.entity.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
	
	/**
	 * スケジュール情報を全件取得する
	*/
	@Query(value="select s from Schedule s")	    // @Queryの標準の書き方（エンティティクラス名 エンティティクラスのカラム名を使用）
	public List<Schedule> findAllData();

	/**
	 * 年度と月をキーにスケジュール情報を取得する
	*/
	@Query(value="select s from Schedule s where nendo = :nendo and month = :month")	
	public List<Schedule> findByNengetsu(Integer nendo, Integer month);

	/**
	 * 最後にInsertされたAUTO_INCREMENTの値を取得する
	*/
	@Query(value="select LAST_INSERT_ID()", nativeQuery = true)
	public Integer getLastSeq();
	
}
