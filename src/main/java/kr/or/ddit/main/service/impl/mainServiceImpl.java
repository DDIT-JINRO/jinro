package kr.or.ddit.main.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.main.service.mainService;

@Service
public class mainServiceImpl implements mainService {

	@Autowired
	MainMapper mainMapper;
	
	@Override
	public String getAge(String memId) {

		Date date= this.mainMapper.getAge(memId);
		
		LocalDate birthDate = date.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
		LocalDate today = LocalDate.now();
	    Period period = Period.between(birthDate, today);
	    
	    int age = period.getYears();
	    
	    if (age >= 19) {
            return "adult"; 
        } else {
            return "minor"; 
        } 
	}

}
