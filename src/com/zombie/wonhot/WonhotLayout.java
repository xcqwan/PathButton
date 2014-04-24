package com.zombie.wonhot;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class WonhotLayout extends RelativeLayout {
	//��ʼ����ʶ
	private boolean hasInit = false;
	//չ����ʶ
	private boolean areButtonsShowing = false;
	private Context mycontext;
	//��ť�м�ת��ͼ
	private ImageView cross;
	//������ť
	private RelativeLayout rlButton;
	//����������
	private WonhotAnimations myani;
	//�Ӱ�ť��
	private RelativeLayout childLayout;
	// �Ӱ��o��
	private List<LinearLayout> childList;
	
	//Ĭ��λ��
	private int alignCode = LayoutConstants.LEFTBOTTOM;
	private int alignHorizontal = CENTER_HORIZONTAL;
	private int alignVertical = ALIGN_PARENT_BOTTOM;
	private int duretime = 300;
	private int step = 100;

	public WonhotLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mycontext = context;
	}

	public WonhotLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mycontext = context;
	}

	public WonhotLayout(Context context) {
		super(context);
		this.mycontext = context;
	}
	
	/**
	 * ����λ��
	 * @param pcode
	 */
	public void setAlignCode(int pcode) {
		alignCode = pcode;
		switch (pcode) {
		case LayoutConstants.LEFTTOP :
			alignHorizontal = ALIGN_PARENT_LEFT;
			alignVertical = ALIGN_PARENT_TOP;
			break;
		case LayoutConstants.LEFTCENTER :
			alignHorizontal = ALIGN_PARENT_LEFT;
			alignVertical = CENTER_VERTICAL;
			break;
		case LayoutConstants.LEFTBOTTOM :
			alignHorizontal = ALIGN_PARENT_LEFT;
			alignVertical = ALIGN_PARENT_BOTTOM;
			break;
		case LayoutConstants.CENTERTOP :
			alignHorizontal = CENTER_HORIZONTAL;
			alignVertical = ALIGN_PARENT_TOP;
			break;
		case LayoutConstants.CENTERBOTTOM :
			alignHorizontal = CENTER_HORIZONTAL;
			alignVertical = ALIGN_PARENT_BOTTOM;
			break;
		case LayoutConstants.RIGHTTOP :
			alignHorizontal = ALIGN_PARENT_RIGHT;
			alignVertical = ALIGN_PARENT_TOP;
			break;
		case LayoutConstants.RIGHTCENTER :
			alignHorizontal = ALIGN_PARENT_RIGHT;
			alignVertical = CENTER_VERTICAL;
			break;
		case LayoutConstants.RIGHTBOTTOM :
			alignHorizontal = ALIGN_PARENT_RIGHT;
			alignVertical = ALIGN_PARENT_BOTTOM;
			break;
		case LayoutConstants.CENTER :
			alignHorizontal = CENTER_HORIZONTAL;
			alignVertical = CENTER_VERTICAL;
			break;
		default:
			//Ĭ������
			alignCode = LayoutConstants.CENTERBOTTOM;
			alignHorizontal = CENTER_HORIZONTAL;
			alignVertical = ALIGN_PARENT_BOTTOM;
			break;
		}
	}
	
	/**
	 * �����м䰴ť��ʽ
	 * @param showhideButtonId
	 * 			����ͼ
	 * @param crossId
	 * 			�м���תͼ
	 */
	public void setCenterStyle(int showhideButtonId, int crossId) {
		rlButton = new RelativeLayout(mycontext);
		rlButton.setLayoutParams(getWrapRoleLayout());
		rlButton.setBackgroundResource(showhideButtonId);
		cross = new ImageView(mycontext);
		cross.setImageResource(crossId);
		LayoutParams crosslps = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		crosslps.alignWithParent = true;
		crosslps.addRule(CENTER_IN_PARENT, RelativeLayout.TRUE);
		cross.setLayoutParams(crosslps);
		rlButton.addView(cross);
	}
	
	/**
	 * ����Ӱ�ť
	 * @param imgResid
	 * @return
	 */
	public int addItem(int imgResid) {
		if (childList == null) {
			childList = new ArrayList<LinearLayout>();
		}
		
		ImageView img = new ImageView(mycontext);

		img.setBackgroundResource(imgResid);
		img.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		LinearLayout itemLinear = new LinearLayout(mycontext);
		itemLinear.setLayoutParams(getWrapRoleLayout());
		// �˴�����ΪGONE
		itemLinear.setVisibility(View.INVISIBLE);
		itemLinear.setId((int)(Math.random() * 1000+ 1000));
		itemLinear.addView(img);
		
		childList.add(itemLinear);
		
		return itemLinear.getId();
	}

	public void init(int radius, final int durationMillis, final int step) {
		duretime = durationMillis;
		
		childLayout = new RelativeLayout(mycontext);

		for (LinearLayout item : childList) {
			childLayout.addView(item);
		}
		childLayout.setLayoutParams(getMatchRoleLayout());
		
		myani = new WonhotAnimations(childLayout, alignCode, radius);
		rlButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (areButtonsShowing) {
					cross.startAnimation(WonhotAnimations.getRotateAnimation(-270, 0, duretime + step * childList.size()));
					myani.startAnimationsOut(duretime, step);
					
				} else {
					cross.startAnimation(WonhotAnimations.getRotateAnimation(0, -270, duretime + step * childList.size()));
					myani.startAnimationsIn(duretime, step);
				}
				areButtonsShowing = !areButtonsShowing;
			}
		});
		cross.startAnimation(WonhotAnimations.getRotateAnimation(0, 360, 200));
		this.addView(childLayout);
		this.addView(rlButton);
		hasInit = true;
	}
	
	public LayoutParams getWrapRoleLayout() {
		LayoutParams layout = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layout.alignWithParent = true;
		layout.addRule(alignHorizontal, RelativeLayout.TRUE);
		layout.addRule(alignVertical, RelativeLayout.TRUE);
		return layout; 
	}
	
	public LayoutParams getMatchRoleLayout() {
		LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layout.alignWithParent = true;
		layout.addRule(alignHorizontal, RelativeLayout.TRUE);
		layout.addRule(alignVertical, RelativeLayout.TRUE);
		return layout; 
	}

	/**
	 * ����
	 */
	public void collapse() {
		myani.startAnimationsOut(duretime, step);
		cross.startAnimation(WonhotAnimations.getRotateAnimation(-270, 0, duretime));
		areButtonsShowing = false;
	}

	/**
	 * չ��
	 */
	public void expand() {
		myani.startAnimationsIn(duretime, step);
		cross.startAnimation(WonhotAnimations.getRotateAnimation(0, -270, duretime));
		areButtonsShowing = true;
	}

	/**
	 * �Ƿ��ʼ��
	 */
	public boolean isInit() {
		return hasInit;
	}

	/**
	 * �Ƿ�չ��
	 */
	public boolean isShow() {
		return areButtonsShowing;
	}

	/**
	 * �����Ӱ�ť�ĵ���¼�
	 */
	public void setButtonsOnClickListener(final OnClickListener l) {

		if (childList != null) {
			for (LinearLayout child : childList) {
				if (child != null)
					child.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(final View view) {
							// �˴���������¼����簴ť����������ز˵�
							collapse();
							l.onClick(view);
						}
					});
			}
		}
	}
}
