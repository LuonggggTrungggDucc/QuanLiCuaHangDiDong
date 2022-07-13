package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cuahangthietbionline.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import model.Sanpham;

public class Sanphamadapter extends RecyclerView.Adapter<Sanphamadapter.ItemHoder>{
    Context context;
    ArrayList<Sanpham> arrayListsanpham;
    ItemClickListener itemClickListener;
    public Sanphamadapter(Context context, ArrayList<Sanpham> arrayListsanpham,ItemClickListener itemClickListener) {
        this.context = context;
        this.arrayListsanpham = arrayListsanpham;
        this.itemClickListener=itemClickListener;
    }

    @Override
    public ItemHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinnhat,null);
        ItemHoder itemHoder=new ItemHoder(v);
        return itemHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHoder holder, int position) {
        Sanpham sanpham=arrayListsanpham.get(position);
        holder.txttensanpham.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText("Giá "+decimalFormat.format(sanpham.getGiasanpham())+" Đ");
        Picasso.get().load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.loadding)
                .error(R.drawable.error)
                .into(holder.imghinhsanpham);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemCick(sanpham);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayListsanpham.size();
    }
    public interface ItemClickListener{
        void onItemCick(Sanpham details);


    }

    public class ItemHoder extends RecyclerView.ViewHolder{
        public ImageView imghinhsanpham;
        public TextView txttensanpham, txtgiasanpham;

        public ItemHoder(@NonNull View itemView) {
            super(itemView);
            imghinhsanpham=(ImageView) itemView.findViewById(R.id.imgsanphammoi);
            txttensanpham=(TextView) itemView.findViewById(R.id.textviewtensanphammoi);
            txtgiasanpham=(TextView) itemView.findViewById(R.id.textviewgiasanphammoi);
        }
    }
}
