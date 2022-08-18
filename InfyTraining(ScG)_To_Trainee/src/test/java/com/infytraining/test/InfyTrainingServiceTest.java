package com.infytraining.test;


import java.time.LocalDate;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import com.infytraining.exception.InfyTrainingException;
import com.infytraining.model.Trainee;
import com.infytraining.service.InfyTrainingService;
import com.infytraining.service.InfyTrainingServiceImpl;

public class InfyTrainingServiceTest {

	 private InfyTrainingService infyTrainingService= new InfyTrainingServiceImpl();
	
@Test
	public void addTraineeInvalidDateOfJoiningTest() throws InfyTrainingException{
		Trainee Haris = new Trainee("John Paul", LocalDate.of(2014, 9, 21), "Passport", "John_paul", 9875412345L);
		InfyTrainingException e = Assertions.assertThrows(InfyTrainingException.class,()->infyTrainingService.addTrainee(Haris));
		Assertions.assertEquals("Inavalid date_of_Joining", e.getMessage());
		
	}
@Test
	public void generateTraineeReportNoRecordsFoundTest() throws InfyTrainingException {
		InfyTrainingException e = Assertions.assertThrows(InfyTrainingException.class,()->infyTrainingService.generateTraineeReport("Passport"));
		Assertions.assertEquals("Invalid Driving Liciese.", e.getMessage());
			
	}
}
