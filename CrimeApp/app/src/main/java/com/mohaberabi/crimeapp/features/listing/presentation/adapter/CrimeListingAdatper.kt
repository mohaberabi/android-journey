package com.mohaberabi.crimeapp.features.listing.presentation.adapter

import android.icu.util.Calendar
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mohaberabi.crimeapp.core.model.Crime
import com.mohaberabi.crimeapp.databinding.CrimeListingItemBinding
import java.util.Date


class CrimeListingAdapter(
    private val onShare: (Crime) -> Unit,
    private val onPickupContact: (Crime) -> Unit,
    private val crimes: List<Crime>
) : RecyclerView.Adapter<CrimeListingAdapter.CrimeHolder>() {
    inner class CrimeHolder(
        private val binding: CrimeListingItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(crime: Crime) {
            with(binding) {
                crimeTtl.text = crime.title
                crimeDate.text = crime.date.toSimpleDate()
                share.setOnClickListener {
                    onShare(crime)
                }
                contact.setOnClickListener {
                    onPickupContact(crime)
                }

            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CrimeHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = CrimeListingItemBinding.inflate(inflator, parent, false)

        return CrimeHolder(
            binding
        )
    }

    override fun getItemCount(): Int = crimes.size

    override fun onBindViewHolder(
        holder: CrimeHolder,
        position: Int
    ) {
        val crime = crimes[position]
        holder.bind(crime)
    }
}

fun Date.toSimpleDate(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return "${calendar.get(Calendar.DAY_OF_MONTH)}-${calendar.get(Calendar.MONTH)}-${
        calendar.get(
            Calendar.YEAR
        )
    }"
}