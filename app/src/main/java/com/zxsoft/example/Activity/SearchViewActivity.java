package com.zxsoft.example.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.zxsoft.example.Adapter.HistoryRecyclerAdapter;
import com.zxsoft.example.R;
import com.zxsoft.example.RecyclerViewForEmpty;
import com.zxsoft.example.SetList;
import com.zxsoft.example.SqliteDataBase.DataBaseHelper;
import com.zxsoft.example.model.bean.Message;
import com.zxsoft.example.model.bean.MessageStore;
import com.zxsoft.example.model.bean.MessageTwo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.zxsoft.example.HomeApplication.getContext;

public class SearchViewActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView search_result;
    private EditText input;
    private RadioButton query;
    private Button back;
    private View view;
    private HistoryRecyclerAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<MessageStore>result=new SetList<>();
    private DataBaseHelper dataBaseHelper= new DataBaseHelper(getContext(),"NewsBase.db",null,3);

    //搜索数据库
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        search_result=findViewById(R.id.search_result);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mLayoutManager=new LinearLayoutManager(getContext());
        search_result.setLayoutManager(mLayoutManager);
        input=findViewById(R.id.input);
        initQuery();
    }

    @Override
    public void onResume() {
        super.onResume();
        input.clearComposingText();
    }
    //执行查询操作
    private void initQuery() {
        query=findViewById(R.id.query);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_result.setVisibility(View.VISIBLE);
                String str=input.getText().toString();
                result.clear();
                if(str!=null&&str.length()>0) {
                    SQLiteDatabase db=dataBaseHelper.getReadableDatabase();
                    //Cursor cursor=db.query("PostMessage",null,null,null,null,null,null);
                    String sql="select * from historyNews where title like ?";
                    Cursor cursor=db.rawQuery(sql,new String[]{"%"+str+"%"});
                    if(cursor!=null&&cursor.getCount()!=0) {
                        while(cursor.moveToNext()) {
                            MessageStore ms=new MessageStore();
                            ms.setUniquekey(cursor.getString(cursor.getColumnIndex("uniquekey")));
                            ms.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                            ms.setAuthor_name(cursor.getString(cursor.getColumnIndex("author_name")));
                            ms.setCategory(cursor.getString(cursor.getColumnIndex("category")));
                            ms.setThumbnail_pic_s(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s")));
                            ms.setThumbnail_pic_s02(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s02")));
                            ms.setThumbnail_pic_s03(cursor.getString(cursor.getColumnIndex("thumbnail_pic_s03")));
                            ms.setDate(cursor.getString(cursor.getColumnIndex("date")));
                            ms.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                            result.add(ms);
                        }
                        mAdapter=new HistoryRecyclerAdapter(result, new HistoryRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                EventBus.getDefault().postSticky(new MessageTwo(result, position));
                                Intent intent=new Intent(SearchViewActivity.this, HistoryWebActivity.class);
                                startActivity(intent);
                            }
                        });
                        search_result.setAdapter(mAdapter);

                    } else {
                       search_result.setVisibility(View.GONE);
                    }
                } else {
                    search_result.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
