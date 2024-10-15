package com.mohaberabi.crimeapp.features.listing.presentation.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohaberabi.crimeapp.R
import com.mohaberabi.crimeapp.databinding.FragmentCrimeDetailBinding
import com.mohaberabi.crimeapp.databinding.FragmentCrimeListingBinding

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mohaberabi.crimeapp.core.model.Crime
import com.mohaberabi.crimeapp.core.presentation.collectLifeCycleFlow
import com.mohaberabi.crimeapp.features.listing.presentation.adapter.CrimeListingAdapter
import com.mohaberabi.crimeapp.features.listing.presentation.viewmodel.CrimeListingViewModel
import kotlinx.coroutines.launch

class CrimeListingFragment : Fragment() {

    private val viewmodel by viewModels<CrimeListingViewModel>()


    private var _binding: FragmentCrimeListingBinding? = null
    private val binding get() = checkNotNull(_binding)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureMenu()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCrimeListingBinding.inflate(
            layoutInflater,
            container,
            false
        )

        val layoutManager = LinearLayoutManager(requireContext())
        binding.crimeRecView.layoutManager = layoutManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureAppBar()

        collectLifeCycleFlow(
            viewmodel.crimes,
        ) { crimes ->
            binding.crimeRecView.adapter =
                CrimeListingAdapter(
                    crimes = crimes,
                    onPickupContact = {
                    },
                    onShare = {
                        shareCrimeWithChoice(it.title)
                    }
                )

        }


    }


    private fun configureAppBar() {
        binding.toolBar.apply {
            (requireActivity() as AppCompatActivity).setSupportActionBar(this)
        }
    }

    private fun configureMenu() {
        val provider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_crime_list, menu)

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.new_crime -> goToDetail()
                }
                return true
            }
        }

        requireActivity().addMenuProvider(provider)
    }


    private fun goToDetail() =
        findNavController().navigate(CrimeListingFragmentDirections.goToDetail())

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun shareCrime(crimeData: String) {
        val intent = createShareIntent(crimeData)
        startActivity(intent)
    }

    private fun createShareIntent(crimeData: String): Intent {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, crimeData)
            putExtra(Intent.EXTRA_SUBJECT, "Crime Report")
        }
        return intent
    }

    private fun shareCrimeWithChoice(crime: String) {

        val intent = createShareIntent(crime)
        val choiceIntent = Intent.createChooser(intent, "choose how to share your crime")
        startActivity(choiceIntent)
    }


}