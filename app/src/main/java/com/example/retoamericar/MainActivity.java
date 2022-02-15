package com.example.retoamericar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    CardView llamada, correo, localizacion, acercaDe;
    CardView agenda, partner, pedido, envio, importar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llamada = (CardView) findViewById(R.id.bLlamada);
        correo = (CardView) findViewById(R.id.bGmail);
        localizacion = (CardView) findViewById(R.id.bUbicacion);
        acercaDe = (CardView) findViewById(R.id.bAcercaDe);

        //Llamada telefono
        llamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numero_telefono = "634428466";
                String url = "tel:+34" + numero_telefono;

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        //Correo empresa
        correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_gmail = "lapislazulireto@gmail.com";
                String url = "mailto:"+txt_gmail;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });

        //Ubicacion
        localizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        //Acerca de AmeriCar
        acercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DialogoPersonalizado  dialogo = new DialogoPersonalizado ();
                dialogo.show(fragmentManager, "tagAlerta");
            }
        });
        agenda = (CardView) findViewById(R.id.bAgenda);
        partner = (CardView) findViewById(R.id.bPartners);
        pedido = (CardView) findViewById(R.id.bPedidos);
        envio = (CardView) findViewById(R.id.bMandar);
        importar = (CardView) findViewById(R.id.bImportar);

        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Hacer la agenda
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        partner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PartnersActivity.class);
                startActivity(intent);
            }
        });

        pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PedidosActivity.class);
                startActivity(intent);
            }
        });

        envio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: a saber que vamos a hacer con esto
                //Intent intent = new Intent(MainActivity.this, Gmail_attachment.class);
                //startActivity(intent);
            }
        });

        importar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar();
                //TODO: Hacer el import de xml
            }
        });



    }

    public void insertar(){
        ArrayList<Articulo> lista;
        ArticuloXML<Articulo> lector = new ArticuloXML<>();
        lista = lector.lector(new File("/data/data/com.example.lapislazulireto/AlmacenDelegacion.xml"));

        retoSQLiteHelper rsdb = new retoSQLiteHelper(this, "reto", null, 1);
        SQLiteDatabase db = rsdb.getReadableDatabase();

        for (int i = 0; i < lista.size(); i++) {
            ContentValues nuevo = new ContentValues();
            nuevo.put("idArticulo", lista.get(i).getId());
            nuevo.put("descripcion", lista.get(i).getDesc());
            nuevo.put("prCost", lista.get(i).getPrCost());
            nuevo.put("prVent", lista.get(i).getPrVent());
            nuevo.put("existencias", lista.get(i).getExistencias());
            nuevo.put("bajoMinimo", lista.get(i).getBajoMinimo());
            nuevo.put("sobreMaximo", lista.get(i).getSobreMaximo());
            nuevo.put("fecUltEnt", lista.get(i).getFecUltEnt());
            nuevo.put("fecUltSal", lista.get(i).getFecUltSal());

            db.insert("Articulos",null,nuevo);

        }
        db.close();
    }
}