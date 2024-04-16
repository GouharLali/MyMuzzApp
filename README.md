# MyMuzzApp

MyMuzzApp is a messaging app developed using Android and Kotlin.

## Features
- Send and receive messages in real-time.
- Message timestamp for each message.
- Support for displaying sender and receiver messages.

## Getting Started
To get started with this project, follow these steps:
1. Clone this repository.
2. Open the project in Android Studio.
3. Build and run the project on an Android device or emulator.

## Dependencies
- Kotlin
- AndroidX
- Room Database

# Implementation Decisions

## Architecture
The application follows the Model-View-ViewModel (MVVM) architecture pattern to separate concerns between UI, data, and business logic. 
This architecture enhances maintainability and testability.

## UI Components
- **RecyclerView**: RecyclerView is utilized to display chat messages efficiently. 
- Its flexible architecture allows for smooth scrolling and optimal performance, even with large datasets.
- **Custom Chat Bubbles**: Custom chat bubble layouts are implemented using LinearLayout and RelativeLayout, providing a visually appealing chat interface.

## Type Converters
Type converters are employed to handle Date objects in database operations. 
This ensures proper serialization and deserialization of Date objects, facilitating storage and retrieval of timestamp data.

## Messaging Logic
- **Message Sending**: Messages are sent by the user through an EditText input field and an ImageButton. 
- Upon sending a message, it is added to the local database and displayed in the chat interface.

- **Message Display**: Chat messages are displayed using RecyclerView and custom ViewHolder implementations. 
- Messages are differentiated based on their sender (user or other), and appropriate layout is applied.

## ViewModel and Repository
- **ViewModel**: The MessageViewModel class is responsible for managing UI-related data and communication with the repository. 
- It observes changes in message data and updates the UI accordingly.

- **Repository**: The MessageRepository class abstracts data operations from the ViewModel and interacts with the Room database through the MessageDao interface.
