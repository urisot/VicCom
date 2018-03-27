package mx.com.viccom.viccom.Fragments;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import mx.com.viccom.viccom.Clases.clsHisRecibos;
import mx.com.viccom.viccom.Clases.clsParameter;
import mx.com.viccom.viccom.Clases.clsRecibos;
import mx.com.viccom.viccom.R;
import mx.com.viccom.viccom.Utilities.SendToWCF;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabHisRecibosFragment extends Fragment {

    private clsRecibos o_recibo = new clsRecibos();
    private ActualizaHisReciosHttp actualizaHisReciosHttp;

    private ArrayList<String> alFechas = new ArrayList<>();
    private ArrayList<BarEntry> barEntriesImportes = new ArrayList<>();
    private ArrayList<BarEntry> barEntriesConsumos = new ArrayList<>();
    private BarChart barChartImportes;
    private BarChart barChartConsumos;
    private ProgressBar pbGraficas;

    private Button btnImportes,btnConsumos;


    public TabHisRecibosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            o_recibo = bundle.getParcelable("RECIBO");
        }

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_his_recibos, container, false);
        barChartImportes = (BarChart) view.findViewById(R.id.grafHisImportes);
        barChartConsumos = (BarChart) view.findViewById(R.id.grafHisConsumos);
        btnImportes = (Button) view.findViewById(R.id.btnImportes);
        btnConsumos = (Button) view.findViewById(R.id.btnConsumos);

        pbGraficas = (ProgressBar) view.findViewById(R.id.pbGraficas);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

