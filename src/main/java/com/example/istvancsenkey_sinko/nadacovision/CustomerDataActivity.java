package com.example.istvancsenkey_sinko.nadacovision;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.istvancsenkey_sinko.nadacovision.connector.server.SendPost;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;


public class CustomerDataActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_data);
    }

    public void save(View v) {
        try {
            JSONObject saveCustomerRequest = new JSONObject();
            saveCustomerRequest.accumulate("firstName", ((EditText) findViewById(R.id.customer_firstname)).getText());
            saveCustomerRequest.accumulate("lastName", ((EditText) findViewById(R.id.customer_lastname)).getText());
            saveCustomerRequest.accumulate("email", ((EditText) findViewById(R.id.customer_email)).getText());
            saveCustomerRequest.accumulate("telephone", ((EditText) findViewById(R.id.customer_phone)).getText());
            saveCustomerRequest.accumulate("birthday", getBirthday());
            saveCustomerRequest.accumulate("zip", ((EditText) findViewById(R.id.customer_zip)).getText());
            saveCustomerRequest.accumulate("town", ((EditText) findViewById(R.id.customer_city)).getText());
            saveCustomerRequest.accumulate("address", ((EditText) findViewById(R.id.customer_address)).getText());
            SendPost sendPost = new SendPost("https://vision-alacart.rhcloud.com/app/customer/create", saveCustomerRequest.toString());
            Thread t = new Thread(sendPost);
            t.start();


        } catch (JSONException e) {
            Log.e("SAVING CUSTOMER FAILED", e.getMessage());
        }
    }

    private long getBirthday() {
        String birthDay = ((EditText) findViewById(R.id.customer_birthday)).getText().toString();
        Calendar calendar = Calendar.getInstance();
        String year = birthDay.substring(0, 4);
        String month = birthDay.substring(4, 6);
        String day = birthDay.substring(6, 8);
        calendar.set(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        return calendar.getTimeInMillis();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
