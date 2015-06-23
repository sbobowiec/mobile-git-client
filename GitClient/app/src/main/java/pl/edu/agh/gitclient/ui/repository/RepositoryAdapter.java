package pl.edu.agh.gitclient.ui.repository;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.gitclient.model.Repository;

@EBean
public class RepositoryAdapter extends BaseAdapter {

    private List<Repository> mRows = new ArrayList<>();

    private Context mContext;

    public void init(Context context) {
        mContext = context;
    }

    public void setRows(List<Repository> repositories) {
        if (repositories == null) {
            return;
        }
        mRows.clear();
        mRows.addAll(repositories);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mRows.size();
    }

    @Override
    public Repository getItem(int position) {
        return mRows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RepositoryItemView view;
        if (convertView == null) {
            view = RepositoryItemView_.build(mContext);
        } else {
            view = (RepositoryItemView) convertView;
        }
        Repository repository = getItem(position);
        if (repository != null) {
            view.bind(repository);
        }

        return view;
    }

}
