package com.yuangao.taskmaster.taskmaster;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.UUID;

@DynamoDBTable(tableName = "Task")
public class Task {

    public String id;

    String title;
    String description;
    String status;
    String assignee;
    String pic;

    public Task(){}

    public Task(String title, String description, String assignee){
        this.title = title;
        this.description = description;
        this.assignee = assignee;
    }

    @DynamoDBHashKey
    @DynamoDBGeneratedUuid(DynamoDBAutoGenerateStrategy.CREATE)
    public String getId() {
        return id;
    }

    @DynamoDBAttribute
    public String getTitle() {
        return title;
    }

    @DynamoDBAttribute
    public String getDescription() {
        return description;
    }

    @DynamoDBAttribute
    public String getStatus() {
        return status;
    }

    @DynamoDBAttribute
    public String getAssignee() {
        return assignee;
    }

    @DynamoDBAttribute
    public String getPic(){ return pic;}

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

}
