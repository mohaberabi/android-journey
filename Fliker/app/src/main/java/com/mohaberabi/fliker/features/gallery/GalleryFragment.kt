package com.mohaberabi.fliker.features.gallery

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.mohaberabi.fliker.FlickerApplication
import com.mohaberabi.fliker.PollWorker
import com.mohaberabi.fliker.R
import com.mohaberabi.fliker.core.collectLifeCycleFlow
import com.mohaberabi.fliker.core.viewmodelFactory
import com.mohaberabi.fliker.databinding.FragmentGalleryBinding
import com.mohaberabi.fliker.features.gallery.adapter.PhotosAdapter
import com.mohaberabi.fliker.features.gallery.viewmodel.GalleryViewModel
import kotlinx.coroutines.launch


class GalleryFragment : Fragment() {


    private val repository by lazy {
        FlickerApplication.appModule.flickerRepository
    }

    private val viewmodel by viewModels<GalleryViewModel>(
        factoryProducer = {
            viewmodelFactory {
                GalleryViewModel(repository)
            }
        },
    )
    private val permissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) {}
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = checkNotNull(_binding)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enqueueWork()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (Build.VERSION.SDK_INT >= 33) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        _binding = FragmentGalleryBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        collectLifeCycleFlow(
            viewmodel.photos,
        ) { photos ->
            val adapter = PhotosAdapter(
                photos,
                onPhotoClicked = {
                    goToWebView(it.url)
                },
            )
            binding.photoRecView.adapter = adapter
            binding.photoRecView.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }


    private fun enqueueWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<PollWorker>()
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(requireContext())
            .enqueue(workRequest)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun goToWebView(url: String) {
        findNavController().navigate(GalleryFragmentDirections.goToWebView(url))
    }

    private fun openWeb(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}