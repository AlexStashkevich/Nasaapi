package by.psu.nasaapi.ui.data

import android.app.DatePickerDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import by.psu.nasaapi.R
import by.psu.nasaapi.binding.FragmentDataBindingComponent
import by.psu.nasaapi.di.Injectable
import kotlinx.android.synthetic.main.fragment_data.view.*
import java.util.*
import javax.inject.Inject

class DataFragment : Fragment(), DatePickerDialog.OnDateSetListener, Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var dataBindingComponent = FragmentDataBindingComponent(this)

    private lateinit var viewModel: DataViewModel
    private lateinit var mAdapter: ApodRecycler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = ApodRecycler(dataBindingComponent)
        view.apodRecycler.adapter = mAdapter

        view.apodFab.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            context?.let { context ->
                val datePicker = DatePickerDialog(context, this, year, month, day)
                datePicker.show()
            }
        }

        postponeEnterTransition()
        view.viewTreeObserver
            .addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(DataViewModel::class.java)

        viewModel.apods.observe(viewLifecycleOwner, Observer (mAdapter::setData))
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, day: Int) {
        viewModel.addApod("$year-$month-$day")
    }
}
