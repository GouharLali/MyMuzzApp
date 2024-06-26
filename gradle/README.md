# Implementation Decisions

## Architecture
The application follows the Model-View-ViewModel (MVVM) architecture pattern to separate concerns between UI, data, and business logic. 
This architecture enhances maintainability and testability.

## Database
Room Persistence Library is used for local storage, providing an abstraction layer over SQLite. 
This choice simplifies database operations and ensures data persistence across app sessions.

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

