package jp.wat.basket.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "user_nendo")
public class UserNendo {
	
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    private String userId;
    private Integer nendo;
	private String registUser;		// 登録ユーザー
	private Timestamp registTime;	// 登録日時
	private String updateUser;		// 更新ユーザー
	private Timestamp updateTime;	// 更新日時
	
	// JPA requirement
    protected UserNendo() {}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getNendo() {
		return nendo;
	}
	public void setNendo(Integer nendo) {
		this.nendo = nendo;
	}
	public String getRegistUser() {
		return registUser;
	}
	public void setRegistUser(String registUser) {
		this.registUser = registUser;
	}
	public Timestamp getRegistTime() {
		return registTime;
	}
	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
}


//@Entity
//@Table(name = "user_nendo")
//public class UserNendo {
//	
//	@EmbeddedId
//	private UserNendoPK userNendoPK;
//
//	private String registUser;		// 登録ユーザー
//	private Timestamp registTime;	// 登録日時
//	private String updateUser;		// 更新ユーザー
//	private Timestamp updateTime;	// 更新日時
//	
//	public UserNendo(){
//	}
//	
//	@Id
//	@Column(name="user_id")
//	public String getUserId() {
//		return userNendoPK.getUserId();
//	}
//	public void setUserId(String userId) {
//		userNendoPK.setUserId(userId);
//	}
//
//	@Id
//	@Column(name="nendo")
//	public Integer getNendo() {
//		return userNendoPK.getNendo();
//	}
//	public void setNendo(Integer nendo) {
//		userNendoPK.setNendo(nendo);
//	}
//	public String getRegistUser() {
//		return registUser;
//	}
//	public void setRegistUser(String registUser) {
//		this.registUser = registUser;
//	}
//	public Timestamp getRegistTime() {
//		return registTime;
//	}
//	public void setRegistTime(Timestamp registTime) {
//		this.registTime = registTime;
//	}
//	public String getUpdateUser() {
//		return updateUser;
//	}
//	public void setUpdateUser(String updateUser) {
//		this.updateUser = updateUser;
//	}
//	public Timestamp getUpdateTime() {
//		return updateTime;
//	}
//	public void setUpdateTime(Timestamp updateTime) {
//		this.updateTime = updateTime;
//	}
//	
//	@Embeddable
//	public static class UserNendoPK implements Serializable{
//		
//		@Embedded
//	    private String userId;
//	    private Integer nendo;
//
//	    public UserNendoPK(){	
//	    }
//	    
//		public String getUserId() {
//			return userId;
//		}
//
//		public void setUserId(String userId) {
//			this.userId = userId;
//		}
//
//		public Integer getNendo() {
//			return nendo;
//		}
//
//		public void setNendo(Integer nendo) {
//			this.nendo = nendo;
//		}
//
//		@Override
//		public int hashCode() {
//			final int prime = 31;
//			int result = 1;
//			result = prime * result + ((nendo == null) ? 0 : nendo.hashCode());
//			result = prime * result
//					+ ((userId == null) ? 0 : userId.hashCode());
//			return result;
//		}
//
//		@Override
//		public boolean equals(Object obj) {
//			if (this == obj)
//				return true;
//			if (obj == null)
//				return false;
//			if (getClass() != obj.getClass())
//				return false;
//			UserNendoPK other = (UserNendoPK) obj;
//			if (nendo == null) {
//				if (other.nendo != null)
//					return false;
//			} else if (!nendo.equals(other.nendo))
//				return false;
//			if (userId == null) {
//				if (other.userId != null)
//					return false;
//			} else if (!userId.equals(other.userId))
//				return false;
//			return true;
//		}
//
//		
//	}
//}
