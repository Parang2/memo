package com.example.menojang;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.menojang.FileIOStream.FileIOStreamCheckDir;
import com.example.menojang.FileIOStream.FileIOStreamCheckFile;
import com.example.menojang.FileIOStream.FileIOStreamRead;
import com.example.menojang.FileIOStream.FileIOStreamWrite;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MemoActivity extends AppCompatActivity {

    EditText EditText1;
    EditText EditText2;
    private long backBtnTime = 0;
    int pos = 0;

    FileIOStreamCheckDir cFileIOStreamCheckDir;
    FileIOStreamCheckFile cFileIOStreamCheckFile;
    FileIOStreamWrite cFileIOStreamWrite;
    FileIOStreamRead cFileIOStreamRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        cFileIOStreamRead = new FileIOStreamRead(this);

        EditText1 = (EditText)findViewById(R.id.EditText1);
        EditText2 = (EditText)findViewById(R.id.EditText2);

        if(Define.ins().chek == 2){
            if(!(getIntent().getIntExtra("pos",-1) == -1)){
                int pos1 = getIntent().getExtras().getInt("pos");
                pos = pos1;
                System.out.println("pos : " + pos);
                System.out.println("listViewItemlist1 = " + Define.ins().adapter.listViewItemList.get(pos1).getTitle());
                System.out.println("listViewItemlist2 = " + Define.ins().adapter.listViewItemList.get(pos1).getMemo());
                EditText1.setText(Define.ins().adapter.listViewItemList.get(pos1).getTitle());
                EditText2.setText(Define.ins().adapter.listViewItemList.get(pos1).getMemo());
            }
        }

        //????????????, ??????????????? ??????
        InputFilter filterKoEnNum2 = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if(source.equals("")){ // for backspace
                    return source;
                }
                if(source.toString().matches("^[a-z0-9???-??????-??????-??? ]*$")){
                    return source;
                }
                else {
                    Log.e("TAG", "???????????? ??? ?????????????????? ???????????? ??? ????????????.");
                    return "";
                }
            }
        };

        EditText1.setFilters(new InputFilter[] { filterKoEnNum2 });
        EditText2.setFilters(new InputFilter[] { filterKoEnNum2 });
    }

    @Override
    public void onBackPressed(){
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if(0 <= gapTime && 1000 >= gapTime) {
            System.out.println("?????? ??????"+Define.ins().chek);

            if(Define.ins().chek == 1){
                if(EditText1.getText().toString().equals("")){
                    if(EditText2.getText().toString().equals("")){
                        Define.ins().lstFileTitle += " ??";
                        Define.ins().lstFileTime += getTime() + "??";
                        Define.ins().lstFileMemo += " ??";
                    }
                    else{
                        Define.ins().lstFileTitle += " ??";
                        Define.ins().lstFileTime += getTime() + "??";
                        Define.ins().lstFileMemo += EditText2.getText().toString() + "??";
                    }
                }
                else if(EditText2.getText().toString().equals("")){
                    Define.ins().lstFileTitle += EditText1.getText().toString() + "??";
                    Define.ins().lstFileTime += getTime() + "??";
                    Define.ins().lstFileMemo += " ??";
                }
                else{
                    Define.ins().lstFileTitle += EditText1.getText().toString() + "??";
                    Define.ins().lstFileTime += getTime() + "??";
                    Define.ins().lstFileMemo += EditText2.getText().toString() + "??";
                }



            }
            if(Define.ins().chek == 2){
                String setTitle = Define.ins().lstFileTitle;
                String setTime = Define.ins().lstFileTime;
                String setMemo = Define.ins().lstFileMemo;

                System.out.println("?????? : " + setMemo);


                if(EditText1.getText().toString().equals("")){
                    if(EditText2.getText().toString().equals("")){
                        setTitle = setTitle.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTitle() + "??", " ??");
                        setTime = setTime.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTime() + "??", getTime() + "??");
                        setMemo = setMemo.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getMemo() + "??", " ??");
                    }
                    else{
                        setTitle = setTitle.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTitle() + "??", " ??");
                        setTime = setTime.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTime() + "??", getTime() + "??");
                        setMemo = setMemo.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getMemo() + "??", EditText2.getText().toString() + "??");
                    }
                }
                else if(EditText2.getText().toString().equals("")){
                    setTitle = setTitle.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTitle() + "??", EditText1.getText().toString() + "??");
                    setTime = setTime.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTime() + "??", getTime() + "??");
                    setMemo = setMemo.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getMemo() + "??", " ??");
                }
                else{
                    setTitle = setTitle.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTitle() + "??", EditText1.getText().toString() + "??");
                    setTime = setTime.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTime() + "??", getTime() + "??");
                    setMemo = setMemo.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getMemo() + "??", EditText2.getText().toString() + "??");
                }


                System.out.println("?????? : " + setMemo);

                Define.ins().lstFileTitle = setTitle;
                Define.ins().lstFileTime = setTime;
                Define.ins().lstFileMemo = setMemo;


            }

            cFileIOStreamWrite = new FileIOStreamWrite(this);
            cFileIOStreamWrite.writeData("title", Define.ins().lstFileTitle);
            cFileIOStreamWrite.writeData("time", Define.ins().lstFileTime);
            cFileIOStreamWrite.writeData("memo", Define.ins().lstFileMemo);

            cFileIOStreamRead = new FileIOStreamRead(this);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            backBtnTime = curTime;
            Toast.makeText(this, "?????? ??? ????????? ???????????????.",Toast.LENGTH_SHORT).show();
        }
    }

    public static String getTime(){
        Long mNow;
        Date mDate;
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}
