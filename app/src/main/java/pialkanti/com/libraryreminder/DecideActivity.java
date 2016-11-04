package pialkanti.com.libraryreminder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Pial on 10/17/2016.
 */
public class DecideActivity extends AppCompatActivity {
    Boolean logInValue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        logInValue = pref.getBoolean("LogIN",false);
        if(logInValue == true){
            Intent intent = new Intent(DecideActivity.this,UserHomeActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent = new Intent(DecideActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
