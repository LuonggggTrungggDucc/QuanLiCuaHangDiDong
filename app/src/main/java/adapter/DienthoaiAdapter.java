package adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuahangthietbionline.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import model.Sanpham;

public class DienthoaiAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayListdienthoai;

    public DienthoaiAdapter(Context context, ArrayList<Sanpham> arrayListdienthoai) {
        this.context = context;
        this.arrayListdienthoai = arrayListdienthoai;
    }

    @Override
    public int getCount() {
        return arrayListdienthoai.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListdienthoai.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public static class Viewhoder{
        public TextView txttensp,txtgiasp,txtmotasp;
        public ImageView imghinhsp;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewhoder viewhoder=null;
        if(view==null){
            viewhoder=new Viewhoder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_dienthoai,null);
            viewhoder.txttensp=(TextView) view.findViewById(R.id.textviewsp);
            viewhoder.txtgiasp=(TextView) view.findViewById(R.id.textviewgiasp);
            viewhoder.txtmotasp=(TextView) view.findViewById(R.id.textviewmotasp);
            viewhoder.imghinhsp=(ImageView) view.findViewById(R.id.imgdienthoai);
            view.setTag(viewhoder);
        }else{
            viewhoder= (Viewhoder) view.getTag();
        }
        Sanpham sanpham= (Sanpham) getItem(i);
        viewhoder.txttensp.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewhoder.txtgiasp.setText("Giá "+decimalFormat.format(sanpham.getGiasanpham())+" Đ");
        viewhoder.txtmotasp.setMaxLines(2);
        viewhoder.txtmotasp.setEllipsize(TextUtils.TruncateAt.END);
        viewhoder.txtmotasp.setText(sanpham.getMotasanpham());
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.loadding)
                .error(R.drawable.error)
                .into(viewhoder.imghinhsp);

        return view;
    }
}
