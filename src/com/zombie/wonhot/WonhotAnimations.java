package com.zombie.wonhot;

import java.util.ArrayList;
import java.util.List;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.view.ViewPropertyAnimator;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

public class WonhotAnimations {
	//半径
	public final int R; 
	// 位置编号
	private int pc; 
	//父组件
	private ViewGroup clayout;
	//按钮个数
	private final int amount;
	//子按钮排列弧度
	private double fullangle = 180.0;
	//排列方向
	private byte xOri = 1, yOri = 1; 
	//是否展开
	private boolean isOpen = false;
	private List<ViewPropertyAnimator> viewAnimators = new ArrayList<ViewPropertyAnimator>();

	/**
	 * 
	 * @param comlayout
	 *            组件
	 * @param poscode
	 *             位置编号RIGHTBOTTOM、CENTERBOTTOM、LEFTBOTTOM、LEFTCENTER、
	 *            LEFTTOP、CENTERTOP、RIGHTTOP、RIGHTCENTER
	 * @param radius
	 *            半径
	 */
	public WonhotAnimations(ViewGroup comlayout, int poscode, int radius) {
		this.pc = poscode;
		this.clayout = comlayout;
		this.amount = clayout.getChildCount();
		this.R = radius;

		// 初始化动画，每个view对应一个animator
		for (int i = 0; i < amount; i++) {
			View childAt = clayout.getChildAt(i);
			ViewPropertyAnimator anim = animate(childAt);
			viewAnimators.add(anim);
		}
		
		switch (poscode) {
		case LayoutConstants.RIGHTBOTTOM:
			fullangle = 90;
			xOri = -1;
			yOri = -1;
			break;
		case LayoutConstants.CENTERBOTTOM:
			fullangle = 180;
			xOri = -1;
			yOri = -1;
			break;
		case LayoutConstants.LEFTBOTTOM:
			fullangle = 90;
			xOri = 1;
			yOri = -1;
			break;
		case LayoutConstants.LEFTCENTER:
			fullangle = 180;
			xOri = 1;
			yOri = -1;
			break;
		case LayoutConstants.LEFTTOP:
			fullangle = 90;
			xOri = 1;
			yOri = 1;
			break;
		case LayoutConstants.CENTERTOP:
			fullangle = 180;
			xOri = -1;
			yOri = 1;
			break;
		case LayoutConstants.RIGHTTOP:
			fullangle = 90;
			xOri = -1;
			yOri = 1;
			break;
		case LayoutConstants.RIGHTCENTER:
			fullangle = 180;
			xOri = -1;
			yOri = -1;
			break;
		case LayoutConstants.CENTER:
			fullangle = 360;
			xOri = 1;
			yOri = 1;
			break;
		default:
			break;
		}
	}

	private class AnimListener implements AnimatorListener {

		private View target;

		public AnimListener(View _target) {
			target = _target;
		}

		@Override
		public void onAnimationStart(Animator animation) {

		}

		@Override
		public void onAnimationEnd(Animator animation) {
			if (!isOpen) {
				target.setVisibility(View.INVISIBLE);
			}
		}

		@Override
		public void onAnimationCancel(Animator animation) {
			
		}

		@Override
		public void onAnimationRepeat(Animator animation) {

		}
	}

	/**
	 * 弹出动画
	 * 
	 * @param durationMillis
	 *            动画时长
	 */
	public void startAnimationsIn(int durationMillis, int step) {
		isOpen = true;
		for (int i = 0; i < clayout.getChildCount(); i++) {
			final LinearLayout inoutimagebutton = (LinearLayout) clayout.getChildAt(i);

			double offangle = fullangle / (amount - 1);
			if (pc == LayoutConstants.CENTER) {
				offangle = fullangle / amount;
			}

			final double deltaY, deltaX;
			if (pc == LayoutConstants.LEFTCENTER || pc == LayoutConstants.RIGHTCENTER) {
				deltaX = Math.sin(offangle * i * Math.PI / 180) * R;
				deltaY = Math.cos(offangle * i * Math.PI / 180) * R;
			} else {
				deltaY = Math.sin(offangle * i * Math.PI / 180) * R;
				deltaX = Math.cos(offangle * i * Math.PI / 180) * R;
			}
			Log.i("test", "i : " + i + ",x : " + deltaX + ",y : " + deltaY);

			ViewPropertyAnimator viewPropertyAnimator = viewAnimators.get(i);
			viewPropertyAnimator.setListener(null);
			viewPropertyAnimator.setDuration(durationMillis + step * i);

			inoutimagebutton.setVisibility(View.VISIBLE);
			viewPropertyAnimator.x((float) (inoutimagebutton.getLeft() + xOri * deltaX)).y((float) (inoutimagebutton.getTop() + yOri * deltaY));

		}
	}

	/**
	 * 收缩动画
	 * 
	 * @param durationMillis
	 *            动画时长
	 */
	public void startAnimationsOut(int durationMillis, int step) {
		isOpen = false;
		for (int i = 0; i < clayout.getChildCount(); i++) {
			final LinearLayout inoutimagebutton = (LinearLayout) clayout.getChildAt(i);
			ViewPropertyAnimator viewPropertyAnimator = viewAnimators.get(i);
			viewPropertyAnimator.x((float) inoutimagebutton.getLeft()).y((float) inoutimagebutton.getTop());
			viewPropertyAnimator.setListener(new AnimListener(inoutimagebutton));
			viewPropertyAnimator.setDuration(durationMillis + step * (clayout.getChildCount() - i - 1));
		}

	}

	public int getPosCode() {
		return this.pc;
	}

	/**
	 * 自转动画
	 * 
	 * @param fromDegrees
	 *            开始角度
	 * @param toDegrees
	 *            结束角度
	 * @param durationMillis
	 *            动画时长
	 */
	public static Animation getRotateAnimation(float fromDegrees, float toDegrees, int durationMillis) {
		RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotate.setDuration(durationMillis);
		rotate.setFillAfter(true);
		return rotate;
	}

}