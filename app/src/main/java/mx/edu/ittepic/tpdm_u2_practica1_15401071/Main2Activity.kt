package mx.edu.ittepic.tpdm_u2_practica1_15401071

import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class Main2Activity : AppCompatActivity() {

    var descripTarea: EditText ?=null
    var realizado: EditText?=null
    var editID:EditText?=null
    var btnagg: Button?=null
    var delete:Button?=null
    var mostrarlist:Button?=null
    var basedatos=BaseDatos(this,"Practica1",null,1)
    var cad =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        descripTarea=findViewById(R.id.descrTarea)
        realizado=findViewById(R.id.realizado)
        btnagg=findViewById(R.id.btnagregar)
        delete=findViewById(R.id.btneliminar)
        mostrarlist=findViewById(R.id.btnlistas)
        editID=findViewById(R.id.editID)

        btnagg?.setOnClickListener {
            insertar()
        }
    }

    fun insertar(){
        try {
            var trans=basedatos.writableDatabase
            var SQL="INSERT INTO LISTA VALUES(null,'DESCRIPCION','REALIZADO','IDLISTA')"
            if (validarCampos()==false){
                mensaje("ALGO SALIO MAL","REVISE BIEN LA SINTAXIS")
                return
            }
            SQL = SQL.replace("DESCRIPCION",descripTarea?.text.toString())
            SQL = SQL.replace("REALIZADO",realizado?.text.toString())
            SQL = SQL.replace("IDLISTA",cad)

            trans.execSQL(SQL)
            trans.close()
        }catch (err:SQLiteException){
            mensaje("error","no se pudp insertar")
        }
    }

    fun mensaje(t:String,m:String){
        AlertDialog.Builder(this).setTitle(t).setMessage(m).setPositiveButton("ok"){ dialog, which ->  }.show()
    }
    fun  validarCampos():Boolean{
        if((descripTarea?.text.toString().isEmpty()) || (realizado?.text.toString().isEmpty()) || editID?.text.toString().isEmpty()){
            return false
        }else{
            return true
        }
    }
    fun limpiarCampos(){
        descripTarea?.setText("")
        realizado?.setText("")
        editID?.setText("")
    }
}
