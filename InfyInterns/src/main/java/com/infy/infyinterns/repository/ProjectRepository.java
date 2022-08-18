package com.infy.infyinterns.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.infy.infyinterns.entity.Project;

public interface ProjectRepository extends CrudRepository<Project, Integer>{
	List<Project> findByIdeaOwner(Integer IdeaOwner);

    // add methods if required

}
