package com.example.orderandinventorysystem.ui.customer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.orderandinventorysystem.ConnectionPhpMyAdmin;
import com.example.orderandinventorysystem.Model.Customer;
import com.example.orderandinventorysystem.R;
import com.example.orderandinventorysystem.ui.bill.add_new_bill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class new_customer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Customer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save: {
                //constructor
                Customer cust = new Customer("1", "2", "3", "4", "5", "6", "7","8", "9");
                AddCust addCust = new AddCust(cust);
                addCust.execute("");
                this.finish();
                Intent intent = new Intent(this, CustomerMain.class);
                intent.putExtra("Customer", cust);
                startActivity(intent);
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_main_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public class AddCust extends AsyncTask<String,String,String> {

        Customer customer;

        AddCust(Customer customer) {

            this.customer = customer;
        }

        String checkConnection = "";
        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {

            ConnectionPhpMyAdmin connectionClass = new ConnectionPhpMyAdmin();
            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    checkConnection = "No";
                } else {
                    String query = "SELECT * FROM CUSTOMER ORDER BY CUSTID DESC LIMIT 1";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    String latestID;

                    if (rs.next()) {
                        latestID = rs.getString(1);
                        int numID = Integer.parseInt(latestID.substring(3,8)) + 1;
                        if (numID < 10)
                            latestID = "CU-0000" + Integer.toString(numID);
                        else if (numID < 100)
                            latestID = "CU-000" + Integer.toString(numID);
                        else if (numID < 1000)
                            latestID = "CU-00" + Integer.toString(numID);
                        else if (numID < 10000)
                            latestID = "CU-0" + Integer.toString(numID);
                        else if (numID < 100000)
                            latestID = "CU-" + Integer.toString(numID);

                        Log.d("ID", latestID);
                    }

                    else {
                        latestID = "CU-00001";
                        Log.d("ID", latestID);
                    }

                    query = "INSERT INTO CUSTOMER VALUES('" + latestID + "', '" + customer.getCustName() + "', '" +
                            customer.getIcNo() + "', '" + customer.getEmail() + "', '" + customer.getPhone() + "', '" +
                            customer.getMobile() + "', '" + customer.getCompanyName() + "', '" + customer.getGender() + "', '" +
                            customer.getCustType() + "', '" + customer.getAddress() + "')";

                    stmt = con.createStatement();
                    stmt.executeUpdate(query);

                    checkConnection = "Yes";
                    isSuccess = true;

                }
            } catch (Exception ex) {
                Log.d("Error", ex.toString());
                isSuccess = false;
                checkConnection = "No";
            }

            return checkConnection;
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }
}
