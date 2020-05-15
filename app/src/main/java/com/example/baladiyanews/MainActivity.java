package com.example.baladiyanews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private String titre ;
    private int idNotification;
    private String text ;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "http://172.17.0.2:5000/Article";
    private static final String TAG = MainActivity.class.getName();
    ArrayList<String> listid =new ArrayList<>();
    ArrayList<String> listdescription =new ArrayList<>();
    ArrayList<String> listdate =new ArrayList<>();
    ArrayList<String> listimages =new ArrayList<>();
    ArrayList<String> listversion = new ArrayList<>();
    ArrayList<String> listversionNumber = new ArrayList<>();


    ListView lView;

    ListAdapter lAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lView = (ListView) findViewById(R.id.androidList);
        sendAndRequestResponse();

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tt=(TextView)view.findViewById(R.id.aNametxt);
                TextView txtimage =(TextView)view.findViewById(R.id.imagtxt);
                TextView txtdate =(TextView)view.findViewById(R.id.txtdate);
                TextView txtdescription =(TextView)view.findViewById(R.id.txtdescription);

                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                intent.putExtra("titre",tt.getText().toString() );
                intent.putExtra("date",txtdate.getText().toString() );
                intent.putExtra("description",txtdescription.getText().toString() );
                intent.putExtra("image",txtimage.getText().toString() );
                startActivity(intent);
            }
        });

    }
    private void sendAndRequestResponse() {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jArray = new JSONArray(response);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                   for(int i=0;i<jArray.length();i++){
                        JSONObject jsonObject1 = jArray.getJSONObject(i);
                         listid.add(jsonObject1.getString("idArticle"));
                         listdescription.add(jsonObject1.getString("description"));
                         listimages.add(jsonObject1.getString("imageArticle"));
                         listversion.add(jsonObject1.getString("nameArticle"));
                         listversionNumber.add(jsonObject1.getString("dateCreated"));
                         listdate.add(jsonObject1.getString("dateEnd"));
                        String dateEnd=jsonObject1.getString("dateEnd");
                        String dateArticle=jsonObject1.getString("dateCreated");
                       try {
                           Date strDate = sdf.parse(dateEnd);
                           Date ARTDate = sdf.parse(dateArticle);
                           int NewArticle = (int) ((ARTDate.getTime()-System.currentTimeMillis() ) / (1000 * 60 * 60 * 24));
                           int days = (int) ((strDate.getTime()-System.currentTimeMillis() ) / (1000 * 60 * 60 * 24));
                          // Toast.makeText(getApplicationContext(),jsonObject1.getString("idArticle")+":"+days , Toast.LENGTH_LONG).show();
                          if ((days == 1)||(days == 0)||(NewArticle == 0)) {
                               idNotification= Integer.parseInt(jsonObject1.getString("idArticle"));
                                titre=jsonObject1.getString("nameArticle");
                                text=jsonObject1.getString("description");
                               displayNotification();
                          }
                       }
                       catch (Exception e) {
                           Toast.makeText(getApplicationContext(),"Response :  date error " , Toast.LENGTH_LONG).show();
                       }


                    }


                    lAdapter = new ListAdapter(MainActivity.this,listversion, listversionNumber, listimages,listdate,listdescription,listid);
                   lView.setAdapter(lAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Response : error " , Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Response : flase" , Toast.LENGTH_LONG).show();
                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }
    private void displayNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"personnal_notifications");
        builder.setSmallIcon(R.drawable.chat);
        builder.setContentTitle(titre);
        builder.setContentText(text);
        builder.setVibrate(new long[]{500,1000});
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(idNotification,builder.build());

    }
}
