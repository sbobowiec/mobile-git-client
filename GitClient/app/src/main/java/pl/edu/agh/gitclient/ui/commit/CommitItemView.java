package pl.edu.agh.gitclient.ui.commit;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import pl.edu.agh.gitclient.R;
import pl.edu.agh.gitclient.model.Commit;

@EViewGroup(R.layout.view_commit_item)
public class CommitItemView extends RelativeLayout {

    @ViewById(R.id.message)
    TextView mMessage;

    @ViewById(R.id.author)
    TextView mAuthor;

    private Context mContext;

    private Commit mCommit;

    public CommitItemView(Context context) {
        super(context);
        mContext = context;
    }

    public void bind(Commit commit) {
        if (commit == null) {
            return;
        }
        mCommit = commit;
        String message = commit.getMessage();
        String author = commit.getCommitterLogin();

        mMessage.setText(message);
        mAuthor.setText(author);
    }

    @Click(R.id.content)
    public void onCommitItemClick() {
        if (mCommit == null) {
            return;
        }
        CommitInfoActivity_.intent(mContext).mCommit(mCommit).start();
    }

}
