package com.mirea.tuguzt.pcbuilder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.mirea.tuguzt.pcbuilder.databinding.ActivityMainBinding
import com.mirea.tuguzt.pcbuilder.presentation.repository.ComponentRepository

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    companion object {
        lateinit var repository: ComponentRepository
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        repository = ComponentRepository(application)

        val view = binding.root
        setContentView(view)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val fab = binding.fab
        fab.setOnClickListener {
            val fragmentContainer = binding.navHostFragment
            fragmentContainer.findNavController().navigate(R.id.action_component_add_fragment)
        }
    }
}
