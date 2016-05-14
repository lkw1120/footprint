package footprint.footprint;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;


public class CalendarView extends LinearLayout
{
	/**
	 * date 는 날짜 그대로
	 * month 는 -1 ( ex. 1월 = 0 )
	 * year 는 +1900 ( ex. 2016년 = 1900 + 116 )
	 */


	// for logging
	private static final String LOGTAG = "Calendar View";

	// how many days to show, defaults to six weeks, 42 days
	private static final int DAYS_COUNT = 42;

	// default date format
	private static final String DATE_FORMAT = "MMM yyyy";

	// date format
	private String dateFormat;

	// 현재 화면에 보이는 달
	private Calendar currentDate = Calendar.getInstance();


	// select past date
	private Date pastday = null;

	public static Date selectedDateInfo = new Date();

	//event handling
	private EventHandler eventHandler = new EventHandler() {
		@Override
		public void onDayLongPress(Date date) {
			Log.d("EVENT", "LONG_PRESS_SUCCESS");


			pastday = date;
			updateCalendar();
		}

		@Override
		public void onDayPress(Date date) {
			Log.d("EVENT","SHORT_PRESS_SUCCESS");
			Log.d("EVENT", "LONG_PRESS " + date.getYear() + " " + date.getMonth() + " " + date.getDate());

			pastday = date;
			updateCalendar();
			MainActivity.displayMapSubInfo(date);

		}
	};

	// internal components
	private LinearLayout header;
	private ImageView btnPrev;
	private ImageView btnNext;
	private TextView txtDate;
	private GridView grid;

	// seasons' rainbow
	int[] rainbow = new int[] {
			R.color.summer,
			R.color.fall,
			R.color.winter,
			R.color.spring
	};

	// month-season association (northern hemisphere, sorry australia :)
	int[] monthSeason = new int[] {2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};

	public CalendarView(Context context)
	{
		super(context);
	}

