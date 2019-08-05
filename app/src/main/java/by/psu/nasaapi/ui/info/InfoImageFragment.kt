package by.psu.nasaapi.ui.info

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import by.psu.nasaapi.R
import by.psu.nasaapi.binding.FragmentDataBindingComponent
import by.psu.nasaapi.databinding.FragmentInfoImageBinding
import by.psu.nasaapi.di.Injectable
import by.psu.nasaapi.model.Apod
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class InfoImageFragment : Fragment(), Injectable {

    private var dataBindingComponent = FragmentDataBindingComponent(this)
    private lateinit var binding: FragmentInfoImageBinding
    private lateinit var apod: Apod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            apod = InfoImageFragmentArgs.fromBundle(bundle = it).apod
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_info_image,
            container,
            false,
            dataBindingComponent
        )

        setupAnimation()
        setupListener()

        return binding.root
    }

    private fun setupListener() {
        binding.infoApodImg.setOnClickListener {
            val imageDirection = InfoImageFragmentDirections.openImageDialog(apod.url)
            findNavController().navigate(imageDirection)
        }
    }

    private fun setupAnimation() {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.move)
        postponeEnterTransition()
        binding.imageRequestListener = object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                startPostponedEnterTransition()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                startPostponedEnterTransition()
                return false
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.data = apod
    }
}
