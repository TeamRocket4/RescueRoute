# **RescueRoute Platform**
![image-removebg-preview](https://github.com/user-attachments/assets/7fe822f4-2064-4547-b217-24f48ee80f27)
  [![Watch the video](https://img.youtube.com/vi/vphUmE2l6xg/0.jpg)](https://www.youtube.com/watch?v=vphUmE2l6xg)


This article introduces **RescueRoute**, an innovative platform designed to revolutionize emergency medical management. Our solution prioritizes real-time ambulance coordination, optimized routing, and streamlined resource allocation. By incorporating traffic conditions, hospital availability, and real-time constraints, RescueRoute empowers emergency teams to save lives through faster response times and improved collaboration.

Our platform integrates cutting-edge technologies like AI-based optimization algorithms, robust service-oriented architecture, and mobile solutions for seamless emergency management.

---

## **Table of Contents**  
1. [Overview](#overview)  
2. [Software Architecture](#software-architecture)  
3. [Docker Configuration](#docker-configuration)  
4. [Frontend](#frontend)  
5. [Backend](#backend)  
6. [Mobile App](#mobile-app)  
7. [Getting Started](#getting-started)  
8. [Illustrative Examples](#illustrative-examples)  
9. [Contributing](#contributing)

---

## **Overview**  
**RescueRoute** is a platform aimed at transforming emergency response efficiency. Its goal is to reduce intervention times through optimal routing and coordination of ambulances and medical teams. The platform leverages real-time data to ensure effective decision-making and enhances collaboration between hospitals, dispatchers, and ambulance teams. By addressing challenges in resource management, routing, and communication, RescueRoute significantly improves patient survival rates and operational efficiency.

---

## **Software Architecture**  

### **Overview**  
RescueRoute employs a modern, service-oriented architecture featuring:  
1. **Backend (Spring Boot)**: Provides RESTful APIs for robust request handling, secure data access, and business logic processing.  
2. **Frontend (React.js)**: Offers a dynamic and responsive interface for administrators.  
3. **Mobile App (Android)**: Supports ambulance teams with real-time navigation and mission updates.

### **Backend Details**  
- **Spring Boot Framework**: Robust backend services with efficient routing and secure REST endpoints.  
- **Spring Security**: Ensures sensitive medical data protection.  
- **MySQL Database**: Manages real-time data for ambulances, interventions, and hospitals.  
- **Optimization Algorithms**: Utilizes advanced algorithms for traffic-aware routing and emergency prioritization.

### **Frontend and Mobile App**  
- **React.js**: Provides administrators with a real-time dashboard for ambulance tracking and resource allocation.  
- **Android App with Android Studio**: Ensures seamless communication and navigation for ambulance teams.

### **Real-Time Communication**  
- **WebSocket Integration**: Delivers real-time updates for ambulance locations and mission statuses.  

---

## **Docker Configuration**  

Below is the `docker-compose.yml` setup to run the platform services.

### **Services**  
- **Jenkins**: For continuous integration and build automation.  
- **SonarQube**: For code quality and security analysis.  
- **JMeter**: For performance testing.  
- **Trivy**: For vulnerability scanning.  
- **Spring Boot**: Backend service to handle business logic and API endpoints.  
- **MySQL**: Database for managing emergency data.  
- **React.js Dashboard**: Admin interface for monitoring and managing resources.  

### **Setup**  
```yaml
services:
  jenkins:
    image: jenkins/jenkins:lts
    container_name: jenkins
    user: root
    ports:
      - "8090:8080"
      - "50000:50000"
    volumes:
      - jenkins_home:/var/jenkins_home
      - /var/run/docker.sock:/var/run/docker.sock
      - maven_repo:/var/jenkins_home/.m2/repository

  sonarqube:
    image: sonarqube:community
    container_name: sonarqube
    network_mode: "host"
    volumes:
      - sonarqube_data:/opt/sonarqube/data
      - sonarqube_extensions:/opt/sonarqube/extensions
      - sonarqube_logs:/opt/sonarqube/logs

  jmeter:
    image: justb4/jmeter:latest
    container_name: jmeter
    network_mode: "host"
    volumes:
      - jenkins_home:/var/jenkins_home
    command: tail -f /dev/null

  trivy:
    image: aquasec/trivy:latest
    container_name: trivy
    network_mode: "host"
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
      - trivy_cache:/root/.cache/
    command: server --listen 0.0.0.0:8081

  ambulance_db:
    container_name: ambulance_db
    image: mysql:8.0
    ports:
      - "3306:3306"
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: rescueroute
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-u", "root", "-p$$MYSQL_ROOT_PASSWORD"]
      interval: 5s
      timeout: 5s
      retries: 5
    networks:
      - ambulance-network

  springboot:
    build:
      context: ./Ambulance_Spring
      dockerfile: Dockerfile
    container_name: springboot_service
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://ambulance_db:3306/rescueroute
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root
      SPRING_JPA_HIBERNATE_DDL-AUTO: update
    depends_on:
      ambulance_db:
        condition: service_healthy
    networks:
      - ambulance-network


  dashboard:
    build:
      context: ./dashboard
      dockerfile: Dockerfile
    container_name: dashboard
    ports:
      - "3001:3000"  # Change the external port to 3001
    depends_on:
      - ambulance_db
    networks:
      - ambulance-network


volumes:
  jenkins_home:
  sonarqube_data:
  sonarqube_extensions:
  sonarqube_logs:
  trivy_cache:
  maven_repo:
  mysql_data:

networks:
  ambulance-network:
    driver: bridge
```

### **Networks and Volumes**  
- Uses `ambulance-network` for service communication.  
- Persistent volumes ensure data retention across container restarts.  

---

## **Frontend**  

### **Technologies Used**  
- **Next.js**  
- **Material-UI**  

### **Features**  
1. **Admin Dashboard**: Tracks ambulance locations, interventions, and hospital statuses.  
2. **Interactive Map**: Visualizes real-time vehicle positions and route progress.
3.  **Mobile APP**: : Provides drivers with tools for real-time navigation, live traffic updates, and emergency details. The app ensures seamless communication with dispatchers and facilitates efficient route planning to minimize response times.

---

## **Backend**  

### **Technologies Used**  
- **Spring Boot**  
- **MySQL**  

### **Features**  
- **RESTful APIs**: Handles emergency data and operational workflows.  
- **Real-Time Updates**: Utilizes WebSocket for live data synchronization.  
- **Secure Data Access**: Implements Spring Security for robust protection.  

---

## **Mobile App**  

### **Technologies Used**  
- **Android Studio**  
- **Java/Kotlin**  

### **Features**  
1. **Real-Time Navigation**: Optimized routing based on traffic and priorities.  
2. **Mission Details**: Provides ambulance teams with critical patient and route information.  
3. **Notification System**: Real-time updates for mission changes and emergency prioritization.  

---

## **Getting Started**  

### **Prerequisites**  
1. **Docker & Docker-Compose**
2. **Jenkins**  
3. **Java (17+)**  
4. **Node.js (16+)**  
5. **Android Studio (2022+)**

### **Setup Instructions**  
1. Clone the repository:  
   ```bash
   git clone <repository_url>
   cd RescueRoute
   ```

2. Run the services with Docker Compose:  
   ```bash
   docker-compose up
   ```

3. Access the application:  
   - **Backend**: http://localhost:8080  
   - **Frontend**: http://localhost:3001  
   - **Android App**: Load the project in Android Studio and deploy it on a device/emulator.  

---

## **Illustrative Examples**  

### **Scenario 1: Administrator Dashboard**  
The admin dashboard enables real-time ambulance tracking and mission management through an intuitive React.js interface.  

### **Scenario 2: Ambulance Navigation**  
Ambulance teams use the Android app for optimized routing based on live traffic data.  

### **Scenario 3: Real-Time Visualization**  
An interactive map displays ongoing missions, ambulance routes, and hospital availability.  

---


**Contributors**:  
- Amine Azilal  
- Hamza ELBOUZIDI
- Bilal MOTASSIM
- Nizar BOUSSANE
- Abdelhamid BOURWAY

---

