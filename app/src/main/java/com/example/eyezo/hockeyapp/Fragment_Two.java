package com.example.eyezo.hockeyapp;

import android.content.Context;
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
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import static android.graphics.Color.WHITE;

public class Fragment_Two extends Fragment {

    ImageButton btnAddCirclePen, btnMinusCirclePen;
    RadioButton _1stHalf, _2ndHalf;
    TextView txt1stHalfCirclePen, txt2ndHalfCirclePen;
    private FragTwoData listner;

    private PieChart mChart;

    TextView tv1, tv2,tv3,tv4,tv5;



    private float[] yData = {20.0f,20.0f,20.0f,20.0f,20.0f};
    private int [] xData = {1,2,3,4,5};


    public interface FragTwoData
    {
        /*
         * within the interface get the data that we need as it will be called when needed
         */
        void circlePenetration(String firstHalfPen, String secondHalfPen);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_two, container,false);


        _1stHalf = view.findViewById(R.id.firstHalfCircle);
        _2ndHalf = view.findViewById(R.id.secondHalfCircle);

        txt1stHalfCirclePen = view.findViewById(R.id.txtCircleTeam1st);
        txt2ndHalfCirclePen = view.findViewById(R.id.txtTCircleTeam1st2nd);

        mChart = (PieChart) view.findViewById(R.id.chart1);



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
                  int val = 0;

                if(e == null)

                    return;
                    Log.i("Value", "val: "+ e.getY()+",xIndex: "+ e.getData());


                  val = Integer.parseInt(txt1stHalfCirclePen.getText().toString());
                  val +=1;
                  txt1stHalfCirclePen.setText(String.valueOf(val));

                  tv1 = view.findViewById(R.id.tv_tv1);


                  tv2 = view.findViewById(R.id.tv_tv2);


                  tv3 = view.findViewById(R.id.tv_tv3);

                  tv4 = view.findViewById(R.id.tv_tv4);

                  tv5 = view.findViewById(R.id.tv_tv5);

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

              if(_2ndHalf.isChecked())
              {


                  int val = 0;

                  if(e == null)

                      return;


                  val = Integer.parseInt(txt2ndHalfCirclePen.getText().toString());
                  val +=1;
                  txt2ndHalfCirclePen.setText(String.valueOf(val));


                  tv1 = view.findViewById(R.id.tv_tv1);


                  tv2 = view.findViewById(R.id.tv_tv2);


                  tv3 = view.findViewById(R.id.tv_tv3);

                  tv4 = view.findViewById(R.id.tv_tv4);

                  tv5 = view.findViewById(R.id.tv_tv5);

                  switch ((int) e.getData())
                  {
                      case 0:



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
        if(context instanceof FragTwoData)
        {
            /*
             * apply listener if context is of FragTwoData
             */
            listner = (FragTwoData) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + "must implement FragTwoData");
        }



    }
    String[] v = new String []{"","", "", "",""};

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
