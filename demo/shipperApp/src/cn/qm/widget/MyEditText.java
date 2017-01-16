package cn.qm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cn.qm.shipperapp.R;

public class MyEditText extends RelativeLayout {
	public static final int INPUTTYPE_PAWWOWRD = 1;
	public static final int INPUTTYPE_NORMAL = 0;
	public static final int INPUTTYPE_NUMBER = 2;
	public static final int ACTIONDONE = 0;
	public static final int ACTIONGO = 1;
	public static final int ACTIONSEARCH = 2;
	public static final int ACTIONNEXT = 3;
	private EditText editText;
	private ImageView clear;

	public EditText getEditText() {
		return editText;
	}

	public void setEditText(EditText editText) {
		this.editText = editText;
	}

	public MyEditText(Context context) {
		this(context, null);
	}

	public MyEditText(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	private void init(AttributeSet attrs) {

		LayoutInflater.from(getContext()).inflate(R.layout.mycar_edittext, this);
		editText = (EditText) findViewById(R.id.mycar_edittext);
		clear = (ImageView) findViewById(R.id.mycar_imageview_clear);
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String val = s.toString();
				if ("".equals(val.trim())) {
					clear.setVisibility(View.INVISIBLE);
				} else {
					clear.setVisibility(View.VISIBLE);
				}
			}
		});
		clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				editText.getText().clear();
			}
		});

		if (attrs == null) {
			return;// 跳出该方法，不执行下面语句
		}
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyEditText);
		int N = a.getIndexCount();
		for (int i = 0; i < N; i++) {
			int index = a.getIndex(i);
			switch (index) {
			case R.styleable.MyEditText_my_text:
				setText(a.getString(index));
				break;
			case R.styleable.MyEditText_my_singleLine:
				editText.setSingleLine(a.getBoolean(index, false));
				break;
			case R.styleable.MyEditText_my_hideClear:
				if (a.getBoolean(index, false)) {
					clear.setVisibility(View.INVISIBLE);
				} else {
					clear.setVisibility(View.VISIBLE);
				}
				break;
			case R.styleable.MyEditText_my_hint:
				setHint(a.getString(index));
				break;
			case R.styleable.MyEditText_my_inputType:
				setTextType(a.getInt(index, 0));
				break;
			case R.styleable.MyEditText_my_imeOptions:
				setImeOptions(a.getInt(index, 0));
				break;
			// 限制数据框的长度
			case R.styleable.MyEditText_my_maxLength:
				int max = a.getInt(index, -1);
				if (max != -1) {// 若果默认值不等于-1，设置它的长度
					setMaxlength(max);
				}
				break;
			case R.styleable.MyEditText_my_textSize:
				setTextSize(a.getString(index));
				break;

			default:
				break;
			}
		}
	}

	private void setTextSize(String s) {
		String size = s.substring(0, s.length() - 2);
		editText.setTextSize(Float.parseFloat(size));
	}

	/**
	 * 设置输入框的长度
	 * 
	 * @param maxLength
	 *            最大长度
	 */
	public void setMaxlength(int maxLength) {
		editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(maxLength) });
	}

	/**
	 * 获取文本
	 */
	public String getText() {
		return editText.getText().toString().trim();
	}

	/**
	 * 设置一下它的明文、密文
	 * 
	 * @param type
	 */
	public void setTextType(int type) {
		switch (type) {
		case INPUTTYPE_NORMAL:
			editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
			break;
		case INPUTTYPE_PAWWOWRD:
			editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
			break;
		case INPUTTYPE_NUMBER:
			editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
			break;

		default:
			break;
		}
	}

	/**
	 * 设置一下它的明文、密文
	 * 
	 * @param type
	 */
	public void setImeOptions(int type) {
		switch (type) {

		case ACTIONDONE:
			editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
			break;
		case ACTIONGO:
			editText.setImeOptions(EditorInfo.IME_ACTION_GO);
			break;
		case ACTIONSEARCH:
			editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
			break;
		case ACTIONNEXT:
			editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
			break;

		default:
			break;
		}
	}

	/**
	 * 设置编辑框文本内容
	 * 
	 * @param text
	 *            要设置的文本内容
	 */
	public void setText(String text) {
		editText.setText(text);
	}

	/**
	 * 设置提示
	 * 
	 * @param text
	 *            要设置的文本内容
	 */
	public void setHint(String text) {
		editText.setHint(text);
	}

}
