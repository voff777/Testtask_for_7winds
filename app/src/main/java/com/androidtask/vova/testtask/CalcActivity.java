package com.androidtask.vova.testtask;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalcActivity extends AppCompatActivity implements View.OnClickListener{
    TextView info, total;
    Button btnOk, btnCancel, btnBuy;
    EditText edtNumber;
    Integer price, totalPrice;
    Boolean enabledState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        info = (TextView) findViewById(R.id.infoView);
        total = (TextView) findViewById(R.id.total);
        btnOk = (Button) findViewById(R.id.ok);
        btnCancel = (Button) findViewById(R.id.cancel);
        btnBuy = (Button) findViewById(R.id.buy);
        edtNumber = (EditText) findViewById(R.id.editText);

        Intent getdata = getIntent();
        price = Integer.parseInt(getdata.getStringExtra("price"));

        info.setText("Вы выбрали " + getdata.getStringExtra("name") +
                " за " + getdata.getStringExtra("price") + " руб.");

        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
        totalPrice=0;
        enabledState=false;
        btnBuy.setEnabled(enabledState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intentcall = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode("*100#")));
            startActivity(intentcall);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.ok){
            try {
                totalPrice = price * Integer.parseInt(edtNumber.getText().toString());
                total.setText("Итог: " + totalPrice + " рублей");
                enabledState = true;
                btnBuy.setEnabled(enabledState);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Укажите количество",Toast.LENGTH_SHORT).show();
            }
        }
        if (v.getId()==R.id.cancel){
            finish();
        }
        if (v.getId()==R.id.buy){
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, "zakazvody@email.com");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Новый заказ");
            intent.putExtra(Intent.EXTRA_TEXT, "Доставка воды по адресу _");
            startActivity(Intent.createChooser(intent, "Отправка письма"));
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("saved_message", totalPrice);
        outState.putBoolean("enabled_state", enabledState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
            if (savedInstanceState.getInt("saved_message")!=0)
            total.setText("Итог: " + savedInstanceState.getInt("saved_message") + " рублей");
            totalPrice = savedInstanceState.getInt("saved_message");
            enabledState = savedInstanceState.getBoolean("enabled_state");
            btnBuy.setEnabled(enabledState);
    }
}
