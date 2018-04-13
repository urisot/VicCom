package mx.com.viccom.viccom.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;



import java.util.ArrayList;

import mx.com.viccom.viccom.Clases.clsRecibos;
import mx.com.viccom.viccom.R;

/**
 * Created by Admin on 28/02/2018.
 */

public class RecibosRecyclerViewAdapter  extends RecyclerView.Adapter<RecibosRecyclerViewAdapter.ViewHolder> {
    private ArrayList<clsRecibos> ArListRecibos = new ArrayList<clsRecibos>();
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;


    public RecibosRecyclerViewAdapter(ArrayList<clsRecibos> listrecibos, int layout, OnItemClickListener listener) {
        this.ArListRecibos = listrecibos;
        this.layout = layout;
        this.itemClickListener = listener;
    }

    public void updateData(ArrayList<clsRecibos> listrecibos) {
        ArListRecibos.clear();
        ArListRecibos.addAll(listrecibos);
        notifyDataSetChanged();
    }
    public void addItem(int position, clsRecibos listrecibos) {
        ArListRecibos.add(position, listrecibos);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        ArListRecibos.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos el layout y se lo pasamos al constructor del ViewHolder, donde manejaremos
        // toda la lógica como extraer los datos, referencias...
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Llamamos al método Bind del ViewHolder pasándole objeto y listener
        holder.bind(ArListRecibos.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return ArListRecibos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Elementos UI a rellenar
        public TextView txtNombre;
        public TextView txtCuenta;
        public TextView txtImporte;
        public TextView txtVencimiento;
        public TextView isRecVencido;
        public Button btnPagar;
        public ImageButton btnEliminar;


        public ViewHolder(View itemView) {
            // Recibe la View completa. La pasa al constructor padre y enlazamos referencias UI
            // con nuestras propiedades ViewHolder declaradas justo arriba.
            super(itemView);
            txtNombre = (TextView) itemView.findViewById(R.id.txt_Nombre_US);
            txtCuenta = (TextView) itemView.findViewById(R.id.txt_Cuenta_US);
            txtImporte = (TextView) itemView.findViewById(R.id.txt_Importe_US);
            txtVencimiento = (TextView) itemView.findViewById(R.id.txt_Vencimiento_US);
            isRecVencido = (TextView) itemView.findViewById(R.id.isRecVencido);
            btnPagar = (Button) itemView.findViewById(R.id.btnPagar_us);
            btnEliminar = (ImageButton) itemView.findViewById(R.id.btnEliminar_us);

        }

        public void bind(final clsRecibos recibo, final OnItemClickListener listener) {
            try {
                // Procesamos los datos a renderizar
                txtNombre.setText(recibo.getRazon_social());
                txtCuenta.setText(recibo.getId_cuenta()+"");
                txtImporte.setText("$ "+ Math.round(recibo.getTotal()) +".");
                txtVencimiento.setText(recibo.getFecha_vencimiento());

                /*if (!(Math.round(recibo.getTotal())>0)){
                    btnPagar.setVisibility(View.INVISIBLE);
                }*/

                if (!(recibo.getVencido() == 0)){
                    isRecVencido.setVisibility(View.VISIBLE);
                    txtVencimiento.setTextColor(Color.RED);
                }


//            Picasso.with(context).load(movie.getId()).fit().into(imageViewPoster);
                // imageViewPoster.setImageResource(movie.getPoster());

                // Definimos que por cada elemento de nuestro recycler view, tenemos un click listener
                // que se comporta de la siguiente manera...
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(recibo, getAdapterPosition());
                    }
                });

                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        listener.onLongItemClick(recibo, getAdapterPosition());
                        return false;
                    }
                });
                btnPagar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onButton1ItemClick(recibo, getAdapterPosition());
                    }
                });
                btnEliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onButton2ItemClick(recibo, getAdapterPosition());
                    }
                });

            }catch (Exception e) {//4
                e.printStackTrace();
                //Toast.makeText(context,"error no hay datos para mostrar",Toast.LENGTH_SHORT).show();
            }//4
        }
    }

    // Declaramos nuestra interfaz con el/los método/s a implementar
    public interface OnItemClickListener {
        void onItemClick(clsRecibos recibo, int position);
        void onLongItemClick(clsRecibos recibo, int position);
        void onButton1ItemClick(clsRecibos recibo, int position);
        void onButton2ItemClick(clsRecibos recibo, int position);
    }


}

