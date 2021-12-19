package io.github.tuguzt.pcbuilder.presentation.view.account

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.tuguzt.pcbuilder.databinding.ActivityAuthBinding

/**
 * Activity for user login.
 */
class AuthActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityAuthBinding
    private inline val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
    }
}
