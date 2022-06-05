package io.github.tuguzt.pcbuilder.presentation.di

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.tuguzt.pcbuilder.data.datasource.AuthDataSource
import io.github.tuguzt.pcbuilder.data.datasource.UserTokenDataSource
import io.github.tuguzt.pcbuilder.data.datasource.UsersDataSource
import io.github.tuguzt.pcbuilder.data.datasource.local.impl.LocalComponentDataSource
import io.github.tuguzt.pcbuilder.data.datasource.local.impl.LocalUserTokenDataSource
import io.github.tuguzt.pcbuilder.data.datasource.remote.api.BackendAuthAPI
import io.github.tuguzt.pcbuilder.data.datasource.remote.api.BackendUsersAPI
import io.github.tuguzt.pcbuilder.data.datasource.remote.impl.RemoteAuthDataSource
import io.github.tuguzt.pcbuilder.data.datasource.remote.impl.RemoteComponentDataSource
import io.github.tuguzt.pcbuilder.data.datasource.remote.impl.RemoteUsersDataSource
import io.github.tuguzt.pcbuilder.data.repository.*
import io.github.tuguzt.pcbuilder.data.repository.impl.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton
import io.github.tuguzt.pcbuilder.domain.interactor.serialization.json as domainJson

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideJson(): Json = Json(domainJson) {}

    private const val serverClientId = "SECRET"

    @Provides
    @Singleton
    fun provideGoogleSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(serverClientId)
            .requestEmail()
            .build()
    }

    @Provides
    @Singleton
    fun provideGoogleSignInClient(
        @ApplicationContext context: Context,
        options: GoogleSignInOptions,
    ): GoogleSignInClient = GoogleSignIn.getClient(context, options)

    @Provides
    fun provideLastSignedInGoogleAccount(@ApplicationContext context: Context): GoogleSignInAccount? =
        GoogleSignIn.getLastSignedInAccount(context)

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context): EncryptedSharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val fileName = "user-credentials"
        val encryptedSharedPreferences = EncryptedSharedPreferences.create(
            fileName,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
        return encryptedSharedPreferences as EncryptedSharedPreferences
    }

    @Provides
    @Local
    fun provideLocalComponentRepository(dataSource: LocalComponentDataSource): ComponentRepository =
        ComponentRepositoryImpl(dataSource)

    @Provides
    @Remote
    fun provideRemoteComponentRepository(dataSource: RemoteComponentDataSource): ComponentRepository =
        ComponentRepositoryImpl(dataSource)

    @Provides
    fun provideAuthDataSource(backendAuthAPI: BackendAuthAPI): AuthDataSource =
        RemoteAuthDataSource(backendAuthAPI)

    @Provides
    fun provideUsersDataSource(backendUsersAPI: BackendUsersAPI): UsersDataSource =
        RemoteUsersDataSource(backendUsersAPI)

    @Provides
    fun provide(sharedPreferences: EncryptedSharedPreferences): UserTokenDataSource =
        LocalUserTokenDataSource(sharedPreferences)

    @Provides
    fun provideAuthRepository(dataSource: AuthDataSource): AuthRepository =
        AuthRepositoryImpl(dataSource)

    @Provides
    fun provideUsersRepository(dataSource: UsersDataSource): UsersRepository =
        UsersRepositoryImpl(dataSource)

    @Provides
    fun provideTokenRepository(dataSource: UserTokenDataSource): UserTokenRepository =
        UserTokenRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideCurrentUserRepository(): CurrentUserRepository = CurrentUserRepositoryImpl()
}
