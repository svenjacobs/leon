Léon can be called programmatically from other applications to clean URLs.  
Just like the user-facing application it is based on the standard mechanism of intents.

To send text to Léon for cleaning, create an `Intent` with action `com.svenjacobs.app.leon.CLEAN`
and put the text to clean as an extra with key `Intent.EXTRA_TEXT`.

For Compose, use
[rememberLauncherForActivityResult](https://developer.android.com/reference/kotlin/androidx/activity/compose/package-summary#rememberLauncherForActivityResult(androidx.activity.result.contract.ActivityResultContract,kotlin.Function1))
from `androidx.activity:activity-compose`.

```kotlin
val launcher = rememberLauncherForActivityResult(
    ActivityResultContracts.StartActivityForResult()
) { result ->
    if (result.resultCode == Activity.RESULT_OK) {
        val cleanedText = result.data?.getStringExtra(Intent.EXTRA_TEXT)
        // Do something with cleanedText
    }
}

val intent = Intent("com.svenjacobs.app.leon.CLEAN").apply {
    putExtra(Intent.EXTRA_TEXT, "text to clean")
}
launcher.launch(intent)
```

For legacy Android development use
[startActivityForResult](https://developer.android.com/reference/android/app/Activity#startActivityForResult(android.content.Intent,%20int))
and the related API for Fragments.

Note that the cleaning is performed in the context of the user settings, meaning enabled/disabled
sanitizers and other options are taken into account.
