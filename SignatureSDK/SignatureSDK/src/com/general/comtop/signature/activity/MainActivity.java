package com.general.comtop.signature.activity;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.general.comtop.signature.R;
import com.general.comtop.signature.utility.SignatureHelper;
import com.general.comtop.signature.utility.SignatureHelper.IOnDeleteSignatureSuccessListener;
import com.general.comtop.signature.utility.SignatureHelper.IOnSaveSignatureSuccessListener;
import com.general.comtop.signature.view.SignatureListView;

/**
 * 实现保存电子签名成功的接口和删除电子签名成功的接口
 * 
 * @author shiyaofang
 * 
 */
public class MainActivity extends Activity implements IOnSaveSignatureSuccessListener, IOnDeleteSignatureSuccessListener {

	/** 电子签名列表 */
	private SignatureListView sigListView;
	/** 电子签名的辅助类 */
	private SignatureHelper sigHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lib_activity_main);
		initSignature();
	}

	/**
	 * 初始化电子签名
	 */
	private void initSignature() {
		// 初始化电子签名的辅助类
		sigHelper = SignatureHelper.getInstance(this);
		// 设置保存的电子签名的相对路径(有默认值)
		sigHelper.setSaveSignaturePath("/hello_sig");

		// 设置保存成功和删除成功的回调函数
		sigHelper.setOnSaveSuccessListener(this);
		sigHelper.setOnDeleteSuccessListener(this);

		// 初始化电子签名列表
		sigListView = (SignatureListView) findViewById(R.id.sig_listview);
		// 设置电子签名列表的adapter(数据源根据具体的业务情况来获取，这里是默认获取sd卡中的电子签名)
		sigListView.setSignatureAdapter(sigListView.getSignatureListFromSDCard());
	}

	/**
	 * 删除成功的回调方法。可以在这里做你想要的任何操作，比如删除数据库中的记录或删除文件等等
	 */
	@Override
	public void onDeleteSignatureSuccess(List<String> urlList) {
		for (String url : urlList) {
			File file = new File(url);
			file.delete();
		}
	}

	/**
	 * 保存成功的回调方法。可以在这里做你想要的任何操作，比如插入到数据库或写入文件等等
	 */
	@Override
	public void onSaveSignatureSuccess(String url) {
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 界面退出的时候一定要将对话框清除掉！！
		sigHelper.dismissDialog();
	}
}
