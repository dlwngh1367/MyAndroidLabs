package algonquin.cst2335.lee00824.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import algonquin.cst2335.lee00824.data.MainViewModel;
import algonquin.cst2335.lee00824.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding variableBinding;
    private MainViewModel model;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        model.editString.observe(this, s -> {
          variableBinding.mytext.setText("Your edit text has " +s);
        });

        variableBinding.myedittext.setText(model.editString);
        variableBinding.mybutton.setOnClickListener(click -> {
                    model.editString.postValue(variableBinding.myedittext.getText().toString());
                    variableBinding.myedittext.setText("Your edit text has: " + model.editString);
                });


        Button btn = variableBinding.mybutton;
        TextView mytext = variableBinding.textview;
        EditText myedit = variableBinding.myedittext;
        String editString = myedit.getText().toString();
        mytext.setText( "Your edit text has: " + editString);
    }




}