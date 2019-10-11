package mx.edu.ittepic.tpdm_u2_practica1_15401071

import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var descripcion : EditText?=null
    var date : EditText?=null
    var btnagg : Button?=null
    var btnconsultar : Button?=null
    var btneliminar : Button?=null
    var btnaggtarea : Button?=null
    var mostrar : Button?=null
    var mostrarlist : TextView?=null



    var basedatos = BaseDatos(this,"practica1",null,1)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        descripcion=findViewById(R.id.descriplista)
        date=findViewById(R.id.fechalista)
        btnagg=findViewById(R.id.insertar)
        btneliminar=findViewById(R.id.eliminar)
        btnaggtarea=findViewById(R.id.addtarea)
        mostrar=findViewById(R.id.btnmostrar)
        mostrarlist=findViewById(R.id.mostrarlist)


        btnagg?.setOnClickListener {
            insertar()
            println()
        }
        mostrar?.setOnClickListener {
            pedirID(mostrar?.text.toString())
        }



    }

    fun insertar(){

        try {

            var transaccion = basedatos.writableDatabase
            var SQL="INSERT INTO LISTA VALUES(null,'DESCRIPCION','FECHACREACION')"
            if (validarCampos()==false){
                mensaje("ERROR","Algun campo detecto está vacío")
                return
            }

            SQL=SQL.replace("DESCRIPCION",descripcion?.text.toString())
            SQL=SQL.replace("FECHACREACION",date?.text.toString())
            transaccion.execSQL(SQL)
            mensaje("ahuevo","ya se pudo insertar")
            limpiarCampos()
            transaccion.close()

        }catch (err:SQLiteException){
                mensaje("ERROR","NO SE PUDO INSERTAR")
        }

    }
    fun mostrar(id:String, btnEtiqueta:String){
        try{
            var transccion = basedatos.readableDatabase
            var SQL="SELECT * FROM  LISTA WHERE ID="+id
            var respuesta = transccion.rawQuery(SQL,null)

            if (respuesta.moveToFirst()==true) {
                var cadena = "DESCRIPCION: " + respuesta.getString(1) + "\nFECHACREACION:" + respuesta.getString(2)

                if (btnEtiqueta.startsWith("MOSTRAR LISTA")) {
                    mostrarlist?.setText(cadena)

                } else {
                    mensaje("ERROR", "NO EXISTE ID")
                }


            }
        }catch (err: SQLiteException){
            mensaje("ERRO","NO SE ENCUENTRA")

        }
    }

    fun  pedirID(etiqueta:String){
        var campo = EditText(this)
        campo.inputType =InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this).setTitle("ATENCION").setMessage("ESCRIBA EL ID A  ${etiqueta}: ").setView(campo)
            .setPositiveButton("OK"){dialog,which->
                if(validarCampo(campo) == false){
                    Toast.makeText(this@MainActivity, "ERROR CAMPO VACÍO", Toast.LENGTH_LONG).show()
                    return@setPositiveButton
                }
                mostrar(campo.text.toString(),etiqueta)


            }.setNeutralButton("CANCELAR"){dialog, which ->  }.show()
    }

    fun mensaje(t:String,m:String){
        AlertDialog.Builder(this).setTitle(t).setMessage(m).setPositiveButton("ok"){ dialog, which ->  }.show()
    }
    fun  validarCampos():Boolean{
        if((descripcion?.text.toString().isEmpty()) || (date?.text.toString().isEmpty())){
            return false
        }else{
            return true
        }
    }
    fun limpiarCampos(){
        descripcion?.setText("")
        date?.setText("")
    }
    fun validarCampo(campo: EditText): Boolean{
        if(campo.text.toString().isEmpty()){
            return false
        }else{
            return true
        }
    }
}
