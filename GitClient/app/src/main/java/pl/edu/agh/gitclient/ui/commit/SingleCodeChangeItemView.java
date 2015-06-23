package pl.edu.agh.gitclient.ui.commit;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import pl.edu.agh.gitclient.R;
import pl.edu.agh.gitclient.algorithm.ChangeStats;
import pl.edu.agh.gitclient.algorithm.ElementType;

@EViewGroup(R.layout.view_single_code_change_item)
public class SingleCodeChangeItemView extends LinearLayout {

    @ViewById(R.id.name)
    TextView mName;

    @ViewById(R.id.insertions)
    TextView mInsertions;

    @ViewById(R.id.deletions)
    TextView mDeletions;

    public SingleCodeChangeItemView(Context context) {
        super(context);
    }

    public void bind(ChangeStats stats) {
        if (stats == null) {
            return;
        }
        ElementType type = stats.getElementType();
        String prefix = "";
        switch (type) {
            case FILE:
                prefix += "FILE: ";
                break;
            case CLASS:
                prefix += "-> CLASS: ";
                break;
            case CONSTRUCTOR:
                prefix += "-> CONSTRUCTOR: ";
                break;
            case METHOD:
                prefix += "-> METHOD: ";
                break;
            case ENUM:
                prefix += "-> ENUM: ";
                break;
            case OTHER:
                prefix += "OTHER: ";
                break;
        }

        mName.setText(prefix + stats.getName());
        mInsertions.setText("\t\t\t\t+ insertions: " + stats.getLinesAdded());
        mDeletions.setText("\t\t\t\t- deletions: " + stats.getLinesRemoved());
    }

}
