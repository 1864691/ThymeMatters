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
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Anchor;

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


public class Sales_Report extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_report);


        //Send request to fetch all meat meals from meals table:
        //Send network request to 000webhost:
        //Define URL:
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://thymematters.000webhostapp.com/REPORTS/SalesReport.php").newBuilder();

        //If you want to add query parameters:
        //urlBuilder.addQueryParameter("meal_category_name","Meat");
        //urlBuilder.addQueryParameter("password",Password);

        String url = urlBuilder.build().toString();
        //Check if network is available: https://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
        boolean networkAvailable = isNetworkAvailable();
        if(!networkAvailable){ StyleableToast.makeText(Sales_Report.this, "No Internet Connection", Toast.LENGTH_LONG, R.style.noInternet).show(); return;}

        //Send Request

        //Initialise progree bar: https://stackoverflow.com/questions/15083226/waiting-progress-bar-in-android
        //Progress Bar Functions: https://www.journaldev.com/9652/android-progressdialog-example
        //final ProgressDialog progressDialog = ProgressDialog.show(this, "Fetching Meat Meals", "Please wait...");

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse( Call call,  Response response) throws IOException {
                if (response.isSuccessful()){
                    final String myResponse = response.body().string();

                    Sales_Report.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //processData(myResponse);
                                barGraph(myResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });



//        AnyChartView anyChartView = findViewById(R.id.any_chart_view1);
//        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
//
//        CircularGauge circularGauge = AnyChart.circular();
//        circularGauge.bounds(0, 0, "100%", "20%");
//
//        circularGauge.data(new SingleValueDataSet(new String[] { "93", "56", "100"}));
//        circularGauge.fill("#fff")
//                .stroke(null)
//                .padding(0d, 0d, 0d, 0d)
//                .margin(50d, 50d, 50d, 50d);
//        circularGauge.startAngle(0d);
//        circularGauge.sweepAngle(270d);
//
//        Circular xAxis = circularGauge.axis(0)
//                .radius(100d)
//                .width(1d)
//                .fill((Fill) null);
//        xAxis.scale()
//                .minimum(0d)
//                .maximum(100d);
//        xAxis.ticks("{ interval: 1 }")
//                .minorTicks("{ interval: 1 }");
//        xAxis.labels().enabled(false);
//        xAxis.ticks().enabled(false);
//        xAxis.minorTicks().enabled(false);
//
//
//        circularGauge.label(3d)
//                .text("Fluoride, <span style=\"\">93%</span>")
//                .useHtml(true)
//                .hAlign(HAlign.CENTER)
//                .vAlign(VAlign.MIDDLE);
//        circularGauge.label(3d)
//                .anchor(String.valueOf(Anchor.RIGHT_CENTER))
//                .padding(0d, 10d, 0d, 0d)
//                .height(17d / 2d + "%")
//                .offsetY(40d + "%")
//                .offsetX(0d);
//        Bar bar3 = circularGauge.bar(3d);
//        bar3.dataIndex(3d);
//        bar3.radius(40d);
//        bar3.width(17d);
//        bar3.fill(new SolidFill("#ffd54f", 1d));
//        bar3.stroke(null);
//        bar3.zIndex(5d);
//        Bar bar103 = circularGauge.bar(103d);
//        bar103.dataIndex(5d);
//        bar103.radius(40d);
//        bar103.width(17d);
//        bar103.fill(new SolidFill("#F5F4F4", 1d));
//        bar103.stroke("1 #e5e4e4");
//        bar103.zIndex(4d);
//
//        circularGauge.label(4d)
//                .text("Zinc Oxide, <span style=\"\">56%</span>")
//                .useHtml(true)
//                .hAlign(HAlign.CENTER)
//                .vAlign(VAlign.MIDDLE);
//        circularGauge.label(4d)
//                .anchor(String.valueOf(Anchor.RIGHT_CENTER))
//                .padding(0d, 10d, 0d, 0d)
//                .height(17d / 2d + "%")
//                .offsetY(20d + "%")
//                .offsetX(0d);
//        Bar bar4 = circularGauge.bar(4d);
//        bar4.dataIndex(4d);
//        bar4.radius(20d);
//        bar4.width(17d);
//        bar4.fill(new SolidFill("#455a64", 1d));
//        bar4.stroke(null);
//        bar4.zIndex(5d);
//        Bar bar104 = circularGauge.bar(104d);
//        bar104.dataIndex(5d);
//        bar104.radius(20d);
//        bar104.width(17d);
//        bar104.fill(new SolidFill("#F5F4F4", 1d));
//        bar104.stroke("1 #e5e4e4");
//        bar104.zIndex(4d);
//
//        circularGauge.margin(50d, 50d, 50d, 50d);
//        circularGauge.title()
//                .text("Payment Methods' +\n" +
//                        "    '<br/><span style=\"color:#929292; font-size: 12px;\">(ACME CORPORATION)</span>")
//                .useHtml(true);
//        circularGauge.title().enabled(true);
//        circularGauge.title().hAlign(HAlign.CENTER);
//        circularGauge.title()
//                .padding(0d, 0d, 0d, 0d)
//                .margin(0d, 0d, 20d, 0d);
//
//        anyChartView.setChart(circularGauge);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void barGraph(String response) throws JSONException {
        JSONArray json = new JSONArray(response);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        Cartesian cartesian = AnyChart.column();


        cartesian.bounds(0, 0, "99%", "99%");

        List<DataEntry> data = new ArrayList<>();

        for (int i =0 ; i < json.length(); i++){

            JSONObject jobject = json.getJSONObject(i);

            String month = jobject.getString("Month");
            int total = Integer.parseInt(jobject.getString("Totals"));
            data.add(new ValueDataEntry(month, total));
        }

        Column column = cartesian.column(data);


        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(String.valueOf(Anchor.CENTER_BOTTOM))
                .offsetX(0d)
                .offsetY(5d)
                .format("R{%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title("Sales per month");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Months");
        cartesian.yAxis(0).title("Sales (Rands)");

        anyChartView.setChart(cartesian);

    }


}