## 📌 Flow for API Chaining
The following steps represent the full lifecycle of a course, from creation to deletion, including assignment and quiz management.

***1. Create Course***
POST /courses
Initializes a new course and returns a course_id to be used in subsequent steps.

***2. Update Course***
PUT /courses/{course_id}
Updates the course details using the previously generated course_id.

***3. Create Assignment***
POST /courses/{course_id}/assignments
Adds an assignment to the specified course.

***4. Create Quiz***
POST /courses/{course_id}/quizzes
Adds a quiz to the specified course.

***5. Submit Assignment***
POST /assignments/{assignment_id}/submit
Simulates a user submitting the assignment. The assignment_id must be captured from step 3.

***6. Submit Quiz***
POST /quizzes/{quiz_id}/submit
Simulates a user submitting the quiz. The quiz_id must be captured from step 4.

***7. Delete Course***
DELETE /courses/{course_id}
Deletes the course and cleans up all related resources after the assignment and quiz have been submitted.

![image](https://github.com/user-attachments/assets/9b31e449-954f-4a59-bb12-ffa694b5d499)
