package algonquin.cst2335.lee00824.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
            variableBinding.textview.setText("Your edit text has " + s);
        });

        //variableBinding.myedittext.setText(model.editString);
        variableBinding.mybutton.setOnClickListener(click -> {
            model.editString.postValue(variableBinding.myedittext.getText().toString());
            //        variableBinding.myedittext.setText("Your edit text has: " + model.editString);
        });

        model.buttonSelected.observe(this, selected -> {
            variableBinding.checkBox.setChecked(selected);
            variableBinding.radioButton.setChecked(selected);
            variableBinding.switchButton.setChecked(selected);
            Context context = getApplicationContext();
            CharSequence text = "The value is now: " + selected;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });

        variableBinding.checkBox.setOnCheckedChangeListener((checkbox, isChecked) ->
        {
            model.buttonSelected.postValue(isChecked);
        });

        variableBinding.radioButton.setOnCheckedChangeListener((radiobutton, isChecked) ->
        {
            model.buttonSelected.postValue(isChecked);
        });

        variableBinding.switchButton.setOnCheckedChangeListener((switch1, isChecked) ->
        {
            model.buttonSelected.postValue(isChecked);
        });
        // setOnCheckedChangeListener( (btn, isChecked) -> { } );


        variableBinding.myimagebutton.setOnClickListener(click ->
        {
            int width = variableBinding.myimagebutton.getWidth();
            int height = variableBinding.myimagebutton.getHeight();
            Context context = getApplicationContext();
            CharSequence text = "The width = " + width + " and height = " + height;
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });

    }
}