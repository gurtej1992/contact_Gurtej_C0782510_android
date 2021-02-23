package com.lambton.contact_gurtej_c0782510_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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

import com.baoyz.actionsheet.ActionSheet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ActionSheet.ActionSheetListener {
    ImageView search_icon;
    EditText search;
    RecyclerView recyclerView;
    contactsAdapter madapter;
    ContactDatabase contactDatabase;
    List<Contact> listContacts;
    Button add;
    TextView txtTotal,title;
    Contact selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = (Button) findViewById(R.id.btnAdd);
        txtTotal = findViewById(R.id.txtTotal);
        title = findViewById(R.id.textView);
        search = (EditText) findViewById(R.id.search_txt);
        search_icon = (ImageView) findViewById(R.id.search_icon);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        contactDatabase = ContactDatabase.getInstance(MainActivity.this);
        listContacts = contactDatabase.getContactDeo().getAll();
        title.setText(R.string.contacts);
        search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                //do here your stuff f
                // hideSoftKeyboard();
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(search.getWindowToken(), 0);
                String message = search.getText().toString();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
        add.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), AddContactActivity.class);
            startActivityForResult(i, 1);
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

        madapter = new contactsAdapter(this, contactDatabase.getContactDeo().getAll()) {
            @Override
            public void updateScreen(final int pos) {
                Intent i = new Intent(getApplicationContext(), AddContactActivity.class);
                i.putExtra("selectedIndex", pos);
                startActivityForResult(i, 1);

            }

            @Override
            public void longPressIsInvoke(int i) {
                selected = contactDatabase.getContactDeo().getAll().get(i);
                ActionSheet.createBuilder(getApplicationContext(), getSupportFragmentManager())
                        .setCancelButtonTitle("Cancel")
                        .setOtherButtonTitles("Phone Call", "Message", "Email")
                        .setCancelableOnTouchOutside(true)
                        .setListener(MainActivity.this).show();


            }
        };
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(madapter);
        String s = "Total Records = " + contactDatabase.getContactDeo().getAll().size();
        txtTotal.setText(s);
        final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
        //Asking request Permissions
        ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9);
    }

    private void filter(String text) {
        listContacts = contactDatabase.getContactDeo().getAll();
        List<Contact> temp = new ArrayList();
        for (Contact c : listContacts) {
            if (c.getFname().toLowerCase().contains(text.toLowerCase()) || c.getLname().toLowerCase().contains(text.toLowerCase())) {
                temp.add(c);
            }
        }
        madapter.contacts = temp;
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data.getBooleanExtra("changed", false)) {
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
        if (!permissionGranted) {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
        if (selected != null) {
            switch (index) {
                case 0:
                    callOnNumber(selected);
                    break;
                case 1:
                    messageOnNumber(selected);
                    break;
                default:
                    emailOnAddress(selected);
            }
        }
    }

    private void emailOnAddress(Contact selected) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{selected.getEmail()});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        emailIntent.setType("text/plain");
        startActivity(Intent.createChooser(emailIntent,"email"));
    }

    private void messageOnNumber(Contact selected) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + selected.getNumber()));
        intent.putExtra("sms_body", "");
        startActivity(intent);
    }

    public void callOnNumber(Contact selected) {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + selected.getNumber()));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);
        }
    }
}
/* if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + contactDatabase.getContactDeo().getAll().get(i).getNumber()));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(callIntent);
                }*/
