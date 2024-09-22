package com.example.mt_me

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mt_me.databinding.FragmentOneBinding
import com.example.mt_me.databinding.FragmentTwoBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TwoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TwoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTwoBinding.inflate(inflater, container, false)

        val searchWrd = arguments?.getString("searchName") ?: "가래나무"

        val call: Call<PlantResponse> = RetrofitConnection.plantNetServ.getPlantList(
            searchWrd,
            "n/7fVcllIsn+SbZE9RQagnKysnAGx17cS7lGCITkUNvvd4jGID0W+FXrz1AGrjk3MZKTl5lzmcM+RxNpx2j56g==",
            1,
            10,
        )

        call?.enqueue(object : Callback<PlantResponse> {
            override fun onResponse(call: Call<PlantResponse>, response: Response<PlantResponse>) {
                if(response.isSuccessful) {
                    Log.d("mobileApp", "$response")
                    Log.d("mobileApp", "${response.body()}")
                    binding.plantRecyclerView.adapter = PlantAdapter(response.body()!!.body!!.items!!.item)
                    binding.plantRecyclerView.layoutManager = LinearLayoutManager(activity)
                    binding.plantRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
                }
            }

            override fun onFailure(call: Call<PlantResponse>, t: Throwable) {
                Log.d("mobileApp", "onFailure ${call.request()}")
            }
        })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TwoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TwoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}