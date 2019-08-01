package by.psu.nasaapi.ui.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import by.psu.nasaapi.R
import by.psu.nasaapi.databinding.CellApodBinding
import by.psu.nasaapi.model.Apod
import by.psu.nasaapi.ui.common.AutoUpdatableAdapter
import kotlinx.android.synthetic.main.cell_apod.view.*
import kotlin.properties.Delegates

class ApodRecycler(
    private val dataBindingComponent: DataBindingComponent):
    RecyclerView.Adapter<ApodRecycler.ApodViewHolder>(), AutoUpdatableAdapter {

    private var apodList: List<Apod> by Delegates.observable(emptyList()) {
            prop, old, new ->
        autoNotify(old, new) { o, n -> o.date == n.date }
    }

    internal fun setData(data: List<Apod>) {
        apodList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodViewHolder {
        val binding = createBinding(parent)
        return ApodViewHolder(binding = binding)
    }

    override fun getItemCount(): Int = apodList.count()

    override fun onBindViewHolder(holder: ApodViewHolder, position: Int) = holder.bindData(data = apodList[position])

    private fun createBinding(parent: ViewGroup): CellApodBinding {
        val binding = DataBindingUtil.inflate<CellApodBinding>(
            LayoutInflater.from(parent.context),
            R.layout.cell_apod,
            parent,
            false,
            dataBindingComponent
        )

        binding.root.setOnClickListener {mView ->
            binding.data?.let {apod ->
                val extras = FragmentNavigatorExtras(mView.cellApodImg to "infoApodImg")
                val infoDirection = DataFragmentDirections.openInfo(apod)
                mView.findNavController().navigate(infoDirection, extras)
            }
        }

        return binding
    }

    inner class ApodViewHolder(private val binding: CellApodBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(data: Apod) {
            binding.data = data
            binding.cellApodImg.transitionName = data.date
            binding.executePendingBindings()
        }
    }
}