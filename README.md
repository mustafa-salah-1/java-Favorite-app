# Favorite Places Saver 📍

A sleek and modern Android application built with Java and OpenStreetMap (osmdroid) to help you save and manage your favorite locations with ease.

## ✨ Features

- **Interactive Map**: Explore the map and save any location with a simple tap.
- **Persistent Storage**: Your favorite places are saved locally using SharedPreferences in JSON format, ensuring they are always there when you return.
- **Detailed Information**: View specific details for each saved place, including:
  - Custom Title
  - Precise Coordinates (Latitude & Longitude)
  - Detailed Description
- **Easy Management**: 
  - **Edit**: Update the details of your saved places at any time.
  - **Delete**: Remove places you no longer need.
- **Unique Validation**: Prevents duplicate entries by ensuring every place name is unique.
- **Custom UI**: A beautiful, responsive design featuring a custom ListView with coordinates and a dedicated About page.
- **Developer Info**: Connect with the developer through the integrated About page links.

## 🛠️ Tech Stack

- **Language**: Java
- **UI Framework**: Android XML / Material Design
- **Map Engine**: [osmdroid](https://github.com/osmdroid/osmdroid) (OpenStreetMap for Android)
- **Data Persistence**: SharedPreferences (JSON serialization)
- **Architecture**: Controller-Model pattern

## 🚀 Getting Started

### Prerequisites

- Android Studio (Ladybug or later recommended)
- JDK 11 or higher
- Android SDK 24 (Nougat) or higher

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/java-Favorite-app.git
   ```
2. **Open in Android Studio**:
   - File > Open... > Select the project directory.
3. **Sync Gradle**:
   - Let Android Studio download dependencies and sync the project.
4. **Run the App**:
   - Connect an Android device or start an emulator.
   - Click the **Run** button (green play icon).

## 📱 Permissions

The app requires the following permissions to function correctly:
- `INTERNET`: To fetch map tiles.
- `ACCESS_NETWORK_STATE`: To check for connectivity.
- `ACCESS_FINE_LOCATION`: For accurate user positioning.
- `ACCESS_COARSE_LOCATION`: For general user positioning.

## 📁 Project Structure

- `Controllers/`: Contains Activity classes managing UI and logic (Main, AddPlace, PlaceDetail, About).
- `Models/`: Data models for `Place` and `PlaceItem`.
- `res/`: UI layouts, drawables (including custom logo), and values.

## 🤝 Contributing

Contributions are welcome! If you have any ideas, feel free to fork the repository and submit a pull request.

## 📄 License

This project is open-source. Feel free to use and modify it as you see fit.

---

*Built with ❤️ for location lovers.*
