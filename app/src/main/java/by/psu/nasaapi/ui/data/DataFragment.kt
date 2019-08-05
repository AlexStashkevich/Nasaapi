package by.psu.nasaapi.ui.data

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.psu.nasaapi.R
import by.psu.nasaapi.binding.FragmentDataBindingComponent
import by.psu.nasaapi.di.Injectable
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_data.view.*
import javax.inject.Inject

class DataFragment : Fragment(), Injectable {

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

        addSwipe(view.apodRecycler)
        setupFab(view.apodFab)
        setupAnimation(view)
    }

    private fun setupAnimation(view: View) {
        postponeEnterTransition()
        view.viewTreeObserver
            .addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
    }

    private fun addSwipe(recycler: RecyclerView) {
        val swipeCallBack = object: ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                return makeMovementFlags(0, ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = mAdapter.getItem(viewHolder)
                viewModel.removeApod(item)
            }
        }
        val mItemTouchHelper = ItemTouchHelper(swipeCallBack)
        mItemTouchHelper.attachToRecyclerView(recycler)

    }

    private fun setupFab(fab: FloatingActionButton) {
        val direction = DataFragmentDirections.openDatePicker()
        val listener = Navigation.createNavigateOnClickListener(direction)
        fab.setOnClickListener(listener)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(DataViewModel::class.java)

        viewModel.apodLiveData.observe(viewLifecycleOwner, Observer(mAdapter::setData))
    }
}
