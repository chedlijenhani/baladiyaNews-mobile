package com.example.baladiyanews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    ImageRequest imageRequest;
    ImageView img;
    TextView titre,description,date;
    String image ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        img=findViewById(R.id.imageView3);
        titre=findViewById(R.id.textView);
        description=findViewById(R.id.textView1);
        date=findViewById(R.id.textView2);
        Intent intent = getIntent();
        image = intent.getStringExtra("image");
        titre.setText(intent.getStringExtra("titre"));
        description.setText(intent.getStringExtra("description"));
        String txtend ="سليانة في"+intent.getStringExtra("date");
        date.setText(txtend);
        //Toast.makeText(MainActivity2.this, ""+image, Toast.LENGTH_SHORT).show();
        getImg();
    }



    public void getImg() {
        imageRequest = new ImageRequest( "http://172.17.0.2:5000/Article/getImage", new Response.Listener<Bitmap>() {
        @Override
        public void onResponse(final Bitmap response) {
            img.setImageBitmap(response);
        }
    }, 0, 0, null, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity2.this, error.toString(), Toast.LENGTH_LONG).show();
        }
    }) {
        public Map getHeaders() throws AuthFailureError {
            HashMap headers = new HashMap();
            headers.put("Content-Type", "application/json; charset=UTF-8");
            headers.put("x-api-Key", "eiWee8ep9due4deeshoa8Peichai8Eih");
            headers.put("image",image);
            return headers;
        }
    };


    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        requestQueue.add(imageRequest);
        requestQueue.getCache().clear();}

}