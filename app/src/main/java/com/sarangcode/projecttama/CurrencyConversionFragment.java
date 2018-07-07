package com.sarangcode.projecttama;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class CurrencyConversionFragment extends Fragment {
    Spinner spinner;
    Button btn_convert;
    EditText input_value;
    TextView result_textview;
    String country;
    Float input;
    Double result;

    public CurrencyConversionFragment() {
        // Required empty public constructor
    }
    public static CurrencyConversionFragment newInstance() {
        CurrencyConversionFragment fragment = new CurrencyConversionFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_currency_conversion_layout, container, false);
        spinner=v.findViewById(R.id.spinner2);
        btn_convert=v.findViewById(R.id.button_convert);
        input_value=v.findViewById(R.id.input_value);
        result_textview=v.findViewById(R.id.result_textView);

       // String [] countires={"USD","EUR","GBP","INR","JPY","AUD","CNY"};
       // ArrayAdapter<String> adapter =new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,countires);
        //spinner.setAdapter(adapter);
        ArrayList<ItemData> list=new ArrayList<>();
        list.add(new ItemData("USD",R.drawable.usa));
        list.add(new ItemData("EUR",R.drawable.euro));
        list.add(new ItemData("GBP",R.drawable.uk));
        list.add(new ItemData("INR",R.drawable.india));
        list.add(new ItemData("JPY",R.drawable.japan));
        list.add(new ItemData("AUD",R.drawable.aus));
        list.add(new ItemData("CNY",R.drawable.china));
        SpinnerAdpater adapter=new SpinnerAdpater(getActivity(),R.layout.spinner_layout,R.id.txt,list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country= ((TextView)view.findViewById(R.id.txt)).getText().toString();
               // Toast.makeText(getContext(),country,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                input=Float.valueOf(input_value.getText().toString());
                    switch (country) {
                        case "USD":
                            result = input * 107.72;
                            break;
                        case "EUR":
                            result = input * 129.09;
                            break;

                        case "GBP":
                            result = input * 146.65;
                            break;

                        case "INR":
                            result = input * 1.6;
                            break;

                        case "JPY":
                            result = input * 0.983;
                            break;

                        case "AUD":
                            result = input * 81.33;
                            break;
                        case "CNY":
                            result = input * 17.00;
                            break;


                    }
                result_textview.setText("NRS "+result.toString());
                }
        });

        return v;
    }

}
