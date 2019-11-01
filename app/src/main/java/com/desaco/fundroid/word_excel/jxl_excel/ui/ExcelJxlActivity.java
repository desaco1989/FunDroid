package com.desaco.fundroid.word_excel.jxl_excel.ui;


import java.io.InputStream;
import java.util.List;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.desaco.fundroid.R;
import com.desaco.fundroid.word_excel.jxl_excel.adapter.ListViewAdapter;
import com.desaco.fundroid.word_excel.jxl_excel.bean.LocationPoint;
import com.desaco.fundroid.word_excel.jxl_excel.tool.DocumentParserTools;

public class ExcelJxlActivity extends Activity {
	
	private ListView listView;
	private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_excel_activity_main);
        initView();
    }

	private void initView() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.listview);
		adapter = new ListViewAdapter(this, getData());
		listView.setAdapter(adapter);
	}
	
	private List<LocationPoint> getData(){
		//打开raw下的文件 locations
		InputStream is = this.getResources().openRawResource(R.raw.locations2);
		return DocumentParserTools.parseExcel(is);
	}
}
