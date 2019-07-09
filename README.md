# Task Master
It’s a task-tracking application with the same basic goal as Trello: 
allow users to keep track of tasks to be done and their status. 

## Deploying link
- Link for taskmaster backend: http://taskmaster.us-west-2.elasticbeanstalk.com
- Deploy link fro taskmaster frontend: http://taskmaster-lab31.s3-website-us-west-2.amazonaws.com

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
  - GET /users/{name}/tasks 
    - Representing all of the tasks assigned to that user.
  - PUT /tasks/{id}/assign/{assignee} 
    - Assign a particular user to a task.
  
- 9 July 2019
  - Post /tasks/{id}/images 
    - Users should be able to upload images that are associated with tasks.
  - Get /tasks/{id}  
    - Tasks should also include the image URLs associated with that image.
