 allannotation

实现安卓activity，fragment的全注解，并且实现了20多种view事件的全注解实现
,包括事件总线的实现，例子如下
@Bus
@ViewId(R.layout.main_fragment_center)
public class Main_Fragment_Center extends BaseFragment {

	@ViewId(R.id.verify_status)
	private TextView verify_status;

	@ViewId(R.id.score)
	private LinearLayout score;

	@ViewId(R.id.bill)
	private LinearLayout bill;

	@ViewId(R.id.faq)
	private LinearLayout faq;

	@ViewId(R.id.about_us)
	private LinearLayout about_us;

	@ViewId(R.id.check_new)
	private LinearLayout check_new;

	@OnClick(R.id.verify_status)
	private void verify(View v) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), Activity_Verify.class);
		startActivity(intent);
	}

	@OnClick(R.id.score)
	private void score(View v) {
		Intent intent_Score = new Intent();
		intent_Score.setClass(getActivity(), Activity_Score.class);
		startActivity(intent_Score);
	}

	@OnClick(R.id.bill)
	private void bill(View v) {
		Intent intent_Bill = new Intent();
		intent_Bill.setClass(getActivity(), Activity_Bill.class);
		startActivity(intent_Bill);
	}

	@OnClick(R.id.faq)
	private void faq(View v) {
		Intent intent_Faq = new Intent();
		intent_Faq.setClass(getActivity(), Activity_Faq.class);
		startActivity(intent_Faq);
	}

	@OnClick(R.id.about_us)
	private void about(View v) {
		Intent intent_AboutUs = new Intent();
		intent_AboutUs.setClass(getActivity(), Activity_AboutUs.class);
		startActivity(intent_AboutUs);
	}

	@OnClick(R.id.check_new)
	private void check(View v) {
		Intent intent_CheckNew = new Intent();
		intent_CheckNew.setClass(getActivity(), Activity_CheckNew.class);
		startActivity(intent_CheckNew);
	}

	@Subscribe(Bus2.UI_THREAD)
	public void a(String name) {
		// verify_status.setText(name);
		Log.i("debug", "===========" + Thread.currentThread().getName());
	
