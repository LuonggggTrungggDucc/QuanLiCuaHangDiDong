package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cuahangthietbionline.R;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.Loaispadapter;
import adapter.Sanphamadapter;
import model.GioHang;
import model.Loaisp;
import model.Sanpham;
import until.Checkconnection;
import until.Server;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangloaisp;
    Loaispadapter loaispadapter;
    ArrayList<Sanpham> mangsanpham;
    Sanphamadapter sanphamadapter;
    int id;
    String tenloaisp="";
    String hinhanhsp="";
    public static ArrayList<GioHang> manggiohang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if(Checkconnection.HaveNetworkConnection(getApplicationContext())){
            Actionbar();
            ActionViewFlipper();
            Getdulieuloaisp();
            Getdulieusanphammoinhat();
            chonItemlistview();
        }else {
            Checkconnection.Showtoast_short(getApplicationContext(),"Kiểm tra lại mạng");
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

    private void chonItemlistview() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        if(Checkconnection.HaveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else{
                            Checkconnection.Showtoast_short(getApplicationContext(),"Hay kiem tra mang");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if(Checkconnection.HaveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this, DienthoaiActivity.class);
                            intent.putExtra("idloaisp",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }else{
                            Checkconnection.Showtoast_short(getApplicationContext(),"Hay kiem tra mang");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(Checkconnection.HaveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this, LaptopActivity.class);
                            intent.putExtra("idloaisp",mangloaisp.get(i).getId());
                            startActivity(intent);
                        }else{
                            Checkconnection.Showtoast_short(getApplicationContext(),"Hay kiem tra mang");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if(Checkconnection.HaveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this, LienheActivity.class);
                            startActivity(intent);
                        }else{
                            Checkconnection.Showtoast_short(getApplicationContext(),"Hay kiem tra mang");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(Checkconnection.HaveNetworkConnection(getApplicationContext())){
                            Intent intent=new Intent(MainActivity.this, ThongtinActivity.class);
                            startActivity(intent);
                        }else{
                            Checkconnection.Showtoast_short(getApplicationContext(),"Hay kiem tra mang");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void Getdulieusanphammoinhat() {
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.Đuongấnnphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null){
                    int ID=0;
                    String Tensp="";
                    Integer Giasp=0;
                    String Hinhanhsp="";
                    String Motasp="";
                    int IDsanpham=0;
                    for(int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject=response.getJSONObject(i);
                            ID=jsonObject.getInt("id");
                            Tensp=jsonObject.getString("tensp");
                            Giasp=jsonObject.getInt("giasp");
                            Hinhanhsp=jsonObject.getString("hinhanhsp");
                            Motasp=jsonObject.getString("motasp");
                            IDsanpham=jsonObject.getInt("idsanpham");
                            mangsanpham.add(new Sanpham(ID,Tensp,Giasp,Hinhanhsp,Motasp,IDsanpham));
                            sanphamadapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Checkconnection.Showtoast_short(getApplicationContext(),error.toString());
            }
        });
            requestQueue.add(jsonArrayRequest);
    }

    private void Getdulieuloaisp() {
        //hàm của thư viện Volley đọc nội dung đường dẫn Url
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.Đuonganloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            if(response!=null){
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        id=jsonObject.getInt("id");
                        tenloaisp=jsonObject.getString("tenloaisp");
                        hinhanhsp=jsonObject.getString("hinhanhloaisp");
                        mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhsp));
                        loaispadapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mangloaisp.add(3,new Loaisp(3,"Liên hệ","https://i.pinimg.com/originals/57/cf/21/57cf2127a1b9c8fdb334e5860fc22f61.png"));
                mangloaisp.add(4,new Loaisp(4,"Thông tin","https://png.pngtree.com/png-vector/20190916/ourlarge/pngtree-info-icon-for-your-project-png-image_1731084.jpg"));
            }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Checkconnection.Showtoast_short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    //ViewFlipper chạy hình ảnh quản cáo
    private void ActionViewFlipper() {
        //mảng chứa đường dẫn những tấm hình
        ArrayList<String> mangquangcao=new ArrayList<>();
        //thêm vào đường dẫn Url những tấm hình
        mangquangcao.add("https://genk.mediacdn.vn/2019/6/22/promotiondoublestorage-1561186618199280355933.jpg");
        mangquangcao.add("https://st.quantrimang.com/photos/image/2018/07/14/quang-cao-smartphone-650.jpg");
        mangquangcao.add("https://genk.mediacdn.vn/Images/Uploaded/Share/2011/02/28/40iphone.jpg");
        mangquangcao.add("https://cafebiz.cafebizcdn.vn/2017/untitled-1509952351674.png");
        for(int i=0;i<mangquangcao.size();i++){
            ImageView imageView=new ImageView(getApplicationContext());
            //hàm thư viện của Picasso để truyền hình ảnh vào Img bằng Url
           Picasso.get().load(mangquangcao.get(i)).into(imageView);
           //thuộc canh vừa với Viewflipper
           imageView.setScaleType(ImageView.ScaleType.FIT_XY);
           viewFlipper.addView(imageView);
        }
        //viewflipper tự chạy 5s
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        //khởi tại 2 resoure file anim
        Animation animation_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_out=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setOutAnimation(animation_out);
    }

    private void Actionbar() {
        //Hàm hỗ trợ toolbar
        setSupportActionBar(toolbar);
        //set núp HOME
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Tạo núp menu sort_by_sie(---)
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        //bắt sự kiện khi clik vào mở ra thanh menu
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Mở ra từ trái qua giữa
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    //Ánh xạ
    private void Anhxa() {
        toolbar=findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper=findViewById(R.id.viewlipper);
        recyclerViewmanhinhchinh=findViewById(R.id.recyclerview);
        navigationView=findViewById(R.id.navigationview);
        listView=findViewById(R.id.listviewmanhinhchinh);
        drawerLayout=findViewById(R.id.drawerlayout);
        mangloaisp=new ArrayList<>();
        mangloaisp.add(0,new Loaisp(0,"Trang chính","https://noithattinnghia.com/wp-content/uploads/2019/03/cropped-icon-home-cam.png"));
        loaispadapter=new Loaispadapter(mangloaisp,getApplicationContext());
        listView.setAdapter(loaispadapter);
        mangsanpham=new ArrayList<>();
        sanphamadapter=new Sanphamadapter(getApplicationContext(), mangsanpham, new Sanphamadapter.ItemClickListener() {
            @Override
            public void onItemCick(Sanpham details) {
                onClickrecyclerview(details);
            }
        });
        recyclerViewmanhinhchinh.setHasFixedSize(true);
        recyclerViewmanhinhchinh.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewmanhinhchinh.setAdapter(sanphamadapter);
        if(manggiohang!=null){

        }else{
            manggiohang=new ArrayList<>();
        }
    }
    private void onClickrecyclerview(Sanpham details){
        Intent intent=new Intent(MainActivity.this, Chitietsanpham.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("thongtinsanpham",details);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}