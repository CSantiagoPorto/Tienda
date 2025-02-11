package com.example.tienda.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tienda.R
import com.example.tienda.model.Producto

class Recycler_tiendaAdapter(private val listaProductos: List<Producto>) :
    RecyclerView.Adapter<Recycler_tiendaAdapter.MyHolder>() {

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imagen: ImageView = itemView.findViewById(R.id.itemImage)
        val nombre: TextView = itemView.findViewById(R.id.itemName)
        val precio: TextView = itemView.findViewById(R.id.itemPrice)
        val botonAñadir: Button = itemView.findViewById(R.id.itemAddButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_tienda, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return listaProductos.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val producto = listaProductos[position]

      //  holder.itemView.findViewById<TextView>(R.id.textViewNombre).text = producto.nombre
        //holder.itemView.findViewById<TextView>(R.id.textViewPrecio).text = "${producto.precio}€"
    }
}
