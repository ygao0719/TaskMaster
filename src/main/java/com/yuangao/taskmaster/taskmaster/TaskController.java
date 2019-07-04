package com.yuangao.taskmaster.taskmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/tasks")
    public ResponseEntity getTasks(){
        Iterable<Task> tasks= taskRepository.findAll();
        return new ResponseEntity(tasks, HttpStatus.OK);
    }

    @PostMapping("/tasks")
    public ResponseEntity addTask(String title, String description,String assignee){
        Task newTask = new Task(title,description,assignee);
        newTask.setStatus("Available");
        taskRepository.save(newTask);
        return new ResponseEntity(newTask,HttpStatus.OK);
    }

    //find certain task by its id
    @PutMapping("/tasks/{id}/state")
    public ResponseEntity updateStatus(@PathVariable String id){

        Task certainTask = taskRepository.findById(id).get();

        if (certainTask.getStatus().equals("Available")){
            certainTask.setStatus("Assigned");
        }else if(certainTask.getStatus().equals("Assigned")){
            certainTask.setStatus("Accepted");
        }else if(certainTask.getStatus().equals("Accepted")){
            certainTask.setStatus("Finished");
        }

        taskRepository.save(certainTask);
        return new ResponseEntity(certainTask,HttpStatus.OK);
    }

    //find all assignee's tasks
    @GetMapping("/users/{name}/tasks")
    public ResponseEntity getTaskFromUser(@PathVariable String name){

        List<Task> userTasks = taskRepository.findByAssignee(name);
        return new ResponseEntity(userTasks, HttpStatus.OK);

    }

    //assign a task to assignee
    @PutMapping("/tasks/{id}/assign/{assignee}")
    public ResponseEntity assignTaskToUser(@PathVariable String id, @PathVariable String assignee){
        Task task = taskRepository.findById(id).get();
        task.setAssignee(assignee);
        task.setStatus("Assigned");

        taskRepository.save(task);
        return new ResponseEntity(task,HttpStatus.MULTI_STATUS);
    }




}
