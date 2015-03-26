package com.mobidosoft.testactiviti;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobidosoft.testactiviti.utils.HttpPosUtil;
import com.mobidosoft.testactiviti.utils.UtilityHttp;

import org.json.JSONException;

/**
 * A placeholder fragment containing a simple view.
 */
public class SaverPersonFragment extends Fragment {

    private static final String LOG_TAG = SaverPersonFragment.class.getSimpleName();

    public static Context contectParent;
    public SaverPersonFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_save_person, container, false);

        contectParent = rootView.getContext();

        final EditText editTextName =(EditText)rootView.findViewById(R.id.txtName);



        Button buttonAgregar = (Button)rootView.findViewById(R.id.btnGuardar);
        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Start Agregar web services");
                SavePersonTask savePersonTask = new SavePersonTask();
                savePersonTask.execute(editTextName.getText().toString());

                Log.d(LOG_TAG,"End Agregar en web services");
            }
        });



        return rootView;
    }


    class SavePersonTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            Log.v(LOG_TAG, "stat doInBrackGround");

            String name = params[0];
            //final String FIXTURE_BASE_URL = "http://192.168.3.149:3000/hello.json";
            final String FIXTURE_BASE_URL = "http://192.168.0.180:3000/hello.json";
            String jsonString = "{\"person\":{\"name\":\""+ name +"\"}}";
            String resultString = HttpPosUtil.postData(FIXTURE_BASE_URL,jsonString);
            Log.v(LOG_TAG,resultString);

            return resultString;
        }

        @Override
        protected void onPostExecute(String strings) {

            //Toast toast1 = Toast.makeText(parentContext.getApplicationContext(),"Error:Nombre de usuario o password incorrectos", Toast.LENGTH_SHORT);
            //toast1.show();1


            AlertDialog alertDialog;
            alertDialog = new AlertDialog.Builder(contectParent).create();
            alertDialog.setTitle("Result Service");
            alertDialog.setMessage(strings);
            alertDialog.show();

        }
    }
}
