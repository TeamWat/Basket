package jp.wat.basket.Repository;

import java.util.List;

import jp.wat.basket.entity.LoginUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, Integer> {
	
	/**
	 * ユーザー情報を全件取得する
	*/
	@Query(value="select m from LoginUser m")	    // @Queryの標準の書き方（エンティティクラス名 エンティティクラスのカラム名を使用）
	public List<LoginUser> findAllUser();

	/**
	 * ユーザーIDをキーにユーザー情報を取得する
	 * DeleteFlg は　'0'（削除されていない）
	*/
	@Query(value="select m from LoginUser m where userId = :userId and deleteFlg = '0'")	
	public LoginUser findById(String userId);
	
}
