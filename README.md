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

## Containers

An instance of `Container` stores the underlying data. `MemoryContainer` is provided, but since it stores its data in memory, it is suitable primarily for tests.

KoStore is a pure Kotlin library with no dependencies on Android. As a result, there is no `SharedPreferencesContainer` in this library. But the `Container` interface is trivial to implement:

```kotlin
class SharedPreferencesContainer(val prefs: SharedPreferences): Container {
  fun getJSON(key: Key): String? =
    prefs.getString(key.toString(), null)

  fun setJSON(key: Key, json: String) =
    prefs.edit {
      putString(key.toString(), json)
    }

  fun contains(key: Key): Boolean =
    prefs.contains(key.toString())
}
```

## Thread Safety

Thread safety (if any) is provided by the underlying container. Thus, KoStore is not inherently thread-safe, but can be if the right container is used.

## Domains

A `Domain` is a class which serves as a naming container for properties and other domains. (See `ConfigDomain` above for an example.) Domains are arranged in a hierarchy with `AppDomain` at the root.

Each domain (except `AppDomain`) is identified by a `Name`, as is each property. These hierarchical combination of these names produces a `Key`, which is a list of names separated by `/`.

For example, the `apiUrl` in `ConfigDomain` above has the name `apiUrl`. Its domain, `ConfigDomain`, has the name `config`. The root `AppDomain` has no name, i.e., it's an empty string. Combining all of this with `/`, we get the `Key` `/config/apiUrl`, which is used to store the value (as a JSON string) in the underlying `Container`.

Domains can be nested at any level, though `AppDomain` is always at the root: `/foo/bar/baz`, etc.