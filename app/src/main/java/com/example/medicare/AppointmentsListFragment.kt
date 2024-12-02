/*import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicare.AppointmentsDataClass
import com.example.medicare.DatabaseContract
import com.example.medicare.R

class AppointmentsListFragment : Fragment() {

    private lateinit var upcomingRecyclerView: RecyclerView
    private lateinit var upcomingAdapter: AppointmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.appointments_page, container, false)
        upcomingRecyclerView = view.findViewById(R.id.upcomingAppointments_recyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        upcomingAdapter = AppointmentAdapter(requireContext(), ArrayList())
        upcomingRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = upcomingAdapter
        }
    }

    fun updateAppointments(appointments: List<AppointmentsDataClass>) {
        upcomingAdapter.updateData(appointments)
    }

}*/
