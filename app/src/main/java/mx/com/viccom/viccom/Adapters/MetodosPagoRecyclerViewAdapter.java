package mx.com.viccom.viccom.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;



import java.util.ArrayList;

import mx.com.viccom.viccom.Clases.clsMetodoPago;
import mx.com.viccom.viccom.Interfaces.onClickLisener;
import mx.com.viccom.viccom.R;

/**
 * Created by Admin on 28/02/2018.
 */

public class MetodosPagoRecyclerViewAdapter extends RecyclerView.Adapter<MetodosPagoRecyclerViewAdapter.ViewHolder>  {
private ArrayList<clsMetodoPago> ArListMetodosPago = new ArrayList<clsMetodoPago>();
private int layout;
private onClickLisener clickLisener;
private Context context;

public MetodosPagoRecyclerViewAdapter(ArrayList<clsMetodoPago> arListMetodosPago, int layout, onClickLisener clickLisener) {
        ArListMetodosPago = arListMetodosPago;
        this.layout = layout;
        this.clickLisener = clickLisener;
        }

@Override
public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        MetodosPagoRecyclerViewAdapter.ViewHolder vh = new MetodosPagoRecyclerViewAdapter.ViewHolder(v);
        return vh;
        }

@Override
public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(ArListMetodosPago.get(position), clickLisener);
        }

@Override
public int getItemCount() {
        return ArListMetodosPago.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView txtMetodopago;
    private TextView txtDescripcion;
    private ImageView ImgImage;

    public ViewHolder(View itemView) {
        super(itemView);
        txtMetodopago = (TextView) itemView.findViewById(R.id.txtMetodoPago);
        txtDescripcion = (TextView) itemView.findViewById(R.id.txtMetodoDescripcion);
        ImgImage = (ImageView) itemView.findViewById(R.id.ivImagenMetodo);
    }
    public void bind(final clsMetodoPago o_MetodoPago, final onClickLisener listener) {
        try {
            // Procesamos los datos a renderizar

            txtMetodopago.setText(o_MetodoPago.getMetodo());
            txtDescripcion.setText(o_MetodoPago.getDescripcion());
            String url = o_MetodoPago.getImagen_url();
            Picasso.with(context).load(url).into(ImgImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemListClick(o_MetodoPago);
                }
            });



        }catch (Exception e) {//4
            e.printStackTrace();
            //Toast.makeText(context,"error no hay datos para mostrar",Toast.LENGTH_SHORT).show();
        }//4
    }

}
}
