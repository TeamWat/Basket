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
import jp.wat.basket.Repository.UserNendoRepository;
import jp.wat.basket.Repository.LoginUserRepository;
import jp.wat.basket.common.Enum.EnumRole;
import jp.wat.basket.common.Enum.EnumSebango;
import jp.wat.basket.common.Enum.EnumTeamKubun;
import jp.wat.basket.entity.LoginUser;
import jp.wat.basket.entity.Member;
import jp.wat.basket.entity.ScheduleDetail;
import jp.wat.basket.entity.UserNendo;
import jp.wat.basket.model.MemberViewModel;
import jp.wat.basket.model.UserViewModel;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginUserService {
	
	@Autowired
	private LoginUserRepository repository;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	private UserNendoRepository userNendoRepository;
	
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

}
