package com.example.menojang;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Person;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.menojang.FileIOStream.FileIOStreamCheckDir;
import com.example.menojang.FileIOStream.FileIOStreamCheckFile;
import com.example.menojang.FileIOStream.FileIOStreamRead;
import com.example.menojang.FileIOStream.FileIOStreamWrite;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private long backBtnTime = 0;
    ListView listview ;

    FileIOStreamCheckDir cFileIOStreamCheckDir;
    FileIOStreamCheckFile cFileIOStreamCheckFile;
    FileIOStreamWrite cFileIOStreamWrite;
    FileIOStreamRead cFileIOStreamRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        cFileIOStreamRead = new FileIOStreamRead(this);
        cFileIOStreamCheckDir = new FileIOStreamCheckDir(this);
        cFileIOStreamCheckDir.checkDir();

        cFileIOStreamCheckFile = new FileIOStreamCheckFile(this);
        cFileIOStreamCheckFile.checkFile("title", "");
        cFileIOStreamCheckFile.checkFile("time", "");
        cFileIOStreamCheckFile.checkFile("memo", "");

        System.out.println("제목 파일 : " + cFileIOStreamRead.readData("title"));
        Define.ins().lstFileTitle = cFileIOStreamRead.readData("title");
        Define.ins().lstFileTime = cFileIOStreamRead.readData("time");
        Define.ins().lstFileMemo = cFileIOStreamRead.readData("memo");


        String[] splitTitle = Define.ins().lstFileTitle.split("Φ");
        String[] splitTime = Define.ins().lstFileTime.split("¤");
        String[] splitMemo = Define.ins().lstFileMemo.split("Ψ");

        for(int i = 0; i < splitTitle.length; i++){
            System.out.println("시발1 = "+splitTitle[i]);
            System.out.println("시발2 = "+splitTime[i]);
            System.out.println("시발3 = "+splitMemo[i]);
        }

        System.out.println("11111111 : " + Define.ins().lstFileTitle);
        System.out.println("22222222 : " + Define.ins().lstFileTime);




        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView1);
        listview.setAdapter(Define.ins().adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                OnClickHandler(view, position);
            }
        });


        Define.ins().adapter.listViewItemList.clear();
//        System.out.println("adsfsdhfuashgwaegawegbawuiebaiwueg : " + splitTitle);
        if(!splitTitle[0].equals("")){
            for(int i = 0; i < splitTitle.length; i++){
                System.out.println("11111111 : " + splitTitle[i]);
                System.out.println("22222222 : " + splitTime[i]);
                Define.ins().adapter.addItem(splitTitle[i], splitTime[i], splitMemo[i]);
                Define.ins().adapter.notifyDataSetChanged();
            }
        }
        if(splitTitle[0].equals("")){

        }


        Button btnAddMemo = (Button) findViewById(R.id.btnAddMemo);
        btnAddMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Define.ins().chek = 1;
                Intent intent = new Intent(MainActivity.this, MemoActivity.class);
                startActivity(intent);
                finish();
                //!< 메모를 작성하는 액티비티로 이동

            }
        });
    }

    public void OnClickHandler(View view, int pos) {
        cFileIOStreamWrite = new FileIOStreamWrite(MainActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("삭제 / 수정 / 취소").setMessage("선택하세요.");

        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                String deleteTitle = Define.ins().lstFileTitle;
                String deleteTime = Define.ins().lstFileTime;
                String deleteMemo = Define.ins().lstFileMemo;

                System.out.println("deleteTitle1 : " + deleteTitle);
                System.out.println("deleteTime1 : " + deleteTime);

                deleteTitle = deleteTitle.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTitle() + "Φ", "");
                deleteTime = deleteTime.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTime() + "¤", "");
                deleteMemo = deleteMemo.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getMemo() + "Ψ", "");

                System.out.println("deleteTitle2 : " + deleteTitle);
                System.out.println("deleteTime2 : " + deleteTime);

                Define.ins().lstFileTitle = deleteTitle;
                Define.ins().lstFileTime = deleteTime;
                Define.ins().lstFileMemo = deleteMemo;

                cFileIOStreamWrite.writeData("title", Define.ins().lstFileTitle);
                cFileIOStreamWrite.writeData("time", Define.ins().lstFileTime);
                cFileIOStreamWrite.writeData("memo", Define.ins().lstFileMemo);

                Define.ins().adapter.listViewItemList.clear();

                String[] splitTitle = Define.ins().lstFileTitle.split("Φ");
                String[] splitTime = Define.ins().lstFileTime.split("¤");
                String[] splitMemo = Define.ins().lstFileMemo.split("Ψ");

                if(!splitTitle[0].equals("")){
                    for(int i = 0; i < splitTitle.length; i++){
                        System.out.println("씨이이이이이이이이이이이이이이발1 : " + splitTitle[i]);
                        System.out.println("씨이이이이이이이이이이이이이이발2 : " + splitTime[i]);
                        System.out.println("씨이이이이이이이이이이이이이이발3 : " + splitMemo[i]);
                        Define.ins().adapter.addItem( splitTitle[i], splitTime[i], splitMemo[i]);
                    }
                }
                Define.ins().adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("수정", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Define.ins().chek = 2;

                Intent intent = new Intent(MainActivity.this, MemoActivity.class);
                intent.putExtra("pos", pos);
                startActivity(intent);
            }
        });

        builder.setNeutralButton("취소", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        if (0 <= gapTime && 1000 >= gapTime) {
            finish();
        } else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}