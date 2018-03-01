package mx.com.viccom.viccom.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.ArrayList;

import mx.com.viccom.viccom.Clases.clsDetRecibos;
import mx.com.viccom.viccom.R;

/**
 * Created by Admin on 28/02/2018.
 */

public class ConceptosRecyclerViewAdapter extends RecyclerView.Adapter<ConceptosRecyclerViewAdapter.ViewHolder> {
    private ArrayList<clsDetRecibos> ArListConceptos = new ArrayList<clsDetRecibos>();
    private int layout;
    private Context context;

    public ConceptosRecyclerViewAdapter(ArrayList<clsDetRecibos> listConceptos, int layout, Context context) {
        ArListConceptos = listConceptos;
        this.layout = layout;
        this.context = context;
    }
    public void updateData(ArrayList<clsDetRecibos> listConceptos) {
        ArListConceptos.clear();
        ArListConceptos.addAll(listConceptos);
        notifyDataSetChanged();
    }
    public void addItem(int position, clsDetRecibos concepto) {
        ArListConceptos.add(position, concepto);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        ArListConceptos.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        context = parent.getContext();
        ConceptosRecyclerViewAdapter.ViewHolder vh = new ConceptosRecyclerViewAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(ArListConceptos.get(position));
    }

    @Override
    public int getItemCount() {
        return ArListConceptos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtConcept;
        public TextView txtTotal;

        public ViewHolder(View itemView) {
            super(itemView);
            txtConcept = (TextView) itemView.findViewById(R.id.txt_Concepto);
            txtTotal = (TextView) itemView.findViewById(R.id.txt_TotalConcepto);
        }
        public void bind(final clsDetRecibos concepto) {
            // Procesamos los datos a renderizar
            txtConcept.setText(concepto.getConcepto());
            txtTotal.setText(concepto.getTotal()+"");

        }
    }
}
