package algonquin.cst2335.lee00824;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2335.lee00824.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    protected String cityName;
    protected ActivityMainBinding binding;
    protected RequestQueue queue = null;
    protected Bitmap image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        queue =Volley.newRequestQueue(this);

        binding.button.setOnClickListener(click -> {
            cityName = binding.editText.getText().toString();
            String stringURL = null;
            try {
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q=" + URLEncoder.encode(cityName,"UTF-8") +
                        "&appid=c8b0b94673b38aef472714b32b3bf811&units=metric";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            JsonObjectRequest request = new JsonObjectRequest( Request.Method.GET, stringURL, null,
                    ( response ) -> {
                        try {
                            JSONObject coord = response.getJSONObject("coord");
                            JSONArray weatherArray = response.getJSONArray( "weather");
                            int visibility = response.getInt("visibility");
                            String name = response.getString("name");
                            JSONObject postion0 = weatherArray.getJSONObject(0);
                            String description = postion0.getString("description");
                            String iconName = postion0.getString("icon");

                            JSONObject main = response.getJSONObject("main");
                            double current = main.getDouble("temp");
                            double temp_min = main.getDouble("temp_min");
                            double temp_max = main.getDouble("temp_max");
                            int humidity = main.getInt("humidity");

                            String imageUrl = "https://openweathermap.org/img/w/" + iconName + ".png";
                            String pathname = getFilesDir()+"/" + iconName +".png";
                            File file = new File( pathname );

                            if(file.exists()){
                                image = BitmapFactory.decodeFile(pathname);
                            }
                            else{
                                ImageRequest imgReq = new ImageRequest( imageUrl,
                                        new Response.Listener<Bitmap> () {
                                            @Override
                                            public void onResponse(Bitmap bitmap) {

                                                try {
                                                    image = bitmap;
                                                    image.compress(Bitmap.CompressFormat.PNG, 100,
                                                            MainActivity.this.openFileOutput( iconName + ".png",
                                                                    Activity.MODE_PRIVATE));
                                                    FileOutputStream fOut = null;
                                                    try {
                                                        fOut = openFileOutput( iconName +".png", Context.MODE_PRIVATE);
                                                        image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                                        fOut.flush();
                                                        fOut.close();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                } catch (FileNotFoundException e) {
                                                    e.printStackTrace();
                                                }
                                                runOnUiThread( () -> {
                                                    binding.icon.setImageBitmap(image);
                                                    binding.icon.setVisibility(View.VISIBLE);

                                                });


                                            }
                                        }, 1024, 1024, ImageView.ScaleType.CENTER, null,
                                        ( error) -> {

                                        });
                                queue.add(imgReq);
                            }



                            runOnUiThread( ( ) -> {
                                binding.temp.setText("The current temperature is " + current);
                                binding.temp.setVisibility(View.VISIBLE);

                                binding.minTemp.setText("The min temperature is " + temp_min);
                                binding.minTemp.setVisibility(View.VISIBLE);

                                binding.maxTemp.setText("The max temperature is " + temp_max);
                                binding.maxTemp.setVisibility(View.VISIBLE);

                                binding.humidity.setText("The humidity is " + humidity);
                                binding.humidity.setVisibility(View.VISIBLE);

                                binding.icon.setImageBitmap(image);
                                binding.icon.setVisibility(View.VISIBLE);

                                binding.description.setText( description);
                                binding.description.setVisibility(View.VISIBLE);
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    ( error ) -> {

                    }
            );

            queue.add( request );
        });


}
}
