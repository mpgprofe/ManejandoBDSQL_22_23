package com.example.manejandobdsql_22_23;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    ArrayList<Movil> moviles;

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
                moviles = new ArrayList<>();
                ArrayAdapter<String> arrayAdapter;
                List<String> lista = new ArrayList<>();

                if (cursor != null && cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        Movil movil = new Movil(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                        String fila = movil.toString();
                        lista.add(fila);
                        moviles.add(movil);
                    }
                    arrayAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lista);
                    listViewLista.setAdapter(arrayAdapter);
                    listViewLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            editTextId.setText(moviles.get(i).getId());
                            editTextModelo.setText(moviles.get(i).getModelo());
                            editTextMarca.setText(moviles.get(i).getMarca());
                            editTextPrecio.setText(moviles.get(i).getPrecio());

                        }
                    });
                }else{
                    arrayAdapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, lista);
                    listViewLista.setAdapter(arrayAdapter);
                }

            }
        });
        buttonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean resultado = manejadorBD.actualizar(editTextId.getText().toString(), editTextModelo.getText().toString(), editTextMarca.getText().toString(), editTextPrecio.getText().toString());
                Toast.makeText(MainActivity.this, resultado?"Modificado correctamente":"No se ha modificado nada", Toast.LENGTH_SHORT).show();

            }
        });

        buttonBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Usando un alertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


                builder.setMessage("Deseas Borrar este móvil "+editTextModelo.getText().toString()+"?");
                builder.setCancelable(false);
                builder.setNegativeButton("No borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "No se ha borrado", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean borrado = manejadorBD.borrar(editTextId.getText().toString());
                        Toast.makeText(MainActivity.this, borrado?"Borrado correctamente":"Nada a borrar", Toast.LENGTH_SHORT).show();
                        buttonMostrar.callOnClick();
                    }
                });

                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("¡ ATENCIÓN !");
                alert.show();

                //Primera aproximación sin diálo de borrar.
                //boolean borrado = manejadorBD.borrar(editTextId.getText().toString());
                //Toast.makeText(MainActivity.this, borrado?"Borrado correctamente":"Nada a borrar", Toast.LENGTH_SHORT).show();
            }
        });

    }

    class Movil {
        String id, modelo, marca, precio;

        @Override
        public String toString() {
            return "Movil{" +
                    "id='" + id + '\'' +
                    ", modelo='" + modelo + '\'' +
                    ", marca='" + marca + '\'' +
                    ", precio='" + precio + '\'' +
                    '}';
        }

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