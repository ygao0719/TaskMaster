package com.yuangao.taskmaster.taskmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/tasks")
    public String getTasks(Model m){

        Iterable<Task> tasks= taskRepository.findAll();
        m.addAttribute("tasks",tasks);
        return "tasks";
    }

    @PostMapping("/tasks")
    public RedirectView addTask(String title, String description,String assignee){

        Task newTask = new Task(title,description,assignee);
        newTask.setStatus("Available");
        taskRepository.save(newTask);
        return new RedirectView("/tasks");
    }

    @PutMapping("/tasks/{id}/state")
    public String updateStatus(@PathVariable String id){

        Task certainTask = taskRepository.findById(id).get();

        if (certainTask.getStatus().equals("Available")){
            certainTask.setStatus("Assigned");
        }else if(certainTask.getStatus().equals("Assigned")){
            certainTask.setStatus("Accepted");
        }else if(certainTask.getStatus().equals("Accepted")){
            certainTask.setStatus("Finished");
        }

        taskRepository.save(certainTask);
        return "tasks";
    }

}