/*        barChartImportes.setDrawBarShadow(false);
        barChartImportes.setDrawValueAboveBar(true);
        barChartImportes.setMaxVisibleValueCount(60);
        barChartImportes.setPinchZoom(false);

        barChartImportes.setDrawGridBackground(false);
        XAxis xAxis = barChartImportes.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(R.color.icons);
        xAxis.setDrawGridLines(false);*/


        ActualizarDatos();

        btnImportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barChartImportes.setVisibility(View.VISIBLE);
                barChartConsumos.setVisibility(View.INVISIBLE);
                btnImportes.setTextColor(getResources().getColor(R.color.icons));
                btnConsumos.setTextColor(getResources().getColor(R.color.colorAccent));
                barChartImportes.animateY(3000, Easing.EasingOption.EaseOutBack);
                barChartImportes.invalidate();

            }
        });

        btnConsumos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barChartImportes.setVisibility(View.INVISIBLE);
                barChartConsumos.setVisibility(View.VISIBLE);

                btnImportes.setTextColor(getResources().getColor(R.color.colorAccent));
                btnConsumos.setTextColor(getResources().getColor(R.color.icons));
                barChartConsumos.animateY(3000, Easing.EasingOption.EaseOutBack);
                barChartConsumos.invalidate();
            }
        });


    }
    private void ActualizarDatos(){
        //refreshLayout.setRefreshing(true);
        pbGraficas.setVisibility(View.VISIBLE);
        ArrayList<clsParameter> Parametros = new ArrayList<>();
        Parametros.add(new clsParameter("p1", o_recibo.getId_cuenta()+""));

        actualizaHisReciosHttp = new ActualizaHisReciosHttp();
        actualizaHisReciosHttp.execute(Parametros);

    }
    public class ActualizaHisReciosHttp extends AsyncTask<ArrayList<clsParameter>,Integer,ArrayList<clsHisRecibos>> {
        @Override
        protected ArrayList<clsHisRecibos> doInBackground(ArrayList<clsParameter>[] Parametros) {
            ArrayList<clsHisRecibos> listHistorialReturn = new ArrayList<clsHisRecibos>();
            clsParameter HttpResult = new clsParameter();

            try {
                String Url ="http://201.144.165.83/apicomapa/ComapaVic_OS.svc/getHistorialRecibos";

                HttpResult.setParameter_name(SendToWCF.Send_Get(Url, Parametros[0]));

                if(!HttpResult.getParameter_name().equals("Error")) {

                    Gson gson = new GsonBuilder().serializeNulls().create();
                    HttpResult.setParameter_name(HttpResult.getParameter_name().replace("\\/","/"));
                    String jSonResult =HttpResult.getParameter_name().substring(29,HttpResult.getParameter_name().length()-1);
                    listHistorialReturn = gson.fromJson(jSonResult, new TypeToken<ArrayList<clsHisRecibos>>(){}.getType());

                }
                else
                    HttpResult.setParameter_value("Error! Ha ocurrido un error de conexion.");
                return listHistorialReturn;

            }catch (Exception e) {//4
                e.printStackTrace();
            /*    Log.e("Error", e.toString());
                HttpResult.setParameter_name("Error");
                HttpResult.setParameter_value("Error!: "+e.toString());*/

                return listHistorialReturn;
            }//4

        }


        @Override
        protected void onPostExecute(ArrayList<clsHisRecibos> ListHistorial) {
            super.onPostExecute(ListHistorial);
            pbGraficas.setVisibility(View.GONE);
            if (ListHistorial.size()>0){
                int intContador=0;
                int intRegistros = 0;

                if (ListHistorial.size()<5){
                    intRegistros=ListHistorial.size();
                }else{
                    intRegistros=5;
                }

                for (clsHisRecibos recibo : ListHistorial) {

                    alFechas.add(recibo.getCiclo_facturado());
                    barEntriesImportes.add(new BarEntry(recibo.getTotal(),intContador));
                    barEntriesConsumos.add(new BarEntry(recibo.getConsumo(),intContador));

                    //entries.add(new Entry(recibo.getTotal(), intContador));

                    intContador ++;
                    if (intContador == intRegistros){
                        break;
                    }

                }
             /*   barEntriesConsumos.add(new BarEntry(13,0));
                barEntriesConsumos.add(new BarEntry(10,1));
                barEntriesConsumos.add(new BarEntry(15,2));
                barEntriesConsumos.add(new BarEntry(18,3));
                barEntriesConsumos.add(new BarEntry(20,4));
                BarDataSet barDataSet2 = new BarDataSet(barEntriesConsumos,"Consumos");*/

                MostrarDatosGrafica(barChartImportes,barEntriesImportes,alFechas,View.VISIBLE,"Importes facturados");

                MostrarDatosGrafica(barChartConsumos,barEntriesConsumos,alFechas,View.INVISIBLE,"M³ facturados");

               /* BarDataSet barDataSet = new BarDataSet(barEntriesImportes,"Importes facturados");
                BarData barData = new BarData(alFechas,barDataSet);
                barDataSet.setColors(new int[] { R.color.icons }, getContext());
                barDataSet.setValueTextColor(R.color.icons);
                barChartImportes.setVisibility(View.VISIBLE);
                barData.setValueTextColor(R.color.icons);
                barChartImportes.setData(barData);
                barChartImportes.setDescription("");
                barChartImportes.animateY(3000, Easing.EasingOption.EaseOutBack);
                barChartImportes.invalidate();*/



            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }


        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    private void MostrarDatosGrafica(BarChart barChart, ArrayList<BarEntry> barEntries, ArrayList<String> alFechasBar, int visible, String titulo) {

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(60);
        barChart.setPinchZoom(false);

        barChart.setDrawGridBackground(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(R.color.icons);
        xAxis.setDrawGridLines(false);

        BarDataSet barDataSet = new BarDataSet(barEntries,titulo);
        BarData barData = new BarData(alFechasBar,barDataSet);
        barDataSet.setColors(new int[] { R.color.icons }, getContext());
        barDataSet.setValueTextColor(R.color.icons);
        barChart.setVisibility(visible);
        barData.setValueTextColor(R.color.icons);
        barChart.setData(barData);
        barChart.setDescription("");
        barChart.animateY(3000, Easing.EasingOption.EaseOutBack);
        barChart.invalidate();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        actualizaHisReciosHttp.cancel(true);

    }

}
