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
    public RedirectView addTask(String title, String description){

        Task newTask = new Task(title,description);
        newTask.setStatus("Available");;
        taskRepository.save(newTask);
        return new RedirectView("/tasks");
    }

    @PutMapping("/tasks/{id}/state")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestBody Task task){

        Task certainTask = taskRepository.findById(id).get();
        certainTask.setStatus("Assigned");
        taskRepository.save(certainTask);
        return ResponseEntity.ok("task saved");
    }

}
