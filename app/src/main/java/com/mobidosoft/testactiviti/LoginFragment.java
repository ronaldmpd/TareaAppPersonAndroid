package com.mobidosoft.testactiviti;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.mobidosoft.testactiviti.utils.UtilityHttp;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginFragment extends Fragment {

    private static final String LOG_TAG = LoginFragment.class.getSimpleName();

    EditText editTextUserName;
    Button buttonEntrar;

    private ArrayAdapter<String> arrayAdapter;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        editTextUserName =(EditText)rootView.findViewById(R.id.txtUserName);

        buttonEntrar = (Button)rootView.findViewById(R.id.btnEntrar);


        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Start Login");


                String usuario  = editTextUserName.getText().toString();

                Intent intent = new Intent(getActivity(),HomeActivity.class);
                intent.putExtra("name", usuario);


                startActivity(intent);


                Log.d(LOG_TAG,usuario);

                Log.d(LOG_TAG,"End Login");
            }
        });



        Button buttonListar = (Button)rootView.findViewById(R.id.btnListar);
        buttonListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Start Listar");


                String[] results = {"Wilsterman 1 - Aurora 1",
                        "River Plate 1 - Boca Juniors 1",
                        "River Plate 1 - Boca Juniors 1",
                        "River Plate 1 - Boca Juniors 1",
                        "River Plate 1 - Boca Juniors 1",
                        "River Plate 1 - Boca Juniors 1",
                        "River Plate 1 - Boca Juniors 1",
                        "River Plate 1 - Boca Juniors 1",
                        "Wilsterman 1 - Aurora 1",
                        "River Plate 1 - Boca Juniors 1",
                        "River Plate 1 - Boca Juniors 1",
                        "River Plate 1 - Boca Juniors 1",
                        "River Plate 1 - Boca Juniors 1",
                        "River Plate 1 - Boca Juniors 1",
                        "River Plate 1 - Boca Juniors 1",
                        "River Plate 1 - Boca Juniors 1"};
                ArrayList<String> resultList = new ArrayList<>(Arrays.asList(results));

                arrayAdapter = new ArrayAdapter<String>(getActivity(),
                        R.layout.result_view,
                        R.id.result_text_view,
                        resultList);


                final ListView scrollView = (ListView)rootView.findViewById(R.id.result_list_view);

                scrollView.setAdapter(arrayAdapter);




                GetResultTask task = new GetResultTask();
                task.execute();


                Log.d(LOG_TAG,"End Listar");
            }
        });



        Button buttonAgregar = (Button)rootView.findViewById(R.id.btnAgregar);
        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Start Agregar");

                Intent intent = new Intent(getActivity(),SavePersonActivity.class);
                //intent.putExtra("name", usuario);


                startActivity(intent);




                Log.d(LOG_TAG,"End Agregar");
            }
        });


        return rootView;
    }

    class GetResultTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {

            Log.v(LOG_TAG,"stat doInBrackGround");
            String resultString = UtilityHttp.getJsonStringFromNetwork();
            Log.v(LOG_TAG,resultString);

            try {
                return UtilityHttp.parseFixtureJson(resultString);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error parsing" + e.getMessage(), e);
                e.printStackTrace();
                return new String[] {"No DATA"};
            }
        }

        @Override
        protected void onPostExecute(String[] strings) {
            arrayAdapter.clear();

            for(String result :strings){
                arrayAdapter.add(result);
            }
        }
    }

}
