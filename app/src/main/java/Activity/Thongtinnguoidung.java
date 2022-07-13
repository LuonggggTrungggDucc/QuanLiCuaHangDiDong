package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

import until.Checkconnection;
import until.Server;

public class Thongtinnguoidung extends AppCompatActivity {
    EditText edttenkhachhang,edtsodienthoai,edtdiachi,edtemail;
    Button btnxacnhanthongtin, btntrove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinnguoidung);
        Anhxa();
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if(Checkconnection.HaveNetworkConnection(getApplicationContext())) {
            EventButton();
        }else{
            Checkconnection.Showtoast_short(getApplicationContext(),"Kiểm tra lại kết nối mạng");
        }
    }

    private void EventButton() {
        {
            btnxacnhanthongtin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ten=edttenkhachhang.getText().toString().trim();
                    String sdt=edtsodienthoai.getText().toString().trim();
                    String diachi=edtdiachi.getText().toString().trim();
                    String email=edtemail.getText().toString().trim();
                    if(ten.length()>0 && sdt.length()>0 && diachi.length()>0 && email.length()>0){
                        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.Duongdandonhang, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String madonhang) {
                                Log.d("onResponse: ",madonhang);
                                if(Integer.parseInt(madonhang)>0){
                                    RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                                    StringRequest request = new StringRequest(Request.Method.POST, Server.Duongdanchitietdonhang, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if(response.equals("1")){
                                                MainActivity.manggiohang.clear();
                                                Checkconnection.Showtoast_short(getApplicationContext(),"Bạn đã thêm dữ liệu giỏ hàng thành công");
                                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                                startActivity(intent);
                                                Checkconnection.Showtoast_short(getApplicationContext(),"Mời bạn tiếp tục mua hàng");
                                            }else{
                                                Checkconnection.Showtoast_short(getApplicationContext(),"Dữ liệu giỏ hàng của bạn bị lỗi");
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
                                            JSONArray jsonArray=new JSONArray();
                                            for (int i=0;i<MainActivity.manggiohang.size();i++){
                                                JSONObject jsonObject=new JSONObject();
                                                try {
                                                    jsonObject.put("madonhang",madonhang);
                                                    jsonObject.put("masanpham",MainActivity.manggiohang.get(i).getIdsp());
                                                    jsonObject.put("tensanpham",MainActivity.manggiohang.get(i).getTensp());
                                                    jsonObject.put("giasanpham",MainActivity.manggiohang.get(i).getGiasp());
                                                    jsonObject.put("soluongsanpham",MainActivity.manggiohang.get(i).getSoluong());
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                jsonArray.put(jsonObject);
                                            }
                                            HashMap<String,String> hashMap=new HashMap<>();
                                            hashMap.put("json",jsonArray.toString());
                                            return hashMap;
                                        }
                                    };
                                    queue.add(request);
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
                                HashMap<String,String> hashMap=new HashMap<>();
                                hashMap.put("tenkhachhang",ten);
                                hashMap.put("sodienthoai",sdt);
                                hashMap.put("diachi",diachi);
                                hashMap.put("email",email);
                                return hashMap;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }else{
                        Checkconnection.Showtoast_short(getApplicationContext(),"Bạn hãy điền đầy đủ thông tin");
                    }
                }
            });
        }
    }

    private void Anhxa() {
        edttenkhachhang=(EditText) findViewById(R.id.edittextnhaptenkh);
        edtsodienthoai=(EditText) findViewById(R.id.edittextnhapsodienthoai);
        edtdiachi=(EditText) findViewById(R.id.edittextnhapdiachikh);
        edtemail=(EditText) findViewById(R.id.edittextnhapemail);
        btnxacnhanthongtin=(Button) findViewById(R.id.btnxacnhapthongtin);
        btntrove=(Button) findViewById(R.id.btntrove);
    }
}