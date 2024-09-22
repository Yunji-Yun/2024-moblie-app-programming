package com.example.mid20220823

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mid20220823.databinding.DialogTypeBinding
import com.example.mid20220823.databinding.FragmentTwoBinding
import java.text.SimpleDateFormat

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

    var datas : MutableList<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTwoBinding.inflate(inflater, container, false)

        datas = savedInstanceState?.let {
            it.getStringArrayList("datas")?.toMutableList()
        } ?: let {
            mutableListOf<kotlin.String>()
        }

        var checkedfood = ""
        val dialogBinding = DialogTypeBinding.inflate(layoutInflater)
        val eventHandler3 = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if(which == DialogInterface.BUTTON_POSITIVE){
                    Log.d("mobileapp", "BUTTON_POSITIVE")
                    if(dialogBinding.rbtn1.isChecked){
//                      checkedfood =  dialogBinding.rbtn1.text.toString()
                    }
                    else if(dialogBinding.rbtn2.isChecked){
                        checkedfood =  dialogBinding.rbtn2.text.toString()
                    }
                    else if(dialogBinding.rbtn3.isChecked){
                        checkedfood =  dialogBinding.rbtn3.text.toString()
                    }
                }
                else if (which == DialogInterface.BUTTON_NEGATIVE){
                    Log.d("mobileapp", "BUTTON_NEGATIVE")
                }
            }
        }
        val customDlg = AlertDialog.Builder(requireContext()).run(){
            setTitle("종류 선택")
            setIcon(android.R.drawable.ic_dialog_alert)
            setView(dialogBinding.root)

            setNegativeButton("아니오", eventHandler3)
            setPositiveButton("예", eventHandler3)
            create()
        }
        binding.mainFab.setOnClickListener() {
            customDlg.show()
        }



        // 3. 어댑터 추가
        var adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter // Myadapter(datas)

        // 4. 레이아웃 매니저 추가
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        // 6. 데코레이션 추가
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        )

        // 5. 결과를 전달 받아서 처리
        val requestLauncher: ActivityResultLauncher<Intent>
                = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            it.data!!.getStringExtra("result")?.let{
                if(it != "") {
                    datas?.add(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }

        binding.mainFab.setOnClickListener {
            // val intent = Intent(this, AddActivity::class.java)

            // intent.putExtra("checkedFood", checkedFood)

            // requestLauncher.launch(intent)
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("datas", ArrayList(datas))
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