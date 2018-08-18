package jp.wat.basket.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import jp.wat.basket.Repository.MemberRepository;
import jp.wat.basket.Repository.UserMemberRepository;
import jp.wat.basket.Repository.UserNendoRepository;
import jp.wat.basket.Repository.LoginUserRepository;
import jp.wat.basket.common.Enum.EnumRole;
import jp.wat.basket.common.Enum.EnumSebango;
import jp.wat.basket.common.Enum.EnumTeamKubun;
import jp.wat.basket.entity.LoginUser;
import jp.wat.basket.entity.Member;
import jp.wat.basket.entity.ScheduleDetail;
import jp.wat.basket.entity.UserMember;
import jp.wat.basket.entity.UserNendo;
import jp.wat.basket.model.MemberViewModel;
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
	
	public List<UserViewModel> getAllUser(){
		
		// 有効なメンバー情報を取得する
		List<LoginUser> userList = repository.findAllUser();
		
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
	
	public void deleteMember(Member member){
		repository.delete(member.getMemberId());
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
	
	/*
	 * ユーザーのアクセス可能な年度を削除する
	 */
	@Transactional
	public void removeUserNendo(UserNendo userNendo) {
		userNendoRepository.deleteUserNendo(userNendo.getUserId(), userNendo.getNendo());
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
	
	/*
	 * ユーザーのアクセス可能な年度を削除する
	 */
	@Transactional
	public void removeUserMember(UserMember userMember) {
		userMemberRepository.deleteUserMember(userMember.getUserId(), userMember.getNendo(), userMember.getMemberId());
	}

}
