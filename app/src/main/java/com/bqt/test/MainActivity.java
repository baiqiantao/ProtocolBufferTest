package com.bqt.test;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bqt.test.protomodel.PersonEntity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends ListActivity {
	
	private static String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "test_proto";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] array = {"",
				"",
				"",
				"",
				"",};
		setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Arrays.asList(array)));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		switch (position) {
			case 0:
				testPerson();
				break;
			case 1:
				
				break;
			case 2:
				
				break;
			default:
				break;
		}
	}
	
	private void testPerson() {
		PersonEntity.Person person = PersonEntity.Person.newBuilder()
				.setId(3)
				.setName("zhangsan")
				.setEmail("test@qq.com")
				.build();
		Log.i("bqt", "原始的对象内容：" + person.toString());
		
		writeToFile(person.toByteArray());
		
		PersonEntity.Person parsePerson = readPersonFromFile();
		Log.i("bqt", "解析的对象内容：" + parsePerson.toString());
	}
	
	private PersonEntity.Person readPersonFromFile() {
		try {
			FileInputStream input = new FileInputStream(filePath);
			byte[] personBytes = new byte[(int) new File(filePath).length()];
			input.read(personBytes);
			input.close();
			Log.i("bqt", "解析的长度=" + personBytes.length);
			return PersonEntity.Person.parseFrom(personBytes);
		} catch (IOException e) {
			e.printStackTrace();
			return PersonEntity.Person.newBuilder().build();
		}
	}
	
	public static void writeToFile(byte[] content) {
		Log.i("bqt", "保存的长度=" + content.length);
		
		try {
			FileOutputStream out = new FileOutputStream(filePath);
			out.write(content);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}