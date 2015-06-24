package pl.edu.agh.gitclient.ui.commit;

import android.content.Context;
import android.widget.LinearLayout;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import pl.edu.agh.gitclient.R;
import pl.edu.agh.gitclient.algorithm.ChangeStats;

@EViewGroup(R.layout.view_code_change_item)
public class CodeChangeItemView extends LinearLayout {

    @ViewById(R.id.container)
    LinearLayout mContainer;

    private Context mContext;

    public CodeChangeItemView(Context context) {
        super(context);
        mContext = context;
    }

    public void bind(ChangeStats changeStats) {
        if (changeStats == null) {
            return;
        }

        if (mContainer.getChildCount() > 0) {
            mContainer.removeAllViews();
        }

        matchViewToElementType(changeStats);
        List<ChangeStats> childs = changeStats.getChilds();
        for (ChangeStats childChangeStat : childs) {
            bindChild(childChangeStat);
        }
    }

    private void bindChild(ChangeStats changeStats) {
        matchViewToElementType(changeStats);
        List<ChangeStats> childs = changeStats.getChilds();
        for (ChangeStats childChangeStat : childs) {
            bindChild(childChangeStat);
        }
    }

    private void matchViewToElementType(ChangeStats changeStats) {
        SingleCodeChangeItemView singleCodeChangeItemView = SingleCodeChangeItemView_.build(mContext);
        singleCodeChangeItemView.bind(changeStats);
        mContainer.addView(singleCodeChangeItemView);
    }

}
