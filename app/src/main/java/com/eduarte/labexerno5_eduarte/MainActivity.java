package com.eduarte.labexerno5_eduarte;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String[] companyNames, companyCountry, companyIndustry, companyCEO;
    ListView lists;
    int[] companyLogo = {R.drawable.icbc, R.drawable.jp, R.drawable.chinacon, R.drawable.agri, R.drawable.boa, R.drawable.apple, R.drawable.ping, R.drawable.boc ,R.drawable.shell, R.drawable.wells, R.drawable.exxon, R.drawable.att, R.drawable.samsung, R.drawable.citi};
    ArrayList<Company> companyList = new ArrayList<>();
    String[] companyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("TOP GLOBAL COMPANIES");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        companyNames = getResources().getStringArray(R.array.companyNames);
        companyCountry = getResources().getStringArray(R.array.companyCountry);
        companyIndustry = getResources().getStringArray(R.array.companyIndustry);
        companyCEO = getResources().getStringArray(R.array.companyCEO);
        companyInfo = getResources().getStringArray(R.array.companyInfo);

        for(int i=0; i < companyNames.length; i++){
            companyList.add(new Company(companyLogo[i], companyNames[i], "Country: " + companyCountry[i], "Industry: " + companyIndustry[i], "CEO: " + companyCEO[i]));
        }

        lists = findViewById(R.id.listView);

        ArrayAdapter androidArrayAdapter = new CompanyAdaptors(this, R.layout.item, companyList);

        lists.setAdapter(androidArrayAdapter);
        lists.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        try{
            final File folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(folder, "companyInformation.txt");
            final AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
            FileOutputStream fos = new FileOutputStream(file);
            String choice = "Company: " + companyList.get(position).getCompanyNames() + "\n" + companyList.get(position).getCompanyCountry() + "\n" + companyList.get(position).getCompanyIndustry() + "\n" + companyList.get(position).getCompanyCEO();
            fos.write(choice.getBytes());
            fos.close();
            myDialog.setTitle(companyList.get(position).getCompanyNames());
            myDialog.setIcon(companyList.get(position).getCompanyLogo());
            myDialog.setMessage(companyInfo[position]);
            myDialog.setNeutralButton("Close", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    showToast();
                }
            });
            myDialog.create().show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void showToast(){
        try{
            FileInputStream f;
            f = new FileInputStream (new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/companyInformation.txt"));
            int i;
            String str = "";
            while ((i = f.read()) != -1){
                str += Character.toString((char) i);
            }
            f.close();
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
