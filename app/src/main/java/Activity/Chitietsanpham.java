package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cuahangthietbionline.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import model.GioHang;
import model.Sanpham;

public class Chitietsanpham extends AppCompatActivity {
    ImageView imgchitietsp;
    TextView txttensp, txtgiasp, txtmota;
    Spinner spinner;
    Button btnthemgohang;
    Toolbar toolbarchitiet;
    int Id=0;
    String Tenchitiet="";
    int Giachitiet=0;
    String Hinhanhchitiet="";
    String Motachitiet="";
    int Idsanpham=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
        Anhxa();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
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

    private void EventButton() {
        btnthemgohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.manggiohang.size()>0){
                    int sl=Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists=false;
                    for (int i=0;i<MainActivity.manggiohang.size();i++){
                        if(MainActivity.manggiohang.get(i).getIdsp()==Id){
                            MainActivity.manggiohang.get(i).setSoluong(MainActivity.manggiohang.get(i).getSoluong() + sl);
                            if(MainActivity.manggiohang.get(i).getSoluong()>=10){
                                MainActivity.manggiohang.get(i).setSoluong(10);
                            }
                            MainActivity.manggiohang.get(i).setSoluong(MainActivity.manggiohang.get(i).getSoluong() + sl);
                            exists=true;
                        }
                    }
                    if(exists==false){
                        int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi=soluong * Giachitiet;
                        MainActivity.manggiohang.add(new GioHang(Id,Tenchitiet,Giamoi,Hinhanhchitiet,soluong));
                    }
                }else{
                    int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi=soluong * Giachitiet;
                    MainActivity.manggiohang.add(new GioHang(Id,Tenchitiet,Giamoi,Hinhanhchitiet,soluong));
                }
                Intent intent=new Intent(getApplicationContext(),Giohang.class);
                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner(){
        Integer[] soluong= new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> integerArrayAdapter=new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(integerArrayAdapter);
    }

    private void GetInformation() {
        Sanpham sanpham= (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        Id=sanpham.getID();
        Tenchitiet=sanpham.getTensanpham();
        Giachitiet=sanpham.getGiasanpham();
        Hinhanhchitiet=sanpham.getHinhanhsanpham();
        Motachitiet=sanpham.getMotasanpham();
        Idsanpham=sanpham.getIDsanpham();
        txttensp.setText(Tenchitiet);
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txtgiasp.setText("Giá: "+decimalFormat.format(Giachitiet)+" Đ");
        txtmota.setText(Motachitiet);
        Picasso.get().load(Hinhanhchitiet)
                .placeholder(R.drawable.loadding)
                .error(R.drawable.error)
                .into(imgchitietsp);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        imgchitietsp=(ImageView) findViewById(R.id.imgchitietsp);
        txttensp=(TextView) findViewById(R.id.txttensp);
        txtgiasp=(TextView) findViewById(R.id.txtgiachitietsp);
        txtmota=(TextView) findViewById(R.id.txtmotachitietsp);
        spinner=(Spinner) findViewById(R.id.spinnerchitietsp);
        btnthemgohang=(Button) findViewById(R.id.btnthemgiohang);
        toolbarchitiet=(Toolbar) findViewById(R.id.toolbarchitietsp);
    }
}