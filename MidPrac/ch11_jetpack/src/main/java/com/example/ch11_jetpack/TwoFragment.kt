package com.example.ch11_jetpack

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ch11_jetpack.R
import com.example.ch11_jetpack.databinding.FragmentTwoBinding
import com.example.ch11_jetpack.databinding.ItemRecyclerviewBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

// 3-3. viewHolder 클래스 정의
// 변경 가능: ItemRecyclerviewBinding, datas, onBindViewHolder
class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)
// 3-1. adapter 클래스 정의
class MyAdapter(val datas:MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // 3-4. 위에서 만든 MyViewHolder 만들기
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    // 3-5. 전달받은 데이터를 화면에 나타냄
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.itemData.text = datas[position]
    }
    // 3-2. 가진 데이터 수 반환
    override fun getItemCount(): Int {
        return datas.size
    }

}

// 5. ItemDecoration 클래스 정의
class MyItemDecoration(val context: Context) : RecyclerView.ItemDecoration() {
    // 5-1. 그림 -> 항목
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        // 리소스의 이미지 가져와서 뿌림 (맨 위 배치)
        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
            0f, 0f, null)
    }
    // 5-2. 항목 -> 그림
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
            500f, 500f, null)
    }
    // 5-3. 각 아이템 꾸미기
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        // 백 그라운드 컬러 바꿔주기
        view.setBackgroundColor(Color.parseColor(("#123456")))
    }
}
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
        // 1. 바인딩 변수 정의
        val binding = FragmentTwoBinding.inflate(inflater, container, false)

        // 2. 뷰에 들어갈 데이터 만들기 (변경 가능 리스트로)
        var datas = mutableListOf<String>()
        for(i in 1..10){
            datas.add("Item $i")
        }

        // 3. adapter(위에서 정의한 별도의 클래스) & viewHolder
        var adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter // MyAdapter(datas)

        // 4. layoutManager
        // 4-1. linearlayout로 배치
        var linearlayout = LinearLayoutManager(activity)
        linearlayout.orientation = LinearLayoutManager.HORIZONTAL
        // 4-2. gridlayout로 배치
        var gridlayout = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)

        // binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = gridlayout

        // 5. 선택적으로 화면을 꾸밀 수 있음
        binding.recyclerView.addItemDecoration(MyItemDecoration(activity as Context))

        // 6. 머터리얼 플로팅 버튼이 눌러졌을 때 동작
        binding.mainFab.setOnClickListener {
            datas.add("Add Item")
            // 6-1. 어댑터에 변경 사항을 알려줌
            adapter.notifyDataSetChanged()
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