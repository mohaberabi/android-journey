package com.mohaberabi.crimeapp.features.detail.presetnation.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mohaberabi.crimeapp.CrimeApplication
import com.mohaberabi.crimeapp.MainActivity
import com.mohaberabi.crimeapp.R
import com.mohaberabi.crimeapp.core.model.Crime
import com.mohaberabi.crimeapp.core.presentation.collectLifeCycleFlow
import com.mohaberabi.crimeapp.core.presentation.fragments.DatePickerFragment
import com.mohaberabi.crimeapp.core.presentation.viewmodelFactory
import com.mohaberabi.crimeapp.core.utils.createUriFroFile
import com.mohaberabi.crimeapp.core.utils.queryContact
import com.mohaberabi.crimeapp.databinding.FragmentCrimeDetailBinding
import com.mohaberabi.crimeapp.features.detail.presetnation.viewmodel.CrimeDetailViewModel
import com.mohaberabi.crimeapp.features.listing.presentation.adapter.toSimpleDate
import kotlinx.coroutines.launch
import java.io.File
import java.text.DateFormat
import java.util.Date


class CrimeDetailFragment : Fragment() {

    private var _binding: FragmentCrimeDetailBinding? = null
    private val repository = CrimeApplication.appModule.crimeRepository
    private val viewmodel by viewModels<CrimeDetailViewModel>(
        factoryProducer = { viewmodelFactory { CrimeDetailViewModel(repository) } },
    )
    private val contactLauncher =
        registerForActivityResult(
            ActivityResultContracts.PickContact(),
        ) { uri ->
            uri?.let {
                contactChanged(it)
            }
        }
    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture(),
    ) { done ->
        if (done) {
            viewmodel.choosedImgChanged()
        }
    }


    private val binding get() = checkNotNull(_binding)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrimeDetailBinding.inflate(
            layoutInflater,
            container,
            false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        updateUi()
    }


    private fun updateUi() {
        collectLifeCycleFlow(viewmodel.state) { state ->
            with(binding) {
                suspectText.text = state.suspect
                crimeDate.text = state.date.toSimpleDate()
                state.choosedImgName?.let {
                    showCrimeImage(it)
                }
            }

        }
    }

    private fun setupClickListeners() {
        with(binding) {
            crimeDate.setOnClickListener {
                DatePickerFragment(
                    onDatePicked = {
                        viewmodel.chooseDate(it)
                    },
                ).show(childFragmentManager, DatePickerFragment.TAG)
            }
            pickupSuspect.setOnClickListener {
                getContact()
            }
            pickupImage.setOnClickListener {
                takePic()
            }
            crimeTitle.doOnTextChanged { text, _, _, _ ->
                viewmodel.titleChange(text.toString())
            }
            crimeSolved.setOnCheckedChangeListener { _, _ ->
                viewmodel.toggleIsSolved()
            }
        }
    }

    private fun getContact() = contactLauncher.launch(null)

    private fun takePic() {
        val now = System.currentTimeMillis()
        val ext = "JPG"
        val uriWithFile = requireContext().createUriFroFile(
            name = now.toString(),
            ext = ext
        )
        val absoulePath = uriWithFile.second.absolutePath
        viewmodel.tempImgChanged(absoulePath)
        cameraLauncher.launch(uriWithFile.first)
    }

    private fun contactChanged(uri: Uri) {
        val contact = requireContext().queryContact(uri)
        viewmodel.suspectChanged(contact)
    }

    private fun showCrimeImage(file: String) {
        val bitmap = BitmapFactory.decodeFile(file)
        binding.pickupImage.setImageBitmap(bitmap)
//        binding.pickupImage.setImageURI(Uri.parse(file))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}