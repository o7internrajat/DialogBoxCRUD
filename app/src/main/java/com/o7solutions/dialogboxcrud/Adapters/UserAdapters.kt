package com.o7solutions.dialogboxcrud.Adapters

import android.app.AlertDialog
import android.content.Context
import android.icu.text.Transliterator.Position
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.o7solutions.dialogboxcrud.Models.UserData
import com.o7solutions.dialogboxcrud.R

class UserAdapters(val c: Context, val userList: ArrayList<UserData>) :
    RecyclerView.Adapter<UserAdapters.UserViewHolder>() {
    private  val TAG = "UserAdapters"

    inner class UserViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        var tvMenu: TextView
        var tvDescriptionView: TextView
        var tvEdit: TextView
        var ivDelete: ImageView


        init {
            tvMenu = v.findViewById<TextView>(R.id.tvMenu)
            tvDescriptionView = v.findViewById<TextView>(R.id.tvDescriptionView)
            tvEdit = v.findViewById<TextView>(R.id.tvEdit)
            ivDelete = v.findViewById<ImageView>(R.id.ivDelete)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.listmenu, parent, false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newList = userList[position]
        holder.tvMenu.text = newList.menuName
        holder.tvDescriptionView.text = newList.Description
        holder.ivDelete.setOnClickListener {
            userList.removeAt(position)
            Toast.makeText(c, "item Deleted", Toast.LENGTH_SHORT).show()
            notifyDataSetChanged()
        }


        lateinit var etItem: EditText
        lateinit var etDescription: EditText
        holder.tvEdit.setOnClickListener {
            val inflater = LayoutInflater.from(c)
            val v = inflater.inflate(R.layout.fragment_dialog, null)

            val addDialog = AlertDialog.Builder(c)
            addDialog.setView(v)
            etItem = v.findViewById(R.id.etItem)
            etDescription = v.findViewById(R.id.etDescription)

            addDialog.setPositiveButton("Update") { dialog, _ ->
                val item = etItem.text.toString()
                val des = etDescription.text.toString()
                Toast.makeText(c, "Add Information", Toast.LENGTH_SHORT).show()
                userList.add(UserData("Menu:$item", "Description:$des"))
                notifyDataSetChanged()
                dialog.dismiss()
            }
            addDialog.setNegativeButton("Cancel") { dialog, _ ->
            }
            addDialog.create()
            addDialog.show()

        }
    }

    override fun getItemCount(): Int {
        Log.e(TAG, " userList ${userList.size}")
        return userList.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    }


}