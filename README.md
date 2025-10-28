# 🔐 Online Steganography Messenger

> A secure Spring Boot messaging application that hides text messages inside images using LSB (Least Significant Bit) steganography

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Security](#-security-features)
- [Project Structure](#-project-structure)
- [Technologies Used](#-technologies-used)
- [Installation & Setup](#-installation--setup)
- [How It Works](#-how-it-works)
- [Usage Guide](#-usage-guide)
- [Technical Details](#-technical-details)
- [Database Schema](#-database-schema)
- [API Endpoints](#-api-endpoints)
- [Limitations](#-limitations--notes)

---

## 🎯 Overview

**OnlineStegoProject** is a secure messaging platform where text messages are never stored in plaintext. Instead, messages are embedded directly into image pixels using advanced steganography techniques. Only the encoded images are saved, ensuring that even if the database is compromised, the actual message content remains hidden.

### Key Concept

```
Original Image + Secret Message → LSB Encoding → Steganography Image
                                                         ↓
                                                   Database Stores
                                                   Only Image Path
```

---

## ✨ Features

### Core Functionality
- ✅ **Secure Messaging** - Messages hidden inside images using LSB steganography
- ✅ **User Authentication** - Register, login, and secure user sessions
- ✅ **Real-time Chat** - Send and receive encrypted messages with specific contacts
- ✅ **Image Upload** - Support for JPG, PNG, and other image formats
- ✅ **Format Conversion** - Automatic conversion to PNG (lossless) for data integrity

### Advanced Features
- 🔓 **On-Demand Decoding** - Click "Reveal Hidden Message" to view content
- 📥 **Image Download** - Download encoded images for offline decoding
- 🎨 **Client-Side Decoding** - Upload and decode any steganography image
- 🔐 **No Plaintext Storage** - Messages exist ONLY in image pixels
- 🎯 **HTML Escaping** - XSS protection for decoded messages

### Security Highlights
- 🔒 **Zero Plaintext** - Messages never stored in database
- 🖼️ **Image-Only Storage** - Only image paths saved to database
- 📱 **Offline Compatible** - Can decode using external steganography tools
- 🛡️ **XSS Protection** - All decoded text is HTML escaped

---

## 🔒 Security Features

### What Makes It Secure?

1. **No Message Storage**
   - Message text is ONLY embedded in image pixels
   - Database stores only image file paths
   - Even database admins can't read messages

2. **LSB Steganography**
   - Least Significant Bit technique hides data invisibly
   - Changes blue channel's last bit only
   - Indistinguishable from original to human eye

3. **Session Management**
   - User authentication required
   - Secure session handling
   - Only authorized users can view messages

4. **Input Sanitization**
   - HTML escaping prevents XSS attacks
   - Proper validation and error handling

---

## 📁 Project Structure

```
OnlineStegoProject/
│
├── src/
│   ├── main/
│   │   ├── java/com/example/onlinestego/
│   │   │   ├── config/
│   │   │   │   └── WebConfig.java          # Static resource configuration
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.java     # Authentication endpoints
│   │   │   │   ├── HomeController.java     # Home page & user management
│   │   │   │   └── MessageController.java  # Message operations
│   │   │   │
│   │   │   ├── model/
│   │   │   │   ├── User.java              # User entity
│   │   │   │   └── Message.java           # Message entity (no text field!)
│   │   │   │
│   │   │   ├── repo/
│   │   │   │   ├── UserRepository.java     # User data access
│   │   │   │   └── MessageRepository.java # Message data access
│   │   │   │
│   │   │   ├── service/
│   │   │   │   ├── UserService.java        # User business logic
│   │   │   │   └── MessageService.java    # Message business logic
│   │   │   │
│   │   │   ├── util/
│   │   │   │   └── StegoUtil.java         # LSB encoding/decoding
│   │   │   │
│   │   │   └── OnlineStegoApplication.java # Main application class
│   │   │
│   │   └── resources/
│   │       ├── application.properties      # Database & app configuration
│   │       └── templates/
│   │           ├── login.html             # Login page
│   │           ├── register.html          # Registration page
│   │           ├── home.html              # User home/dashboard
│   │           └── chat.html              # Messaging interface
│   │
│   └── test/
│
├── uploads/                                # Encoded images storage
├── pom.xml                                 # Maven dependencies
├── README.md                               # This file
├── HOW_IT_WORKS.md                         # Detailed steganography guide
├── CHANGES.md                              # Update history
└── TESTING_GUIDE.md                        # Testing instructions

```

---

## 🛠️ Technologies Used

### Backend
- **Java 17** - Core programming language
- **Spring Boot 3.2.0** - Application framework
- **Spring Data JPA** - Database ORM
- **MySQL 8.0** - Relational database
- **Thymeleaf** - Template engine for views

### Frontend
- **HTML5** - Page structure
- **CSS3** - Styling
- **JavaScript (ES6+)** - Client-side interactions
- **Bootstrap 5.2.3** - UI framework
- **Canvas API** - Client-side image decoding

### Tools & Libraries
- **Maven** - Dependency management
- **Lombok** - Boilerplate reduction
- **WebJars** - Frontend dependencies

---

## 🚀 Installation & Setup

### Prerequisites

- Java JDK 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Git (optional)

### Step-by-Step Installation

#### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/OnlineStegoProject.git
cd OnlineStegoProject
```

#### 2. Setup MySQL Database

```sql
CREATE DATABASE acetcse;
USE acetcse;
```

#### 3. Configure Application

Edit `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/acetcse
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

# Server Configuration
server.port=8081

# File Upload Directory
app.upload.dir=uploads/
```

#### 4. Build the Project

```bash
# Clean and build
mvn clean install

# Or build without running tests
mvn clean install -DskipTests
```

#### 5. Run the Application

```bash
# Option 1: Using Maven
mvn spring-boot:run

# Option 2: Using the JAR file
java -jar target/online-stego-0.0.1-SNAPSHOT.jar
```

#### 6. Access the Application

Open your browser and navigate to:
```
http://localhost:8081
```

---

## 🔧 How It Works

### LSB (Least Significant Bit) Steganography

The application uses LSB technique to hide messages in images:

#### Encoding Process
```java
1. Read original image pixels
2. Convert message to byte array
3. Store message length (4 bytes)
4. Embed each bit in blue channel's LSB
5. Save as PNG (lossless format)
```

#### Visual Example

**Before Encoding:**
```
Pixel RGB: (255, 200, 150)
Binary:    11111111, 11001000, 10010110
Blue:      10010110 (last bit = 0)
```

**After Encoding:**
```
Pixel RGB: (255, 200, 150 or 151)
Binary:    11111111, 11001000, 10010110 or 10010111
Blue:      10010111 (last bit = 1)
           ↑
      Bit changed - looks identical!
```

### Data Flow

```
┌─────────────┐
│ User Upload │
│   Image     │
└──────┬──────┘
       │
       ▼
┌──────────────────────┐
│  Save Original as    │
│  orig_{UUID}.jpg     │
└──────┬───────────────┘
       │
       ▼
┌──────────────────────┐
│  Encode Message with │
│    StegoUtil         │
│  LSB + Blue Channel  │
└──────┬───────────────┘
       │
       ▼
┌──────────────────────┐
│  Save Encoded as     │
│  enc_{UUID}.png      │
└──────┬───────────────┘
       │
       ▼
┌──────────────────────┐
│  Store Only Path     │
│  in Database         │
│  NO MESSAGE TEXT!    │
└──────────────────────┘
```

### Database Storage

**What IS stored:**
- ✅ Image file path
- ✅ Sender ID
- ✅ Receiver ID
- ✅ Timestamp

**What is NOT stored:**
- ❌ Message text content
- ❌ Message data
- ❌ Any plaintext

---

## 📖 Usage Guide

### For New Users

#### 1. Registration
1. Navigate to `http://localhost:8081/register`
2. Create username and password
3. Click "Register"

#### 2. Login
1. Go to `http://localhost:8081/login`
2. Enter credentials
3. Access your dashboard

#### 3. Send a Message
1. From home page, select a contact
2. Upload an image file
3. Type your secret message
4. Click "Send Message"
5. Image is encoded and stored

#### 4. Read Messages
1. Navigate to chat page
2. Click "Reveal Hidden Message" button
3. Decoded text displays instantly

#### 5. Download Images
- Click "Download Image" button on any message
- Save to local device
- Can decode later or use external tools

#### 6. Decode External Images
1. Scroll to "Decode Any Image" section
2. Click "Choose File"
3. Upload any steganography image
4. Hidden message appears automatically

### For Developers

#### Run in Development Mode

```bash
# Enable hot reload (if using IDE like IntelliJ or Eclipse)
# Or run with spring-boot-devtools
mvn spring-boot:run
```

#### View Application Logs

```bash
# Logs are printed to console
# Look for:
# - "Image size: WxH, capacity: N"
# - "Decoded length: N"
# - "Decoded message: ..."
```

#### Testing Endpoints

```bash
# Health check
curl http://localhost:8081/

# Decode message (POST)
curl -X POST http://localhost:8081/messages/decode \
  -F "file=@uploads/enc_example.png"
```

---

## 🔬 Technical Details

### LSB Algorithm Implementation

#### Key Components

**StegoUtil.java** contains the core encoding/decoding logic:

```java
// Encoding
public static void encodeText(File inputImage, File outputImage, String message)
- Reads image using BufferedImage
- Converts message to UTF-8 byte array
- Embeds length (4 bytes) first
- Loops through pixels (row by row)
- Modifies blue channel's LSB
- Saves as PNG

// Decoding
public static String decodeText(File imageFile)
- Reads LSB from blue channel
- Extracts first 32 bits (message length)
- Extracts remaining bits (message data)
- Converts bytes to UTF-8 string
- Returns decoded message
```

### Capacity Calculation

```
Image Size:  W × H pixels
Capacity:    W × H bits
Max Message: (W × H - 32) / 8 bytes

Example:
100×100 image = 10,000 pixels = 1,232 bytes max
(First 4 bytes reserved for length)
```

### Why PNG?

- **Lossless compression** - preserves exact pixel values
- **RGB mode** - maintains exact R, G, B values
- **No quality loss** - hidden data stays intact
- **Standard format** - compatible with all decoders

### Why JPG Doesn't Work

- **Lossy compression** - destroys hidden data
- **Quality degradation** - alters pixel values
- **Random changes** - bit manipulation lost
- **Unpredictable output** - decoding fails

---

## 🗄️ Database Schema

### Users Table

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);
```

### Messages Table

```sql
CREATE TABLE messagesdb (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sender_id BIGINT,
    receiver_id BIGINT,
    image_path VARCHAR(500),
    sent_at DATETIME,
    FOREIGN KEY (sender_id) REFERENCES users(id),
    FOREIGN KEY (receiver_id) REFERENCES users(id)
);
```

**Important:** Notice there's NO message/text column!

---

## 🎯 API Endpoints

### Authentication
```
POST /register    - Register new user
POST /login       - User login
GET  /logout      - Logout user
```

### Messages
```
GET  /home                    - Dashboard
GET  /chat/{receiverId}       - Chat interface
POST /send                    - Send message
POST /messages/decode         - Decode message
GET  /messages/download/{id}  - Download image
```

### Static Resources
```
GET  /uploads/**              - Serve uploaded images
GET  /css/**                  - CSS files
GET  /js/**                   - JavaScript files
```

---

## ⚠️ Limitations & Notes

### Current Limitations

1. **Image Size Requirement**
   - Message must fit in image capacity
   - Large messages need large images
   - Formula: `message_bytes < (pixels - 4)`

2. **PNG Only Output**
   - Input can be JPG/PNG/other
   - Output always PNG format
   - Original format not preserved

3. **Simple LSB Algorithm**
   - Uses basic LSB technique
   - No advanced encryption
   - Can be detected by steganalysis tools

4. **No Multimedia Support**
   - Text messages only
   - No images/documents in messages
   - No file attachments

5. **Single Server**
   - No clustering support
   - No load balancing
   - Not production-ready for scale

### Security Considerations

- ✅ Basic security implemented
- ✅ HTTPS recommended for production
- ✅ Input validation required
- ✅ SQL injection protection via JPA
- ⚠️ Password hashing recommended
- ⚠️ Session security enhancements needed

### Production Recommendations

1. **Security Enhancements**
   - Implement password hashing (BCrypt)
   - Add CSRF protection
   - Enable HTTPS/TLS
   - Add rate limiting
   - Implement audit logging

2. **Performance**
   - Add caching layer
   - Optimize database queries
   - Add connection pooling
   - Implement CDN for static assets

3. **Monitoring**
   - Add health checks
   - Implement logging framework
   - Set up metrics collection
   - Add error tracking

4. **Scalability**
   - Implement message queue
   - Add database replication
   - Use cloud storage
   - Implement microservices

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👤 Author

**Your Name**
- Email: your.email@example.com
- GitHub: [@Smily2531](https://github.com/Smily2531)

---

## 🙏 Acknowledgments

- Spring Boot community
- Steganography research and algorithms
- Open source contributors

---

## 📞 Support

If you encounter any issues or have questions:

1. Check [HOW_IT_WORKS.md](HOW_IT_WORKS.md) for detailed explanations
2. Review [CHANGES.md](CHANGES.md) for recent updates
3. See [TESTING_GUIDE.md](TESTING_GUIDE.md) for testing information
4. Open an issue on GitHub

---

<div align="center">
  <strong>🔐 Secure Messaging Through Steganography 🔐</strong>
  <br>
  <em>Your secrets hidden in plain sight</em>
</div>

