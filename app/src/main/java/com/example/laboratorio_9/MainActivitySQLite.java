package com.example.laboratorio_9;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivitySQLite extends AppCompatActivity {
    EditText textAgregarUser, textAgregarPassword, textPresentUser, textActualizarUser, textBorrarUser;
    Button btonAgregarUser, btonVerUser, btonActualizarUser, btonBorrarUser;
    myDBHelper DBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sqlite);

        textAgregarUser = findViewById(R.id.textFieldUsername);
        textAgregarPassword = findViewById(R.id.textFieldPassword);

        textPresentUser = findViewById(R.id.textFieldUpdateUsername);
        textActualizarUser = findViewById(R.id.textFieldUpdatePassword);

        textBorrarUser = findViewById(R.id.textFieldDeleteUser);

        btonAgregarUser = findViewById(R.id.btonAgregar);
        btonVerUser = findViewById(R.id.btonVer);
        btonActualizarUser = findViewById(R.id.btonActualizar);
        btonBorrarUser = findViewById(R.id.btonBorrar);

        DBHelper = new myDBHelper(this);

        btonAgregarUser.setOnClickListener(view -> {
            String addUsername = textAgregarUser.getText().toString();
            String addPassword = textAgregarPassword.getText().toString();

            if(TextUtils.isEmpty(textAgregarUser.getText().toString()) || TextUtils.isEmpty(textAgregarPassword.getText().toString())) {
                Toast.makeText(this, "Introducir Usuario y Contraseña", Toast.LENGTH_SHORT).show();
            } else {
                Boolean check = DBHelper.insertarDatos(addUsername, addPassword);
                if(check.equals(true)) {
                    Toast.makeText(this, "Nuevo Registro Agregado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Nuevo Registro No Fue Agregado", Toast.LENGTH_SHORT).show();
                }
                textAgregarUser.setText("");
                textAgregarPassword.setText("");
            }
        });

        btonActualizarUser.setOnClickListener(view -> {
            String presentUsername = textPresentUser.getText().toString();
            String actualizarUsername = textActualizarUser.getText().toString();

            if(TextUtils.isEmpty(textActualizarUser.getText().toString()) || TextUtils.isEmpty(textPresentUser.getText().toString())) {
                Toast.makeText(this, "Actualización Vacía", Toast.LENGTH_SHORT).show();
            } else {
                Boolean check = DBHelper.actializarNombreDeUsuario(presentUsername, actualizarUsername);
                if(check.equals(true)) {
                    Toast.makeText(this, "Registro Modificado", Toast.LENGTH_SHORT).show();
                    textPresentUser.setText("");
                    textActualizarUser.setText("");
                } else {
                    Toast.makeText(this, "Registro No Fue Modificado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btonBorrarUser.setOnClickListener(view -> {
            String borrarUser = textBorrarUser.getText().toString();

            if(TextUtils.isEmpty(textBorrarUser.getText().toString())) {
                Toast.makeText(this, "No Indique El Usuario a Borrar", Toast.LENGTH_SHORT).show();
            } else {
                Boolean check = DBHelper.borrarDatos(borrarUser);
                if(check.equals(true)) {
                    Toast.makeText(this, "Registro Borrado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Registro No Existe", Toast.LENGTH_SHORT).show();
                }
                textBorrarUser.setText("");
            }
        });

        btonVerUser.setOnClickListener(view -> {
            Cursor cursor = DBHelper.obtenerDatos();
            if(cursor.getCount() == 0) {
                Toast.makeText(this, "No Hay Registros", Toast.LENGTH_SHORT).show();
            } else {
                StringBuilder buffer = new StringBuilder();
                while(cursor.moveToNext()) {
                    buffer.append("Nombre de Usuario: "+cursor.getString(1)+"\n");
                    buffer.append("Contraseña: "+cursor.getString(2)+"\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivitySQLite.this)
                        .setCancelable(true)
                        .setTitle("Registros")
                        .setMessage(buffer);
                builder.show();
            }
            cursor.close();
        });
    }
}
