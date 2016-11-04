package pialkanti.com.libraryreminder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import pialkanti.com.libraryreminder.database.DBadapter;
import pialkanti.com.libraryreminder.database.Database;


public class MainActivity extends AppCompatActivity {
    private Toolbar appToolbar;
    private EditText UserName, PassWord;
    private CoordinatorLayout cLayout;
    private Button logIN;
    String user, pass = "";
    ProgressDialog progressDialog;
    String[] bookName, Due, Call_no, Renew;
    int i;
    Database db;
    SQLiteDatabase sql;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public static final String Table_Name = "library_table";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(appToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setTitle(R.string.app_name);

        cLayout = (CoordinatorLayout) findViewById(R.id.CoOrdinatorLayout);
        UserName = (EditText) findViewById(R.id.input_username);
        PassWord = (EditText) findViewById(R.id.input_password);
        logIN = (Button) findViewById(R.id.btn_login);

        logIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This below code hide virtual keyboard after pressing button
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                if (isNetworkAvailable()) {
                    user = UserName.getText().toString();
                    pass = PassWord.getText().toString();

                    if (!user.isEmpty() && !pass.isEmpty()) {

                        progressDialog = new ProgressDialog(MainActivity.this,
                                R.style.AppTheme_Dark_Dialog);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Logging into library.kuet.ac.bd");
                        progressDialog.show();


                        new LoginToLibraryURL().execute(user, pass);
                    } else {
                        if (user.isEmpty())
                            showSnackBar("Enter username");
                        else if (pass.isEmpty())
                            showSnackBar("Enter password");
                    }

                }else{
                    showSnackBar("No Internet connection");
                }
            }
        });


    }

    public void showSnackBar(final String msg) {
        Snackbar snackbar;
        if (msg == "No Internet connection") {
            snackbar = Snackbar.make(cLayout, msg, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Turn On", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });
        } else {
            snackbar = Snackbar.make(cLayout, msg, Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (msg == "Wrong Username or Password") {
                                UserName.setText("");
                                PassWord.setText("");
                                UserName.requestFocus();
                            } else if (msg == "Enter username") {
                                UserName.setText("");
                                UserName.requestFocus();
                            } else if (msg == "Enter password") {
                                PassWord.setText("");
                                PassWord.requestFocus();
                            }

                        }
                    });
        }

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    class LoginToLibraryURL extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            Log.i("Test", strings[0] + " " + strings[1]);

            try {
                org.jsoup.Connection.Response loginForm = Jsoup.connect("http://library.kuet.ac.bd/")
                        .method(org.jsoup.Connection.Method.GET)
                        .execute();
                Document doc = Jsoup.connect("http://180.211.192.70:8000/cgi-bin/koha/opac-user.pl")
                        .data("userid", strings[0])
                        .data("password", strings[1])
                        .cookies(loginForm.cookies())
                        .post();

                progressDialog.cancel();

                String UserName = doc.select("span.loggedinusername").text();
                //Saving Username in SharedPreference
                pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                editor = pref.edit();
                editor.putString("UserName", UserName);
                editor.commit();

                if (doc.select("p").hasText()) {
                    showSnackBar("Wrong Username or Password");
                } else {
                    //Saving data in SharedPreference
                    editor = pref.edit();
                    editor.putBoolean("LogIN", true);
                    editor.commit();


                    bookName = new String[3];
                    Due = new String[3];
                    Call_no = new String[3];
                    Renew = new String[3];
                    i = 0;
                    for (Element book : doc.select("td.title")) {
                        String b = book.select("a").text();
                        b = "<b><font color='#006699'>" + b + "</font></b>";
                        String ba = book.select("span").text();
                        String bn = b + ba;
                        bookName[i] = bn;
                        Log.i("Test", bookName[i]);
                        i++;
                    }
                    i = 0;
                    for (Element book : doc.select("td.date_due")) {
                        String d = book.text();
                        Due[i] = d;
                        Log.i("Test", Due[i]);
                        i++;
                    }
                    i = 0;
                    for (Element book : doc.select("td.call_no")) {
                        String c = book.text();
                        Call_no[i] = c;
                        Log.i("Test", Call_no[i]);
                        i++;
                    }
                    i = 0;
                    for (Element book : doc.select("span.renewals")) {
                        String r = book.text();
                        Renew[i] = r;
                        Log.i("Test", Renew[i]);
                        i++;
                    }

                    //This code is for database work
                    db = new Database(getApplicationContext());
                    sql = db.getWritableDatabase();
                    DBadapter dBadapter = new DBadapter(getApplicationContext());
                    Log.i("Test", "i=" + i + "");
                    for (int sz = 0; sz < i; sz++) {
                        dBadapter.insertBookDetails(bookName[sz], Due[sz], Call_no[sz], Renew[sz]);
                    }
                    Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
                    intent.putExtra("UserName", UserName);
                    startActivity(intent);
                    finish();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }
}
