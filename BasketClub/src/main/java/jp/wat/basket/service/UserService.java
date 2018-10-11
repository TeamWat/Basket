package jp.wat.basket.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import jp.wat.basket.Repository.UserMemberRepository;
import jp.wat.basket.Repository.UserNendoRepository;
import jp.wat.basket.Repository.LoginUserRepository;
import jp.wat.basket.common.Enum.EnumRole;
import jp.wat.basket.entity.LoginUser;
import jp.wat.basket.entity.UserMember;
import jp.wat.basket.entity.UserNendo;
import jp.wat.basket.model.UserViewModel;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private LoginUserRepository repository;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	private UserNendoRepository userNendoRepository;
	
	@Autowired
	private UserMemberRepository userMemberRepository;
	
	public List<UserViewModel> getUserList(String role){
		
		// 有効なメンバー情報を取得する
		// 管理者ロールがない場合は管理者データの取得は不可とする
		List<LoginUser> userList = new ArrayList<LoginUser>();
		if (role.equals("ROLE_ADMIN")) {
			userList = repository.findAllUser();
		} else {
			userList = repository.findUserNotIncludeAdmin();
		}
		
		List<UserViewModel> userViewModelList = new ArrayList<UserViewModel>();
		ModelMapper modelMapper = new ModelMapper();
		UserViewModel userViewModel;
		EnumRole enumRole;

		for (LoginUser user : userList){
			userViewModel = modelMapper.map(user, UserViewModel.class);

			// ロール名を設定
			if (userViewModel.getRole().equals(EnumRole.ADMIN.getSName())){
				enumRole = EnumRole.ADMIN;
			} else if (userViewModel.getRole().equals(EnumRole.OFFICER.getSName())){
				enumRole = EnumRole.OFFICER;
			} else {
				enumRole = EnumRole.PUBLIC;
			}
			
			userViewModel.setRoleCode(enumRole.getCode());
			userViewModel.setRoleName(enumRole.getName());
			
			System.out.println("ROLE:" + enumRole.getName());
			
			// リストに追加
			userViewModelList.add(userViewModel);
		}
		
		return userViewModelList;
	}
	
	public LoginUser findById(String userId){
		return repository.findById(userId);
	}
	
	public void registUser(LoginUser user){
		repository.save(user);
	}
	
	/**
	 * ユーザーIDをキーにユーザー情報および関連するユーザー情報を削除する
	 * @param userId
	 * @return
	 * @throws RuntimeException 
	 */
	@Transactional
	public void deleteUser(String userId) throws RuntimeException{
		
		try {
			// 紐づくユーザー年度情報を削除する
			userNendoRepository.deleteUserNendo(userId);
			// 紐づくユーザーメンバー情報を削除する
			userMemberRepository.deleteUserMember(userId);
			// ユーザー
			repository.deleteUser(userId);

		} catch (RuntimeException runtimeException){
			// データベース更新はロールバックされる
			throw runtimeException;
			
		} catch (Exception exception) {
			// 検査例外(Exception及びそのサブクラスでRuntimeExceptionのサブクラスじゃないもの)が発生した場合はロールバックされずコミットされる。
			throw exception;
		}
	}
	
    private DateFormat SimpleDateFormat(String timeFormat) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Transactional
    public void save(LoginUser user) {
        repository.save(user);
    }

	/*
	 * ユーザーIDをキーにユーザーのアクセス可能な年度を取得する
	 */
	public List<Integer> getPermissionUserNendo(String userId){
		return userNendoRepository.findById(userId);
	}
		
	/*
	 * ユーザーのアクセス可能な年度を追加する
	 */
	@Transactional
	public void addUserNendo(UserNendo userNendo) {
		
		LoginUser loginUser = commonService.getLoginUser();
		
		userNendo.setRegistUser(loginUser.getUserId());
		userNendo.setRegistTime(new Timestamp(System.currentTimeMillis()));
		userNendo.setUpdateUser(loginUser.getUserId());
		userNendo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		
		userNendoRepository.save(userNendo);
		
	}
	

	/**
	 * ユーザーのアクセス可能な年度を削除する
	 * @param userNendo
	 */
	@Transactional
	public void removeUserNendo(UserNendo userNendo) {
		userNendoRepository.deleteUserNendo(userNendo.getUserId(), userNendo.getNendo());
	}
	
	/**
	 * ユーザーのアクセス可能な年度を削除する
	 * @param userId
	 */
	@Transactional
	public void removeUserNendo(String userId) {
		userNendoRepository.deleteUserNendo(userId);
	}

	/*
	 * ユーザーID、年度をキーに ユーザーが閲覧可能なメンバーを所得する
	 */
	public List<Integer> getUserMember(String userId, Integer nendo) {
		return userMemberRepository.findByIdNendo(userId , nendo);
	}
	
	/*
	 * ユーザーIDをキーに ユーザーが閲覧可能なメンバーを所得する
	 */
	public List<UserMember> getUserMember(String userId) {
		return userMemberRepository.findByUserId(userId);
	}
	
	/*
	 * ユーザーが閲覧可能なメンバーを追加する
	 */
	public void addUserMember(UserMember userMember) {
		
		LoginUser loginUser = commonService.getLoginUser();
		
		userMember.setRegistUser(loginUser.getUserId());
		userMember.setRegistTime(new Timestamp(System.currentTimeMillis()));
		userMember.setUpdateUser(loginUser.getUserId());
		userMember.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		
		userMemberRepository.save(userMember);
		
	}
	
	/**
	 * ユーザーのアクセス可能な年度を削除する
	 * @param userMember
	 */
	@Transactional
	public void removeUserMember(UserMember userMember) {
		userMemberRepository.deleteUserMember(userMember.getUserId(), userMember.getNendo(), userMember.getMemberId());
	}
	
	/**
	 * ユーザーIDをキーにuserMember情報を一括削除する
	 * @param userId
	 */
	@Transactional
	public void removeUserMember(String userId) {
		userMemberRepository.deleteUserMember(userId);
	}

}
