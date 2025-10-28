🕵️‍♀️ OnlineStegoProject
Minimal Spring Boot Steganography Messaging App

🧠 Overview

OnlineStegoProject is a secure steganography-based web messaging application built with Spring Boot.
It allows users to hide secret text messages inside images using the Least Significant Bit (LSB) technique.
Unlike typical chat apps, messages are never stored in the database, ensuring privacy and security.

🛡️ Security Features

✅ Messages are never stored in the database — only encoded images are saved.
✅ Text is embedded directly into the image using LSB steganography.
✅ Users can download and decode the images on any external steganography tool.
✅ Basic user authentication system to register and log in securely.

🚀 How to Run the Project
1️⃣ Prerequisites

Java 17 or above

Maven

Eclipse or IntelliJ IDEA

2️⃣ Setup Steps

Download or clone the repository

git clone https://github.com/Smily2531/OnlineStegoProject.git


Import into Eclipse or IntelliJ

Open IDE → Import → Maven → Existing Maven Project → Select project folder.

Run the project

mvn spring-boot:run


or run OnlineStegoApplication.java directly from IDE.

Access the web app

http://localhost:8081/

💡 How to Use

1️⃣ Register users
Go to http://localhost:8081/register and create at least two users.

2️⃣ Login and send hidden messages

Login with one user.

Go to Home → Choose a contact.

Upload an image and enter the message text.

The message will be encoded inside the image.

3️⃣ View or decode messages

Use "Reveal Hidden Message" button under any message.

OR upload any encoded image in the “Decode Any Image” section.

4️⃣ Download encoded images
Every message image has a Download button.

🗂️ Project Structure
OnlineStegoProject/
├── src/
│   ├── main/
│   │   ├── java/com/example/onlinestego/
│   │   │   ├── OnlineStegoApplication.java        # Main Spring Boot starter class
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java            # Handles login, register, logout
│   │   │   │   ├── HomeController.java            # Main chat and messaging endpoints
│   │   │   ├── model/
│   │   │   │   ├── User.java                      # User entity
│   │   │   │   ├── Message.java                   # Message metadata (image path)
│   │   │   ├── repository/
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── MessageRepository.java
│   │   │   ├── service/
│   │   │   │   ├── UserService.java
│   │   │   │   ├── StegoService.java              # Core logic for encoding/decoding
│   │   │   ├── util/
│   │   │   │   ├── StegoUtil.java                 # LSB steganography utility class
│   │   │   ├── config/
│   │   │   │   ├── WebConfig.java                 # Serves uploads/static files
│   │   ├── resources/
│   │   │   ├── static/                            # CSS, JS, images
│   │   │   ├── templates/                         # Thymeleaf templates
│   │   │   │   ├── index.html
│   │   │   │   ├── login.html
│   │   │   │   ├── register.html
│   │   │   │   ├── home.html
│   │   │   │   ├── decode.html
│   │   │   ├── application.properties             # App config (port, upload dir)
│   ├── test/
│   │   └── java/...
├── uploads/                                       # Saved encoded images
├── pom.xml                                        # Maven dependencies
└── README.md

🧩 Technologies Used
Category	Tools
Backend	Java, Spring Boot, Maven
Frontend	HTML, CSS, Thymeleaf
Security	Spring Security (basic auth)
Steganography	Custom LSB encoding/decoding
Database	H2 (only for user info)
Build Tool	Maven
🧠 Notes and Limitations

⚠️ This is a learning-level minimal example — not production-ready.

Only basic input validation and error handling.

No encryption of embedded text (only steganography).

LSB works best with PNG images (avoid JPEG compression).

Text capacity depends on image size (1 byte per pixel).

🧰 Future Improvements

Encrypt message before embedding.

Add message history (optional secure database).

Improve UI with better design and responsive layout.

Add image preview before and after encoding.

👨‍💻 Author

Nakka Smily
📍 B.Tech CSE - Aditya College of Engineering & Technology (A)
🧠 Skills: Java, Spring Boot, R, JavaScript, HTML, CSS

📜 License

This project is licensed under the MIT License.
Feel free to use, modify, and share with credit.
