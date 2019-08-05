package by.psu.nasaapi.ui.image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import by.psu.nasaapi.R
import by.psu.nasaapi.binding.FragmentDataBindingComponent
import by.psu.nasaapi.databinding.DialogFragmentImageBinding
import by.psu.nasaapi.di.Injectable

class ImageFragmentDialog : DialogFragment(), Injectable {

    private var dataBindingComponent = FragmentDataBindingComponent(this)
    private lateinit var binding: DialogFragmentImageBinding
    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            imageUrl = ImageFragmentDialogArgs.fromBundle(it).imageUrl
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_fragment_image,
            container,
            false,
            dataBindingComponent
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.imageUrl = imageUrl
    }
}