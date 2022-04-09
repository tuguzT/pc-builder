package io.github.tuguzt.pcbuilder.presentation.view.account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.haroldadmin.cnradapter.NetworkResponse
import com.squareup.picasso.Picasso
import io.github.tuguzt.pcbuilder.R
import io.github.tuguzt.pcbuilder.databinding.FragmentAccountBinding
import io.github.tuguzt.pcbuilder.domain.model.user.UserRole
import io.github.tuguzt.pcbuilder.presentation.model.user.toUser
import io.github.tuguzt.pcbuilder.presentation.view.*
import io.github.tuguzt.pcbuilder.presentation.viewmodel.account.AccountViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * A [Fragment] subclass which represents information about user account.
 */
class AccountFragment : Fragment() {
    companion object {
        @JvmStatic
        private val LOG_TAG = AccountFragment::class.simpleName
    }

    private val accountViewModel: AccountViewModel by sharedViewModel()

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
        hasOptionsMenu = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activity = requireActivity()
        val contract = ActivityResultContracts.StartActivityForResult()
        _loginLauncher = registerForActivityResult(contract) {
            if (it.resultCode == AppCompatActivity.RESULT_CANCELED) {
                activity.finish()
                return@registerForActivityResult
            }
            accountViewModel.currentUser = it.data?.extras?.toUser()
            activity.invalidateOptionsMenu()
            bindUser()
        }

        bindUser()
        binding.signOut.setOnClickListener {
            val googleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions)
            activity.userSharedPreferences.edit { clear() }
            lifecycleScope.launch {
                googleSignInClient.signOut().await()
                showToast(requireContext(), R.string.signed_out)
                val loginIntent = Intent(activity, AuthActivity::class.java)
                loginLauncher.launch(loginIntent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_account_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val user = accountViewModel.currentUser ?: return
        val moderators = menu.findItem(R.id.toolbar_account_moderators)!!
        moderators.isVisible = user.role == UserRole.Administrator
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.toolbar_account_moderators -> {
            val action = AccountFragmentDirections.actionListFragment()
            findNavController().navigate(action)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun bindUser(): Unit = binding.run {
        lifecycleScope.launch {
            val application = requireActivity().application
            when (val result = accountViewModel.updateUserFromBackend(application)) {
                is NetworkResponse.Success -> {
                    val user = requireNotNull(accountViewModel.currentUser)

                    val sharedPreferences = application.userSharedPreferences
                    username.text = sharedPreferences.getString("username", null)
                        ?: requireNotNull(sharedPreferences.getString("google_username", null))
                    email.text = user.email ?: getString(R.string.email_not_set)

                    val uri = user.imageUri
                    if (uri != null) {
                        Picasso.get().load(uri).into(imageView)
                    } else {
                        imageView.setImageResource(R.drawable.ic_baseline_person_24)
                    }

                    role.visibility = View.GONE
                    if (user.role != UserRole.User) {
                        role.visibility = View.VISIBLE
                        role.text = requireContext().getString(R.string.display_role, user.role)
                    }
                }
                is NetworkResponse.ServerError -> {
                    Log.e(LOG_TAG, "Server error", result.error)
                    showSnackbar(root, R.string.server_error)
                }
                is NetworkResponse.NetworkError -> {
                    Log.e(LOG_TAG, "Network error", result.error)
                    showSnackbar(root, R.string.network_error)
                }
                is NetworkResponse.UnknownError -> {
                    Log.e(LOG_TAG, "Application error", result.error)
                    showSnackbar(root, R.string.application_error)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
