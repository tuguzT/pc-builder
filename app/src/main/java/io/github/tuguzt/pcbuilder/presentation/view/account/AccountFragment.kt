package io.github.tuguzt.pcbuilder.presentation.view.account

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.squareup.picasso.Picasso
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentAccountBinding
import io.github.tuguzt.pcbuilder.presentation.model.user.Admin
import io.github.tuguzt.pcbuilder.presentation.model.user.UserOrdinal
import io.github.tuguzt.pcbuilder.presentation.model.user.role
import io.github.tuguzt.pcbuilder.presentation.view.*
import io.github.tuguzt.pcbuilder.presentation.view.toastShort
import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AccountViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * A [Fragment] subclass which represents information about user account.
 */
class AccountFragment : Fragment() {
    private val viewModel: AccountViewModel by navGraphViewModels(R.id.main_nav_graph)

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
        hasOptionsMenu = true
        return FragmentAccountBinding.inflate(inflater, container, false)
            .also { _binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireActivity()
        val contract = ActivityResultContracts.StartActivityForResult()
        _loginLauncher = registerForActivityResult(contract) {
            if (it.resultCode == AppCompatActivity.RESULT_CANCELED) {
                activity.finish()
                return@registerForActivityResult
            }
            activity.invalidateOptionsMenu()
            bindUser()
        }

        bindUser()
        binding.signOut.setOnClickListener {
            val googleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions)
            activity.userSharedPreferences.edit { clear() }
            lifecycleScope.launch {
                googleSignInClient.signOut().await()
                toastShort { "Signed out successfully" }.show()
                val loginIntent = Intent(activity, LoginActivity::class.java)
                loginLauncher.launch(loginIntent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_account_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val user = viewModel.currentUser
        val moderators = menu.findItem(R.id.toolbar_account_moderators)!!
        moderators.isVisible = user is Admin
    }

    private fun bindUser() {
        val user = viewModel.currentUser!!

        binding.run {
            username.text = user.username
            email.text = user.email

            val uri = user.imageUri
            if (uri != null) {
                Picasso.get().load(uri).into(imageView)
            } else {
                imageView.setImageResource(R.drawable.ic_baseline_person_24)
            }

            role.visibility = View.GONE
            if (user !is UserOrdinal) {
                role.visibility = View.VISIBLE
                role.text = requireContext().getString(R.string.display_role, user.role)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
