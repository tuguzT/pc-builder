package io.github.tuguzt.pcbuilder.presentation.view

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import io.github.tuguzt.pcbuilder.R

/**
 * If this fragment would like to participate in populating the options menu
 * by receiving a call to [Fragment.onCreateOptionsMenu] and related methods.
 */
inline var Fragment.hasOptionsMenu
    @SuppressLint("RestrictedApi")
    get() = hasOptionsMenu()
    set(value) = setHasOptionsMenu(value)

/**
 * This function clears [back stack][NavController.mBackStack] completely and navigates to the root.
 */
fun Fragment.popBackStackRoot() {
    val activity = requireActivity()
    val navController = activity.findNavController(R.id.main_nav_host_fragment)
    val navHostFragment = activity.supportFragmentManager
        .findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment

    val inflater = navHostFragment.navController.navInflater
    val graph = inflater.inflate(R.navigation.main_nav_graph)
    graph.startDestination = R.id.components_nav_graph
    navController.graph = graph
}

/**
 * Returns root view of the parent activity or null
 * if the fragment is associated with a Context instead or activity is not visual.
 *
 * @see requireRootView
 */
val Fragment.rootView: View? get() = activity?.window?.decorView?.findViewById(android.R.id.content)

/**
 * Returns root view of the parent activity.
 *
 * @see rootView
 */
val Fragment.requireRootView get() = requireNotNull(rootView)
