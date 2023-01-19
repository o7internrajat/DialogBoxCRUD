package com.o7solutions.dialogboxcrud.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.o7solutions.dialogboxcrud.models.UserData
import com.o7solutions.dialogboxcrud.R
import com.o7solutions.dialogboxcrud.interfaces.ClickInterface

class UserAdapters(val c: Context, var userList: ArrayList<UserData>, var clickInterface: ClickInterface) :
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
    fun FilteredList(userList: ArrayList<UserData>){
        this.userList=userList
        notifyDataSetChanged()
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
               clickInterface.deleteClick(newList,position)
        }

        holder.tvEdit.setOnClickListener {
               clickInterface.editClick(newList, position)
        }
    }
    override fun getItemCount(): Int {
        Log.e(TAG, " userList ${userList.size}")
        return userList.size
    }
}