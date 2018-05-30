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
import jp.wat.basket.common.Enum.EnumSebango;
import jp.wat.basket.common.Enum.EnumTeamKubun;
import jp.wat.basket.entity.Member;
import jp.wat.basket.entity.ScheduleDetail;
import jp.wat.basket.model.MemberViewModel;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository repository;
	
	public List<MemberViewModel> getAllMember(){
		// 有効なメンバー情報を取得する
		List<Member> memberList = repository.findAllMember();
		
		List<MemberViewModel> memberViewModelList = new ArrayList<MemberViewModel>();
		ModelMapper modelMapper = new ModelMapper();
		MemberViewModel memberViewModel;
		EnumTeamKubun enumTeamKubun;
		EnumSebango enumSebango;
		String teamName ="";

		for (Member member : memberList){
			memberViewModel = modelMapper.map(member, MemberViewModel.class);
			
			// チーム名を設定
			enumTeamKubun = EnumTeamKubun.decode(memberViewModel.getTeamKubun());
			memberViewModel.setTeamName(enumTeamKubun.getName());
			
			// 背番号（表示用）を設定
			enumSebango = EnumSebango.decode(memberViewModel.getNo());
			memberViewModel.setDisplayNo(enumSebango.getName());
			
			// リストに追加
			memberViewModelList.add(memberViewModel);
		}
		
		return memberViewModelList;
	}
	
	public Member findById(Integer memberId){
		return repository.findById(memberId);
	}

	public Member findByNo(Integer no){
		return repository.findByNo(no);
	}
	
	public void registMember(Member member){
		repository.save(member);
	}
	
	public void deleteMember(Member member){
		repository.delete(member.getMemberId());
	}
	
    private DateFormat SimpleDateFormat(String timeFormat) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Transactional
    public void save(Member information) {
        repository.save(information);
    }

}
