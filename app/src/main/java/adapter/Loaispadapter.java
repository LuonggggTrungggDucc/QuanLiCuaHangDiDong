package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cuahangthietbionline.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import model.Loaisp;

public class Loaispadapter extends BaseAdapter {
    ArrayList<Loaisp> arrayListloaisp;
    Context context;

    public Loaispadapter(ArrayList<Loaisp> arrayListloaisp, Context context) {
        this.arrayListloaisp = arrayListloaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListloaisp.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListloaisp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class Viewhoder{
        TextView txttenloaisp;
        ImageView imgloaisp;

}
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewhoder viewhoder=null;
        if(view==null){
            viewhoder=new Viewhoder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_listview_loaisp,null);
            viewhoder.txttenloaisp=(TextView) view.findViewById(R.id.textviewloaisp);
            viewhoder.imgloaisp=(ImageView) view.findViewById(R.id.imageviewloaisp);
            view.setTag(viewhoder);
        }
        else {
            viewhoder=(Viewhoder) view.getTag();
        }
        Loaisp loaisp= (Loaisp) getItem(i);
        viewhoder.txttenloaisp.setText(loaisp.getTenloaisp());
        Picasso.get().load(loaisp.getHinhanhloaisp())
                .placeholder(R.drawable.loadding)
                .error(R.drawable.error)
                .into(viewhoder.imgloaisp);
        return view;
    }
}
