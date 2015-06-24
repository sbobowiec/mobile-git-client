package pl.edu.agh.gitclient.ui.commit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.gitclient.model.Commit;

@EBean
public class CommitAdapter extends BaseAdapter {

    private List<Commit> mRows = new ArrayList<>();

    private Context mContext;

    public void init(Context context) {
        mContext = context;
    }

    public void setRows(List<Commit> commits, String repoName) {
        if (commits == null) {
            return;
        }
        mRows.clear();

        for (int i = 0; i < commits.size(); i++) {
            Commit currCommit = commits.get(i);
            currCommit.setRepoName(repoName);
            mRows.add(currCommit);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mRows.size();
    }

    @Override
    public Commit getItem(int position) {
        return mRows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommitItemView view;
        if (convertView == null) {
            view = CommitItemView_.build(mContext);
        } else {
            view = (CommitItemView) convertView;
        }
        Commit commit = getItem(position);
        if (commit != null) {
            view.bind(commit);
        }

        return view;
    }

}
