package com.general.comtop.signature.utility;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.general.comtop.signature.R;
import com.general.comtop.signature.view.SignatureView;

/**
 * 电子签名组件帮助类
 * 
 * @author shiyaofang
 * 
 */
public class SignatureHelper implements View.OnClickListener {
	/** 上下文对象 */
	private Context context;
	/** 电子签名对话框 */
	private Dialog dialog;
	/** 生产电子签名成功之后的回调接口实例 */
	private IOnSaveSignatureSuccessListener iSaveSuccessListener;
	/** 点击确定按钮时的回调接口实例 */
	private IOnConfirmClickListener iConfirmClickListener;
	/** 删除电子签名成功之后的回调接口实例 */
	private IOnDeleteSignatureSuccessListener iDeleteSuccessListener;
	/** 电子签名View */
	private SignatureView sigView;
	/** 重写按钮 */
	private Button btnReset;
	/** 取消按钮 */
	private Button btnCancel;
	/** 确定按钮 */
	private Button btnConfirm;
	/** 电子签名辅助类实例 */
	private static SignatureHelper sigHelper;
	/** 电子签名的相对路径*/
	private String saveSignaturePath="/signature";
	
	
	/**
	 * 单例的构造函数
	 */
	private SignatureHelper() {

	}

	/**
	 * 获取电子签名辅助类
	 * 
	 * @param context
	 * @return
	 */
	public static SignatureHelper getInstance(Context context) {
		if (null == sigHelper) {
			sigHelper = new SignatureHelper();
		}
		sigHelper.context = context;
		return sigHelper;
	}
	
	public String getSaveSignaturePath() {
		return saveSignaturePath;
	}

	public void setSaveSignaturePath(String saveSignaturePath) {
		this.saveSignaturePath = saveSignaturePath;
	}
	
	/** 生成电子签名成功之后的回调接口 */
	public interface IOnSaveSignatureSuccessListener {
		void onSaveSignatureSuccess(String url);
	}

	/** 点击确定电子签名时的回调函数 */
	public interface IOnConfirmClickListener {
		void onConfirmClick();
	}

	/** 删除电子签名成功之后的回调接口 */
	public interface IOnDeleteSignatureSuccessListener {
		void onDeleteSignatureSuccess(List<String> urlList);
	}

	public void setOnSaveSuccessListener(IOnSaveSignatureSuccessListener iSaveSuccessListener) {
		this.iSaveSuccessListener = iSaveSuccessListener;
	}

	public void setOnConfirmClickListener(IOnConfirmClickListener iConfirmClickListener) {
		this.iConfirmClickListener = iConfirmClickListener;
	}

	public void setOnDeleteSuccessListener(IOnDeleteSignatureSuccessListener iDeleteSuccessListener) {
		this.iDeleteSuccessListener = iDeleteSuccessListener;
	}

	/**
	 * 显示电子签名对话框
	 */
	public void showSigDialog() {
		// 防止在当前实例中弹出多个对话框
		if (dialog == null) {
			dialog = new Dialog(context, R.style.lib_dialog_sig);
			dialog.setContentView(R.layout.lib_dialog_signature);
		}
		if (sigView == null) {
			sigView = (SignatureView) dialog.findViewById(R.id.dialog_signature_view);
		}
		btnConfirm = (Button) dialog.findViewById(R.id.btn_dialog_signature_confirm);
		btnReset = (Button) dialog.findViewById(R.id.btn_dialog_signature_reset);
		btnCancel = (Button) dialog.findViewById(R.id.btn_dialog_signature_cancel);
		initListener();
		dialog.show();
	}

	/**
	 * 初始化监听器
	 */
	private void initListener() {
		btnReset.setTag(android.R.string.no);
		btnReset.setOnClickListener(this);
		btnCancel.setTag(android.R.string.cancel);
		btnCancel.setOnClickListener(this);
		btnConfirm.setTag(android.R.string.ok);
		btnConfirm.setOnClickListener(this);
	}

	/**
	 * 保存电子签名图片
	 * 
	 * @param savePath
	 *            保存的相对路径
	 * @throws Exception
	 */
	public String saveSigPic(String savePath) {
		String url = null;
		if (sigView != null) {
			url = sigView.saveBitmapToPngFile(savePath);
		}
		// 生产电子签名之后，执行回调，并且关闭当前签名的对话框
		if (null != url && url.length() > 0) {
			if (null != iSaveSuccessListener) {
				iSaveSuccessListener.onSaveSignatureSuccess(url);
			}
			dismissDialog();
		}
		return url;
	}

	/**
	 * 删除电子签名图片
	 * 
	 * @param url
	 *            图片的绝对路径
	 */
	public void deleteSigPic(List<String> urlList) {
		if (null != iDeleteSuccessListener) {
			iDeleteSuccessListener.onDeleteSignatureSuccess(urlList);
		}
	}

	/**
	 * 设置标准的图片参数
	 * 
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 */
	public void setStandardBitmapSize(int width, int height) {
		if (sigView != null) {
			sigView.setStandardBitmapSize(width, height);
		}
	}

	/**
	 * 销毁电子签名对话框
	 */
	public void dismissDialog() {
		if (dialog != null && sigView != null) {
			sigView.clearSignature();
			sigView = null;
			dialog.dismiss();
			dialog=null;
		}
	}

	@Override
	public void onClick(View v) {
		switch ((Integer) v.getTag()) {
		// 重写
		case android.R.string.no:
			if (null != sigView) {
				sigView.clearSignature();
			}
			break;
		// 取消
		case android.R.string.cancel:
			dismissDialog();
			break;
		// 确定
		case android.R.string.ok:
			if (null != iConfirmClickListener) {
				iConfirmClickListener.onConfirmClick();
			}
			break;
		default:
			break;
		}
	}
}
