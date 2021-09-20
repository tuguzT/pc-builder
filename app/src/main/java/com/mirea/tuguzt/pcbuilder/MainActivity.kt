package com.mirea.tuguzt.pcbuilder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.mirea.tuguzt.pcbuilder.databinding.ActivityMainBinding
//import com.mirea.tuguzt.pcbuilder.presentation.repository.RepositoryAccess

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding

    val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

//        RepositoryAccess.initRoom(application)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener {
            val fragmentContainer = binding.navHostFragment
            fragmentContainer.findNavController().navigate(R.id.action_component_add_fragment)
        }
    }
}
