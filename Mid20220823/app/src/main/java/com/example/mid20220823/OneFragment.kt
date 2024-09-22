package com.example.mid20220823

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.example.mid20220823.databinding.FragmentOneBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OneFragment : Fragment() {
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
        val binding = FragmentOneBinding.inflate(inflater, container, false)

        var dateString = ""
        binding.btnDate.setOnClickListener {
            DatePickerDialog(requireContext(), object: DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    Toast.makeText(requireContext(), "$year 년 ${month+1} 월 $dayOfMonth 일", Toast.LENGTH_LONG).show()
                    binding.date.text = "$year 년 ${month+1} 월 $dayOfMonth 일"
                    dateString = "$year 년 ${month+1} 월 $dayOfMonth 일"
                }
            }, 2024, 3, 27).show()
        }

        var timeString = ""
        binding.btnTime.setOnClickListener {
            TimePickerDialog(requireContext(), object: TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    Toast.makeText(requireContext(), "$hourOfDay 시 $minute 분", Toast.LENGTH_LONG).show()
                    binding.time.text = "$hourOfDay 시 $minute 분"
                    timeString = "$hourOfDay 시 $minute 분"
                }
            }, 14, 0, true).show()
        }

        var eatString = ""
        val eats= arrayOf<String>("많이", "보통", "적게")
        var selected = 0
        val eventHandler2 = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if(which == DialogInterface.BUTTON_POSITIVE){
                    Toast.makeText(requireContext(), "${eats[selected]}", Toast.LENGTH_LONG).show()
                    binding.eat.text = "${eats[selected]}"
                    eatString = "${eats[selected]}"
                }
                else if (which == DialogInterface.BUTTON_NEGATIVE){
                    Log.d("mobileapp", "BUTTON_NEGATIVE")
                }
            }
        }
        binding.btnAlertSingle.setOnClickListener {
            AlertDialog.Builder(requireContext()).run(){
                setTitle("식사량 선택")
                setIcon(android.R.drawable.ic_dialog_alert)
                setSingleChoiceItems(eats, 1, object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Log.d("mobileapp", "${eats[which]} 선택")
                        selected = which
                    }
                })
                setNegativeButton("아니오", eventHandler2)
                setPositiveButton("예", eventHandler2)
                show()
            }
        }

        val spec = arrayOf<String>("나트륨 적게", "설탕 적게")
        var specString = ""
        val eventHandler = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                if(which == DialogInterface.BUTTON_POSITIVE){
                    if(selected == 0){
                        specString = specString.toString() + spec[0] + " "
                    }
                    else if(selected == 1){
                        specString = specString.toString() + spec[1] + " "
                    }
                    Toast.makeText(requireContext(), specString, Toast.LENGTH_LONG).show()
                    binding.special.text = specString
                }
                else if (which == DialogInterface.BUTTON_NEGATIVE){
                    Log.d("mobileapp", "BUTTON_NEGATIVE")
                }
            }
        }
        binding.btnAlertMulti.setOnClickListener {
            AlertDialog.Builder(requireContext()).run(){
                setTitle("특이사항 선택")
                setIcon(android.R.drawable.ic_dialog_alert)
                // 초기 선택
                setMultiChoiceItems(spec, booleanArrayOf(false, true, true, false), object:
                    DialogInterface.OnMultiChoiceClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int, isChecked: Boolean) {
                        Log.d("mobileapp", "${spec[which]} ${if(isChecked) "선택" else "해제"}")
                    }
                })
                setNegativeButton("아니오", eventHandler)
                setPositiveButton("예", eventHandler)

                show()
            }
        }

        binding.btnSave.setOnClickListener {

            binding.btnSave.text = "수정"
            binding.showInfo.text = dateString +"\n"+timeString+"\n"+eatString+"\n"+specString
            binding.showInfo.visibility=View.VISIBLE
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OneFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OneFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}