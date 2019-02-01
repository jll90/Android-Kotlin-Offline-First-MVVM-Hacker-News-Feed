# Android Kotlin, offline-first, MVVM HackerNewsFeed
Offline-first, MVVM-architectured Android app demo showcasing Google's architectural components such as Room and ViewModels in Kotlin. Other technologies include: Dagger2, Retrofit2, RxJava2, and Kotlin coroutines.
It also features both delete on-swipe and refreshing on-pull functionality, and the ability to remember which news articles have been marked as deleted with the help of SQLite.

## Installation
Clone this project, open with Android Studio (or equivalent), install dependencies with Gradle (tested on 3.2) and run.

## API Support
The app has been tested on API 16 using Android Studio's emulator, and on real devices (APIs 21, 22 and 24)

## Potential enhancements
* Unit testing
* A swipeable intro explaining how to use the app

## On third-party libraries
Dagger2, RxJava2, Retrofit2, and Kotlin coroutines enjoy full support from Google and the community. It's good to leverage these as they have been battle tested.
There is a library (or libraries) that implements swipes on Lists, but I decided to implement this functionality without it so older APIs can be supported. It may be that said libraries do suppoort down to API 16 but I didn't have enough time to check and verify. A good library may be optimal.

## Additional considerations
* The DetailFragment showcases a toolbar that can be completely discarded as Android devices have a back button.
* The app uses Kotlin coroutines (experimental) because I didn't want to bump Kotlin's version to at least 1.3
