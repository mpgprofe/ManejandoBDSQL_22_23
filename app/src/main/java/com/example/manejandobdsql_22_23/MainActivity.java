package com.example.manejandobdsql_22_23;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText editTextId, editTextMarca, editTextModelo, editTextPrecio;
    Button buttonMostrar, buttonGuardar, buttonBorrar, buttonActualizar;

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

    }
}