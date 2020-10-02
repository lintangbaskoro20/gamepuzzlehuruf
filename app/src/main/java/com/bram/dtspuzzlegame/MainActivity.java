package com.bram.dtspuzzlegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    private final int total = 4;
    private  final int[][] BTN_ID = {
            {R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4},
            {R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8},
            {R.id.btn9,R.id.btn10,R.id.btn11,R.id.btn12},
            {R.id.btn13,R.id.btn14,R.id.btn15,R.id.btn16}
    };
    private Button [][] buttons;
    private String[][] angka;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttons = new Button[total][total];
        angka = new String[total][total];

        for (int i = 0; i< total; i++){
            for (int j = 0; j<total; j++){
                buttons[i][j] = findViewById(BTN_ID[i][j]);
                buttons[i][j].setOnClickListener(onClickListenerBuatan);
            }
        }
        startGame();
    }
    View.OnClickListener onClickListenerBuatan = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            for (int i = 0; i<total; i++){
                for (int j = 0; j< total; j++){
                    if (view.getId()==BTN_ID[i][j]){
                        buttonFunction(i,j);
                    }
                }
            }
        }
    } ;
    private void buttonFunction(int kolom, int baris){
        moveCard(kolom,baris);
    }

    private void moveCard(int angkaX, int angkaY){
        int XKotakKosong = -1, YKotakKosong= -1, gap;
        for (int i = 0; i< total; i++){
            for (int j = 0; j<total; j++){
                if (angka[i][j].equals("")){
                    XKotakKosong= i;
                    YKotakKosong = j;
                }
            }
        }
        if (YKotakKosong == angkaY || XKotakKosong == angkaX){
            if (!(XKotakKosong == angkaX && YKotakKosong == angkaY)){
                if (XKotakKosong == angkaX){
                    gap = angkaY - YKotakKosong;
                    if (gap == 1 || gap == -1){
                        if (YKotakKosong < angkaY){
                            angka[angkaX][YKotakKosong] = angka[angkaX][YKotakKosong+1];
                        }else{
                            angka[angkaX][YKotakKosong] = angka[angkaX][YKotakKosong-1];
                        }
                        angka[angkaX][angkaY]="";
                    }
                }
                if (YKotakKosong == angkaY){
                    gap = angkaX - XKotakKosong;
                    if (gap == 1 || gap == -1){
                        if (XKotakKosong < angkaX){
                            angka[XKotakKosong][angkaY] = angka[XKotakKosong+1][angkaY];
                        }else{
                            angka[XKotakKosong][angkaY] = angka[XKotakKosong-1][angkaY];
                        }
                        angka[angkaX][angkaY]="";
                    }
                }
            }
        }
        showAngka(angkaX,angkaY);
    }
    void startGame(){
        String[] array = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
        Random rnd = ThreadLocalRandom.current();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);

            String temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }

        int index = 0;
        for (int i = 0; i < total; i++) {
            for (int j = 0; j < total; j++) {
                if (index == array.length) {
                    buttons[i][j].setText("");
                    angka[i][j] = "";
                    break;
                }
                buttons[i][j].setText(array[index]);
                angka[i][j] = array[index];
                index++;
            }
        }
        checkFinish();
    }
    void showAngka(int angkax, int angkay){
        for (int i = 0; i < total; i++) {
            for (int j = 0; j < total; j++) {
                buttons[i][j].setText(angka[i][j]);
            }
        }

        checkFinish();
    }
    void checkFinish() {
        String[] checker = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
        int index = 0;
        boolean win = false;

        outerloop:
        for (int i = 0; i < total; i++) {
            for (int j = 0; j < total; j++) {
                if (index == checker.length) {
                    win = true;
                    break outerloop;
                }
                if (!angka[i][j].equals(checker[index])) {
                    break outerloop;
                }
                index++;
            }
        }

        if (win) {
            Toast.makeText(MainActivity.this, "Anda Menang !!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_ulangi){
            startGame();
        }else if(item.getItemId() == R.id.menu_keluar){
            finishAndRemoveTask();
        }

        return true;
    }
}