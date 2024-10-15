package com.mohaberabi.fliker.features.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.mohaberabi.fliker.R
import com.mohaberabi.fliker.databinding.FragmentPhotoDetilBinding

class PhotoDetilFragment : Fragment() {


    private val args: PhotoDetilFragmentArgs by navArgs()
    private var _binding: FragmentPhotoDetilBinding? = null

    private val binding get() = checkNotNull(_binding)

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentPhotoDetilBinding.inflate(
            layoutInflater,
            container,
            false
        )
        with(binding) {
            webView.apply {
                
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadUrl(args.url)
                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView?, newProgress: Int) {
                        if (newProgress == 100) {
                            loader.visibility = View.GONE
                        } else {
                            loader.visibility = View.VISIBLE
                            loader.progress = newProgress
                        }
                    }
                }
            }
        }
        return binding.root
    }


}