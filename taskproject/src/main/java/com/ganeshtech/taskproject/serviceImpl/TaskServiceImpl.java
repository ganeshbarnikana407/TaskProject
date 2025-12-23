
package com.ganeshtech.taskproject.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ganeshtech.taskproject.entity.Task;
import com.ganeshtech.taskproject.entity.Users;
import com.ganeshtech.taskproject.exception.APIException;
import com.ganeshtech.taskproject.exception.TaskNotFound;
import com.ganeshtech.taskproject.exception.UserNotFound;
import com.ganeshtech.taskproject.payload.TaskDto;
import com.ganeshtech.taskproject.repository.TaskRepository;
import com.ganeshtech.taskproject.repository.UserRepository;
import com.ganeshtech.taskproject.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public TaskDto saveTask(long userId, TaskDto taskDto) {
		Users user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFound(String.format("User Id% not found", userId)));
		Task task = modelMapper.map(taskDto, Task.class);
		task.setUser(user);
		Task savedTask = taskRepository.save(task);
		return modelMapper.map(savedTask, TaskDto.class);
	}

	@Override
	public List<TaskDto> getAllTasks(long userId) {
		userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFound(String.format("User Id% not found", userId)));

		List<Task> tasks = taskRepository.findAllByUserId(userId);
		return tasks.stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
	}

	@Override
	public TaskDto getTask(long userid, long taskid) {
		Users users = userRepository.findById(userid)
				.orElseThrow(() -> new UserNotFound(String.format("User Id% not found", userid)));

		Task task = taskRepository.findById(taskid)
				.orElseThrow(() -> new TaskNotFound(String.format("Task Id% not found", taskid)));

		if (users.getId() != task.getUser().getId()) {

			throw new APIException(String.format("Task Id %d is not belongs to User Id %d", taskid, userid));

		}

		return modelMapper.map(task, TaskDto.class);
	}

	@Override
	public void deleteTask(long userId, long taskid) {
		Users users = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFound(String.format("User Id% not found", userId)));

		Task task = taskRepository.findById(taskid)
				.orElseThrow(() -> new TaskNotFound(String.format("Task Id% not found", taskid)));

		if (users.getId() != task.getUser().getId()) {

			throw new APIException(String.format("Task Id %d is not belongs to User Id %d", taskid, userId));

		}
		
		taskRepository.deleteById(taskid);

	}
}