package com.testnative;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.text).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String path = "/data/data/1111111" + getPackageName();
				String cmd1 = path + "/lib/libdaemon.so";
				String cmd2 = path + "/daemon";
				String cmd3 = "chmod 777 " + cmd2;
				String cmd4 = "dd if=" + cmd1 + " of=" + cmd2;
				RootCommand(cmd4); // ����lib/libtest.so����һ��Ŀ¼,ͬʱ����Ϊtest.
				RootCommand(cmd3); // �ı�test������,�����Ϊ��ִ��
				RootCommand(cmd2); // ִ��test����.
			}
		});
	}

	public boolean RootCommand(String command) {
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("sh"); // ���shell.
			DataInputStream inputStream = new DataInputStream(
					process.getInputStream());
			DataOutputStream outputStream = new DataOutputStream(
					process.getOutputStream());

			outputStream.writeBytes("cd /data/data/" + getPackageName() + "\n"); // ��֤��command���Լ�������Ŀ¼��ִ��,����Ȩ��д�ļ�����ǰĿ¼

			outputStream.writeBytes(command + " &\n"); // �ó����ں�̨���У�ǰ̨���Ϸ���
			outputStream.writeBytes("exit\n");
			outputStream.flush();
			process.waitFor();

			byte[] buffer = new byte[inputStream.available()];
			inputStream.read(buffer);
			String s = new String(buffer);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
