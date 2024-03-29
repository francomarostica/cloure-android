package com.grupomarostica.cloure.Modules.bank_checks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.grupomarostica.cloure.Core.AvailableCommand;
import com.grupomarostica.cloure.Core.ModuleInfo;
import com.grupomarostica.cloure.R;

import java.util.ArrayList;

public class fragment_bank_checks extends Fragment {
    private ListView listView;
    private BankChecksAdapter adapter;
    private ArrayList<BankCheck> registers;
    private RelativeLayout loaderLayout;
    private ConstraintLayout emptyLayout;
    private Handler handler = new Handler();
    private ModuleInfo moduleInfo;
    private boolean ModuleInitializing = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_panel_scroll,container, false);

        listView = rootView.findViewById(R.id.fragment_panel_scroll_items);
        loaderLayout = rootView.findViewById(R.id.loader);
        emptyLayout = rootView.findViewById(R.id.fragment_scroll_no_results);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BankCheck item = (BankCheck) parent.getAdapter().getItem(position);
                edit(item);
            }
        });

        registerForContextMenu(listView);
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        /*
        for(int i=0;i<moduleInfo.globalCommands.size(); i++){
            GlobalCommand gc = moduleInfo.globalCommands.get(i);
            menu.add(Menu.NONE, gc.Id, Menu.NONE, gc.Title);
        }
        */
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case 1:
                //Agregar
                //Intent intent = new Intent(getActivity(), UserGroupAddActivity.class);
                //startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        BankCheck item = (BankCheck) listView.getItemAtPosition(info.position);
        for (int i=0;i<item.AvailableCommands.size();i++){
            AvailableCommand availableCommand = item.AvailableCommands.get(i);
            menu.add(Menu.NONE, availableCommand.Id, Menu.NONE, availableCommand.Title);
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        BankCheck itemSelected = adapter.getItem(index);

        switch(item.getItemId()) {
            case 1:
                edit(itemSelected);
                return true;
            case 2:
                delete(itemSelected.Id);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        load_data();
    }

    private void edit(BankCheck item){
        //Intent intent = new Intent(getActivity(), UserGroupAddActivity.class);
        //intent.putExtra("id", userGroup.Id);
        //startActivity(intent);
    }

    private void delete(final int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle("Borrar");
        builder.setMessage("¿Está seguro que desea eliminar este registro?");
        builder.setPositiveButton("Si",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*
                        ArrayList<CloureParam> params = new ArrayList<>();
                        params.add(new CloureParam("module", "countries"));
                        params.add(new CloureParam("topic", "borrar"));
                        params.add(new CloureParam("id", Integer.toString(id)));

                        CloureSDK cloureSDK = new CloureSDK();
                        try{
                            String res = cloureSDK.execute(params);
                            JSONObject object = new JSONObject(res);
                            String error = object.getString("Error");
                            if(error.equals("")){
                                load_data();
                            } else {
                                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex){
                            ex.printStackTrace();
                        }
                        */
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void load_data(){
        BackgroundLoad backgroundLoad = new BackgroundLoad();
        new Thread(backgroundLoad).start();
    }

    class BackgroundLoad implements Runnable{
        @Override
        public void run() {
            registers = BankChecks.get_list("");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try{
                        adapter = new BankChecksAdapter(getActivity(), registers);
                        if(registers.size()>0){
                            listView.setAdapter(adapter);
                            listView.setVisibility(View.VISIBLE);
                            emptyLayout.setVisibility(View.INVISIBLE);
                        } else {
                            listView.setVisibility(View.INVISIBLE);
                            emptyLayout.setVisibility(View.VISIBLE);
                        }
                        loaderLayout.setVisibility(View.INVISIBLE);
                        ModuleInitializing=false;
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }
    }
}
