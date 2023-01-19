package com.o7solutions.dialogboxcrud

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.o7solutions.dialogboxcrud.adapters.UserAdapters
import com.o7solutions.dialogboxcrud.models.UserData
import com.o7solutions.dialogboxcrud.interfaces.ClickInterface
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment(), ClickInterface {

    lateinit var recyclerView: RecyclerView
    lateinit var btnAdding: FloatingActionButton
    var userList = ArrayList<UserData>()
    lateinit var userAdapter: UserAdapters
    lateinit var mainActivity: MainActivity
    lateinit var etItem: EditText
    lateinit var etDescription: EditText
    lateinit var svItem:SearchView

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

        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        btnAdding = view.findViewById<FloatingActionButton>(R.id.btnAdding)
        recyclerView = view.findViewById(R.id.recyclerView)
        svItem = view.findViewById(R.id.svItem)

        userAdapter = UserAdapters(mainActivity, userList, this)
        recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        recyclerView.adapter = userAdapter

        svItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
        btnAdding.setOnClickListener {
            val inflater = LayoutInflater.from(mainActivity)
            val v = inflater.inflate(R.layout.fragment_dialog, null)

            val addDialog = AlertDialog.Builder(mainActivity)
            addDialog.setView(v)
            etItem = v.findViewById(R.id.etItem)
            etDescription = v.findViewById(R.id.etDescription)
            addDialog.setPositiveButton("Add") { dialog, _ ->

                val item = etItem.text.toString()
                val des = etDescription.text.toString()

                etItem.setText(item)
                etDescription.setText(des)

                Toast.makeText(mainActivity, "Add Information", Toast.LENGTH_SHORT).show()
                Log.e(TAG, " userList ${userList.size}")
                userList.add(UserData("Menu:$item", "Description:$des"))
                userAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            addDialog.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(mainActivity, "Cancel", Toast.LENGTH_SHORT).show()
            }
            addDialog.create()
            addDialog.show()
        }
        return view
    }
    companion object {

        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun editClick(userData: UserData, position: Int) {
        val inflater = LayoutInflater.from(mainActivity)
        val v = inflater.inflate(R.layout.fragment_dialog, null)

        val addDialog = AlertDialog.Builder(mainActivity)
        addDialog.setView(v)

        etItem = v.findViewById(R.id.etItem)
        etDescription = v.findViewById(R.id.etDescription)
        etItem.setText(userData.menuName)
        etDescription.setText(userData.menuDescription)
        addDialog.setPositiveButton("Update") { dialog, _ ->

            val item = etItem.text.toString()
            val des = etDescription.text.toString()


            Toast.makeText(mainActivity, "Information Update", Toast.LENGTH_SHORT).show()
            userList.set(position, UserData(menuName = item, menuDescription = des))
            userAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel") { dialog, _ ->
        }
        addDialog.create()
        addDialog.show()
    }
    override fun deleteClick(userData: UserData, position: Int) {
        userList.removeAt(position)
        Toast.makeText(mainActivity,"Delete",Toast.LENGTH_SHORT).show()
       userAdapter.notifyDataSetChanged()
    }

        fun filterList(query: String?) {
            if (query != null) {
                val filter = ArrayList<UserData>()
                for (i in userList) {
                    if (i.menuName.lowercase(Locale.ROOT).contains(query)) {
                        filter.add(i)
                    }
                }
                if (filter.isEmpty()) {
                    Toast.makeText(mainActivity, "No Data Found", Toast.LENGTH_SHORT).show()
                } else {
                 userAdapter.FilteredList(filter)
                }
            }
        }
    }


