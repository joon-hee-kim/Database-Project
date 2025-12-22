# Database-Project
This is the result of a JDBC project created using Java Eclipse and MySQL. </br> </br>

## Motivation
The aim of this university project was to develop a JDBC application with SNS functionalities as part of our database practice using Java Eclipse and MySQL. </br> </br>

## Project Structure
<img width="1079" alt="스크린샷 2024-07-03 오전 11 43 25" src="https://github.com/joon-hee-kim/Database-Project/assets/121689436/1a629933-2a9d-4924-b957-d309cc9975f2"> </br>

For simplicity, I have implemented class diagrams with class names only. </br>

1. Board.java: </br>
- Manages the main board interface where users can post messages, view posts, and interact with the posts by liking or deleting them. It integrates with the database to fetch and store posts. </br>
 
2. ChatServer.java: </br>
- Acts as a server for handling chat messages between users. Manages client connections and broadcasts messages to connected clients. </br> </br> 

3. ChatWindow.java: </br>
- Provides a chat interface for users to send and receive messages. Connects to ChatServer and interacts with DbAccess to store chat history. </br>
 
4. DbAccess.java: </br>
- Handles all database operations including user authentication, post management, follow management, and message storage.
- Acts as the data access layer for the application. </br>
 
5. Edit.java: </br>
- Provides a user interface for users to edit their profile information, specifically for changing their passwords. </br></br>

6. Follow.java: </br>
- Manages the follow relationships between users. Provides functionality to follow/unfollow users and view followers.
- Also integrates chat initiation with followers. </br>
 
7. Login.java: </br>
- Provides the login interface for the application.
- Authenticates users against the database and navigates to the main board or registration window based on user input. </br></br>

8. Message.java: </br>
- Represents a chat message entity containing sender ID, message text, and timestamp. Used for storing and retrieving chat messages. </br></br>

9. Post.java: </br>
- Represents a post entity containing post ID, content, writer, date, and likes. Used for storing and retrieving posts. </br></br>

10. Register.java: </br>
- Provides the user registration interface. Collects user details and stores them in the database. </br></br>

11. Search.java: </br>
- Allows users to search for other users by ID. Provides options to follow the searched users or view their board. </br></br>

12. Start.java: </br>
- Entry point of the application. Initializes the login interface. </br></br>

## Output
<img src="https://github.com/user-attachments/assets/fc0d3fa5-9403-4bfa-a623-deb814a0ca4f" width="300"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/889c114d-88a7-4a10-95bd-aaa6bc4f77c4" width="300"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/9ca568a6-0f21-4a3c-8fb5-68d03924847f" width="300"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/fc5cf74a-8b7a-4927-acf0-a7281e42e52e" width="300"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/e1b2bc3a-444c-415e-90d9-ed6ca01e481d" width="400"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/7c8bea93-3979-4c74-83de-83aef97de4d4" width="400"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/3ac36988-2a95-46d9-b2ea-baa868ec11af" width="400"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/8259a96b-aabe-47e0-8682-4bc58340affe" width="500"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/2f2536c0-ccae-4f04-b72d-d498fc74bc95" width="300"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/0cd6dd38-9610-4339-8b74-ad408fe92c9a" width="300"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/9e4e78a9-423a-475a-8000-171801490216" width="300"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/b0eef98b-eb7e-4c79-895d-be57e76cd840" width="400"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/63e5b64a-a590-4d66-beda-a57ddb13126a" width="400"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/1dea9cbf-5b2d-494b-8987-5bca32f000ed" width="400"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/3e231f8b-f38e-4f6b-9bac-44f5833dbc0c" width="400"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/ba594bcd-9d66-4077-bb1c-fb6c12823fa3" width="600"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/973aaa9e-01cc-4f72-9a56-61d26b29335d" width="600"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/5b30ea54-4474-413b-b58d-02b35ce00edc" width="600"/>

<br/><br/>

<img src="https://github.com/user-attachments/assets/a6b9c9c7-b898-48b5-ac4e-6b05aa111f6c" width="600"/>


## 👥 Team Member
201934219 Kim Joonhee </br>
202035331 Park Ga-hyeon </br>
202037033 Park Jeong-su </br>
202237783 Lee Chae-eun </br>
 
## ✔️ Source
* Github Link: [Reference Link](https://github.com/SongChiyoon/twitter/tree/master) </br>
* Blog Link: [Reference Link](https://bskwak.tistory.com/181) </br>
* Blog Link: [Reference Link](https://blog.naver.com/highkrs/220844554537) </br>
