package com.calendar.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;

/**
 * 日历控件头部绘制类
 * @Descriptio.n: 日历控件头部绘制类

 * @FileName: DateWidgetDayHeader.java 

 * @Package com.calendar.demo 

 * @Author Hanyonglu

 * @Date 2012-3-19 下午03:28:39 

 * @Version V1.0
 */
public class DateWidgetDayHeader extends View {
	// 字体大小
	private final static int fTextSize = 22;
	private Paint pt = new Paint();
	private RectF rect = new RectF();
	private int iWeekDay = -1;

	public DateWidgetDayHeader(Context context, int iWidth, int iHeight) {
		super(context);
		setLayoutParams(new LayoutParams(iWidth, iHeight));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// 设置矩形大小
		rect.set(0, 0, this.getWidth(), this.getHeight());
		rect.inset(1, 1);

		// 绘制日历头部
		drawDayHeader(canvas);
	}

	private void drawDayHeader(Canvas canvas) {
		// 画矩形，并设置矩形画笔的颜色
		pt.setColor(MainActivity.Calendar_WeekBgColor);
		canvas.drawRect(rect, pt);

		// 写入日历头部，设置画笔参数
		pt.setTypeface(null);
		pt.setTextSize(fTextSize);
		pt.setAntiAlias(true);
		pt.setFakeBoldText(true);
		pt.setColor(MainActivity.Calendar_WeekFontColor);
		
		// draw day name
		final String sDayName = DayStyle.getWeekDayName(iWeekDay);
		final int iPosX = (int) rect.left + ((int) rect.width() >> 1)
				- ((int) pt.measureText(sDayName) >> 1);
		final int iPosY = (int) (this.getHeight()
				- (this.getHeight() - getTextHeight()) / 2 - pt
				.getFontMetrics().bottom);
		canvas.drawText(sDayName, iPosX, iPosY, pt);
	}

	// 得到字体高度
	private int getTextHeight() {
		return (int) (-pt.ascent() + pt.descent());
	}

	// 得到一星期的第几天的文本标记
	public void setData(int iWeekDay) {
		this.iWeekDay = iWeekDay;
	}
}