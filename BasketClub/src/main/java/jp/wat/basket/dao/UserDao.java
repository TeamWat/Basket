package jp.wat.basket.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import jp.wat.basket.entity.UserMaster;

/**
 * @author wtakahashi
 *
 */
public class UserDao extends JdbcDaoSupport{
	 
	  @Autowired
	  protected JdbcTemplate jdbcTemplate;

	  //初期化
	  protected void initDao(){
		  this.jdbcTemplate = new JdbcTemplate(getDataSource());
	  }
	  	  
	  public UserMaster getUserByKey(int id) throws SQLException {	  
		UserMaster user = new UserMaster();  
	  
		//ユーザーリスト
        List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM user_master");
        	for (Map<String, Object> map : list) {
        		System.out.println(map.get("user_id").toString() + "-" + map.get("user_name").toString());
        	} 	
	    return user;
	  }
	  
	  /**
	   * user_master から条件なしで全件を取得する
	   * 
	   * @param なし
	   * @return entity.UserMaster のリスト。データが0件の場合も空のリストを返却する
	   * @throws SQLException
	   */
	  public List<UserMaster> getUserList() throws SQLException {
	  
		  UserMaster userMaster = new UserMaster();
		  List<UserMaster> userList = new ArrayList<UserMaster>();  
		  List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT * FROM user_master");
		  
      	  for (Map<String, Object> map : list) {
      		System.out.println(map.get("user_id").toString() + "-" + map.get("user_name").toString());
      		
      		try{
      			userMaster = convertDbToObj(map);
      		}catch(Exception e){
      			e.printStackTrace();
      		}
      		userList.add(userMaster);
      	  }
		   
	   return userList;
	  }
	  
	  
	  /**
	   * user_master の データ(map型)を UserMasterクラスに変換して返却する
	   * 
	   * @param DBから取得した user_master データ の map
	   * @return entity.UserMaster のインスタンス
	   * @throws NullPointerException
	   */
	  private UserMaster convertDbToObj(Map map) throws NullPointerException{
		
		  UserMaster userMaster = new UserMaster();
	
		  //ID
		  userMaster.setUser_id((int)map.get("user_id"));		  
		  //ユーザー名前
		  userMaster.setUser_name((String)map.get("user_name"));
		  //メールアドレス
		  userMaster.setMail((String)map.get("mail"));
		  //権限
		  userMaster.setRole((String)map.get("role"));
		  
		  //共通項目
		  userMaster.setRegist_user((String)map.get("regist_user"));
		  DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss.SSS");
		  userMaster.setRegist_time(formatter.format(map.get("regist_time")));
		  userMaster.setUpdate_user((String)map.get("update_user"));	  
		  userMaster.setUpdate_time(formatter.format(map.get("update_time")));
		  userMaster.setDelete_flg((boolean)map.get("delete_flg"));
		  
		return userMaster;
	  }
	  
	   
	}
