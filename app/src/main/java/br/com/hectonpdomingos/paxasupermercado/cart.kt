package br.com.hectonpdomingos.paxasupermercado

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_cart.*


class cart : AppCompatActivity() {


    var quantity = arrayListOf<Int>()
    var price = arrayListOf<Double>()
    var totalPrice: Double? = null
    var myListProducts = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

       val btnVoltar: Button = findViewById(R.id.btnVoltar) as Button
        btnVoltar.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })


        val choseProducts: ArrayList<String> = intent.getStringArrayListExtra("Prod")
        for (item in choseProducts) {
            myListProducts.add(item.toString())
        }


        val listaProdutos = findViewById(R.id.myCart) as ListView
        val cartAdapter = ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, myListProducts)
        // Assign adapter to ListView
        listaProdutos.setAdapter(cartAdapter)


    }
}