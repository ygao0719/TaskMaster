# Task Master
It’s a task-tracking application with the same basic goal as Trello: 
allow users to keep track of tasks to be done and their status. 

## Deploying link
taskmaster.us-west-2.elasticbeanstalk.com

## Issues encountered during deployment
We used to use the RDS as the database to support applications. But this time we are 
using NoSQL. I am not sure how to make the application connect to DynamoDB.

## Features
- 4 July 2019:

  - GET /tasks - get a list of all tasks currently in the database.

  - POST /tasks - add a new task to the database - add title, description and assignmee.

  - PUT /tasks/{id}/state - update the state of the tasks. State advances from Assigned -> Accepted -> Finished

## ChangeLog
- 4 July 2019
  - GET /users/{name}/tasks representing all of the tasks assigned to that user.
  - PUT /tasks/{id}/assign/{assignee} assign a particular user to a task.