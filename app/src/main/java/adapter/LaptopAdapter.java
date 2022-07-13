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

public class LaptopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayListlaptop;

    public LaptopAdapter(Context context, ArrayList<Sanpham> arrayListlaptop) {
        this.context = context;
        this.arrayListlaptop = arrayListlaptop;
    }

    @Override
    public int getCount() {
        return arrayListlaptop.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListlaptop.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class Viewhoder{
        public TextView txttenlaptop,txtgialaptop,txtmotalaptop;
        public ImageView imghinhlaptop;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       Viewhoder viewhoder=null;
        if(view==null){
            viewhoder=new Viewhoder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_laptop,null);
            viewhoder.txttenlaptop=(TextView) view.findViewById(R.id.textviewtenlaptop);
            viewhoder.txtgialaptop=(TextView) view.findViewById(R.id.textviewgialaptop);
            viewhoder.txtmotalaptop=(TextView) view.findViewById(R.id.textviewmotalaptop);
            viewhoder.imghinhlaptop=(ImageView) view.findViewById(R.id.imglaptop);
            view.setTag(viewhoder);
        }else{
            viewhoder= (Viewhoder) view.getTag();
        }
        Sanpham sanpham= (Sanpham) getItem(i);
        viewhoder.txttenlaptop.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewhoder.txtgialaptop.setText("Giá "+decimalFormat.format(sanpham.getGiasanpham())+" Đ");
        viewhoder.txtmotalaptop.setMaxLines(2);
        viewhoder.txtmotalaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewhoder.txtmotalaptop.setText(sanpham.getMotasanpham());
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.loadding)
                .error(R.drawable.error)
                .into(viewhoder.imghinhlaptop);
        return view;
    }
}
