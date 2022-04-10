package io.github.tuguzt.pcbuilder.presentation.view.account

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.tuguzt.pcbuilder.databinding.ActivityAuthBinding

/**
 * Activity for user login.
 */
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
    }
}
