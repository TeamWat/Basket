package jp.wat.basket.service;

import java.util.List;

import javax.transaction.Transactional;

import jp.wat.basket.Repository.ScheduleDetailRepository;
import jp.wat.basket.entity.ScheduleDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
	
	@Autowired
	private ScheduleDetailRepository repository;
	
	public List<ScheduleDetail> getScheduleData(Integer nendo, Integer month){
		return repository.findByNengetsu(nendo, month);
	}
	
    @Transactional
    public void save(ScheduleDetail scheduleDetail) {
        repository.save(scheduleDetail);
    }
    
    @Transactional
	public Integer insert(ScheduleDetail scheduleDetail) {
    	repository.save(scheduleDetail);
		return repository.getLastSeq();
	}
    
    @Transactional
    public void delete(ScheduleDetail schedule) {
        repository.delete(schedule);
    }
    
}
