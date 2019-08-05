package by.psu.nasaapi.ui.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import by.psu.nasaapi.R
import by.psu.nasaapi.databinding.CellApodImageBinding
import by.psu.nasaapi.databinding.CellApodVideoBinding
import by.psu.nasaapi.model.Apod
import by.psu.nasaapi.ui.common.AutoUpdatableAdapter
import kotlinx.android.synthetic.main.cell_apod_image.view.*
import kotlin.properties.Delegates

class ApodRecycler(
    private val dataBindingComponent: DataBindingComponent
): RecyclerView.Adapter<BaseViewHolder<*>>(), AutoUpdatableAdapter {

    private var apodList: List<Apod> by Delegates.observable(emptyList()) {
            _, old, new ->
        autoNotify(old, new) { o, n -> o.date == n.date }
    }

    internal fun setData(data: List<Apod>) {
        this.apodList = data
    }

    internal fun getItem(holder: RecyclerView.ViewHolder): Apod {
        return apodList[holder.adapterPosition]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_IMAGE -> {
                val binding = DataBindingUtil.inflate<CellApodImageBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.cell_apod_image,
                    parent,
                    false,
                    dataBindingComponent
                )
                ImageViewHolder(binding = binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<CellApodVideoBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.cell_apod_video,
                    parent,
                    false,
                    dataBindingComponent
                )
                VideoViewHolder(binding = binding)
            }
        }
    }

    override fun getItemCount(): Int = apodList.count()

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder) {
            is ImageViewHolder -> holder.bind(apodList[position])
            is VideoViewHolder -> holder.bind(apodList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = apodList[position]
        return when(item.mediaType) {
            "image" -> TYPE_IMAGE
            else ->TYPE_VIDEO
        }
    }

    inner class ImageViewHolder(private val binding: CellApodImageBinding) : BaseViewHolder<Apod>(binding.root) {
        override fun bind(item: Apod) {
            binding.imageData = item
            binding.cellApodImageImg.transitionName = item.date
            binding.executePendingBindings()

            binding.root.setOnClickListener {mView ->
                binding.imageData?.let {apod ->
                    val extras = FragmentNavigatorExtras(mView.cellApodImageImg to "infoApodImg")
                    val infoDirection = DataFragmentDirections.openInfoImage(apod)
                    mView.findNavController().navigate(infoDirection, extras)
                }
            }
        }
    }

    inner class VideoViewHolder(private val binding: CellApodVideoBinding) : BaseViewHolder<Apod>(binding.root) {
        override fun bind(item: Apod) {
            binding.videoData = item
            binding.executePendingBindings()

            binding.root.setOnClickListener {mView ->
                binding.videoData?.let {apod ->
                    val infoDirection = DataFragmentDirections.openInfoVideo(apod)
                    mView.findNavController().navigate(infoDirection)
                }
            }
        }
    }

    companion object {
        private const val TYPE_IMAGE = 0
        private const val TYPE_VIDEO = 1
    }
}