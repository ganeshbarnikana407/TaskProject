package com.ganeshtech.taskproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ganeshtech.taskproject.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findAllByUserId(long userId);

}
