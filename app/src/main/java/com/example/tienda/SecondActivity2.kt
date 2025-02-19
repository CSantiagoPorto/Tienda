package com.example.tienda

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tienda.adapter.Recycler_tiendaAdapter
import com.example.tienda.databinding.ActivitySecond2Binding
import com.example.tienda.model.Producto
import com.google.android.material.snackbar.Snackbar

class SecondActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivitySecond2Binding
    private lateinit var carrito: ArrayList<Producto>
    private lateinit var adapter: Recycler_tiendaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySecond2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        // Recuperamos el carrito pasado desde MainActivity
        // Asegúrate de que Producto implementa Serializable
        carrito = (intent.getSerializableExtra("carrito") as? ArrayList<Producto>) ?: arrayListOf()


        // Configuramos el RecyclerView

        adapter = Recycler_tiendaAdapter(carrito, object : Recycler_tiendaAdapter.OnProductoListener {
            override fun onProductoAdd(producto: Producto) {}
        })


        binding.recyclerCarrito.adapter = adapter
        binding.recyclerCarrito.layoutManager = LinearLayoutManager(this)
       // adapter.notifyDataSetChanged()
        adapter.actualizarLista(carrito)



        // Calculamos el total de la compra y lo mostramos en txtTotal
        val total = carrito.sumOf { it.precio }
        binding.txtTotal.text = "Total: $total €"
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_confirmar_compra -> {
                Snackbar.make(binding.root, "Enhorabuena, compra por valor de ${carrito.sumOf { it.precio }}€ realizada", Snackbar.LENGTH_LONG).show()
                true
            }
            R.id.menu_vaciar_carrito -> {
                carrito.clear()
                adapter.notifyDataSetChanged()
                Snackbar.make(binding.root, "Carrito vaciado", Snackbar.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
