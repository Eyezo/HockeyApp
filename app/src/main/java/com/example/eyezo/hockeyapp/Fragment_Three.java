package com.example.eyezo.hockeyapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;



import java.util.ArrayList;

import static android.graphics.Color.WHITE;


public class Fragment_Three extends Fragment  {

    RadioButton _1stHalf, _2ndHalf;


    TextView txt1stHalfCirclePen, txt2ndHalfCirclePen;
    private FragThreeDataListner listner;

    private PieChart mChart;

    TextView tv1, tv2,tv3,tv4,tv5;

   int val = 0;





    public interface FragThreeDataListner
    {
        void penaltyCorners(String firstHalfPenalty, String secondHalfPennalty);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_three, container,false);
        PieChart pieChart = (PieChart) view.findViewById(R.id.chart1);

        _1stHalf = view.findViewById(R.id.firstHalfCircle);
        _2ndHalf = view.findViewById(R.id.secondHalfCircle);

        txt1stHalfCirclePen = view.findViewById(R.id.txtCircleTeam1st);
        txt2ndHalfCirclePen = view.findViewById(R.id.txtTCircleTeam1st2nd);

        pieChart = (PieChart) view.findViewById(R.id.chart1);


        _1stHalf = view.findViewById(R.id.firstHalfCircle);
        _2ndHalf = view.findViewById(R.id.secondHalfCircle);

        txt1stHalfCirclePen = view.findViewById(R.id.txtCircleTeam1st);
        txt2ndHalfCirclePen = view.findViewById(R.id.txtTCircleTeam1st2nd);

        mChart = (PieChart) view.findViewById(R.id.chart1);

        tv1 = view.findViewById(R.id.tv1);


        tv2 = view.findViewById(R.id.tv2);


        tv3 = view.findViewById(R.id.tv3);

        tv4 = view.findViewById(R.id.tv4);

        tv5 = view.findViewById(R.id.tv5);




        moveOffScreen();

        mChart.setUsePercentValues(false);
        mChart.getDescription().setEnabled(false);
        mChart.setDrawHoleEnabled(false);
        mChart.setTransparentCircleRadius(31f);


        mChart.setMaxAngle(180);
        mChart.setRotationAngle(180);
        mChart.setCenterTextOffset(0,-20);
        setData(5,100);



        mChart.animateY(1000, Easing.EasingOption.EaseInCubic);

        Legend l = mChart.getLegend();

        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        mChart.setEntryLabelColor(WHITE);
        mChart.setEntryLabelTextSize(12f);

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                /*
                 * Increments value of penalty by clicking where circle was penetrated and assign it to the appropriate textView
                 */
                if(_1stHalf.isChecked())
                {
                    if(e == null)

                        return;

                        Log.i("Value", "val: "+ e.getY()+",xIndex: "+ e.getData());

                    val = Integer.parseInt(txt1stHalfCirclePen.getText().toString());
                    val +=1;
                    txt1stHalfCirclePen.setText(String.valueOf(val));



                    switch ((int) e.getData())
                    {
                        case 0:


                            val = Integer.parseInt(tv1.getText().toString());
                            val +=1;
                            tv1.setText(String.valueOf(val));

                            break;

                        case 1:
                            val = Integer.parseInt(tv2.getText().toString());
                            val +=1;
                            tv2.setText(String.valueOf(val));
                            break;

                        case 2:
                            val = Integer.parseInt(tv3.getText().toString());
                            val +=1;
                            tv3.setText(String.valueOf(val));
                            break;

                        case 3:
                            val = Integer.parseInt(tv4.getText().toString());
                            val +=1;
                            tv4.setText(String.valueOf(val));
                            break;

                        case 4:
                            val = Integer.parseInt(tv5.getText().toString());
                            val +=1;
                            tv5.setText(String.valueOf(val));
                            break;
                    }
                }

                if (_2ndHalf.isChecked())
                {

                    if(e == null)

                        return;

                    val = Integer.parseInt(txt2ndHalfCirclePen.getText().toString());
                    val +=1;
                    txt2ndHalfCirclePen.setText(String.valueOf(val));


                    switch ((int) e.getData()) {
                        case 0:

                            val = 0;
                            val = Integer.parseInt(tv1.getText().toString());
                            val += 1;
                            tv1.setText(String.valueOf(val));

                            break;

                        case 1:
                            val = Integer.parseInt(tv2.getText().toString());
                            val += 1;
                            tv2.setText(String.valueOf(val));
                            break;

                        case 2:
                            val = Integer.parseInt(tv3.getText().toString());
                            val += 1;
                            tv3.setText(String.valueOf(val));
                            break;

                        case 3:
                            val = Integer.parseInt(tv4.getText().toString());
                            val += 1;
                            tv4.setText(String.valueOf(val));
                            break;

                        case 4:
                            val = Integer.parseInt(tv5.getText().toString());
                            val += 1;
                            tv5.setText(String.valueOf(val));
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FragThreeDataListner)
        {
            listner = (FragThreeDataListner) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + "must implement FragThreeDataListner");
        }
    }


    private void setData (int count, int range)
    {

        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(20,0));
        yValues.add(new PieEntry(20,1));
        yValues.add(new PieEntry(20,2));
        yValues.add(new PieEntry(20,3));
        yValues.add(new PieEntry(20,4));
        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSelectionShift(5f);
        dataSet.setSliceSpace(3f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15f);
        data.setValueTextColor(WHITE);
        data.setDrawValues(false);


        mChart.setData(data);
        mChart.invalidate();

    }
    private void moveOffScreen()
    {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;

        int offSet = (int) (height*0.2);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)mChart.getLayoutParams();
        params.setMargins(0,0,0,-offSet);
        mChart.setLayoutParams(params);

    }
}
