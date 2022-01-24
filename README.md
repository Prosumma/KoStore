# KoStore

KoStore allows the simple creation of a type-safe, hierarchical data store that is not "stringly typed". Any serializable data type may be stored.

```kotlin
class ConfigDomain(parent: AppDomain): ChildDomain<AppDomain>(NAME, parent) {
  companion object {
    const val NAME = "config"
  }

  val apiUrl: String by stored { "https://api.myserver.com" }
  val maxRetries: Int? by stored()
}

// Add ConfigDomain as a child of AppDomain
val AppDomain.config: ConfigDomain by child(ConfigDomain.NAME, ::ConfigDomain)

fun main() {
  val appDomain = AppDomain(SharedPreferencesContainer())
  appDomain.config.maxRetries = 5
  print(appDomain.config.apiUrl)
}
```