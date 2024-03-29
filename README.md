# GitHub App
GitHub App is an Android application designed to assist users in searching for account details on GitHub by entering their username. Build with [Android Studio](https://developer.android.com/studio).

## Features
- **Search User:** Find someone by entering their username in the search bar
- **User Details:** View comprehensive account information by visiting their profile
- **Favorite Users:** Mark your favorite users for easy access
- **Theme Settings:** Customize the app's theme to suit your preferences

## Demo 
![GitHub App GIF](https://github.com/raflizocky/github-app/blob/main/images/github-app.gif)

## Tech Stack
- [Kotlin](https://kotlinlang.org/) – language
- [MVVM](https://www.youtube.com/watch?v=FrteWKKVyzI) – architectural pattern
- [Retrofit](https://square.github.io/retrofit/) - networking (API)
- [Room](https://developer.android.com/training/data-storage/room) - database
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - data storage
- [Fragment](https://developer.android.com/guide/fragments) - UI component

## Getting Started
### Prerequisites
Here's what you need to be able to run GitHub App:
- Android Studio (https://developer.android.com/studio/install)
- Android Device/Emulator (I use Devices, if [Emulator](https://developer.android.com/studio/run/emulator))
- Gradle (https://gradle.org/releases/)
- JDK (I use [OpenJDK](https://openjdk.org/), if [OracleJDK](https://www.oracle.com/java/technologies/downloads/))
- GitHub REST API (https://docs.github.com/en/rest)

### 1. Clone the repository
```shell
https://github.com/raflizocky/github-app.git
```

### 2. Configure the variables in `local.properties`
| Variable | Value |
|---|---|
| sdk_dir | < Your sdk location > |
| API_KEY | < [GitHub API KEY](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens) > |
| BASE_URL | < [GitHub REST API](https://docs.github.com/en/rest?apiVersion=2022-11-28) > |

### 3. Run the app
Fill in the necessary details in the ```local.properties``` file, then [run the app](https://developer.android.com/studio/run).

## Contributing

GitHub App is an Android project and welcomes contributions from the community.

If you'd like to contribute, please fork the repository and make changes as you'd like. Pull requests are warmly welcome.

### Our Contributors ✨

<a href="https://github.com/raflizocky/github-app/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=raflizocky/github-app" />
</a>

## Inspiration

- [Android Developer guides](https://developer.android.com/guide) -  Reference materials and documentation for Android app development
- [Belajar Fundamental Aplikasi Android](https://www.dicoding.com/academies/14) - Android Application Fundamentals Course
