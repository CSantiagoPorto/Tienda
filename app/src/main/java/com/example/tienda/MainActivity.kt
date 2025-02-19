package com.example.tienda

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.tienda.adapter.Recycler_tiendaAdapter
import com.example.tienda.adapter.SpinnerCategoriasAdapter
import com.example.tienda.databinding.ActivityMainBinding
import com.example.tienda.model.Categoria
import com.example.tienda.model.Producto
import com.google.gson.Gson
import com.google.android.material.snackbar.Snackbar

// >>>>>>>> CAMBIO: Ahora MainActivity implementa OnProductoListener
class MainActivity : AppCompatActivity(), Recycler_tiendaAdapter.OnProductoListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterSpinnerCategoriasAdapter: SpinnerCategoriasAdapter
    private lateinit var listaCategorias: ArrayList<Categoria>
    private lateinit var adapterRecycler: Recycler_tiendaAdapter
    private lateinit var listaProductos: ArrayList<Producto>
    private lateinit var carrito: ArrayList<Producto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Es importante llamar a setContentView antes de manipular la vista
        setContentView(binding.root)

        instancias() // No mover de aquí. Si lo pones más abajo nunca se inicializa listaCategorias

        // Configuro el listener del Spinner para filtrar los productos según la categoría seleccionada
        binding.spinnerCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val categoriaSeleccionada = listaCategorias[position].nombre
                actualizarListaProductos(categoriaSeleccionada)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.btnVerCarrito.setOnClickListener {
            val intent = Intent(this, SecondActivity2::class.java)
            intent.putExtra("carrito", carrito)
            startActivity(intent)
        }


        adapterRecycler = Recycler_tiendaAdapter(listaProductos, this)
        adapterSpinnerCategoriasAdapter = SpinnerCategoriasAdapter(this, listaCategorias)


        // Inicializamos el adaptador del RecyclerView
        binding.recyclerProductos.layoutManager = LinearLayoutManager(this)
        //adapterRecycler = Recycler_tiendaAdapter(listaProductos, this)
        binding.recyclerProductos.adapter = adapterRecycler

        cargarCategorias()
        cargarProductos()
    }

    private fun instancias() {
        // Inicializamos las listas vacías
        listaCategorias = arrayListOf()
        listaProductos = arrayListOf()
        carrito = ArrayList()
    }

    private fun cargarCategorias() {
        val url = "https://dummyjson.com/products/categories"
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                listaCategorias.clear()
                for (i in 0 until response.length()) {
                    val nombreCategoria = response.getString(i)
                    listaCategorias.add(Categoria(nombreCategoria))
                }
                adapterSpinnerCategoriasAdapter.notifyDataSetChanged()
            },
            { error ->
                Log.e("Volley", "Error al cargar categorías: $error")
            }
        )
        Volley.newRequestQueue(applicationContext).add(request)
    }

    private fun cargarProductos() {
        val url = "https://dummyjson.com/products"
        val peticion = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val gson = Gson()
                val productosArray = gson.fromJson(
                    response.getJSONArray("products").toString(),
                    Array<Producto>::class.java
                )
                listaProductos.clear()
                listaProductos.addAll(productosArray)
                adapterRecycler.notifyDataSetChanged()
            },
            { error ->
                Log.e("Volley", "Error al obtener productos: $error")
            }
        )
        Volley.newRequestQueue(applicationContext).add(peticion)
    }

    private fun actualizarListaProductos(categoria: String) {
        val productosFiltrados = listaProductos.filter { it.category == categoria }
        adapterRecycler.actualizarLista(productosFiltrados)
    }


    override fun onProductoAdd(producto: Producto) {
        carrito.add(producto)
        Snackbar.make(binding.root, "${producto.nombre} añadido al carrito", Snackbar.LENGTH_SHORT).show()
        Log.d("Carrito", "Producto añadido: ${producto.nombre}. Total en carrito: ${carrito.size}")
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    //Con el intent hacemos los cambios
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_ver_carrito -> {
                val intent = Intent(this, SecondActivity2::class.java)
                intent.putExtra("carrito", carrito)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
