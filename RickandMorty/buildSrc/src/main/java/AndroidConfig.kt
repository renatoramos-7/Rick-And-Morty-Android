object AndroidConfig {

    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0

    const val applicationId = "com.renatoramos.rickandmorty"

    const val compileSdk = 36
    const val minSdk = 21
    const val targetSdk = 36

    const val versionCode = versionMajor * 100 + versionMinor * 10 + versionPatch
    const val versionName = "$versionMajor.$versionMinor.$versionPatch"

    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

}