	public CalendarView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initControl(context, attrs);
	}

	public CalendarView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		initControl(context, attrs);
	}

	/**
	 * Load control xml layout
	 */
	private void initControl(Context context, AttributeSet attrs)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.control_calendar, this);

		loadDateFormat(attrs);
		assignUiElements();
		assignClickHandlers();

		updateCalendar();
	}

	private void loadDateFormat(AttributeSet attrs)
	{
		TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

		try
		{
			// try to load provided date format, and fallback to default otherwise
			dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);
			if (dateFormat == null)
				dateFormat = DATE_FORMAT;
		}
		finally
		{
			ta.recycle();
		}
	}
	private void assignUiElements()
	{
		// layout is inflated, assign local variables to components
		header = (LinearLayout)findViewById(R.id.calendar_header);
		btnPrev = (ImageView)findViewById(R.id.calendar_prev_button);
		btnNext = (ImageView)findViewById(R.id.calendar_next_button);
		txtDate = (TextView)findViewById(R.id.calendar_date_display);
		grid = (GridView)findViewById(R.id.calendar_grid);
	}

	private void assignClickHandlers()
	{
		/**
		 * 오늘날짜로 초기화
		 */
		txtDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Date today = new Date();

				currentDate = Calendar.getInstance();
				currentDate.set(today.getYear() + 1900,today.getMonth(),today.getDate());
				pastday=null;

				updateCalendar();
				MainActivity.displayMapSubInfo(today);

			}
		});
		// add one month and refresh UI
		btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currentDate.add(Calendar.MONTH, 1);
				updateCalendar();
			}
		});

		// subtract one month and refresh UI
		btnPrev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currentDate.add(Calendar.MONTH, -1);
				updateCalendar();
			}
		});

		// long-pressing a day
		grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id) {
				// handle long-press
				if (eventHandler == null) {
					Log.d("EVENT","LONG_PRESS_FAIL");
					return false;
				}

				eventHandler.onDayLongPress((Date) view.getItemAtPosition(position));
				return true;
			}
		});

		// short-pressing a day
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> view, View cell, int position, long id) {
				// handle long-press
				if (eventHandler == null) {
					Log.d("EVENT", "LONG_PRESS_FAIL");
					return;
				}

				eventHandler.onDayPress((Date) view.getItemAtPosition(position));
				return;
			}
		});

	}

	/**
	 * Display dates correctly in grid
	 */
	public void updateCalendar()
	{
		updateCalendar(null);
	}

	/**
	 * Display dates correctly in grid
	 */
	public void updateCalendar(HashSet<Date> events)
	{
		ArrayList<Date> cells = new ArrayList<>();
		Calendar calendar = (Calendar)currentDate.clone();

		// determine the cell for current month's beginning
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		// move calendar backwards to the beginning of the week
		calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

		// fill cells
		while (cells.size() < DAYS_COUNT)
		{
			cells.add(calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}

		// update grid
		grid.setAdapter(new CalendarAdapter(getContext(), cells, events));

		// update title
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		txtDate.setText(sdf.format(currentDate.getTime()));

		// set header color according to current season
		int month = currentDate.get(Calendar.MONTH);
		int season = monthSeason[month];
		int color = rainbow[season];

		header.setBackgroundColor(getResources().getColor(color));
	}


	private class CalendarAdapter extends ArrayAdapter<Date>
	{
		// days with events
		private HashSet<Date> eventDays;

		// for view inflation
		private LayoutInflater inflater;

		public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays)
		{
			super(context, R.layout.control_calendar_day, days);
			this.eventDays = eventDays;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View view, ViewGroup parent)
		{

			// day in question
			Date date = getItem(position);
			int day = date.getDate();
			int month = date.getMonth();
			int year = date.getYear();

			// today
			Date today = new Date();


			// inflate item if it does not exist yet
			if (view == null) {
				view = inflater.inflate(R.layout.control_calendar_day, parent, false);
			}

			// if this day has an event, specify event image
			view.setBackgroundResource(0);
			if (eventDays != null) {
				for (Date eventDate : eventDays) {
					if (eventDate.getDate() == day &&
							eventDate.getMonth() == month &&
							eventDate.getYear() == year) {
						// mark this day for event
						//view.setBackgroundResource(R.drawable.reminder);
						break;
					}
				}
			}

			// clear styling
			((TextView) view).setTypeface(null, Typeface.NORMAL);
			((TextView) view).setTextColor(Color.BLACK);


			if (month != currentDate.getTime().getMonth() || year != currentDate.getTime().getYear()){

			// if this day is outside current month, grey it out

			((TextView)view).setTextColor(getResources().getColor(R.color.greyed_out));

			}
			else if (pastday!=null) {
				if(day == pastday.getDate() && month == pastday.getMonth() && year == pastday.getYear()) {

					((TextView) view).setTypeface(null, Typeface.BOLD);
					((TextView) view).setTextColor(getResources().getColor(R.color.pastday));

					selectedDateInfo = pastday;
					//pastday=null;
					Log.d("SELECTDATEINFO", pastday.toGMTString());
				}
			}
			else if (day == today.getDate() && month == today.getMonth() && year == today.getYear()) {

				 // if it is today, set it to blue/bold

				 ((TextView) view).setTypeface(null, Typeface.BOLD);
				 ((TextView) view).setTextColor(getResources().getColor(R.color.today));

				selectedDateInfo = today;

				Log.d("SELECTDATEINFO", today.toGMTString());
			}
			else {}
			// set text
			((TextView) view).setText(String.valueOf(date.getDate()));

			return view;
		}
	}

	/**
	 * Assign event handler to be passed needed events
	 */
	public void setEventHandler(EventHandler eventHandler)
	{
		this.eventHandler = eventHandler;
	}

	/**
	 * This interface defines what events to be reported to
	 * the outside world

	 */
	public interface EventHandler
	{
		void onDayLongPress(Date date);
		void onDayPress(Date date);
	}


}
