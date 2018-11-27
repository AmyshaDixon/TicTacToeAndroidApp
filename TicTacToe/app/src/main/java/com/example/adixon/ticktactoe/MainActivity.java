package com.example.adixon.ticktactoe;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    /* Declare Fields */
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private boolean p1Turn = true;
    private boolean switchP1Turn = true;
    private TextView gameInfo;
    private Switch s;
    private Menu MainMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Connect switch menu button to variable s */
        s = findViewById(R.id.playerSwitch);

        /* Connect gameInfo to textView */
        gameInfo = findViewById(R.id.textView);

        /* Connect Buttons/text to variables for readability;
         * Goes from left to right per row of three */
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);

        /* Set text for gameInfo */
        gameInfo.setText("X's Turn");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Get the default inflater object */
        MenuInflater mi = getMenuInflater();

        /* Inflate the menu created */
        mi.inflate(R.menu.menu, menu);

        /* Set constant MainMenu as menu */
        MainMenu = menu;

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem checkable = menu.findItem(R.id.playerSwitch);

        checkable.setChecked(switchP1Turn);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item == MainMenu.findItem(R.id.playerSwitch)) {
            /* Clear Current Game */
            onClickNew(findViewById(R.id.buttonNewGame));

            /* Set GameInfo to selected player */
            setGameInfo();

            /* Uncheck s checkbox */
            //s.setChecked(isChecked);

            /* Toggle check box */
            switch (item.getItemId()) {
                case R.id.playerSwitch:
                    switchP1Turn = !item.isChecked();
                    item.setChecked(switchP1Turn);

                    /* Set p1Turn to match switchP1Turn */
                    p1Turn = switchP1Turn;

                    /* Set gameInfo text */
                    setGameInfo();

                    return true;
                default:
                    return false;
            }
        }
        else if(item == MainMenu.findItem(R.id.about)) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);

            /* Set Title */
            adb.setTitle("About");

            /* Set Message */
            adb.setMessage("This is a basic game of Tick-Tack-Toe. You may choose if X or O" +
                    "goes first in the option menu; selection will start a new game. After a game" +
                    "is won, press the \"New game\" option at the bottom to play again");

            /* Use okay to close dialog */
            adb.setPositiveButton("Cool, lets play", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            /* Show dialog */
            adb.show();

            return true;
        }

        return true;
    }

    public void setGameInfo() {
        if(p1Turn) {
            gameInfo.setText("X's Turn");
        }
        else {
            gameInfo.setText("O's Turn");
        }
    }

    public void onClick(View v) {
        if(p1Turn == true) {
            fillP1Buttons(v, button1);
            fillP1Buttons(v, button2);
            fillP1Buttons(v, button3);
            fillP1Buttons(v, button4);
            fillP1Buttons(v, button5);
            fillP1Buttons(v, button6);
            fillP1Buttons(v, button7);
            fillP1Buttons(v, button8);
            fillP1Buttons(v, button9);
        }
        else {
            fillP2Buttons(v, button1);
            fillP2Buttons(v, button2);
            fillP2Buttons(v, button3);
            fillP2Buttons(v, button4);
            fillP2Buttons(v, button5);
            fillP2Buttons(v, button6);
            fillP2Buttons(v, button7);
            fillP2Buttons(v, button8);
            fillP2Buttons(v, button9);
        }

        checkWinner();
    }

    public void fillP1Buttons(View v, Button b) {
        if(v == b && b.getText() == "") {
            b.setText("X");

            /* Change color of button */
            b.setBackgroundColor(Color.parseColor("#ef9a2b"));

            /* Pass Turn */
            p1Turn = false;

            /* Set gameInfo */
            gameInfo.setText("O's Turn");
        }
    }

    public void fillP2Buttons(View v, Button b) {
        if(v == b && b.getText() == "") {
            b.setText("O");

            /* Change color of button */
            b.setBackgroundColor(Color.parseColor("#66ffcc"));

            /* Pass Turn */
            p1Turn = true;

            /* Set gameInfo */
            gameInfo.setText("X's Turn");
        }
    }

    public void checkWinner() {
        /* Horizontal win */
        checkHorizontal("X");
        checkHorizontal("O");

        /* Vertical win */
        checkVertical("X");
        checkVertical("O");

        /* Diagonal win */
        checkDiagonal("X");
        checkDiagonal("O");

        /* Check for tie  and set buttons */
        if(checkIfAllAreNotEmpty()){

            gameInfo.setText("It's a Tie!!");

            disableOrEnableButtons(false);
        }
        /* Check horizontals again for special case */
        checkHorizontal("X");
        checkHorizontal("O");

        /* Check verticals again for special case*/
        checkVertical("X");
        checkVertical("O");


        /* Check diagonals again for special case */
        checkDiagonal("X");
        checkDiagonal("O");
    }

    public void checkHorizontal(String s) {
        /* Check each row individually */
        if(button1.getText() == s && button2.getText() == s &&
                button3.getText() == s) {
            gameInfo.setText(s + " Wins!!");

            disableOrEnableButtons(false);

            setWinnerColors(s);
        }
        else if(button4.getText() == s && button5.getText() == s &&
                button6.getText() == s) {
            gameInfo.setText(s + " Wins!!");

            disableOrEnableButtons(false);

            setWinnerColors(s);
        }
        else if(button7.getText() == s && button8.getText() == s &&
                button9.getText() == s) {
            gameInfo.setText(s + " Wins!!");

            disableOrEnableButtons(false);

            setWinnerColors(s);
        }
    }

    public void checkVertical(String s){
        /* Check each column individually */
        if(button1.getText() == s && button4.getText() == s &&
                button7.getText() == s) {
            gameInfo.setText(s + " Wins!!");

            disableOrEnableButtons(false);

            setWinnerColors(s);
        }
        else if(button2.getText() == s && button5.getText() == s &&
                button8.getText() == s) {
            gameInfo.setText(s + " Wins!!");

            disableOrEnableButtons(false);

            setWinnerColors(s);
        }
        else if(button3.getText() == s && button6.getText() == s &&
                button9.getText() == s) {
            gameInfo.setText(s + " Wins!!");

            disableOrEnableButtons(false);

            setWinnerColors(s);
        }
    }

    public void checkDiagonal(String s) {
        /* Check each diagonal individually; only two cases*/
        if(button1.getText() == s && button5.getText() == s &&
                button9.getText() == s) {
            gameInfo.setText(s + " Wins!!");

            disableOrEnableButtons(false);

            setWinnerColors(s);
        }
        else if(button3.getText() == s && button5.getText() == s &&
                button7.getText() == s) {
            gameInfo.setText(s + " Wins!!");

            disableOrEnableButtons(false);

            setWinnerColors(s);
        }
    }

    public boolean checkIfAllAreNotEmpty() {
        if(!(button1.getText() == "" || button2.getText() == "" ||
                button3.getText() == "" || button4.getText() == "" ||
                button5.getText() == "" || button6.getText() == "" ||
                button7.getText() == "" || button8.getText() == "" ||
                button9.getText() == "")) {
            return true;
        }

        return false;
    }

    public void setWinnerColors(String s) {
        /* Set color to winner for all buttons */
        if(!button5.isEnabled()) { // If any button is disabled, apply winner color
            if(s == "X") {
                setAllButtonsOneColor("#ef9a2b");
            }
            else {
                setAllButtonsOneColor("#66ffcc");
            }
        }
    }

    public void setAllButtonsOneColor(String s){
        button1.setBackgroundColor(Color.parseColor(s));
        button2.setBackgroundColor(Color.parseColor(s));
        button3.setBackgroundColor(Color.parseColor(s));
        button4.setBackgroundColor(Color.parseColor(s));
        button5.setBackgroundColor(Color.parseColor(s));
        button6.setBackgroundColor(Color.parseColor(s));
        button7.setBackgroundColor(Color.parseColor(s));
        button8.setBackgroundColor(Color.parseColor(s));
        button9.setBackgroundColor(Color.parseColor(s));

    }

    public void disableOrEnableButtons(Boolean b) {
        button1.setEnabled(b);
        button2.setEnabled(b);
        button3.setEnabled(b);
        button4.setEnabled(b);
        button5.setEnabled(b);
        button6.setEnabled(b);
        button7.setEnabled(b);
        button8.setEnabled(b);
        button9.setEnabled(b);
    }

    public void onClickNew(View v) {
        if(v == findViewById(R.id.buttonNewGame)) {
            button1.setText("");
            button2.setText("");
            button3.setText("");
            button4.setText("");
            button5.setText("");
            button6.setText("");
            button7.setText("");
            button8.setText("");
            button9.setText("");
        }

        /* Reset turns */
        p1Turn = switchP1Turn;

        setGameInfo();

        /* Reset button colors */
        button1.setBackgroundResource(android.R.drawable.btn_default);
        button2.setBackgroundResource(android.R.drawable.btn_default);
        button3.setBackgroundResource(android.R.drawable.btn_default);
        button4.setBackgroundResource(android.R.drawable.btn_default);
        button5.setBackgroundResource(android.R.drawable.btn_default);
        button6.setBackgroundResource(android.R.drawable.btn_default);
        button7.setBackgroundResource(android.R.drawable.btn_default);
        button8.setBackgroundResource(android.R.drawable.btn_default);
        button9.setBackgroundResource(android.R.drawable.btn_default);

        /* Enable Buttons */
        disableOrEnableButtons(true);

    }
}
