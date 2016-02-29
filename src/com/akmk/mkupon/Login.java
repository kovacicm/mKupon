package com.akmk.mkupon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
	
	private EditText usernameEditText;
	private EditText passwordEditText;
	private String sUserName;
	private String sPassword;
	private Intent pocetna;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);        
        
        Button launch = (Button)findViewById(R.id.login_button);
        
        pocetna=new Intent(this, Pocetna.class);
        
        launch.setOnClickListener( new OnClickListener()

                {
                        public void onClick(View viewParam)

                        { // this gets the resources in the xml file and assigns it to a local variable of type EditText
                       usernameEditText = (EditText) findViewById(R.id.usernameEditText);
                       passwordEditText = (EditText) findViewById(R.id.passwordEditText);
                       
                        // the getText() gets the current value of the text box
                        // the toString() converts the value to String data type
                        // then assigns it to a variable of type String
                        sUserName = usernameEditText.getText().toString();
                        sPassword = passwordEditText.getText().toString(); 
                        
                        
                        startActivity(pocetna);
                        
                        
                        
           //             postData();

                        }  

                }              

                ); // end of launch.setOnclickListener
       
        
    }
    
    public void postData() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://omiskastinazagreb.com/mw/usr/login.php");
        BufferedReader in;

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("myusername", sUserName));
            nameValuePairs.add(new BasicNameValuePair("mypassword", sPassword));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            in = new BufferedReader
            (new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
            sb.append(line + NL);
            }
            in.close();
            String page = sb.toString();
            System.out.println(page);
            
            
        } catch (ClientProtocolException e) {
        	e.printStackTrace();
            // TODO Auto-generated catch block
        } catch (IOException e) {
        	e.printStackTrace();
            // TODO Auto-generated catch block
        }
    }
}