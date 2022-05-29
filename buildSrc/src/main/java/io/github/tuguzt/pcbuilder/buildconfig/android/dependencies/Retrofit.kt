package io.github.tuguzt.pcbuilder.buildconfig.android.dependencies

object Retrofit {
    private const val version = "2.9.0"

    const val dependency = "com.squareup.retrofit2:retrofit:$version"

    object ThirdParty {
        const val kotlinXSerializationConverter =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
        const val networkResponseAdapter = "com.github.haroldadmin:NetworkResponseAdapter:5.0.0"
    }
}
