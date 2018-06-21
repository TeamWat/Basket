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
import jp.wat.basket.Repository.UserRepository;
import jp.wat.basket.common.Enum.EnumRole;
import jp.wat.basket.common.Enum.EnumSebango;
import jp.wat.basket.common.Enum.EnumTeamKubun;
import jp.wat.basket.entity.Member;
import jp.wat.basket.entity.ScheduleDetail;
import jp.wat.basket.entity.User;
import jp.wat.basket.model.MemberViewModel;
import jp.wat.basket.model.UserViewModel;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public List<UserViewModel> getAllUser(){
		// 有効なメンバー情報を取得する
		List<User> userList = repository.findAllUser();
		
		List<UserViewModel> userViewModelList = new ArrayList<UserViewModel>();
		ModelMapper modelMapper = new ModelMapper();
		UserViewModel userViewModel;
		EnumRole enumRole;
		String roleName ="";

		for (User user : userList){
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
	
	public User findById(String userId){
		return repository.findById(userId);
	}
	
	public void registUser(User user){
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
    public void save(User user) {
        repository.save(user);
    }

}
