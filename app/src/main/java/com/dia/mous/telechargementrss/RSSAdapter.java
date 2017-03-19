package com.dia.mous.telechargementrss;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Created by Mous on 06/03/2017.
 */

public class RSSAdapter extends RecyclerView.Adapter<RSSAdapter.ArticleViewHolder> implements XMLAsyncTask.DocumentConsumer {

    private Document _document = null;
    public static String monUrl = "monHtmlContent";

   // private static final int VIEW_TYPE_ARTICLE = 0;
   // private static final int VIEW_TYPE_PROGRESS = 1;
   // private boolean _hasMore = false;

    @Override
    public int getItemCount()
    {
        if(_document != null){
            return _document.getElementsByTagName("item").getLength();
        }
        else {
            return 0;
        }
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewTpe)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell, parent, false);

        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position)
    {
        Element item = (Element) _document.getElementsByTagName("item").item(position);
        holder.setElement(item);
    }

    @Override
    public void setXMLDocument(Document document)
    {
        _document = document;
        notifyDataSetChanged();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        private final TextView _titre;
        private Element _currentElement;
        private String _link;

        public ArticleViewHolder(final View itemView)
        {
            super(itemView);
            _titre = ((TextView)itemView.findViewById(R.id.titre));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Toast.makeText(context,"ecouteur attentif", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(view.getContext(), WebViewActivity.class);
                    _link = _currentElement.getElementsByTagName("link").item(0).getTextContent();
                    i.putExtra(monUrl, _link);
                    context.startActivity(i);
                }
            });
        }

        public void setElement(Element element)
        {
            _currentElement = element;
            _titre.setText(element.getElementsByTagName("title").item(0).getTextContent());
        }
    }

}
