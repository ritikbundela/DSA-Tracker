# DSA Tracker - Backend

![](https://github.com/ritikbundela/DSA-Tracker/blob/main/.mvn/overview.jpeg)


##  Introduction

**DSA Tracker** is a robust Spring Boot backend application designed to help developers consistently practice Data Structures and Algorithms. It goes beyond simple list management by , **AI-powered recommendations**, and **automated email reminders** to keep users accountable.

This system allows users to log their solved problems, maintains a daily streak count, and uses **Google Gemini AI** to suggest similar problems for further practice.

##  Key Features

* ** User Authentication**: Secure Signup and Login functionality using Spring Security (BCrypt password encoding).
* ** Problem Management**: Full CRUD capabilities to track solved DSA problems (Title, Link, Topic, Difficulty, Status).
* ** AI Integration (Gemini)**: Fetches and recommends similar DSA problems based on the topic and title of the problem you just solved.
* **  Notifications**:
    * **Welcome Emails**: Sent upon successful registration.
    * **Password Reset**: Secure token-based "Forgot Password" flow via email.

##  Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3.4.3 (Web, Data JPA, Security, Mail, Cache, Scheduling)
* **Database:**  MySQL 
* **AI Service:** Google Gemini API (Generative AI)
* **Storage:** Cloudinary (Image management)
* **Tools:** Maven, Lombok, JavaMailSender

##  Getting Started

Follow these instructions to set up the project locally.

### Prerequisites

* Java Development Kit (JDK) 21 or higher
* Maven
* MySQL Database
* A Cloudinary account
* A Google Gemini API Key
* A Gmail account (for SMTP email sending)

### Installation

1.  **Clone the repository**
    ```bash
    git clone [https://github.com/your-username/dsa-tracker-adv.git](https://github.com/your-username/dsa-tracker-adv.git)
    cd dsa-tracker-adv
    ```

2.  **Configure Environment Variables**
    This project relies on environment variables for security. You can set them in your IDE or system, or create a `.env` file if you are using a dotenv loader.
    
    *Update the following keys in `src/main/resources/application.properties` or set them as system variables:*

    ```properties
    # Database Configuration
    DB_URL=jdbc:postgresql://localhost:5432/your_db_name
    DB_USERNAME=your_db_username
    DB_PASSWORD=your_db_password

    # Email Configuration (Gmail SMTP)
    MAIL_USERNAME=your_email@gmail.com
    MAIL_PASSWORD=your_app_password

    # Google Gemini AI
    GEMINI_API_KEY=your_gemini_api_key

    # Frontend Integration
    ALLOWED_ORIGINS=http://localhost:5173
    FORGOT_PASS_URL=http://localhost:5173/forgot-password
    ```

3.  **Build the project**
    ```bash
    ./mvnw clean install
    ```

4.  **Run the application**
    ```bash
    ./mvnw spring-boot:run
    ```
    The server will start on port `8080` (default).

## 📖 API Endpoints

### Auth Controller (`/api/auth`)
* `POST /signup`: Register a new user.
* `POST /login`: Authenticate user.
* `POST /forgot-password`: Request a password reset link.
* `POST /reset-password`: Reset password using the token.

### Problem Controller (`/api/problems`)
* `POST /`: Add a solved problem (Updates streak).
* `GET /{userId}`: Get all problems for a specific user.
* `PUT /{id}`: Update problem details.
* `DELETE /{id}`: Delete a problem.
* `GET /{id}/similar`: Get AI-suggested similar problems.

### Streak Controller (`/api/streaks`)
* `GET /{userId}`: Get streak history for a user.
* `POST /update/{userId}`: Manually update streak status.

### Profile Controller (`/api/profile`)
* `POST /upload`: Upload/Update user profile image.

## 🤝 Contributing

Contributions are welcome!
1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request


## 📄 License

This project is open-source and available under the [MIT License](LICENSE).
