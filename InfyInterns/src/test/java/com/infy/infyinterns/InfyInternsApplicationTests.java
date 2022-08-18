package com.infy.infyinterns;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.infy.infyinterns.dto.MentorDTO;
import com.infy.infyinterns.dto.ProjectDTO;
import com.infy.infyinterns.entity.Mentor;
import com.infy.infyinterns.exception.InfyInternException;
import com.infy.infyinterns.repository.MentorRepository;
import com.infy.infyinterns.service.ProjectAllocationService;
import com.infy.infyinterns.service.ProjectAllocationServiceImpl;

@SpringBootTest
public class InfyInternsApplicationTests {

	@Mock
	private MentorRepository mentorRepository;

	@InjectMocks
	private ProjectAllocationService projectAllocationService = new ProjectAllocationServiceImpl();

	@Test
	public void allocateProjectCannotAllocateTest() throws Exception {
		MentorDTO md = new MentorDTO(1001,"Warner",6);
		ProjectDTO pd =new ProjectDTO(1,"Shoe Cart",10012,LocalDate.of(2021, 06, 02),md);
		Mentor m = new Mentor();
		m.setMentorId(md.getMentorId());
		m.setMentorName(md.getMentorName());
		m.setNumberOfProjectsMentored(md.getNumberOfProjectsMentored());
		Mockito.when(mentorRepository.findById(md.getMentorId())).thenReturn(Optional.of(m));
		InfyInternException e = Assertions.assertThrows(InfyInternException.class, ()->projectAllocationService.allocateProject(pd));
		Assertions.assertEquals("Service.CANNOT ALLOCATE PROJECT",e.getMessage());
	}

	@Test
	public void allocateProjectMentorNotFoundTest() throws Exception {
		MentorDTO md = new MentorDTO(1009,"Bethany",2);
		ProjectDTO pd = new ProjectDTO(1,"Shoe Cart",10012,LocalDate.of(2021, 06, 02),md);
		Mockito.when(mentorRepository.findById(pd.getMentorDTO().getMentorId())).thenReturn(Optional.empty());
		InfyInternException e = Assertions.assertThrows(InfyInternException.class, ()->projectAllocationService.allocateProject(pd));
		Assertions.assertEquals("Service.MENTOR_NOTFOUND",e.getMessage());
	
	}
}