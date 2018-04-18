package jp.wat.basket.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import jp.wat.basket.Repository.MemberRepository;
import jp.wat.basket.entity.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	private static final String Integer = null;
	@Autowired
	private MemberRepository repository;
	
	public List<Member> getAllMember(){
		// 有効なメンバー情報を取得する
		return repository.findAllByOrderByIdAsc();
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
