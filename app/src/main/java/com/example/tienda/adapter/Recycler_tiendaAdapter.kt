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

class Recycler_tiendaAdapter(private var listaProductos: List<Producto>) :
    RecyclerView.Adapter<Recycler_tiendaAdapter.MyHolder>() {

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

        // Cargar la imagen con Glide
       // Glide.with(holder.itemView.context)
         //   .load(producto.imagen)
           //() .into(holder.imagen)

        // Manejar clic en el botón "Añadir al carrito"
        holder.botonAñadir.setOnClickListener {
            // Lógica para añadir al carrito (pendiente de implementar)
        }
    }fun actualizarLista(nuevaLista: List<Producto>) {
        (listaProductos as ArrayList<Producto>).clear()
        (listaProductos as ArrayList<Producto>).addAll(nuevaLista)
        notifyDataSetChanged()
    }

}
