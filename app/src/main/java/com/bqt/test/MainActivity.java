package com.bqt.test;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bqt.test.protomodel.Person;
import com.bqt.test.protomodel.PersonEntity;
import com.google.gson.Gson;
import com.google.protobuf.InvalidProtocolBufferException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends ListActivity {
	
	private static String protoFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_proto";
	private static String jsonfilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_json";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] array = {"以proto格式保存对象",
				"解析proto方式保存的对象内容",
				"以Json格式保存对象",
				"解析son方式保存的对象内容",
				"",};
		setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Arrays.asList(array)));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		switch (position) {
			case 0:
				PersonEntity.Person protoPerson = PersonEntity.Person.newBuilder()
						.setId(3)
						.setName("zhangsan")
						.setEmail("test@qq.com")
						.build();
				Log.i("bqt", "原始的对象内容：" + protoPerson.toString());
				writeToFile(protoPerson.toByteArray(), protoFilePath);//以proto格式保存对象，保存的长度=25
				break;
			case 1:
				byte[] bytes = readFromFile(protoFilePath);
				try {
					PersonEntity.Person protoPerson2 = PersonEntity.Person.parseFrom(bytes);//解析proto方式保存的对象内容
					Log.i("bqt", protoPerson2.toString());
				} catch (InvalidProtocolBufferException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				Person jsonPerson = Person.newBuilder().id(3).name("zhangsan").email("test@qq.com").build();
				Log.i("bqt", "原始的对象内容：" + jsonPerson.toString());
				writeToFile(new Gson().toJson(jsonPerson).getBytes(), jsonfilePath);//以Json格式保存对象，保存的长度=48
				break;
			case 3:
				byte[] bytes2 = readFromFile(jsonfilePath);
				String json = new String(bytes2);
				Person jsonPerson2 = new Gson().fromJson(json, Person.class);
				Log.i("bqt", jsonPerson2.toString());
				break;
		}
	}
	
	private byte[] readFromFile(String path) {
		try {
			FileInputStream input = new FileInputStream(path);
			byte[] personBytes = new byte[(int) new File(path).length()];
			input.read(personBytes);
			input.close();
			Log.i("bqt", "解析的长度=" + personBytes.length);
			return personBytes;
		} catch (IOException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}
	
	public static void writeToFile(byte[] content, String path) {
		Log.i("bqt", "保存的长度=" + content.length);
		try {
			FileOutputStream out = new FileOutputStream(path,false);
			out.write(content);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}