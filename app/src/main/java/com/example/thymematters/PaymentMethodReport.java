package com.example.thymematters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.muddz.styleabletoast.StyleableToast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PaymentMethodReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method_report);


        HttpUrl.Builder urlBuilder2 = HttpUrl.parse("https://thymematters.000webhostapp.com/REPORTS/SalesReport2.php").newBuilder();

        //If you want to add query parameters:
        //urlBuilder.addQueryParameter("meal_category_name","Meat");
        //urlBuilder.addQueryParameter("password",Password);

        String url2 = urlBuilder2.build().toString();
        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable2 = isNetworkAvailable();
        if(!networkAvailable2){ StyleableToast.makeText(PaymentMethodReport.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request

        //Initialise progree bar: https://stackoverflow.com/questions/15083226/waiting-progress-bar-in-android
        //Progress Bar Functions: https://www.journaldev.com/9652/android-progressdialog-example
        //final ProgressDialog progressDialog = ProgressDialog.show(this, "Fetching Meat Meals", "Please wait...");

        OkHttpClient client2 = new OkHttpClient();
        Request request2 = new Request.Builder()
                .url(url2)
                .build();

        client2.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse( Call call,  Response response) throws IOException {
                if (response.isSuccessful()){
                    final String myResponse = response.body().string();

                    PaymentMethodReport.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //processData(myResponse);
                                pieGraph(myResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void pieGraph(String response) throws JSONException {
        JSONArray json = new JSONArray(response);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Pie pie = AnyChart.pie();
        pie.bounds(0, 0, "100%", "100%");

        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(PaymentMethodReport.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        List<DataEntry> data = new ArrayList<>();

        for (int i =0 ; i < json.length(); i++){

            JSONObject jobject = json.getJSONObject(i);

            String category = jobject.getString("Payment_Method");
            int total = Integer.parseInt(jobject.getString("Total"));
            data.add(new ValueDataEntry(category, total));
        }

        pie.data(data);

        pie.title("Payment Methods");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Categories")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        anyChartView.setChart(pie);
    }

//    public void pieGraph(String response) throws JSONException {
//        JSONArray json = new JSONArray(response);
//
//        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
//        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
//
//        Pie pie = AnyChart.pie();
//
//        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
//            @Override
//            public void onClick(Event event) {
//                Toast.makeText(PaymentMethodReport.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        List<DataEntry> data = new ArrayList<>();
//
//        for (int i =0 ; i < json.length(); i++){
//
//            JSONObject jobject = json.getJSONObject(i);
//
//            String category = jobject.getString("Payment_Method");
//            int total = Integer.parseInt(jobject.getString("Total"));
//            data.add(new ValueDataEntry(category, total));
//        }
//
//        pie.data(data);
//
//        pie.title("Meal Categories");
//
//        pie.labels().position("outside");
//
//        pie.legend().title().enabled(true);
//        pie.legend().title()
//                .text("Categories")
//                .padding(0d, 0d, 10d, 0d);
//
//        pie.legend()
//                .position("center-bottom")
//                .itemsLayout(LegendLayout.HORIZONTAL)
//                .align(Align.CENTER);
//
//        anyChartView.setChart(pie);
//    }

}