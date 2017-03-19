package com.dia.mous.telechargementrss;

import android.os.AsyncTask;
import android.provider.DocumentsContract;
import android.util.Log;

import org.w3c.dom.Document;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import static javax.xml.parsers.DocumentBuilderFactory.newInstance;

/**
 * Created by Mous on 06/03/2017.
 */

public class XMLAsyncTask extends AsyncTask<String, Void, Document> {

    interface DocumentConsumer{
        void setXMLDocument(Document document);
    }

    private DocumentConsumer _consumer;

    public XMLAsyncTask(DocumentConsumer consumer ){
        _consumer = consumer;
    }

    @Override
    protected Document doInBackground(String...params)
    {
        Document document = null;
        try {
            Thread.sleep(0);
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            InputStream stream = connection.getInputStream();
            try {
               document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
            } finally {
                stream.close();
            }

        }
        catch (InterruptedException ex)//par intérruption entre autre de XMLAsinctask (appel de _task.cancel(true) dans onDestroy de MainAcrivity)
        {
           // Log.i("XMLAsinctask","Téléchargement intérrompu");
            return null;
        }
        catch (Exception ex) {
           // Log.i("XMLasyncTask", "Exception pendant téléchargement",ex);
            throw new RuntimeException(ex);
        }
        return document;
    }

    @Override
    protected void onPostExecute(Document resultat)
    {
       // Log.i("XMLAsincTask", "Fini");
        _consumer.setXMLDocument(resultat);
    }
}
