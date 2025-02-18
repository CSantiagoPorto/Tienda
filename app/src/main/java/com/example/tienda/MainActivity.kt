package com.example.tienda

import android.os.Bundle
import android.util.Log
import android.view.View

import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tienda.adapter.Recycler_tiendaAdapter
import org.json.JSONArray
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.tienda.databinding.ActivityMainBinding
import com.example.tienda.adapter.SpinnerCategoriasAdapter
import com.example.tienda.model.Categoria
import com.example.tienda.model.Producto

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  adapterSpinnerCategoriasAdapter: SpinnerCategoriasAdapter
    private lateinit var listaCategorias:ArrayList<Categoria>
    private lateinit var adapterRecycler: Recycler_tiendaAdapter
    private lateinit var listaProductos: ArrayList<Producto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        instancias()//No mover de aquí. Si lo pones más abajo nunca se inicializa listaCategorias
        adapterRecycler = Recycler_tiendaAdapter(listaProductos)
        binding.recyclerProductos.adapter = adapterRecycler
        binding.recyclerProductos.layoutManager = LinearLayoutManager(this)

        setContentView(binding.root)
        binding.spinnerCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val categoriaSeleccionada = listaCategorias[position].nombre
                actualizarListaProductos(categoriaSeleccionada)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        adapterSpinnerCategoriasAdapter = SpinnerCategoriasAdapter(this, listaCategorias)
        binding.spinnerCategorias.adapter = adapterSpinnerCategoriasAdapter

        /*
Crea la lista de categorías.
Conecta el Adapter al Spinner.
Maneja la selección con un OnItemSelectedListener.*/
    }
    private fun cargarProductos() {
        val url = "https://dummyjson.com/products"
        val peticion = JsonObjectRequest(url, { response ->
            val gson = Gson()
            val productosArray = gson.fromJson(response.getJSONArray("products").toString(), Array<Producto>::class.java)
            listaProductos.clear()
            listaProductos.addAll(productosArray)
            adapterRecycler.notifyDataSetChanged()
        }, { error ->
            Log.e("Volley", "Error al obtener productos: $error")
        })

        Volley.newRequestQueue(applicationContext).add(peticion)
    }

    private fun instancias() {
        listaCategorias = arrayListOf() // Inicializamos la lista vacía
        listaProductos = arrayListOf()  // Inicializamos la lista vacía

        adapterSpinnerCategoriasAdapter = SpinnerCategoriasAdapter(this, listaCategorias)
        binding.spinnerCategorias.adapter = adapterSpinnerCategoriasAdapter

        adapterRecycler = Recycler_tiendaAdapter(listaProductos)
        binding.recyclerProductos.adapter = adapterRecycler
        binding.recyclerProductos.layoutManager = LinearLayoutManager(this)

        cargarCategorias() 
        cargarProductos()
    }



    private fun actualizarListaProductos(categoria: String) {
        val productosFiltrados = listaProductos.filter { it.categoria == categoria }
        adapterRecycler.actualizarLista(productosFiltrados)
    }


}