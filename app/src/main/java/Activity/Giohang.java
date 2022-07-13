package Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cuahangthietbionline.R;

import java.text.DecimalFormat;

import adapter.GiohangAdapter;
import until.Checkconnection;

public class Giohang extends AppCompatActivity {
    Toolbar Toolbargiohang;
    TextView txtThongbao;
    static TextView txtTongtien;
    ListView listViewgiohang;
    Button btnthanhtoan, btntieptucmua;
    GiohangAdapter giohangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        ActionTollbar();
        CheckData();
        EventUtil();
        CacthOnItem();
        EventButton();
    }

    private void EventButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckData();
                Intent intent=new Intent(Giohang.this,MainActivity.class);
                giohangAdapter.notifyDataSetChanged();
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.manggiohang.size()<=0){
                    Checkconnection.Showtoast_short(Giohang.this,"Giỏ hàng của bạn không có sản phẩm để thanh toán");
                }else{
                    Intent intent=new Intent(getApplicationContext(),Thongtinnguoidung.class);
                    startActivity(intent);

                }
            }
        });
    }

    private void CacthOnItem() {
        listViewgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Giohang.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MainActivity.manggiohang.size()<=0){
                            txtThongbao.setVisibility(View.VISIBLE);
                        }else{
                            MainActivity.manggiohang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            EventUtil();
                            if(MainActivity.manggiohang.size()<=0) {
                                txtThongbao.setVisibility(View.VISIBLE);
                            }else{
                                txtThongbao.setVisibility(View.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                EventUtil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        giohangAdapter.notifyDataSetChanged();
                        EventUtil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventUtil()  {
        long tongtien=0;
        for (int i=0;i<MainActivity.manggiohang.size();i++){
            tongtien+=MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txtTongtien.setText(decimalFormat.format(tongtien)+" Đ");
    }

    private void CheckData() {
        if(MainActivity.manggiohang.size()<=0)
        {
            giohangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.VISIBLE);
            listViewgiohang.setVisibility(View.INVISIBLE);
        }else{
            giohangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.INVISIBLE);
            listViewgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void ActionTollbar() {
        setSupportActionBar(Toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void Anhxa() {
        txtThongbao=(TextView) findViewById(R.id.textviewthongbao);
        txtTongtien=(TextView) findViewById(R.id.textviewtongtien);
        Toolbargiohang=(Toolbar) findViewById(R.id.toolbargiohang);
        listViewgiohang=(ListView) findViewById(R.id.listviewgiohang);
        btnthanhtoan=(Button) findViewById(R.id.btnthanhtoangiohang);
        btntieptucmua=(Button) findViewById(R.id.btntieptucmuahang);
        giohangAdapter=new GiohangAdapter(Giohang.this,MainActivity.manggiohang);
        listViewgiohang.setAdapter(giohangAdapter);
    }
}