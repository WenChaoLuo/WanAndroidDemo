package com.zc.demo;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.View;

import com.db.chart.animation.Animation;
import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.util.Tools;
import com.db.chart.view.LineChartView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.chart)
    LineChartView mChart;
    private final String[] mLabels =
            {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private boolean mFirstStage;

    private final float[][] mValues =
            {{27f, 37f, 39f, 43f, 56f, 80f, 83f, 65f, 48f, 28f, 35f, 18f},
                    {85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f, 85f,85f}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mChart.setOnClickListener(this);

        LineSet dataset = new LineSet(mLabels, mValues[0]);

        dataset.setColor(Color.parseColor("#004f7f"))
                .setThickness(Tools.fromDpToPx(3))
                .setSmooth(true)
                .beginAt(0)
                .endAt(12);
        for (int i = 0; i < mLabels.length; i ++) {
            Point point = (Point) dataset.getEntry(i);
            point.setColor(Color.parseColor("#ffffff"));
            point.setStrokeColor(Color.parseColor("#0290c3"));
            if (i == 1 || i == 10){ point.setRadius(Tools.fromDpToPx(6));}
        }
        mChart.addData(dataset);

        Paint thresPaint = new Paint();
        thresPaint.setColor(Color.parseColor("#0079ae"));
        thresPaint.setStyle(Paint.Style.STROKE);
        thresPaint.setAntiAlias(true);
        thresPaint.setStrokeWidth(Tools.fromDpToPx(.75f));
        thresPaint.setPathEffect(new DashPathEffect(new float[]{10, 10}, 0));

        Paint gridPaint = new Paint();
        gridPaint.setColor(Color.parseColor("#ffffff"));
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setAntiAlias(true);
        gridPaint.setStrokeWidth(Tools.fromDpToPx(.75f));

        mChart.setBorderSpacing((int) Tools.fromDpToPx(0))
                .setXLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setLabelsColor(Color.parseColor("#304a00"))
                .setYLabels(AxisRenderer.LabelPosition.OUTSIDE)
                .setXAxis(true)
                .setYAxis(true)
                .setGrid(1, 7, gridPaint)
                .setValueThreshold(40f, 40f, thresPaint)
                .setAxisBorderValues(0, 110);
        mChart.setClickable(true);
        mChart.show(new Animation().fromXY(0, .5f));
        Paint paint = new Paint();
        paint.setPathEffect(new DashPathEffect(new float[]{4, 4}, 0));
        paint.setColor(getResources().getColor(R.color.colorAccent));
        mChart.setGrid(6,mValues[0].length,paint);

        mChart.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
