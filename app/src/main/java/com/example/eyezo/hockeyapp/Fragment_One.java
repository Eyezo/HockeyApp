package com.example.eyezo.hockeyapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class Fragment_One extends Fragment {

    private FragOneData listner;
    RadioButton _1stHalf,_2ndHalf;
    ImageButton teamAddTurnOver,teamMinusTurnOver,oppAddTurnOver,oppMinusTurnOver,teamAddGoals, teamMinusGoals,
            oppAddGoals, oppMinusGoals;
    TextView team1stTurnOverText,team2ndTurnOverText,opp1stTurnOverText,opp2ndTurnOverText,team1stGoalsText,
            team2ndGoalsText, opp1stGoalsText, opp2ndGoalsText;

    public interface FragOneData
    {
        /*
         * within the interface get the data that we need as it will be called when needed
         */
        void getTurnOverData(String teamTurnOver1st, String teamTurnOver2nd, String oppTurnOver1st,
                             String oppTurnOver2nd);
        void getGoalsData(String teamGoal1st, String teamGoal2nd, String oppGoal1st, String oppGoal2nd);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one, container,false);
        _1stHalf = view.findViewById(R.id.firstHalf);
        _2ndHalf = view.findViewById(R.id.secondHalf);

        teamAddTurnOver = view.findViewById(R.id.btnTurnOverTeam);
        teamMinusTurnOver = view.findViewById(R.id.btnTurnOverTeamMinus);
        oppAddTurnOver = view.findViewById(R.id.btnTurnOverOpp);
        oppMinusTurnOver = view.findViewById(R.id.btnTurnOverOppMinus);

        team1stTurnOverText = view.findViewById(R.id.txtTurnOverTeam1st);
        team2ndTurnOverText = view.findViewById(R.id.txtTurnOverTeam1st2nd);
        opp1stTurnOverText = view.findViewById(R.id.txtTurnOverOpp1st);
        opp2ndTurnOverText = view.findViewById(R.id.txtTurnOverOpp2nd);

        teamAddGoals = view.findViewById(R.id.btnGoalTeamAdd);
        teamMinusGoals = view.findViewById(R.id.btnGoalMinusTeam);
        oppAddGoals = view.findViewById(R.id.btnGoalOppAdd);
        oppMinusGoals = view.findViewById(R.id.btnGoalMinusOpp);

        team1stGoalsText = view.findViewById(R.id.txtTeamGoal1st);
        team2ndGoalsText = view.findViewById(R.id.txtTeamGoal2nd);
        opp1stGoalsText = view.findViewById(R.id.txtOppGoal1st);
        opp2ndGoalsText = view.findViewById(R.id.txtOppGoal2nd);




        teamAddTurnOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Increments value of turn over by one in the appropriate textView
                 */
                int val;
                if(_1stHalf.isChecked())
                {
                    val = Integer.parseInt(team1stTurnOverText.getText().toString());
                    val +=1;
                    team1stTurnOverText.setText(String.valueOf(val));
                    listner.getTurnOverData(team1stTurnOverText.getText().toString(), team2ndTurnOverText.getText().toString(),
                            opp1stTurnOverText.getText().toString(), opp2ndTurnOverText.getText().toString());
                }
                else if(_2ndHalf.isChecked())
                {
                    val = Integer.parseInt(team2ndTurnOverText.getText().toString());
                    val +=1;
                    team2ndTurnOverText.setText(String.valueOf(val));
                    listner.getTurnOverData(team1stTurnOverText.getText().toString(), team2ndTurnOverText.getText().toString(),
                            opp1stTurnOverText.getText().toString(), opp2ndTurnOverText.getText().toString());
                }

            }
        });
        teamMinusTurnOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * decrements value of turn over by one in the appropriate textView
                 */
                int val;
                if(_1stHalf.isChecked())
                {

                    val = Integer.parseInt(team1stTurnOverText.getText().toString());
                    if(val > 0)
                        val -=1;
                    team1stTurnOverText.setText(String.valueOf(val));
                    listner.getTurnOverData(team1stTurnOverText.getText().toString(), team2ndTurnOverText.getText().toString(),
                            opp1stTurnOverText.getText().toString(), opp2ndTurnOverText.getText().toString());
                }
                else if(_2ndHalf.isChecked())
                {
                    val = Integer.parseInt(team2ndTurnOverText.getText().toString());
                    if(val > 0)
                        val -=1;
                    team2ndTurnOverText.setText(String.valueOf(val));
                    listner.getTurnOverData(team1stTurnOverText.getText().toString(), team2ndTurnOverText.getText().toString(),
                            opp1stTurnOverText.getText().toString(), opp2ndTurnOverText.getText().toString());
                }
            }
        });
        oppAddTurnOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Increments value of turn over by one in the appropriate textView
                 */
                int val;
                if(_1stHalf.isChecked())
                {
                    val = Integer.parseInt(opp1stTurnOverText.getText().toString());
                    val +=1;
                    opp1stTurnOverText.setText(String.valueOf(val));
                    listner.getTurnOverData(team1stTurnOverText.getText().toString(), team2ndTurnOverText.getText().toString(),
                            opp1stTurnOverText.getText().toString(), opp2ndTurnOverText.getText().toString());
                }
                else if(_2ndHalf.isChecked())
                {
                    val = Integer.parseInt(opp2ndTurnOverText.getText().toString());
                    val +=1;
                    opp2ndTurnOverText.setText(String.valueOf(val));
                    listner.getTurnOverData(team1stTurnOverText.getText().toString(), team2ndTurnOverText.getText().toString(),
                            opp1stTurnOverText.getText().toString(), opp2ndTurnOverText.getText().toString());
                }

            }
        });
        oppMinusTurnOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * decrements value of turn over by one in the appropriate textView
                 */
                int val;
                if(_1stHalf.isChecked())
                {
                    val = Integer.parseInt(opp1stTurnOverText.getText().toString());
                    if(val > 0)
                        val -=1;
                    opp1stTurnOverText.setText(String.valueOf(val));
                    listner.getTurnOverData(team1stTurnOverText.getText().toString(), team2ndTurnOverText.getText().toString(),
                            opp1stTurnOverText.getText().toString(), opp2ndTurnOverText.getText().toString());
                }
                else if(_2ndHalf.isChecked())
                {
                    val = Integer.parseInt(opp2ndTurnOverText.getText().toString());
                    if(val > 0)
                        val -=1;
                    opp2ndTurnOverText.setText(String.valueOf(val));
                    listner.getTurnOverData(team1stTurnOverText.getText().toString(), team2ndTurnOverText.getText().toString(),
                            opp1stTurnOverText.getText().toString(), opp2ndTurnOverText.getText().toString());
                }
            }

        });

        goals();

        return view;
    }
    private void goals()
    {
        teamAddGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Increments value of goals by one in the appropriate textView
                 */
                int val;
                if(_1stHalf.isChecked())
                {
                    val = Integer.parseInt(team1stGoalsText.getText().toString());
                    val +=1;
                    team1stGoalsText.setText(String.valueOf(val));
                    listner.getGoalsData(team1stGoalsText.getText().toString(),team2ndGoalsText.getText().toString(),
                            opp1stGoalsText.getText().toString(),opp2ndGoalsText.getText().toString());

                }
                else if(_2ndHalf.isChecked())
                {
                    val = Integer.parseInt(team2ndGoalsText.getText().toString());
                    val +=1;
                    team2ndGoalsText.setText(String.valueOf(val));
                    listner.getGoalsData(team1stGoalsText.getText().toString(),team2ndGoalsText.getText().toString(),
                            opp1stGoalsText.getText().toString(),opp2ndGoalsText.getText().toString());
                }
            }
        });
        teamMinusGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * decrements value of goals by one in the appropriate textView
                 */
                int val;
                if(_1stHalf.isChecked())
                {
                    val = Integer.parseInt(team1stGoalsText.getText().toString());
                    if(val > 0)
                        val -=1;
                    team1stGoalsText.setText(String.valueOf(val));
                    listner.getGoalsData(team1stGoalsText.getText().toString(),team2ndGoalsText.getText().toString(),
                            opp1stGoalsText.getText().toString(),opp2ndGoalsText.getText().toString());
                }
                else if(_2ndHalf.isChecked())
                {
                    val = Integer.parseInt(team2ndGoalsText.getText().toString());
                    if(val > 0)
                        val -=1;
                    team2ndGoalsText.setText(String.valueOf(val));
                    listner.getGoalsData(team1stGoalsText.getText().toString(),team2ndGoalsText.getText().toString(),
                            opp1stGoalsText.getText().toString(),opp2ndGoalsText.getText().toString());
                }

            }
        });
        oppAddGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * Increments value of goals by one in the appropriate textView
                 */
                int val;
                if(_1stHalf.isChecked())
                {
                    val = Integer.parseInt(opp1stGoalsText.getText().toString());
                    val +=1;
                    opp1stGoalsText.setText(String.valueOf(val));
                    listner.getGoalsData(team1stGoalsText.getText().toString(),team2ndGoalsText.getText().toString(),
                            opp1stGoalsText.getText().toString(),opp2ndGoalsText.getText().toString());
                }
                else if(_2ndHalf.isChecked())
                {
                    val = Integer.parseInt(opp2ndGoalsText.getText().toString());
                    val +=1;
                    opp2ndGoalsText.setText(String.valueOf(val));
                    listner.getGoalsData(team1stGoalsText.getText().toString(),team2ndGoalsText.getText().toString(),
                            opp1stGoalsText.getText().toString(),opp2ndGoalsText.getText().toString());
                }

            }
        });
        oppMinusGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * decrements value of goals by one in the appropriate textView
                 */
                int val;
                if(_1stHalf.isChecked())
                {
                    val = Integer.parseInt(opp1stGoalsText.getText().toString());
                    if(val > 0)
                        val -=1;
                    opp1stGoalsText.setText(String.valueOf(val));
                    listner.getGoalsData(team1stGoalsText.getText().toString(),team2ndGoalsText.getText().toString(),
                            opp1stGoalsText.getText().toString(),opp2ndGoalsText.getText().toString());
                }
                else if(_2ndHalf.isChecked())
                {
                    val = Integer.parseInt(opp2ndGoalsText.getText().toString());
                    if(val > 0)
                        val -=1;
                    opp2ndGoalsText.setText(String.valueOf(val));
                    listner.getGoalsData(team1stGoalsText.getText().toString(),team2ndGoalsText.getText().toString(),
                            opp1stGoalsText.getText().toString(),opp2ndGoalsText.getText().toString());
                }
            }
        });
        listner.getGoalsData(team1stGoalsText.getText().toString(),team2ndGoalsText.getText().toString(),
                opp1stGoalsText.getText().toString(),opp2ndGoalsText.getText().toString());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof  FragOneData)
        {
            /*
             * apply listener if context is of FragOneData
             */
            listner = (FragOneData) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + "must implement FragOneData");
        }
    }
}
