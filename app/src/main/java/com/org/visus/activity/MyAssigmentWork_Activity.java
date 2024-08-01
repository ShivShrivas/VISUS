package com.org.visus.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.org.visus.R;
import com.org.visus.activity.datasource.VISUS_DataSource;
import com.org.visus.adapters.Assigment_Adapter;
import com.org.visus.apis.ApiClient;
import com.org.visus.apis.ApiService;
import com.org.visus.databinding.ActivityMyAssigmentWorkBinding;
import com.org.visus.models.GetServices;
import com.org.visus.models.MyAssignment;
import com.org.visus.models.TotalCases;
import com.org.visus.utility.PrefUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAssigmentWork_Activity extends AppCompatActivity {
    TotalCases totalCases;
    List<TotalCases.TotalCasesData> totalCase;
    ActivityMyAssigmentWorkBinding binding;
    List<String> visusServiceList = new ArrayList<>();
    List<String> IDsVisusServiceList = new ArrayList<>();
    String visusService = "Select Service";
    String visusServiceID = "";
    String Token, InvestigatorID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAssigmentWorkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getTotalCases();
        resetService();
        addSpinnerAdapter(binding.spinnerSelectVisusService, visusServiceList);
        getServices();
        callListener();
    }

    private void callListener() {
        binding.spinnerSelectVisusService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position != 0) {
                    visusService = String.valueOf(adapterView.getItemAtPosition(position));
                    visusServiceID = IDsVisusServiceList.get(position - 1);
                    //getMyAssignment(visusServiceID);
                    getMyAssignmentAccordingTOServiceID(visusServiceID);
                } else {
                    visusService = "Select Service";
                    visusServiceID = "";
                    getTotalCases();
                    binding.recylerViewMyAssignment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void resetService() {
        visusServiceList.clear();
        String list = "Select Service";
        visusServiceList.add(list);
        addSpinnerAdapter(binding.spinnerSelectVisusService, visusServiceList);
    }

    public void addSpinnerAdapter(Spinner spinner, List<String> listModels) {
        MySpinnerAdapter dataAdapter = new MySpinnerAdapter(MyAssigmentWork_Activity.this, android.R.layout.simple_spinner_item, listModels);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(dataAdapter);
    }


    private static class MySpinnerAdapter extends ArrayAdapter<String> {
        //Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/muli_semibold.ttf");
        private MySpinnerAdapter(Context context, int resource, List<String> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 0);
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setLayoutParams(params);
            view.setTextColor(Color.BLACK);
            //view.setTextSize(15);
            //view.setTypeface(font);
            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            return view;
        }
    }

    public void goBack(View view) {
        finish();
    }

    private void getServices() {
        VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
        visus_dataSource.open();
        List<GetServices.GetServicesData> getServices = visus_dataSource.getMyServises();
        for (GetServices.GetServicesData servicesData : getServices) {
            visusServiceList.add(servicesData.getVisusServicesText());
            IDsVisusServiceList.add(servicesData.getVisusServicesID().toString());
        }
        visus_dataSource.close();
    }

    private void getTotalCases() {
        VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
        visus_dataSource.open();
        int TotalPendingCases = 0, TotalSubmittedCases = 0;
        totalCase = visus_dataSource.getToalCases();
        for (TotalCases.TotalCasesData totalCasesData : totalCase) {
            TotalPendingCases = TotalPendingCases + totalCasesData.getTotalPendingCases();
            TotalSubmittedCases = TotalSubmittedCases + totalCasesData.getTotalSubmittedCases();
        }
        binding.textViewTotalSubmittedCases.setText(String.valueOf(TotalSubmittedCases));
        binding.textViewTotalPendingCases.setText(String.valueOf(TotalPendingCases));
        visus_dataSource.close();
    }

    private void getToalCasesAccordingToServiceID(String visusServiceID) {
        int TotalPendingCases = 0, TotalSubmittedCases = 0;
        VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
        visus_dataSource.open();
        totalCase = visus_dataSource.getToalCasesAccordingToServiceID(visusServiceID);
        for (TotalCases.TotalCasesData totalCasesData : totalCase) {
            TotalPendingCases = TotalPendingCases + totalCasesData.getTotalPendingCases();
            TotalSubmittedCases = TotalSubmittedCases + totalCasesData.getTotalSubmittedCases();
        }
        binding.textViewTotalSubmittedCases.setText(String.valueOf(TotalSubmittedCases));
        binding.textViewTotalPendingCases.setText(String.valueOf(TotalPendingCases));
        visus_dataSource.close();
    }

    private void getMyAssignmentAccordingTOServiceID(String visusServiceID) {
        VISUS_DataSource visus_dataSource = new VISUS_DataSource(getApplicationContext());
        visus_dataSource.open();
        List<MyAssignment.MyAssignmentData> getDataMyAssignment = visus_dataSource.getDataMyAssignment(visusServiceID);
        visus_dataSource.close();
        getToalCasesAccordingToServiceID(visusServiceID);
        if (getDataMyAssignment.size() == 0) {
            binding.textViewNoRecordMyAssignment.setVisibility(View.VISIBLE);
            binding.recylerViewMyAssignment.setVisibility(View.GONE);
        } else {
            binding.textViewNoRecordMyAssignment.setVisibility(View.GONE);
            binding.recylerViewMyAssignment.setVisibility(View.VISIBLE);
            binding.recylerViewMyAssignment.setHasFixedSize(true);
            binding.recylerViewMyAssignment.setAdapter(new Assigment_Adapter(
                    MyAssigmentWork_Activity.this, getDataMyAssignment, getApplicationContext(), visusService,
                    visusServiceID));
        }
    }


}