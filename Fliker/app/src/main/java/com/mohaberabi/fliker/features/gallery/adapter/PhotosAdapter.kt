package com.mohaberabi.fliker.features.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mohaberabi.fliker.core.network.Photo
import com.mohaberabi.fliker.databinding.PhotoListItemBinding


class PhotosAdapter(
    private val photos: List<Photo>,
    private val onPhotoClicked: (Photo) -> Unit,
) : RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {


    inner class PhotoViewHolder(
        private val binding: PhotoListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.root.setOnClickListener { onPhotoClicked(photo) }
            binding.imageView.load(photo.url)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = PhotoListItemBinding.inflate(inflator, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(
        holder: PhotoViewHolder,
        position: Int
    ) {
        val photo = photos[position]
        holder.bind(photo)
    }
}