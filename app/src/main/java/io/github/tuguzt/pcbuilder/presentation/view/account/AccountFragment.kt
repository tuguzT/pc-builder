package io.github.tuguzt.pcbuilder.presentation.view.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.squareup.picasso.Picasso
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentAccountBinding
import io.github.tuguzt.pcbuilder.presentation.model.user.UserOrdinal
import io.github.tuguzt.pcbuilder.presentation.model.user.role
import io.github.tuguzt.pcbuilder.presentation.view.googleSignInOptions
import io.github.tuguzt.pcbuilder.presentation.view.toastShort
import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AccountViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * A [Fragment] subclass which represents information about user account.
 */
class AccountFragment : Fragment() {
    private val viewModel: AccountViewModel by navGraphViewModels(R.id.account_nav_graph)

    private var _binding: FragmentAccountBinding? = null
    // This helper property is only valid between `onCreateView` and `onDestroyView`.
    private inline val binding get() = _binding!!

    private lateinit var _loginLauncher: ActivityResultLauncher<Intent>
    private inline val loginLauncher get() = _loginLauncher

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)

        val activity = requireActivity()
        val contract = ActivityResultContracts.StartActivityForResult()
        _loginLauncher = registerForActivityResult(contract) {
            if (it.resultCode == AppCompatActivity.RESULT_CANCELED) {
                activity.finish()
                return@registerForActivityResult
            }
            bindAccount()
        }

        bindAccount()
        binding.signOut.setOnClickListener {
            val googleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions)
            lifecycleScope.launch {
                googleSignInClient.signOut().await()
                toastShort { "Signed out successfully" }.show()
                val loginIntent = Intent(activity, LoginActivity::class.java)
                loginLauncher.launch(loginIntent)
            }
        }

        return binding.root
    }

    private fun bindAccount() {
        binding.run {
            val account = viewModel.account

            username.text = account.username
            email.text = account.email
            Picasso.get().load(account.imageUri).into(imageView)

            role.visibility = View.GONE
            if (account !is UserOrdinal) {
                role.visibility = View.VISIBLE
                role.text = requireContext().getString(R.string.display_role, account.role)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
