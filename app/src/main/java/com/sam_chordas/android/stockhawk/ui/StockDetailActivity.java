package com.sam_chordas.android.stockhawk.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.db.chart.Tools;
import com.db.chart.model.LineSet;
import com.db.chart.view.AxisController;
import com.db.chart.view.LineChartView;
import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.service.StockGraphService;

public class StockDetailActivity extends AppCompatActivity {
    private static LineChartView mChart;
    private static int color;

    public StockDetailActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String symbol = intent.getStringExtra("symbol");

        setContentView(R.layout.activity_line_graph);
        mChart = (LineChartView) findViewById(R.id.linechart);
        color = ContextCompat.getColor(this, R.color.material_blue_500);
        StockGraphService service = new StockGraphService();
        service.execute(symbol);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_stocks, menu);
        return true;
    }

    public static void createGraph(String[] labels, float[] entries) {
        float min = 0;
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] > min) {
                min = entries[i];
            }
        }
        LineSet dataset = new LineSet(labels, entries);
        dataset.setColor(Color.parseColor("#ffffff"))
                .setDotsColor(Color.parseColor("#ff0000"))
                .setThickness(1);
        dataset.setFill(Color.parseColor("#ffffff"));
        mChart.addData(dataset);
        mChart.setClickablePointRadius(5);

        mChart.setBorderSpacing(Tools.fromDpToPx(20))
                .setAxisColor(color)
                .setLabelsColor(Color.parseColor("#ffffff"))
                .setYLabels(AxisController.LabelPosition.NONE)
                .setXLabels(AxisController.LabelPosition.OUTSIDE)
                .setXAxis(true)
                .setYAxis(true);
        mChart.show();
    }
}
