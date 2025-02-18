package com.example.tienda.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tienda.R
import com.example.tienda.model.Producto

class Recycler_tiendaAdapter(
    private var listaProductos: MutableList<Producto>,
    private val listener: OnProductoListener
) : RecyclerView.Adapter<Recycler_tiendaAdapter.MyHolder>() {

    // Interfaz de callback -> Esto va a notificar que se ha añadido producto al carrito
    interface OnProductoListener {
        fun onProductoAdd(producto: Producto)
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombre: TextView = itemView.findViewById(R.id.itemName)
        val precio: TextView = itemView.findViewById(R.id.itemPrice)
        val imagen: ImageView = itemView.findViewById(R.id.itemImage)
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

        holder.nombre.text = producto.nombre
        holder.precio.text = "${producto.precio}€"

        // DESCOMENTAR CUANDO METAS EL GLIDE
       // Glide.with(holder.itemView.context)
         //   .load(producto.imagen)
           //() .into(holder.imagen)

        // El click del carrito
        holder.botonAñadir.setOnClickListener {
            listener.onProductoAdd(producto)
        }
    }fun actualizarLista(nuevaLista: List<Producto>) {
        listaProductos.clear()
        listaProductos.addAll(nuevaLista)
        notifyDataSetChanged()
    }

}
