package com.o7solutions.dialogboxcrud

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.service.controls.actions.FloatAction
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.o7solutions.dialogboxcrud.Adapters.UserAdapters
import com.o7solutions.dialogboxcrud.Models.UserData

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var btnAdding:FloatingActionButton
     var userList = ArrayList<UserData>()
    lateinit var userAdapter: UserAdapters
    lateinit var mainActivity: MainActivity
    lateinit var etItem:EditText
    lateinit var etDescription:EditText

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val TAG = "MenuFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        btnAdding = view.findViewById<FloatingActionButton>(R.id.btnAdding)
        recyclerView = view.findViewById(R.id.recyclerView)
        userAdapter = UserAdapters(mainActivity,userList)
        recyclerView.layoutManager=LinearLayoutManager(mainActivity)
        recyclerView.adapter=userAdapter


        


        btnAdding.setOnClickListener{
            val inflater  = LayoutInflater.from(mainActivity)
            val v = inflater.inflate(R.layout.fragment_dialog,null)

            val addDialog = AlertDialog.Builder(mainActivity)
            addDialog.setView(v)
            etItem = v.findViewById(R.id.etItem)
            etDescription = v.findViewById(R.id.etDescription)
            addDialog.setPositiveButton("Add"){
                    dialog,_->
                val item =   etItem.text.toString()
                val des = etDescription.text.toString()
                Toast.makeText(mainActivity,"Add Information",Toast.LENGTH_SHORT).show()
                Log.e(TAG," userList ${userList.size}")
                userList.add(UserData("Menu:$item","Description:$des"))
                userAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            addDialog.setNegativeButton("Cancel"){
                    dialog,_->
                dialog.dismiss()
                Toast.makeText(mainActivity,"Cancel",Toast.LENGTH_SHORT).show()
            }
            addDialog.create()
            addDialog.show()
        }



    return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}



//var dialog = Dialog(mainActivity)
////            dialog.setContentView(R.layout.fragment_dialog)