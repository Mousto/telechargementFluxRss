package com.dia.mous.telechargementrss;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private XMLAsyncTask _task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Guinéenews");
        boolean con = isConnectedToInternet();

        //AsincTask en action
        if(con)
        {
            setContentView(R.layout.activity_main);

            //Toast.makeText(this,Boolean.toString(con),Toast.LENGTH_LONG).show();
            final ProgressBar progress = (ProgressBar) findViewById(R.id.progress);

            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            RSSAdapter adapter = new RSSAdapter();
            recyclerView.setAdapter(adapter);

            XMLAsyncTask task = new XMLAsyncTask(adapter);
            task.execute("http://guineenews.org/feed/");

             /*demander à l’adapter de prévenir lorsque ses données changent.Autrement dit,
         enregistrer un “observer” dans l’adapter qui va être appelé chaque
        fois que l’adapter reçoit un notifyDataSetChanged.Permet de gerer le comportement de la
        ProgressBar(l'arreter par exemple quand le téléchargement est terminé)*/

            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    progress.setVisibility(View.GONE);//rendre invisible la progressBar.
                }
            });
        }else {
           // Toast.makeText(this,Boolean.toString(con),Toast.LENGTH_LONG).show();
            new AlertDialog.Builder(this)
                    .setTitle("connexion internet")
                    .setMessage("Vous n'avez probablement pas de connexion internet")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Whatever...
                            Toast.makeText(getApplicationContext(), "Je vais me brancher !", Toast.LENGTH_LONG).show();
                        }
                    }).show();
            //progress.setVisibility(View.GONE);//rendre invisible la progressBar.
        }



    }

    //Si téléchargement intérrompu (on quitte l'activité)
    @Override
    protected void onDestroy(){
        super.onDestroy();

        if(_task != null)
        {
            _task.cancel(true);//on stop le téléchargement
        }
    }

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
}
