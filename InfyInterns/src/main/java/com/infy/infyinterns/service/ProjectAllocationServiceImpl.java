package com.infy.infyinterns.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.infyinterns.dto.MentorDTO;
import com.infy.infyinterns.dto.ProjectDTO;
import com.infy.infyinterns.entity.Mentor;
import com.infy.infyinterns.entity.Project;
import com.infy.infyinterns.exception.InfyInternException;
import com.infy.infyinterns.repository.MentorRepository;
import com.infy.infyinterns.repository.ProjectRepository;

@Service(value="projectService")
@Transactional
public class ProjectAllocationServiceImpl implements ProjectAllocationService {
	@Autowired
	private ProjectRepository pr;
	
	@Autowired
	private MentorRepository mr;

	@Override
	public Integer allocateProject(ProjectDTO project) throws InfyInternException {
		Optional<Mentor>optional = mr.findById(project.getMentorDTO().getMentorId());
				Mentor mentor = optional.orElseThrow( ()-> new InfyInternException( "Service.MENTOR_NOTFOUND"));
				if (mentor.getNumberOfProjectsMentored () >= 3){
					 throw new InfyInternException ("Service.CANNOT ALLOCATE PROJECT");
				}

				Project p1 = new Project ();
				p1.setProjectId(project.getProjectId());
				p1.setProjectName (project.getProjectName());
				p1.setIdeaOwner (project.getIdeaOwner());
				p1.setReleaseDate(project.getReleaseDate());
				p1.setMentor (mentor) ;
				mentor.setNumberOfProjectsMentored (mentor.getNumberOfProjectsMentored()+1);
				Project p2 = pr.save(p1);
		return p2.getProjectId();
	}

	
	@Override
	public List<MentorDTO> getMentors(Integer numberOfProjectsMentored) throws InfyInternException {
		List<Mentor> L1 = mr.findByNumberOfProjectsMentored(numberOfProjectsMentored);
		if(L1.isEmpty())
			throw new InfyInternException("Service.MENTOR_NOT_FOUND");
		List<MentorDTO> L2 =L1.stream().map(x->new MentorDTO(x.getMentorId(),x.getMentorName(),
				x.getNumberOfProjectsMentored())).collect(Collectors.toList());
		return L2;
	}


	@Override
	public void updateProjectMentor(Integer projectId, Integer mentorId) throws InfyInternException {
		Optional<Mentor> opt = mr.findById(mentorId);
		Mentor mentor= opt.orElseThrow(()->new InfyInternException("Service.MENTOR_NOT_FOUND"));
		if(mentor.getNumberOfProjectsMentored()>=3)
			throw new InfyInternException("Service.CANNOT_ALLOCATE_PROJECT");
		Optional<Project> opt1= pr.findById(projectId);
		Project project = opt1.orElseThrow(()->new InfyInternException("Service.PROJECT_NOT_FOUND"));
		project.setMentor(mentor);
		mentor.setNumberOfProjectsMentored(mentor.getNumberOfProjectsMentored()+1);
	}

	@Override
	public void deleteProject(Integer projectId) throws InfyInternException {
		Optional<Project> opt1= pr.findById(projectId);
		Project p = opt1.orElseThrow(()->new InfyInternException("Service.PROJECT_NOT_FOUND"));
		if(p.getMentor()==null)
			pr.delete(p);
		else {
			Mentor m = p.getMentor();
			m.setNumberOfProjectsMentored(m.getNumberOfProjectsMentored()-1);
			p.setMentor(null);
			pr.delete(p);
			
		}
	}
}