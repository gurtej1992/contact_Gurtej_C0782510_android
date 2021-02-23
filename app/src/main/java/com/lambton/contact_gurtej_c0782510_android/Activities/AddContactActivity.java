package com.lambton.contact_gurtej_c0782510_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lambton.contact_gurtej_c0782510_android.Model.Contact;
import com.lambton.contact_gurtej_c0782510_android.DBHelper.ContactDatabase;
import com.lambton.contact_gurtej_c0782510_android.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddContactActivity extends AppCompatActivity {
    Button back,save,delete;
    EditText fname,lname,email,phone,address;
    ContactDatabase contactDatabase;
    Contact selectedContact;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        title = findViewById(R.id.textView);
        fname = findViewById(R.id.editFname);
        lname = findViewById(R.id.editLname);
        email = findViewById(R.id.editEmailAdd);
        phone = findViewById(R.id.editPhone);
        address = findViewById(R.id.editAdd);
        back = (Button) findViewById(R.id.btnBack);
        save = (Button) findViewById(R.id.btnSave);
        delete = (Button) findViewById(R.id.btnDelete);
        contactDatabase = ContactDatabase.getInstance(AddContactActivity.this);
        back.setOnClickListener(v ->{
            finish();
        });
        delete.setOnClickListener(v -> {
            if(selectedContact != null){
                contactDatabase.getContactDeo().delete(selectedContact);
                Intent i = new Intent();
                i.putExtra("changed",true);
                setResult(RESULT_OK, i);
                finish();
            }
        });
        save.setOnClickListener(v ->{
            if(validateData()){
                if(selectedContact != null){
                    selectedContact.setAddress(address.getText().toString());
                    selectedContact.setEmail(email.getText().toString());
                    selectedContact.setFname(fname.getText().toString());
                    selectedContact.setLname(lname.getText().toString());
                    selectedContact.setNumber(Long.parseLong(phone.getText().toString()));
                    contactDatabase.getContactDeo().update(selectedContact);
                }
                else{
                    saveData();
                }
                Intent i = new Intent();
                i.putExtra("changed",true);
                setResult(RESULT_OK, i);
                finish();
            }
        });
        int selectedIndex = getIntent().getIntExtra("selectedIndex",-1);
        title.setText(R.string.addContact);
        if(selectedIndex != -1){
            title.setText(R.string.updateContact);
            getDataAndSetIT(selectedIndex);
        }

    }

    private void getDataAndSetIT(int pos) {
        delete.setVisibility(View.VISIBLE);
        selectedContact = contactDatabase.getContactDeo().getAll().get(pos);
        fname.setText(selectedContact.getFname());
        lname.setText(selectedContact.getLname());
        address.setText(selectedContact.getAddress());
        email.setText(selectedContact.getEmail());
       phone.setText(String.valueOf(selectedContact.getNumber()));
    }

    private void saveData() {
        Contact contact = new Contact(fname.getText().toString(),lname.getText().toString(),email.getText().toString(),Integer.parseInt(phone.getText().toString()),address.getText().toString());
        contactDatabase.getContactDeo().insert(contact);
    }

    private boolean validateData() {
         if((fname.getText().toString().length() == 0) || (lname.getText().toString().length() == 0) || (address.getText().toString().length() == 0) || (phone.getText().toString().length() == 0) || (email.getText().toString().length() == 0)){
                 Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
             return false;
        }
        else if (!emailValidator(email.getText().toString())){
             email.setError("Email is invalid");
             return false;
         }
        else{
             return true;
         }
    }
    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}