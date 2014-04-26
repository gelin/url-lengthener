package ru.gelin.lengthener.android;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ListView;

/**
 *  A Fragment which allows to add and remove domains (as strings) to/from the list.
 *  This version of the fragment works on devices with API < 11
 */
public class DomainsListFragmentCompat extends DomainsListFragmentBase implements DialogInterface.OnClickListener {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ListView list = getListView();
//        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            list.setMultiChoiceModeListener(new ChoiceListener());
//        }
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                list.setItemChecked(position, true);
//            }
//        });
    }

//    private class ChoiceListener implements AbsListView.MultiChoiceModeListener {
//
//        @Override
//        public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked) {
//            actionMode.setTitle(String.valueOf(getListView().getCheckedItemCount()));
//        }
//
//        @Override
//        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
//            MenuInflater inflater = actionMode.getMenuInflater();
//            inflater.inflate(R.menu.domains_context, menu);
//            return true;
//        }
//
//        @Override
//        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
//            return false;   //nothing to do
//        }
//
//        @Override
//        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.action_delete:
//                    deleteSelectedDomains();
//                    actionMode.finish();
//                    return true;
//            }
//            return false;
//        }
//
//        @Override
//        public void onDestroyActionMode(ActionMode actionMode) {
//            //nothing to do
//        }
//    }

}
