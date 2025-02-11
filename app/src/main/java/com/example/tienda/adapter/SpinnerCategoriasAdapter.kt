package com.example.tienda.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.tienda.R

import com.example.tienda.model.Categoria

class SpinnerCategoriasAdapter(private val context: Context, private val categorias: List<Categoria>) : BaseAdapter() {
    //La hacemos heredar de BaseAdapter, lo que ya nos da los métodos.
    //Le pasamos el contexto, que es el MainActivity
    // Le pasamos la lista de String para guardar las categorías
    override fun getCount(): Int {
        return categorias.size // Número total de elementos en la lista
    }

    override fun getItem(position: Int): Any {
        return categorias[position] // Retorna el elemento en la posición dada
    }

    override fun getItemId(position: Int): Long {
        return position.toLong() // Retorna un ID único para cada elemento
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_spinner_categoria, parent, false)
        //inflamos pasando el contexto y mi xml con la estructura
        val txtCategoria: TextView = view.findViewById(R.id.txtCategoria)
        //Busco el texto en el xml
        txtCategoria.text = categorias[position].nombre // Asigna el texto de la categoría

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_spinner_categoria, parent, false)

        val txtCategoria: TextView = view.findViewById(R.id.txtCategoria)
        txtCategoria.text = categorias[position].nombre

        return view
    }
}
