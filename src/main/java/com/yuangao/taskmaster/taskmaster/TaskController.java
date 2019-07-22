package com.yuangao.taskmaster.taskmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@Controller
public class TaskController {
    private S3Client s3Client;

    @Autowired
    TaskController(S3Client s3Client){
        this.s3Client = s3Client;
    }

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

        AmazonSNSClient snsClient = new AmazonSNSClient();
        String message = "My SMS message";
        String phoneNumber = "+1XXX5550100";
        Map<String, MessageAttributeValue> smsAttributes =
                new HashMap<String, MessageAttributeValue>();
        //<set SMS attributes>
        sendSMSMessage(snsClient, message, phoneNumber, smsAttributes);
    }

    public static void sendSMSMessage(AmazonSNSClient snsClient, String message,
                                      String phoneNumber, Map<String, MessageAttributeValue> smsAttributes) {
        PublishResult result = snsClient.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));
        System.out.println(result); // Prints the message ID.
    }


    //upload img
    @PostMapping("/tasks/{id}/images")
    public ResponseEntity uploadFile(@PathVariable String id, @RequestPart(value="file") MultipartFile file){
        String pic = this.s3Client.uploadFile(file);
        Task task = taskRepository.findById(id).get();
        task.setPic(pic);

        //Evan's genious idea
        String[] picSplit = pic.split("/");
        String fileName = picSplit[picSplit.length-1];
        task.setResizedPic("https://taskmaster-lab31resized.s3-us-west-2.amazonaws.com/resized-" + fileName);

        taskRepository.save(task);
        return new ResponseEntity(task,HttpStatus.MULTI_STATUS);

    }

    //get all task from task id
    @GetMapping("tasks/{id}")
    public ResponseEntity getTasksById(@PathVariable String id){
        Task task = taskRepository.findById(id).get();
        return new ResponseEntity(task,HttpStatus.MULTI_STATUS);
    }

}
