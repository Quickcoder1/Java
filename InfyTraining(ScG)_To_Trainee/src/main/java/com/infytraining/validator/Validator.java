package com.infytraining.validator;

import java.time.LocalDate;

import org.apache.commons.logging.LogFactory;

import com.infytraining.exception.InfyTrainingException;
import com.infytraining.model.Trainee;

public class Validator {
	/*
	 * DO NOT CHANGE METHOD SIGNATURE
	 * DO NOT DELETE/COMMENT METHOD
	 */

	public void validate(Trainee trainee) throws InfyTrainingException {
		try {
		String errMsg=null;
		if(!isValidContactNumber(trainee.getContactNumber())){
			errMsg="Validator.INVALID_CONTACTNUMBER";
		}else if(!isValidDateOfJoining(trainee.getDateOfJoining())){
			errMsg="Validator.INVALID_DATEOFJOINING";
		}else if(!isValidIdProof(trainee.getIdProof())){
			errMsg="Validator.INVALID_IDPROOF";
		}else if(!isValidTraineeName(trainee.getTraineeName())){
			errMsg="Validator.INVALID_TRAINEENAME";
		}
		if(errMsg!=null){
			
			InfyTrainingException ee=new InfyTrainingException(errMsg);
			
			throw ee;
		}
		}catch(InfyTrainingException infyTrainingException){
			LogFactory.getLog(Validator.class).error(infyTrainingException.getMessage(),infyTrainingException);
			throw infyTrainingException;
		}
	}

	/*
	 * DO NOT CHANGE METHOD SIGNATURE
	 * DO NOT DELETE/COMMENT METHOD
	 */
	public Boolean isValidIdProof(String idProof) {
		Boolean result = false;
		if(idProof== "Passport"||(idProof == "Pan card" ||(idProof == "Aadhar ")))
			result = true;
		else
			result = false;
		
		return result;
	}

	
	/*
	 * DO NOT CHANGE METHOD SIGNATURE
	 * DO NOT DELETE/COMMENT METHOD
	 */
	public Boolean isValidDateOfJoining(LocalDate dateOfJoining) {
		Boolean result = false;
		LocalDate today = LocalDate.now();
		LocalDate pastday = today.minusDays(10);
		if(dateOfJoining.isAfter(pastday)&& dateOfJoining.isBefore(today) || dateOfJoining.equals(today)) {
			result = true;
		}else
			result = false;
		
		return result;
	}
	
	/*
	 * DO NOT CHANGE METHOD SIGNATURE
	 * DO NOT DELETE/COMMENT METHOD
	 */
	public Boolean isValidTraineeName(String traineeName) {
		Boolean result = false;
		String regex = "[A-Z][a-z]+\\s[A-Z][a-z]+";
		if (traineeName!=" " && traineeName.matches(regex)){
			result = true;
		}else {
			result = false;
		}
		return result;
	}


	/*
	 * DO NOT CHANGE METHOD SIGNATURE
	 * DO NOT DELETE/COMMENT METHOD
	 */
	public Boolean isValidContactNumber(Long contactNumber) {
		Boolean result = false;
		String regex = "[6,7,8,9][0-9]{9}";
		if (contactNumber!=null) {
			if (contactNumber.toString().matches(regex)) {
				result = true;
			}else {
				result = false;
			}
		}
		return result;
	}

}
