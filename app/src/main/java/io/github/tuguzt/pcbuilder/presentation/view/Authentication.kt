package io.github.tuguzt.pcbuilder.presentation.view

import com.google.android.gms.auth.api.signin.GoogleSignInOptions

/**
 * Google sign-in options of the application.
 */
val googleSignInOptions =
    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
