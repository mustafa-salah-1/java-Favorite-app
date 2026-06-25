<p align="center">
  <img src="app/src/main/res/drawable/logo.xml" alt="Favorite Logo" width="128" height="128">
</p>

<h1 align="center">Favorite Places Saver 📍</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white" alt="Platform">
  <img src="https://img.shields.io/badge/Language-Java-ED8B00?logo=java&logoColor=white" alt="Language">
  <img src="https://img.shields.io/badge/API-24%2B-brightgreen" alt="Min SDK">
  <img src="https://img.shields.io/badge/Map-OpenStreetMap-7ebc59?logo=openstreetmap&logoColor=white" alt="Map">
  <img src="https://img.shields.io/badge/License-MIT-yellow" alt="License">
</p>

<p align="center">
  A sleek and modern Android application to save, browse, and manage your favorite locations on an interactive map — all stored locally on your device.
</p>

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| 🗺️ **Interactive Map** | Explore and tap anywhere on the OpenStreetMap to save a location |
| 📋 **Place List** | View all saved places with real-time search filtering by name |
| 📝 **Place Details** | See name, coordinates, description, and a pinpoint marker on the map |
| ✏️ **Edit Places** | Update the name and description of any saved place |
| 🗑️ **Delete Places** | Remove places with a confirmation dialog |
| 🚫 **Duplicate Detection** | Prevents saving two places with the same name |
| 📱 **About Page** | Simple about screen with a link to the developer's website |
| 💾 **Local Storage** | All data persisted on-device — no account or internet required (maps excepted) |

## 📸 Screenshots

<p align="center">
  <i>Screenshots coming soon</i>
</p>

## 🛠️ Tech Stack

<div align="center">

| Technology | Purpose |
|------------|---------|
| **Java** | Primary programming language |
| **Android XML** | UI layout definitions |
| **Material Design** | UI components and theming |
| **osmdroid** | OpenStreetMap map rendering |
| **SharedPreferences** | Local JSON data persistence |
| **DialogFragment** | Save/delete confirmation dialogs |

</div>

## 🚀 Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio) (Ladybug or later)
- JDK 11+
- An Android device or emulator running **API 24+** (Android 7.0 Nougat)

### Installation

```bash
# Clone the repository
git clone https://github.com/yourusername/java-Favorite-app.git

# Open in Android Studio
# File > Open... > select the project directory
```

Let Android Studio sync the Gradle dependencies, then press **Run** ▶️.

## 📁 Project Structure

```
app/
├── src/main/java/com/example/favorite/
│   ├── Components/           # Reusable UI components
│   │   ├── PlaceAdapter.java           # Custom ListView adapter
│   │   ├── SavePlaceDialogFragment.java # Add/Edit place dialog
│   │   └── DeleteConfirmDialogFragment.java # Delete confirmation dialog
│   ├── Controllers/          # Activity classes
│   │   ├── MainController.java         # Home screen with list + search
│   │   ├── AddPlaceController.java     # Map screen to pick a location
│   │   ├── PlaceDetailController.java  # View/Edit/Delete a place
│   │   └── AboutController.java        # About the developer
│   └── Models/               # Data layer
│       ├── Place.java                  # CRUD operations (SharedPreferences)
│       └── PlaceItem.java              # Place data class
├── src/main/res/             # Resources
│   ├── layout/               # XML layouts
│   ├── drawable/             # Icons and graphics
│   ├── values/               # Strings, colors, themes
│   └── xml/                  # Backup rules
└── build.gradle              # App-level build config
```

## 🔒 Permissions

| Permission | Reason |
|------------|--------|
| `INTERNET` | Fetching OpenStreetMap tile images |

*No location permissions are required — coordinates are entered by tapping the map.*

## 🤝 Contributing

Contributions are welcome! Feel free to open an issue or submit a pull request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

---

<p align="center">
  Built with ❤️ by <a href="https://mustafa-salah.com">Mustafa Salah</a>
</p>
