package com.zombie.test;

import com.zombie.wonhot.LayoutConstants;
import com.zombie.wonhot.WonhotLayout;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	private WonhotLayout clayout;
	private int composer_camera;
	private int composer_music;
	private int composer_place;
	private int composer_sleep;
	private int composer_thought;
	private int composer_with;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// ÒýÓÃ¿Ø¼þ
		clayout = (WonhotLayout) findViewById(R.id.test);
		clayout.setAlignCode(LayoutConstants.LEFTBOTTOM);
		clayout.setCenterStyle(R.drawable.composer_button, R.drawable.composer_icn_plus);
		composer_camera = clayout.addItem(R.drawable.composer_camera);
		composer_music = clayout.addItem(R.drawable.composer_music);
		composer_place = clayout.addItem(R.drawable.composer_place);
		composer_sleep = clayout.addItem(R.drawable.composer_sleep);
		composer_thought = clayout.addItem(R.drawable.composer_thought);
		composer_with = clayout.addItem(R.drawable.composer_with);
		
		clayout.init(200, 500, 100);
		OnClickListener clickit = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == composer_camera) {
					System.out.println("composer_camera");
				} else if (v.getId() == composer_music) {
					System.out.println("composer_music");
				} else if (v.getId() == composer_place) {
					System.out.println("composer_place");
				} else if (v.getId() == composer_sleep) {
					System.out.println("composer_sleep");
				} else if (v.getId() == composer_thought) {
					System.out.println("composer_thought");
				} else if (v.getId() == composer_with) {
					System.out.println("composer_with");
				}
			}
		};
		clayout.setButtonsOnClickListener(clickit);
		
		initTestLayout(R.id.test1, LayoutConstants.CENTERBOTTOM);
		initTestLayout(R.id.test2, LayoutConstants.CENTERTOP);
		initTestLayout(R.id.test3, LayoutConstants.LEFTTOP);
		initTestLayout(R.id.test4, LayoutConstants.LEFTCENTER);
		initTestLayout(R.id.test5, LayoutConstants.RIGHTTOP);
		initTestLayout(R.id.test6, LayoutConstants.RIGHTCENTER);
		initTestLayout(R.id.test7, LayoutConstants.RIGHTBOTTOM);
		initTestLayout(R.id.test8, LayoutConstants.CENTER);
	}
	
	private void initTestLayout(int resid, int position) {
		WonhotLayout test = (WonhotLayout) findViewById(resid);
		test.setAlignCode(position);
		test.setCenterStyle(R.drawable.composer_button, R.drawable.composer_icn_plus);
		test.addItem(R.drawable.composer_camera);
		test.addItem(R.drawable.composer_music);
		test.addItem(R.drawable.composer_place);
		test.addItem(R.drawable.composer_sleep);
		test.addItem(R.drawable.composer_thought);
		test.addItem(R.drawable.composer_with);

		test.init(200, 500, 100);
		
		OnClickListener clickit = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v.getId() == composer_camera) {
					System.out.println("composer_camera");
				} else if (v.getId() == composer_music) {
					System.out.println("composer_music");
				} else if (v.getId() == composer_place) {
					System.out.println("composer_place");
				} else if (v.getId() == composer_sleep) {
					System.out.println("composer_sleep");
				} else if (v.getId() == composer_thought) {
					System.out.println("composer_thought");
				} else if (v.getId() == composer_with) {
					System.out.println("composer_with");
				}
			}
		};
		test.setButtonsOnClickListener(clickit);
	}

}
