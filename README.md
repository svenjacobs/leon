![example workflow](https://github.com/svenjacobs/leon/actions/workflows/android.yml/badge.svg)

**Léon - The URL Cleaner** is an Android application which removes tracking and other obsolete
parameters from a URL before sharing. Its usage is simple, integrating into Android's standard
sharing mechanism.

Léon runs on Android 5.0 and later, is open source and does not contain any tracking or advertising
frameworks. Léon does not collect any data about you.

The benefits of removing tracking parameters are:

- Protects your and the recipient's privacy
- Improves readability of links
- Saves characters (in Tweets for example)

## Installation

- [Google Play Store](https://play.google.com/store/apps/details?id=com.svenjacobs.app.leon)

## How to use

When sharing a link, select **URL Cleaner** as the receiving application. Then from within URL
Cleaner share the cleaned URL to the actual target application.

<img src="./app/src/main/res/drawable-nodpi/howto_pixel_5.webp" width="250" />

## Issues & feedback

The app is currently in public beta phase. Please
report [bugs](https://github.com/svenjacobs/leon/issues) or
give [feedback](https://github.com/svenjacobs/leon/discussions).

## Technical implementation

This app is also meant as a blueprint for modern Android development, presenting and evaluating
recommended and cutting-edge technologies and libraries such as:

- [Kotlin](https://kotlinlang.org/) programming language
- Kotlin [coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- Jetpack [Compose](https://developer.android.com/jetpack/compose)
- Jetpack [Navigation](https://developer.android.com/guide/navigation)
- Jetpack [Hilt](https://dagger.dev/hilt/)
- several other Jetpack & AndroidX libraries
