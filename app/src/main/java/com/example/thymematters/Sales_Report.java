package com.example.thymematters;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.charts.CircularGauge;
import com.anychart.core.axes.Circular;
import com.anychart.core.gauge.pointers.Bar;
import com.anychart.enums.Anchor;
import com.anychart.graphics.vector.Fill;
import com.anychart.graphics.vector.SolidFill;
import com.anychart.graphics.vector.text.HAlign;
import com.anychart.graphics.vector.text.VAlign;

public class Sales_Report extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_report);

//        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
//        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
//
//        Cartesian cartesian = AnyChart.column();
//
//        List<DataEntry> data = new ArrayList<>();
//        data.add(new ValueDataEntry("Rouge", 80540));
//        data.add(new ValueDataEntry("Foundation", 94190));
//        data.add(new ValueDataEntry("Mascara", 102610));
//        data.add(new ValueDataEntry("Lip gloss", 110430));
//        data.add(new ValueDataEntry("Lipstick", 128000));
//        data.add(new ValueDataEntry("Nail polish", 143760));
//        data.add(new ValueDataEntry("Eyebrow pencil", 170670));
//        data.add(new ValueDataEntry("Eyeliner", 213210));
//        data.add(new ValueDataEntry("Eyeshadows", 249980));
//
//        Column column = cartesian.column(data);
//
//        column.tooltip()
//                .titleFormat("{%X}")
//                .position(Position.CENTER_BOTTOM)
//                .anchor(Anchor.CENTER_BOTTOM)
//                .offsetX(0d)
//                .offsetY(5d)
//                .format("${%Value}{groupsSeparator: }");
//
//        cartesian.animation(true);
//        cartesian.title("Top 10 Cosmetic Products by Revenue");
//
//        cartesian.yScale().minimum(0d);
//
//        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");
//
//        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
//        cartesian.interactivity().hoverMode(HoverMode.BY_X);
//
//        cartesian.xAxis(0).title("Product");
//        cartesian.yAxis(0).title("Revenue");
//
//        anyChartView.setChart(cartesian);

        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));

        CircularGauge circularGauge = AnyChart.circular();
        circularGauge.data(new SingleValueDataSet(new String[] { "93", "56", "100"}));
        circularGauge.fill("#fff")
                .stroke(null)
                .padding(0d, 0d, 0d, 0d)
                .margin(50d, 50d, 50d, 50d);
        circularGauge.startAngle(0d);
        circularGauge.sweepAngle(270d);

        Circular xAxis = circularGauge.axis(0)
                .radius(100d)
                .width(1d)
                .fill((Fill) null);
        xAxis.scale()
                .minimum(0d)
                .maximum(100d);
        xAxis.ticks("{ interval: 1 }")
                .minorTicks("{ interval: 1 }");
        xAxis.labels().enabled(false);
        xAxis.ticks().enabled(false);
        xAxis.minorTicks().enabled(false);


        circularGauge.label(3d)
                .text("Fluoride, <span style=\"\">93%</span>")
                .useHtml(true)
                .hAlign(HAlign.CENTER)
                .vAlign(VAlign.MIDDLE);
        circularGauge.label(3d)
                .anchor(Anchor.RIGHT_CENTER)
                .padding(0d, 10d, 0d, 0d)
                .height(17d / 2d + "%")
                .offsetY(40d + "%")
                .offsetX(0d);
        Bar bar3 = circularGauge.bar(3d);
        bar3.dataIndex(3d);
        bar3.radius(40d);
        bar3.width(17d);
        bar3.fill(new SolidFill("#ffd54f", 1d));
        bar3.stroke(null);
        bar3.zIndex(5d);
        Bar bar103 = circularGauge.bar(103d);
        bar103.dataIndex(5d);
        bar103.radius(40d);
        bar103.width(17d);
        bar103.fill(new SolidFill("#F5F4F4", 1d));
        bar103.stroke("1 #e5e4e4");
        bar103.zIndex(4d);

        circularGauge.label(4d)
                .text("Zinc Oxide, <span style=\"\">56%</span>")
                .useHtml(true)
                .hAlign(HAlign.CENTER)
                .vAlign(VAlign.MIDDLE);
        circularGauge.label(4d)
                .anchor(Anchor.RIGHT_CENTER)
                .padding(0d, 10d, 0d, 0d)
                .height(17d / 2d + "%")
                .offsetY(20d + "%")
                .offsetX(0d);
        Bar bar4 = circularGauge.bar(4d);
        bar4.dataIndex(4d);
        bar4.radius(20d);
        bar4.width(17d);
        bar4.fill(new SolidFill("#455a64", 1d));
        bar4.stroke(null);
        bar4.zIndex(5d);
        Bar bar104 = circularGauge.bar(104d);
        bar104.dataIndex(5d);
        bar104.radius(20d);
        bar104.width(17d);
        bar104.fill(new SolidFill("#F5F4F4", 1d));
        bar104.stroke("1 #e5e4e4");
        bar104.zIndex(4d);

        circularGauge.margin(50d, 50d, 50d, 50d);
        circularGauge.title()
                .text("Payment Methods' +\n" +
                        "    '<br/><span style=\"color:#929292; font-size: 12px;\">(ACME CORPORATION)</span>")
                .useHtml(true);
        circularGauge.title().enabled(true);
        circularGauge.title().hAlign(HAlign.CENTER);
        circularGauge.title()
                .padding(0d, 0d, 0d, 0d)
                .margin(0d, 0d, 20d, 0d);

        anyChartView.setChart(circularGauge);
    }
}