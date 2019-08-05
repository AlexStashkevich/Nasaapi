package by.psu.nasaapi.ui.info


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.databinding.DataBindingUtil

import by.psu.nasaapi.R
import by.psu.nasaapi.binding.FragmentDataBindingComponent
import by.psu.nasaapi.databinding.FragmentInfoVideoBinding
import by.psu.nasaapi.di.Injectable
import by.psu.nasaapi.model.Apod

class InfoVideoFragment : Fragment(), Injectable {

    private var dataBindingComponent = FragmentDataBindingComponent(this)
    private lateinit var binding: FragmentInfoVideoBinding
    private lateinit var apod: Apod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            apod = InfoVideoFragmentArgs.fromBundle(bundle = it).apod
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_info_video,
            container,
            false,
            dataBindingComponent
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.apodVideo = apod

        binding.apodVideoView.settings.apply {
            @SuppressLint("SetJavaScriptEnabled")
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
        }
        binding.apodVideoView.loadUrl(apod.url)
    }
}
