package pl.edu.agh.gitclient.ui.commit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.gitclient.algorithm.ChangeStats;

@EBean
public class CodeChangesAdapter extends BaseAdapter {

    private List<ChangeStats> mRows = new ArrayList<>();

    private Context mContext;

    public void init(Context context) {
        mContext = context;
    }

    public void setRows(List<ChangeStats> changeStatsList) {
        if (changeStatsList == null) {
            return;
        }
        mRows.clear();
        mRows.addAll(changeStatsList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mRows.size();
    }

    @Override
    public ChangeStats getItem(int position) {
        return mRows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CodeChangeItemView view;
        if (convertView == null) {
            view = CodeChangeItemView_.build(mContext);
        } else {
            view = (CodeChangeItemView) convertView;
        }
        ChangeStats changeStats = getItem(position);
        if (changeStats != null) {
            view.bind(changeStats);
        }

        return view;
    }

}
