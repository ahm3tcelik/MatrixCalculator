package com.ahmetc.matriscalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private NumberPicker scanCarpan, matrisAi, matrisAj, matrisBi, matrisBj, scanCarpan2;
    private ConstraintLayout matrisALayout, matrisBLayout;
    private TableRow matrisAContainer, matrisBContainer, rowA1, rowA2, rowB1, rowB2;
    private EditText[][] matrisA, matrisB;
    private boolean[] createdMatris = {false,false};
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    public void createMatrisA(View view) {
        createdMatris[0] = true;
        matrisA = new EditText[matrisAi.getValue()][matrisAj.getValue()];
        TableLayout tableLayoutA = new TableLayout(this);
        for(int i = 0; i < matrisAi.getValue(); i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setGravity(Gravity.CENTER);
            for(int j = 0; j < matrisAj.getValue(); j++) {
                matrisA[i][j] = new EditText(MainActivity.this);
                matrisA[i][j].setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                matrisA[i][j].setHint("0");
                matrisA[i][j].setMinEms(2);
                matrisA[i][j].setTextColor(getResources().getColor(R.color.colorAccent));
                matrisA[i][j].setHintTextColor(getResources().getColor(R.color.colorAccent));
                matrisA[i][j].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                matrisA[i][j].setPadding(10,10,10,10);
                tableRow.addView(matrisA[i][j]);
            }
            tableLayoutA.addView(tableRow);
        }
        matrisALayout.setVisibility(View.INVISIBLE);
        matrisAContainer.removeView(matrisALayout);
        matrisAContainer.addView(tableLayoutA);
        // Show Buttons
        rowA1.setVisibility(View.VISIBLE);
        rowA2.setVisibility(View.VISIBLE);
    }

    public void matrisACarp(View view) {
        for(int i = 0; i < matrisAi.getValue(); i++) {
            for(int j = 0; j < matrisAj.getValue(); j++) {
                int temp;
                if (matrisA[i][j].getText().toString().trim().isEmpty()) temp = 0;
                else temp = Integer.valueOf(matrisA[i][j].getText().toString().trim());
                matrisA[i][j].setText(String.valueOf(temp * scanCarpan.getValue()));
            }
        }
        Toast.makeText(this, "A Matrisi " + scanCarpan.getValue() + " ile çarpıldı", Toast.LENGTH_SHORT).show();
    }
    public void matrisAInvo(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Matris Calculator");
        alert.setPositiveButton("Tamam", null);
        switch (Matris.checkInvo(matrisA,matrisAi.getValue(),matrisAj.getValue())) {
            case 0: {
                alert.setMessage("A Matrisi kare matris olmalıdır.");
                break;
            }
            case 1: {
                alert.setMessage("A Matrisi İnvolütiftir.");
                break;
            }
            case 2: {
                alert.setMessage("A Matrisi İnvolütif değildir.");
            }
        }
        alert.show();
    }

    public void matrisADet(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Matris Calculator");
        if(matrisAi.getValue() != 3 || matrisAj.getValue() != 3) {
            alert.setMessage("A matrisi 3x3 tipinde olmalıdır");
        }
        else alert.setMessage("A Matrisinin Determinantı : " + Matris.getDeterminant(Matris.getNumberFromMatris(matrisA,3,3)));
        alert.setPositiveButton("Tamam", null);
        alert.show();
    }

    public void matrisATers(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.app_name);
        alert.setPositiveButton("Tamam", null);
        if(matrisAi.getValue() != 3 || matrisAj.getValue() != 3) {
            alert.setMessage("A matrisi 3x3 tipinde olmalıdır");
            alert.show();
        }
        else {
            String[][] newMatris = Matris.getTers(matrisA,matrisAi.getValue(),matrisAj.getValue());
            if(newMatris[0][0] == null) {
                alert.setMessage("Determinant 0 olduğundan hesaplanamıyor");
                alert.show();
            }
            else {
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.sonuc_layout);
                HorizontalScrollView sonucContainer = dialog.findViewById(R.id.sonucContainer);
                Button button = dialog.findViewById(R.id.button);
                TableLayout tableLayout = new TableLayout(dialog.getContext());
                for(int i = 0; i < matrisAi.getValue(); i++) {
                    TableRow tableRow = new TableRow(MainActivity.this);
                    tableRow.setGravity(Gravity.CENTER);
                    for (int j = 0; j < matrisAj.getValue(); j++) {
                        TextView textView = new TextView(MainActivity.this);
                        textView.setTextSize(textView.getTextSize() - 10);
                        textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                        textView.setText(newMatris[i][j]);
                        textView.setMinEms(2);
                        textView.setTextColor(getResources().getColor(R.color.colorAccent));
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        textView.setPadding(1,1,1,1);
                        tableRow.addView(textView);
                    }
                    tableLayout.addView(tableRow);
                }
                sonucContainer.addView(tableLayout);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }
    }

    public void createMatrisB(View view) {
        createdMatris[1] = true;
        matrisB = new EditText[matrisBi.getValue()][matrisBj.getValue()];
        TableLayout tableLayoutB = new TableLayout(this);
        for(int i = 0; i < matrisBi.getValue(); i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setGravity(Gravity.CENTER);
            for(int j = 0; j < matrisBj.getValue(); j++) {
                matrisB[i][j] = new EditText(MainActivity.this);
                matrisB[i][j].setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                matrisB[i][j].setHint("0");
                matrisB[i][j].setMinEms(2);
                matrisB[i][j].setTextColor(getResources().getColor(R.color.colorAccent));
                matrisB[i][j].setHintTextColor(getResources().getColor(R.color.colorAccent));
                matrisB[i][j].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                matrisB[i][j].setPadding(10,10,10,10);
                tableRow.addView(matrisB[i][j]);
            }
            tableLayoutB.addView(tableRow);
        }
        matrisBLayout.setVisibility(View.INVISIBLE);
        matrisBContainer.removeView(matrisBLayout);
        matrisBContainer.addView(tableLayoutB);
        // Show Buttons
        rowB1.setVisibility(View.VISIBLE);
        rowB2.setVisibility(View.VISIBLE);
    }

    public void matrisBCarp(View view) {
        for(int i = 0; i < matrisBi.getValue(); i++) {
            for(int j = 0; j < matrisBj.getValue(); j++) {
                int temp;
                if (matrisB[i][j].getText().toString().trim().isEmpty()) temp = 0;
                else temp = Integer.valueOf(matrisB[i][j].getText().toString().trim());
                matrisB[i][j].setText(String.valueOf(temp * scanCarpan2.getValue()));
            }
        }
        Toast.makeText(this, "B Matrisi " + scanCarpan2.getValue() + " ile çarpıldı", Toast.LENGTH_SHORT).show();
    }
    public void matrisBInvo(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Matris Calculator");
        alert.setPositiveButton("Tamam", null);
        switch (Matris.checkInvo(matrisB,matrisBi.getValue(),matrisBj.getValue())) {
            case 0: {
                alert.setMessage("B Matrisi kare matris olmalıdır.");
                break;
            }
            case 1: {
                alert.setMessage("B Matrisi İnvolütiftir.");
                break;
            }
            case 2: {
                alert.setMessage("B Matrisi İnvolütif değildir.");
            }
        }
        alert.show();
    }

    public void matrisBDet(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.app_name);
        if(matrisBi.getValue() != 3 || matrisBj.getValue() != 3) {
            alert.setMessage("B matrisi 3x3 tipinde olmalıdır");
        }
        else alert.setMessage("B Matrisinin Determinantı : " + Matris.getDeterminant(Matris.getNumberFromMatris(matrisB,3,3)));
        alert.setPositiveButton("Tamam", null);
        alert.show();
    }

    public void matrisBTers(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.app_name);
        alert.setPositiveButton("Tamam", null);
        if(matrisBi.getValue() != 3 || matrisBj.getValue() != 3) {
            alert.setMessage("B matrisi 3x3 tipinde olmalıdır");
            alert.show();
        }
        else {
            String[][] newMatris = Matris.getTers(matrisB,matrisBi.getValue(),matrisBj.getValue());
            if(newMatris[0][0] == null) {
                alert.setMessage("Determinant 0 olduğundan hesaplanamıyor");
                alert.show();
            }
            else {
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.sonuc_layout);
                HorizontalScrollView sonucContainer = dialog.findViewById(R.id.sonucContainer);
                Button button = dialog.findViewById(R.id.button);
                TableLayout tableLayout = new TableLayout(dialog.getContext());
                for(int i = 0; i < matrisBi.getValue(); i++) {
                    TableRow tableRow = new TableRow(MainActivity.this);
                    tableRow.setGravity(Gravity.CENTER);
                    for (int j = 0; j < matrisBj.getValue(); j++) {
                        TextView textView = new TextView(MainActivity.this);
                        textView.setTextSize(textView.getTextSize() - 10);
                        textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                        textView.setText(newMatris[i][j]);
                        textView.setMinEms(2);
                        textView.setTextColor(getResources().getColor(R.color.colorAccent));
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        textView.setPadding(1,1,1,1);
                        tableRow.addView(textView);
                    }
                    tableLayout.addView(tableRow);
                }
                sonucContainer.addView(tableLayout);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }
    }
    public void aCarpiB(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle(R.string.app_name);
        alert.setPositiveButton("Tamam", null);
        if(!createdMatris[0] || !createdMatris[1]) {
            alert.setMessage("İki Matris de oluşturulmalıdır.");
            alert.show();
        }
        else {
            String[][] newMatris = Matris.matrisCarp(matrisA,matrisB,matrisAi.getValue(), matrisAj.getValue(),
                    matrisBi.getValue(), matrisBj.getValue());
            if(newMatris[0][0] == null) {
                alert.setMessage("A matrisinin sütun sayısı ile B matrisinin satır sayısı eşit olmalıdır.");
                alert.show();
            }
            else {
                final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.sonuc_layout);
                HorizontalScrollView sonucContainer = dialog.findViewById(R.id.sonucContainer);
                Button button = dialog.findViewById(R.id.button);
                TableLayout tableLayout = new TableLayout(dialog.getContext());
                for(int i = 0; i < matrisAi.getValue(); i++) {
                    TableRow tableRow = new TableRow(dialog.getContext());
                    tableRow.setGravity(Gravity.CENTER);
                    for (int j = 0; j < matrisBj.getValue(); j++) {
                        TextView textView = new TextView(dialog.getContext());
                        textView.setTextSize(textView.getTextSize() - 10);
                        textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                        textView.setText(newMatris[i][j]);
                        textView.setMinEms(2);
                        textView.setTextColor(getResources().getColor(R.color.colorAccent));
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        textView.setPadding(1,1,1,1);
                        tableRow.addView(textView);
                    }
                    tableLayout.addView(tableRow);
                }
                sonucContainer.addView(tableLayout);
                Snackbar.make(sonucContainer, "[A] . [B] Hesaplandı", Snackbar.LENGTH_SHORT).show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }
    }
    public void aEksiB(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle(R.string.app_name);
        alert.setPositiveButton("Tamam", null);
        if(!createdMatris[0] || !createdMatris[1]) {
            alert.setMessage("İki Matris de oluşturulmalıdır.");
            alert.show();
        }
        else if(matrisAi.getValue() != matrisBi.getValue() || matrisAj.getValue() != matrisBj.getValue()) {
            alert.setMessage("A ve B matrislerinin satır ve sütun sayıları eşit olmalıdır.");
            alert.show();
        }
        else {
            String[][] newMatris = Matris.matrisCikar(matrisA,matrisB,matrisAi.getValue(), matrisBj.getValue());
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.sonuc_layout);
            HorizontalScrollView sonucContainer = dialog.findViewById(R.id.sonucContainer);
            Button button = dialog.findViewById(R.id.button);
            TableLayout tableLayout = new TableLayout(dialog.getContext());
            for(int i = 0; i < matrisAi.getValue(); i++) {
                TableRow tableRow = new TableRow(dialog.getContext());
                tableRow.setGravity(Gravity.CENTER);
                for (int j = 0; j < matrisBj.getValue(); j++) {
                    TextView textView = new TextView(dialog.getContext());
                    textView.setTextSize(textView.getTextSize() - 10);
                    textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                    textView.setText(newMatris[i][j]);
                    textView.setMinEms(2);
                    textView.setTextColor(getResources().getColor(R.color.colorAccent));
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    textView.setPadding(1,1,1,1);
                    tableRow.addView(textView);
                }
                tableLayout.addView(tableRow);
            }
            sonucContainer.addView(tableLayout);
            Snackbar.make(sonucContainer, "[A] - [B] Hesaplandı", Snackbar.LENGTH_SHORT).show();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    public void aArtiB(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle(R.string.app_name);
        alert.setPositiveButton("Tamam", null);
        if(!createdMatris[0] || !createdMatris[1]) {
            alert.setMessage("İki Matris de oluşturulmalıdır.");
            alert.show();
        }
        else if(matrisAi.getValue() != matrisBi.getValue() || matrisAj.getValue() != matrisBj.getValue()) {
            alert.setMessage("A ve B matrislerinin satır ve sütun sayıları eşit olmalıdır.");
            alert.show();
        }
        else {
            String[][] newMatris = Matris.matrisTopla(matrisA,matrisB,matrisAi.getValue(), matrisBj.getValue());
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.sonuc_layout);
            HorizontalScrollView sonucContainer = dialog.findViewById(R.id.sonucContainer);
            Button button = dialog.findViewById(R.id.button);
            TableLayout tableLayout = new TableLayout(dialog.getContext());
            for(int i = 0; i < matrisAi.getValue(); i++) {
                TableRow tableRow = new TableRow(dialog.getContext());
                tableRow.setGravity(Gravity.CENTER);
                for (int j = 0; j < matrisBj.getValue(); j++) {
                    TextView textView = new TextView(dialog.getContext());
                    textView.setTextSize(textView.getTextSize() - 10);
                    textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                    textView.setText(newMatris[i][j]);
                    textView.setMinEms(2);
                    textView.setTextColor(getResources().getColor(R.color.colorAccent));
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    textView.setPadding(1,1,1,1);
                    tableRow.addView(textView);
                }
                tableLayout.addView(tableRow);
            }
            sonucContainer.addView(tableLayout);
            Snackbar.make(sonucContainer, "[A] + [B] Hesaplandı", Snackbar.LENGTH_SHORT).show();
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        matrisAi = findViewById(R.id.matrisAi);
        matrisAj = findViewById(R.id.matrisAj);
        matrisBi = findViewById(R.id.matrisBi);
        matrisBj = findViewById(R.id.matrisBj);
        scanCarpan = findViewById(R.id.scanCarpan);
        scanCarpan2 = findViewById(R.id.scanCarpan2);
        matrisALayout = findViewById(R.id.matrisALayout);
        matrisBLayout = findViewById(R.id.matrisBLayout);
        matrisAContainer = findViewById(R.id.matrisAContainer);
        matrisBContainer = findViewById(R.id.matrisBContainer);
        rowA1 = findViewById(R.id.rowA1);
        rowB1 = findViewById(R.id.rowB1);
        rowA2 = findViewById(R.id.rowA2);
        rowB2 = findViewById(R.id.rowB2);
        scanCarpan.setMinValue(0);scanCarpan2.setMinValue(0);
        scanCarpan.setMaxValue(999);scanCarpan2.setMaxValue(999);
        matrisAi.setMaxValue(5);matrisBi.setMaxValue(5);
        matrisAi.setMinValue(1);matrisBi.setMinValue(1);
        matrisAj.setMaxValue(5);matrisBj.setMaxValue(5);
        matrisAj.setMinValue(1);matrisBj.setMinValue(1);
    }
    /* for Toolbar */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_temizle) {
            Toast.makeText(this, "Temizlendi", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this,MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
