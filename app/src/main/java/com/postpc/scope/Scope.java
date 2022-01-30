package com.postpc.scope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageHelper;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

public class Scope extends AppCompatActivity {

    ImageView playBtn, shareBtn;
    GraphView graphView;
    private BluetoothSocket mBTSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scope);

        graphView = (GraphView) findViewById(R.id.graph_view);

        playBtn = (ImageView) findViewById(R.id.playBtn);
        playBtn.setTag("Run");

        graphView.getGridLabelRenderer().setGridColor(getResources().getColor(R.color.graphColor));
        graphView.getGridLabelRenderer().setHorizontalLabelsColor(getResources().getColor(R.color.axisColor));
        graphView.getGridLabelRenderer().setVerticalLabelsColor(getResources().getColor(R.color.axisColor));
        graphView.getGridLabelRenderer().setHorizontalAxisTitleColor(getResources().getColor(R.color.axisColor));
        graphView.getGridLabelRenderer().setVerticalAxisTitleColor(getResources().getColor(R.color.axisColor));
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("Time");
        graphView.getGridLabelRenderer().setVerticalAxisTitle("V [V]");
        graphView.setTitle("Scope");

        DataPoint[] points = new DataPoint[100];
        for (int i = 0; i < points.length; i++) {
            points[i] = new DataPoint(i, Math.sin(i*0.5));
        }

        final LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), "Point clicked: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(35);
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(true);

        graphView.addSeries(series);

        shareBtn = (ImageView) findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphView.takeSnapshotAndShare(Scope.this, "Scope", "Share scope image");
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playBtn.getTag() == "Run")
                {
                    playBtn.setImageResource(R.drawable.stop);
                    playBtn.setTag("Stop");
                }
                else
                {
                    playBtn.setImageResource(R.drawable.start);
                    playBtn.setTag("Run");
                }
            }
        });
    }
}
