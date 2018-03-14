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
import mx.com.viccom.viccom.Clases.clsTarjeta;
import mx.com.viccom.viccom.Interfaces.onClickLisener;
import mx.com.viccom.viccom.R;

/**
 * Created by Admin on 11/03/2018.
 */

public class TarjetasPagoRecyclerViewAdapter extends RecyclerView.Adapter<TarjetasPagoRecyclerViewAdapter.ViewHolder> {
    private ArrayList<clsTarjeta> ArListTarjetas = new ArrayList<clsTarjeta>();
    private int layout;
    private onClickLisener clickLisener;
    private Context context;

    public TarjetasPagoRecyclerViewAdapter(ArrayList<clsTarjeta> arListTarjetas, int layout, onClickLisener clickLisener) {
        ArListTarjetas = arListTarjetas;
        this.layout = layout;
        this.clickLisener = clickLisener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        TarjetasPagoRecyclerViewAdapter.ViewHolder vh = new TarjetasPagoRecyclerViewAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(ArListTarjetas.get(position), clickLisener);

    }

    @Override
    public int getItemCount() {
        return ArListTarjetas.size();
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
        public void bind(final clsTarjeta o_Tarjeta, final onClickLisener listener) {
            try {
                // Procesamos los datos a renderizar

                txtMetodopago.setText(o_Tarjeta.getNumero());
                txtDescripcion.setText(o_Tarjeta.getNombre());

                String url = "";
                if (o_Tarjeta.getTipo() == 0){
                    url="https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/Old_Visa_Logo.svg/220px-Old_Visa_Logo.svg.png";
                }else  {
                    url="https://upload.wikimedia.org/wikipedia/commons/thumb/7/72/MasterCard_early_1990s_logo.png/220px-MasterCard_early_1990s_logo.png";
                }

                Picasso.with(context).load(url).into(ImgImage);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemListClick(o_Tarjeta);
                    }
                });



            }catch (Exception e) {//4
                e.printStackTrace();
                //Toast.makeText(context,"error no hay datos para mostrar",Toast.LENGTH_SHORT).show();
            }//4
        }
    }
}
