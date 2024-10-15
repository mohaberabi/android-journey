package com.mohaberabi.kdraw

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mohaberabi.kdraw.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val blueSkyColor by lazy {
        ContextCompat.getColor(this, R.color.blue_sky)
    }
    private val sunsetSkyColor by lazy {
        ContextCompat.getColor(this, R.color.sunset_sky)
    }
    private val nightSkyColor by lazy {
        ContextCompat.getColor(this, R.color.night_sky)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.root.setOnClickListener {
            startAnimation()
        }
    }

    private fun startAnimation() {
        val sunYStart = binding.sun.top.toFloat()
        val sunYEnd = binding.sky.height.toFloat()
        val animator =
            ObjectAnimator.ofFloat(binding.sun, "y", sunYStart, sunYEnd)
                .setDuration(3000)
        animator.interpolator = AccelerateInterpolator()
        val colorsAnimator = ObjectAnimator.ofInt(
            binding.sky,
            "backgroundColor", blueSkyColor, sunsetSkyColor
        ).setDuration(3000)
        animator.start()
        colorsAnimator.start()
    }
}