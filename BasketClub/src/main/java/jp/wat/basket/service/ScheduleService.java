package jp.wat.basket.service;

import java.util.List;

import javax.transaction.Transactional;

import jp.wat.basket.Repository.ScheduleRepository;
import jp.wat.basket.entity.Schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
	
	@Autowired
	private ScheduleRepository repository;
	
	public List<Schedule> getScheduleData(Integer nendo, Integer month){
		return repository.findByNengetsu(nendo, month);
	}
	
    @Transactional
    public void save(Schedule schedule) {
        repository.save(schedule);
    }
    
    @Transactional
	public Integer insert(Schedule schedule) {
    	repository.save(schedule);
		return repository.getLastSeq();
	}
    
    @Transactional
    public void delete(Schedule schedule) {
        repository.delete(schedule);
    }



}
