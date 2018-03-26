package jp.wat.basket.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import jp.wat.basket.Repository.InformationRepository;
import jp.wat.basket.entity.Information;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InformationService {
	
	@Autowired
	private InformationRepository repository;
	
	public List<Information> getInformation(){
		
		
		LocalDateTime now = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(now);  // yyyy/mm/dd hh:mm:ss
		
		// 有効期限内のimformationを取得する
		return repository.findByExpireDate(timestamp);
		
	}
	
    private DateFormat SimpleDateFormat(String timeFormat) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Transactional
    public void save(Information information) {
        repository.save(information);
    }

}
