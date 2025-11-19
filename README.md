# ☁️ SkyPulse

**A modern Android weather forecast application**

SkyPulse is a real-time weather app that provides accurate and up-to-date meteorological
information, allowing users to check the weather for their current location or any city worldwide.

## 📋 Features

- 🌍 **Auto-location** - Get weather for your current location automatically
- 🔍 **City Search** - Check weather anywhere in the world
- ⭐ **Favorite Cities** - Save and manage your frequent locations
- 📅 **Extended Forecast** - 5-7 day weather prediction
- 💾 **Offline Mode** - Access cached data without internet
- 🎨 **Material Design 3** - Modern and fluid interface
- 🌓 **Light/Dark Theme** - Adapts to your preferences
- 🔔 **Notifications** - Important weather alerts

## 🛠️ Tech Stack

- **Language:** Kotlin
- **Architecture:** MVVM (Model-View-ViewModel)
- **Android Components:**
    - Activities & Fragments
    - RecyclerView
    - Room Database (SQLite)
    - SharedPreferences
    - Location Services
- **Networking:**
    - Retrofit 2
    - OkHttp
    - Gson/Moshi
- **API:** OpenWeatherMap API
- **UI/UX:**
    - Material Design Components
    - View Binding
    - Glide/Coil (image loading)

## 📱 Requirements

- Android 8.0 (API 26) or higher
- Internet connection (for real-time data)
- Location permissions (optional)

## 🚀 Installation

1. Clone this repository:

```bash
git clone https://github.com/your-username/skypulse.git
```

2. Open the project in Android Studio

3. Get a free API Key from [OpenWeatherMap](https://openweathermap.org/api)

4. Create a `local.properties` file in the project root and add:

```properties
WEATHER_API_KEY=your_api_key_here
```

5. Sync the project with Gradle

6. Run the app on an emulator or physical device

## 🎯 Future Features

- [ ] Home screen widget
- [ ] Interactive temperature charts
- [ ] Weather radar
- [ ] Share forecasts
- [ ] Multi-language support

## 👨‍💻 Author

**Raul Catalinas Esteban**

- [GitHub](https://github.com/RaulCatalinas)
- [X/Twitter](https://x.com/CatalinasRaul)
- [Instagram](https://www.instagram.com/raulcatalinasesteban)
- [Web con el resto mis apps](https://gets-my-apps.vercel.app)

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Developed as final project for the Mobile App Development course - SEPE
- Weather API provided by OpenWeatherMap
- Icons from [Material Design Icons](https://materialdesignicons.com/)

---

⭐ If you like this project, give it a star on GitHub!