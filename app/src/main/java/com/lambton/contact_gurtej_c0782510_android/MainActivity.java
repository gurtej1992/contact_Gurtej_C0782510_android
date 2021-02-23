package com.lambton.contact_gurtej_c0782510_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView search_icon;
    EditText search;
    RecyclerView recyclerView;
    contactsAdapter madapter;
    ContactDatabase contactDatabase;
    List<Contact> listContacts;
    Button add;
    TextView txtTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = (Button) findViewById(R.id.btnAdd);
        txtTotal = findViewById(R.id.txtTotal);
        search=(EditText) findViewById(R.id.search_txt);
        search_icon=(ImageView) findViewById(R.id.search_icon);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        contactDatabase = ContactDatabase.getInstance(MainActivity.this);
        listContacts =  contactDatabase.getContactDeo().getAll();
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    //do here your stuff f
                    // hideSoftKeyboard();
                    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                    String  message = search.getText().toString();
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        add.setOnClickListener(view -> {
            Intent i=new Intent(getApplicationContext(),AddContactActivity.class);
            startActivityForResult(i,1);
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        madapter = new contactsAdapter(this,contactDatabase.getContactDeo().getAll()) {
            @Override
            public void deleteAddress(final int pos) {


            }
        };
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(madapter);
        String s = "Total no. of records are "+contactDatabase.getContactDeo().getAll().size();
        txtTotal.setText(s);
        final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
        //Asking request Permissions
        ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9);
    }

    private void filter(String text) {
        listContacts =  contactDatabase.getContactDeo().getAll();
        List<Contact> temp = new ArrayList();
        for (Contact c :listContacts) {
            if(c.getFname().toLowerCase().contains(text.toLowerCase()) || c.getLname().toLowerCase().contains(text.toLowerCase())){
                temp.add(c);
            }
        }
        madapter.contacts = temp;
        recyclerView.getAdapter().notifyDataSetChanged();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                if(data.getBooleanExtra("changed",false)){
                    madapter.contacts = contactDatabase.getContactDeo().getAll();
                    madapter.notifyDataSetChanged();
                }
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        if (requestCode == 9) {
            permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
        if(!permissionGranted){
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }
}
