package algonquin.cst2335.lee00824;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.w(TAG, "In onCreate() - Loading Widgets" );

        Button loginButton = (Button) findViewById(R.id.login);
        EditText emailEditText = (EditText) findViewById(R.id.emailText);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String emailAddress = prefs.getString("LoginName", "");
        emailEditText.setText(emailAddress);

        loginButton.setOnClickListener(clk -> {

            editor.putString("LoginName", emailEditText.getText().toString());
            editor.apply();

            Intent nextPage = new Intent( MainActivity.this, SecondActivity.class);
            nextPage.putExtra( "EmailAddress", emailEditText.getText().toString() );
            startActivity(nextPage);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "In onStart() - Application visible on screen" );

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "In onResume() - Responding to user input" );

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "In onPause() - Not responding to user input" );

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, "In onStop() - Application not visible" );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "In onDestroy() - Memory is freed" );

    }

}