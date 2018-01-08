package br.com.hectonpdomingos.paxasupermercado

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import android.widget.AdapterView.OnItemClickListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_quantity.*


class MainActivity : AppCompatActivity()   {



    var produtoLink: RequestQueue? = null
    val linkPromocao = "http://192.168.88.10/data.php"
    val produtos = "http://192.168.88.10/prod.json"

    //All products
    var arrayProdutos = arrayListOf<String>()
    var priceList: ArrayList<Double> = ArrayList()

    //cart itens starts with 0
    var numberItensCart: Int = 0

    //products that users choose
    var meuCarrinho: ArrayList<String> = ArrayList()
    var meuCarrinhoPrice: ArrayList<Double> = ArrayList()
    var totalPrice: Double? = 0.0
    var finalPrice: Double? = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        val btnProsseguir: Button = findViewById(R.id.btnProsseguir) as Button
        btnProsseguir.setOnClickListener(View.OnClickListener {
            val btnProsseguir = Intent(this, cart::class.java)
            btnProsseguir.putStringArrayListExtra("Prod", meuCarrinho)
            startActivity(btnProsseguir)
        })


        /////////////////////

        //button myCart
        val btnMyCart: ImageButton = findViewById(R.id.cartButton) as ImageButton
        btnMyCart.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, cart::class.java)
            intent.putStringArrayListExtra("Prod", meuCarrinho)
            startActivity(intent)
        })


        //Display total
        var tVTotalSelected: TextView = findViewById(R.id.tVtotalSelected) as TextView
        //numbers cart itens // portrain view
        val cartItensPortrain: TextView = findViewById(R.id.cartItensPortrain) as TextView
        //list prod
        produtoLink = Volley.newRequestQueue(this)

       // getString(linkPromocao)
        getjsonArray(produtos)



        }//end of onCreate

      fun totalToPay(){
        totalPrice = 0.0
        for (priceItem in 0.until(meuCarrinhoPrice.size)){
            totalPrice = (totalPrice?.plus(meuCarrinhoPrice[priceItem]))
        }
        finalPrice = totalPrice
        tVtotalSelected.setText("R$: " + "%.2f".format(finalPrice))
      }



fun setQuantity(){


    //Quantity
    val mBuilder = AlertDialog.Builder(this@MainActivity)
    val mView = layoutInflater.inflate(R.layout.activity_quantity, null)

    //BTN and Textview pop up
    val tVQty = mView.findViewById(R.id.qty) as EditText
    val btnQty = mView.findViewById(R.id.btnQty) as Button

    mBuilder.setView(mView)
    val dialog = mBuilder.create()
    dialog.show()

    btnQty.setOnClickListener(View.OnClickListener {
        if (!tVQty.text.toString().isEmpty()) {

            dialog.dismiss()
            recreate()
        } else {



        }


    })



}










//inflate productList
       fun startList(){
        //listview
        val listaProdutos = findViewById(R.id.ListViewlistaProdutos) as ListView
        val adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, arrayProdutos)
        // Assign adapter to ListView
        listaProdutos.setAdapter(adapter)






        //Button to select items
        listaProdutos.onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View,
                                     position: Int, id: Long) {


                // ListView Clicked item value
                val itemValue = listaProdutos.getItemAtPosition(position) as String
                numberItensCart = numberItensCart + 1
                meuCarrinho.add(arrayProdutos[position])
                //tVtotalSelected.setText(priceList[position].toString())
                meuCarrinhoPrice.add(priceList[position])



                //calculate the item qty
               totalToPay()
                //Show +1 in the cart icon
                cartItensPortrain.setText(numberItensCart.toString())

                //setqty
               // setQuantity()
                // Show Alert
                Toast.makeText(applicationContext,
                        "Position :$position  ListItem : $itemValue", Toast.LENGTH_LONG)
                        .show()

            }

        }


    }


    //get data from json file on server
    fun getjsonArray(url: String){
        val jsonArray = JsonArrayRequest(Request.Method.GET, url,
                Response.Listener{
                     response: JSONArray ->
                       try {
                           // Log.d("Response:===>", response.toString())

                           for (item in 0..response.length() -1){
                               val prodObj = response.getJSONObject(item)

                               var prod = prodObj.getString("prod")
                               var prodPrice = prodObj.getDouble("preco")
                               Log.d("Oferta: ", prod)


                               //adding name of the product selected
                               arrayProdutos.add(prod.toString() )
                              priceList.add(prodPrice)



                           }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

               startList()
                },
                Response.ErrorListener{
                    error: VolleyError? ->
                    try {
                        Log.d("Error: ", error.toString())
                    }catch (e: JSONException){e.printStackTrace()}
                })
        produtoLink!!.add(jsonArray)
    }





    fun getString(url: String){
        val stringRequest = StringRequest(Request.Method.GET,url,Response.Listener{
            response: String? ->
            try {

                Log.d("Response", response)
            }catch (e: JSONException){

            }
        }, Response.ErrorListener { 
            error: VolleyError? ->
            try {
                Log.d("Error:", error.toString())

            }catch (e: JSONException){
                e.printStackTrace()
            }
        })
        produtoLink!!.add(stringRequest)
    }
}
