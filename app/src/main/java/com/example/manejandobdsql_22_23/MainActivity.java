package com.example.manejandobdsql_22_23;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editTextId, editTextMarca, editTextModelo, editTextPrecio;
    Button buttonMostrar, buttonGuardar, buttonBorrar, buttonActualizar;
    ListView listViewLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextId = findViewById(R.id.editTextID);
        editTextMarca = findViewById(R.id.editTextMarca);
        editTextModelo = findViewById(R.id.editTextModelo);
        editTextPrecio = findViewById(R.id.editTextPrecio);
        buttonActualizar = findViewById(R.id.buttonActualizar);
        buttonBorrar = findViewById(R.id.buttonBorrar);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        buttonMostrar = findViewById(R.id.buttonMostrar);
        listViewLista = findViewById(R.id.listViewLista);

        ManejadorBD manejadorBD = new ManejadorBD(this);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean resultado = manejadorBD.insertar(editTextModelo.getText().toString(), editTextMarca.getText().toString(), editTextPrecio.getText().toString());

                if (resultado) {
                    Toast.makeText(MainActivity.this, "Se ha insertado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error en la inserción", Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = manejadorBD.listar();
                ArrayList<Movil> moviles = new ArrayList<>();
                ArrayAdapter <String> arrayAdapter;
                List<String> lista = new ArrayList<>();

                if (cursor!=null && cursor.getCount()>0){
                    while (cursor.moveToNext()){
                        String fila = "";
                        fila+= "ID: "+cursor.getString(0);
                        fila+= " Modelo: "+cursor.getString(1);
                        fila+= " Marca: "+cursor.getString(2);
                        fila+= " Precio: "+cursor.getString(3);
                        lista.add(fila);
                        moviles.add(new Movil(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
                    }
                    arrayAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lista);
                    listViewLista.setAdapter(arrayAdapter);
                }

            }
        });

    }

    class Movil{
        String id, modelo,marca, precio;

        public Movil(String id, String modelo, String marca, String precio) {
            this.id = id;
            this.modelo = modelo;
            this.marca = marca;
            this.precio = precio;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getModelo() {
            return modelo;
        }

        public void setModelo(String modelo) {
            this.modelo = modelo;
        }

        public String getMarca() {
            return marca;
        }

        public void setMarca(String marca) {
            this.marca = marca;
        }

        public String getPrecio() {
            return precio;
        }

        public void setPrecio(String precio) {
            this.precio = precio;
        }
    }
}