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


## 👥 Team Member
201934219 Kim Joonhee </br>
202035331 Park Ga-hyeon </br>
202037033 Park Jeong-su </br>
202237783 Lee Chae-eun </br>
 
## ✔️ Source
* Github Link: [Reference Link](https://github.com/SongChiyoon/twitter/tree/master) </br>
* Blog Link: [Reference Link](https://bskwak.tistory.com/181) </br>
* Blog Link: [Reference Link](https://blog.naver.com/highkrs/220844554537) </br>
