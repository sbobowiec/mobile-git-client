package pl.edu.agh.gitclient.ui.repository;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import pl.edu.agh.gitclient.R;
import pl.edu.agh.gitclient.model.Repository;

@EViewGroup(R.layout.view_repository_item)
public class RepositoryItemView extends RelativeLayout {

    @ViewById(R.id.name)
    TextView mRepoName;

    private Context mContext;

    private Repository mRepository;

    public RepositoryItemView(Context context) {
        super(context);
        mContext = context;
    }

    public void bind(Repository repository) {
        if (repository == null) {
            return;
        }
        mRepository = repository;
        mRepoName.setText(repository.getName());
    }

    @Click(R.id.content)
    public void onRepositoryItemClick() {
        if (mRepository == null) {
            return;
        }
        RepositoryInfoActivity_.intent(mContext).mRepository(mRepository).start();
    }

}
