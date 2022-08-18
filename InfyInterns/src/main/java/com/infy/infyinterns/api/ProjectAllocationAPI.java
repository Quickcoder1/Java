package com.infy.infyinterns.api;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.infyinterns.dto.MentorDTO;
import com.infy.infyinterns.dto.ProjectDTO;
import com.infy.infyinterns.exception.InfyInternException;
import com.infy.infyinterns.service.ProjectAllocationService;
@RestController
@RequestMapping(value = "/infyinterns")
public class ProjectAllocationAPI
{
	@Autowired
	private ProjectAllocationService pas;
	
	@Autowired Environment env;

    // add new project along with mentor details
	@PostMapping(value = "/project")
    public ResponseEntity<String> allocateProject(ProjectDTO project) throws InfyInternException{
		
		Integer projectid = pas.allocateProject(project);
		String msg = env.getProperty("API.ALLOCATION_SUCCESS")+ ":" + projectid;
	return new ResponseEntity<>(msg,HttpStatus.CREATED);
    }

    // get mentors based on idea owner
	@GetMapping("/mentor/{numberOfProjectsMentored}")
    public ResponseEntity<List<MentorDTO>> getMentors(@PathVariable Integer numberOfProjectsMentored) throws InfyInternException
    {
		List<MentorDTO>list = pas.getMentors(numberOfProjectsMentored);
	return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // update the mentor of a project
	@PutMapping("/project/{projectId}/{mentorId}")
    public ResponseEntity<String> updateProjectMentor(@PathVariable Integer projectId,
    		@PathVariable @Min(value=1000, message ="{MENTOR.MENTORID.INVALID}")
    @Max(value=9999,message="{MENTOR.MENTORID.INVALID}")
						      Integer mentorId) throws InfyInternException
    {
		pas.updateProjectMentor(projectId, mentorId);
		String msg = env.getProperty("API.PROJECT_UPDATE_SUCCESS");
	return new ResponseEntity<>(msg,HttpStatus.OK);
    }

    // delete a project
	@DeleteMapping
    public ResponseEntity<String> deleteProject(Integer projectId) throws InfyInternException
    {
		pas.deleteProject(projectId);
		
		String msg = env.getProperty("API.PROJECT_DELETE_SUCCESS");
	return new ResponseEntity<>(msg, HttpStatus.OK);
    }

}
