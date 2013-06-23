package edu.fsu.cs.alathrop.mobile_homework5;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		ActionBar.OnNavigationListener {

	private int GenderVariable = 0;
	List<String> UsernameArray = new ArrayList<String>();

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	Cursor mCursor;
	CursorAdapter mCursorAdapter;

	String[] mProjection;
	String[] mListColumns;
	String mSelectionClause;
	String[] mSelectionArgs;
	String mOrderBy;

	int[] mListItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.relative);

		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		/*
		 * actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		 * 
		 * // Set up the dropdown list navigation in the action bar.
		 * actionBar.setListNavigationCallbacks( // Specify a SpinnerAdapter to
		 * populate the dropdown list. new
		 * ArrayAdapter<String>(actionBar.getThemedContext(),
		 * android.R.layout.simple_list_item_1, android.R.id.text1, new String[]
		 * { getString(R.string.title_section1),
		 * getString(R.string.title_section2),
		 * getString(R.string.title_section3), }), this);
		 */

		final Spinner spinner = (Spinner) findViewById(R.id.Country);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.Countries, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);

		Button submit = (Button) findViewById(R.id.Submit);

		submit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				EditText FirstNameVariable = (EditText) findViewById(R.id.FirstName);
				TextView FirstNameTextView = (TextView) findViewById(R.id.FirstNameTextView);
				EditText LastNameVariable = (EditText) findViewById(R.id.LastName);
				TextView LastNameTextView = (TextView) findViewById(R.id.LastNameTextView);
				EditText PhoneVariable = (EditText) findViewById(R.id.PhoneNumber);
				TextView PhoneTextView = (TextView) findViewById(R.id.PhoneNumberTextView);
				EditText EmailVariable = (EditText) findViewById(R.id.Email);
				TextView EmailTextView = (TextView) findViewById(R.id.EmailTextView);
				EditText Email2Variable = (EditText) findViewById(R.id.Email2);
				TextView Email2TextView = (TextView) findViewById(R.id.Email2TextView);
				EditText UsernameVariable = (EditText) findViewById(R.id.Username);
				TextView UsernameTextView = (TextView) findViewById(R.id.UsernameTextView);
				EditText PasswordVariable = (EditText) findViewById(R.id.Password);
				TextView PasswordTextView = (TextView) findViewById(R.id.PasswordTextView);
				EditText Password2Variable = (EditText) findViewById(R.id.Password2);
				TextView Password2TextView = (TextView) findViewById(R.id.Password2TextView);
				TextView GenderTextView = (TextView) findViewById(R.id.GenderTextView);
				TextView CountryTextView = (TextView) findViewById(R.id.CountryTextView);
				RadioGroup RadioSexGroup = (RadioGroup) findViewById(R.id.RadioSex);
				int SexSelection = RadioSexGroup.getCheckedRadioButtonId();
				RadioButton RadioSexButton = (RadioButton) findViewById(SexSelection);
				boolean checked = ((RadioButton) findViewById(SexSelection))
						.isChecked();
				Spinner s = (Spinner) findViewById(R.id.Country);
				String CountrySelection = s.getSelectedItem().toString();
				boolean finished = true;

				if (FirstNameVariable.getText().toString().length() == 0) {
					FirstNameTextView.setTextColor(Color.RED);
					finished = false;
				} else
					FirstNameTextView.setTextColor(Color.BLACK);
				if (LastNameVariable.getText().toString().length() == 0) {
					LastNameTextView.setTextColor(Color.RED);
					finished = false;
				} else
					LastNameTextView.setTextColor(Color.BLACK);
				if (PhoneVariable.getText().toString().length() == 0) {
					PhoneTextView.setTextColor(Color.RED);
					finished = false;
				} else
					PhoneTextView.setTextColor(Color.BLACK);
				if (EmailVariable.getText().toString().length() == 0) {
					EmailTextView.setTextColor(Color.RED);
					finished = false;
				} else
					EmailTextView.setTextColor(Color.BLACK);
				if (Email2Variable.getText().toString().length() == 0) {
					Email2TextView.setTextColor(Color.RED);
					finished = false;
				} else
					Email2TextView.setTextColor(Color.BLACK);
				if (UsernameVariable.getText().toString().length() == 0) {
					UsernameTextView.setTextColor(Color.RED);
					finished = false;
				} else
					UsernameTextView.setTextColor(Color.BLACK);
				if (PasswordVariable.getText().toString().length() == 0) {
					PasswordTextView.setTextColor(Color.RED);
					finished = false;
				} else
					PasswordTextView.setTextColor(Color.BLACK);
				if (Password2Variable.getText().toString().length() == 0) {
					Password2TextView.setTextColor(Color.RED);
					finished = false;
				} else
					Password2TextView.setTextColor(Color.BLACK);
				if (SexSelection == -1) {
					GenderTextView.setTextColor(Color.RED);
					finished = false;
				} else
					GenderTextView.setTextColor(Color.BLACK);
				if (!PasswordVariable.getText().toString()
						.equals(Password2Variable.getText().toString())) {
					Password2TextView.setTextColor(Color.RED);
					finished = false;
				} else
					Password2TextView.setTextColor(Color.BLACK);
				if (!EmailVariable.getText().toString()
						.equals(Email2Variable.getText().toString())) {
					Email2TextView.setTextColor(Color.RED);
					finished = false;
				} else
					Email2TextView.setTextColor(Color.BLACK);

				if (finished) {
					boolean duplicate = false;

					// check to see if username is already taken

					// query(Uri table, String[] columns, String selection,
					// String[] args, String orderBy)
					// Select U.Email
					// From Users U
					// Where U.Email = EmailVariable
					
					/*
					if (UsernameArray.isEmpty()) {
						UsernameTextView.setTextColor(Color.RED);
					} else {
						mProjection = new String[] { UserDatabase.COLUMN_EMAIL };
						mListColumns = new String[] { UserDatabase.COLUMN_EMAIL };
						mListItems = new int[] { R.id.Email };
						mSelectionClause = UserDatabase.COLUMN_EMAIL + " = ?";
						mSelectionArgs = new String[] { EmailVariable.getText().toString().trim() };
						mCursor = getContentResolver().query(
								Uri.parse("content://userdb.provider/Users"),
								mProjection, mSelectionClause, mSelectionArgs, null);
						
						mCursorAdapter = new SimpleCursorAdapter(
								getApplicationContext(), R.layout.query1,
								mCursor, mListColumns, mListItems);
					}
					*/
					
					if (!UsernameArray.isEmpty()
							&& UsernameArray.contains(UsernameVariable
									.getText().toString())) {
						UsernameTextView.setTextColor(Color.RED);
					} else if (duplicate) {
						UsernameTextView.setTextColor(Color.RED);
						Toast.makeText(getApplicationContext(),
								"Username already taken", Toast.LENGTH_SHORT)
								.show();
					} else {

						Uri mNewUri;
						ContentValues mNewValues = new ContentValues();
						mNewValues.put(UserDatabase.COLUMN_FIRSTNAME,
								FirstNameVariable.getText().toString().trim());
						mNewValues.put(UserDatabase.COLUMN_LASTNAME,
								LastNameVariable.getText().toString().trim());
						mNewValues.put(UserDatabase.COLUMN_PHONE, PhoneVariable
								.getText().toString().trim());
						mNewValues.put(UserDatabase.COLUMN_EMAIL, EmailVariable
								.getText().toString().trim());
						mNewValues.put(UserDatabase.COLUMN_USERNAME,
								UsernameVariable.getText().toString().trim());
						mNewValues.put(UserDatabase.COLUMN_PASSWORD,
								PasswordVariable.getText().toString().trim());

						switch (SexSelection) {
						case R.id.RadioMale:
							if (checked)
								mNewValues.put(UserDatabase.COLUMN_GENDER,
										"Male");
							break;
						case R.id.RadioFemale:
							if (checked)
								mNewValues.put(UserDatabase.COLUMN_GENDER,
										"Female");
							break;
						default:
							mNewValues.put(UserDatabase.COLUMN_GENDER, "Other");
							break;
						}

						mNewValues.put(UserDatabase.COLUMN_COUNTRY,
								CountrySelection.trim());

						mNewUri = getContentResolver().insert(
								UserDatabase.CONTENT_URI, mNewValues);

						UsernameTextView.setTextColor(Color.BLACK);
						UsernameArray
								.add(UsernameVariable.getText().toString());
						Toast.makeText(getApplicationContext(),
								"Added to the database", Toast.LENGTH_SHORT)
								.show();
					}
				}

			}
		});

		Button clear = (Button) findViewById(R.id.Clear);

		clear.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				EditText FirstNameVariable = (EditText) findViewById(R.id.FirstName);
				TextView FirstNameTextView = (TextView) findViewById(R.id.FirstNameTextView);
				EditText LastNameVariable = (EditText) findViewById(R.id.LastName);
				TextView LastNameTextView = (TextView) findViewById(R.id.LastNameTextView);
				EditText PhoneVariable = (EditText) findViewById(R.id.PhoneNumber);
				TextView PhoneTextView = (TextView) findViewById(R.id.PhoneNumberTextView);
				EditText EmailVariable = (EditText) findViewById(R.id.Email);
				TextView EmailTextView = (TextView) findViewById(R.id.EmailTextView);
				EditText Email2Variable = (EditText) findViewById(R.id.Email2);
				TextView Email2TextView = (TextView) findViewById(R.id.Email2TextView);
				EditText UsernameVariable = (EditText) findViewById(R.id.Username);
				TextView UsernameTextView = (TextView) findViewById(R.id.UsernameTextView);
				EditText PasswordVariable = (EditText) findViewById(R.id.Password);
				TextView PasswordTextView = (TextView) findViewById(R.id.PasswordTextView);
				EditText Password2Variable = (EditText) findViewById(R.id.Password2);
				TextView Password2TextView = (TextView) findViewById(R.id.Password2TextView);
				TextView GenderTextView = (TextView) findViewById(R.id.GenderTextView);
				RadioGroup RadioSexGroup = (RadioGroup) findViewById(R.id.RadioSex);
				int SexSelection = RadioSexGroup.getCheckedRadioButtonId();
				RadioButton RadioSexButton = (RadioButton) findViewById(SexSelection);

				FirstNameVariable.setText("");
				LastNameVariable.setText("");
				PhoneVariable.setText("");
				EmailVariable.setText("");
				Email2Variable.setText("");
				UsernameVariable.setText("");
				PasswordVariable.setText("");
				Password2Variable.setText("");

				RadioSexGroup.clearCheck();
				spinner.setSelection(0);

				FirstNameTextView.setTextColor(Color.BLACK);
				LastNameTextView.setTextColor(Color.BLACK);
				PhoneTextView.setTextColor(Color.BLACK);
				EmailTextView.setTextColor(Color.BLACK);
				Email2TextView.setTextColor(Color.BLACK);
				UsernameTextView.setTextColor(Color.BLACK);
				PasswordTextView.setTextColor(Color.BLACK);
				Password2TextView.setTextColor(Color.BLACK);
				GenderTextView.setTextColor(Color.BLACK);
				Password2TextView.setTextColor(Color.BLACK);
				Email2TextView.setTextColor(Color.BLACK);

			}
		});

	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment).commit();
		return true;
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

}
