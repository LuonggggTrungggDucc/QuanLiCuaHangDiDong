package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangthietbionline.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import adapter.DienthoaiAdapter;
import model.Sanpham;
import until.Checkconnection;
import until.Server;

public class DienthoaiActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listViewdt;
    DienthoaiAdapter dienthoaiAdapter;
    ArrayList<Sanpham> sanphamArrayList;
    int idsp=0;
    int page=1;
    View footerview;
    boolean isLoading=false;
    mHandler mHandler;
    boolean limitdata= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dienthoai);
        Anhxa();
        if(Checkconnection.HaveNetworkConnection(getApplicationContext())){
            Getidsp();
            Actiontoolbar();
            Getdata(page);
            LoadMoreData();
        }else{
            Checkconnection.Showtoast_short(getApplicationContext(),"Kiem tra mang");
            finish();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menugiohang,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent=new Intent(getApplicationContext(),Giohang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadMoreData() {
        listViewdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),Chitietsanpham.class);
                intent.putExtra("thongtinsanpham",sanphamArrayList.get(i));
                startActivity(intent);
            }
        });
        listViewdt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int FisrtItem, int VisibleItem, int TotalItem) {
                if(FisrtItem+VisibleItem==TotalItem && TotalItem!=0 && isLoading==false && limitdata==false){
                    isLoading=true;
                    ThreadData threadData=new ThreadData();
                    threadData.start();
                }

            }
        });
    }

    private void Getdata(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String duongdan= Server.Đuongandienthoai+String.valueOf(page);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String Tendt="";
                int Giadt=0;
                String Hinhanhdt="";
                String Mota="";
                int Idspdt=0;
                if(response!=null && response.length()!=2){
                    listViewdt.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            Tendt=jsonObject.getString("tensp");
                            Giadt=jsonObject.getInt("giasp");
                            Hinhanhdt=jsonObject.getString("hinhanhsp");
                            Mota=jsonObject.getString("motasp");
                            Idspdt=jsonObject.getInt("idsanpham");
                            sanphamArrayList.add(new Sanpham(id,Tendt,Giadt,Hinhanhdt,Mota,Idspdt));
                            dienthoaiAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitdata=true;
                    listViewdt.removeFooterView(footerview);
                    Checkconnection.Showtoast_short(getApplicationContext(),"Het du lieu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(idsp));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Actiontoolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();;
            }
        });
    }

    private void Getidsp() {
        idsp=getIntent().getIntExtra("idloaisp",-1);
        Log.d("Gia tri laoi san pham",idsp+"");
    }

    private void Anhxa() {
        toolbar=(Toolbar) findViewById(R.id.toolbardienthoai);
        listViewdt=(ListView) findViewById(R.id.listviewdienthoai);
        sanphamArrayList=new ArrayList<>();
        dienthoaiAdapter=new DienthoaiAdapter(getApplicationContext(),sanphamArrayList);
        listViewdt.setAdapter(dienthoaiAdapter);
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview=inflater.inflate(R.layout.processbar,null);
        mHandler=new mHandler();
    }
    public class mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    listViewdt.addFooterView(footerview);
                    break;
                case 1:
                    Getdata(++page);
                    isLoading= false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message=mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}