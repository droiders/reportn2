/*
 * Copyright (c) 2010, Lauren Darcey and Shane Conder
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are 
 * permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright notice, this list of 
 *   conditions and the following disclaimer.
 *   
 * * Redistributions in binary form must reproduce the above copyright notice, this list 
 *   of conditions and the following disclaimer in the documentation and/or other 
 *   materials provided with the distribution.
 *   
 * * Neither the name of the <ORGANIZATION> nor the names of its contributors may be used
 *   to endorse or promote products derived from this software without specific prior 
 *   written permission.
 *   
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT 
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR 
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, 
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF 
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * <ORGANIZATION> = Mamlambo
 */
package com.technotalkative.viewstubdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class FormActivity extends Activity {
	EditText name, surname, age;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form);
		 init();
	}
	
	private void init() {
		name = (EditText) findViewById(R.id.EditTextName);
		surname = (EditText) findViewById(R.id.EditTextEmail);
	/*	age = (EditText) findViewById(R.id.editTextAge);
		age.setInputType(InputType.TYPE_CLASS_NUMBER);*/
		readPerson();
	}
	public void sendFeedback(View button) {

		
		final EditText nameField = (EditText) findViewById(R.id.EditTextName);
		String name = nameField.getText().toString();
			
		final EditText emailField = (EditText) findViewById(R.id.EditTextEmail);
		String email = emailField.getText().toString();
		
		final EditText feedbackField = (EditText) findViewById(R.id.EditTextFeedbackBody);
		String feedback = feedbackField.getText().toString();
		
		final Spinner feedbackSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackType);
		String feedbackType = feedbackSpinner.getSelectedItem().toString();

		
		final CheckBox responseCheckbox = (CheckBox) findViewById(R.id.CheckBoxResponse);
		boolean bRequiresResponse = responseCheckbox.isChecked();

		// Take the fields and format the message contents
		String subject = formatFeedbackSubject(feedbackType);

		String message = formatFeedbackMessage(feedbackType, name,
			 email, feedback, bRequiresResponse);
		
		// Create the message
		sendFeedbackMessage(subject, message);
	}

	
	protected String formatFeedbackSubject(String feedbackType) {
		
		String strFeedbackSubjectFormat = getResources().getString(
				R.string.feedbackmessagesubject_format);

		String strFeedbackSubject = String.format(strFeedbackSubjectFormat, feedbackType);
		
		return strFeedbackSubject;

	}
	
	protected String formatFeedbackMessage(String feedbackType, String name,
			String email, String feedback, boolean bRequiresResponse) {
		
		String strFeedbackFormatMsg = getResources().getString(
				R.string.feedbackmessagebody_format);

		String strRequiresResponse = getResponseString(bRequiresResponse);

		String strFeedbackMsg = String.format(strFeedbackFormatMsg,
				feedbackType, feedback, name, email, strRequiresResponse);
		
		return strFeedbackMsg;

	}
	

	protected String getResponseString(boolean bRequiresResponse)
	{
		if(bRequiresResponse==true)
		{
			return getResources().getString(R.string.feedbackmessagebody_responseyes);
		} else {
			return getResources().getString(R.string.feedbackmessagebody_responseno);
		}
			
	}

	public void sendFeedbackMessage(String subject, String message) {

		Intent messageIntent = new Intent(android.content.Intent.ACTION_SEND);

		String aEmailList[] = { "appfeedback@yourappwebsite.com" };
		messageIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);

		messageIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);

		messageIntent.setType("plain/text");
		messageIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);

		startActivity(messageIntent);
	}
	

	public void save(View view) {
		String nameText = name.getText().toString();
		String surnameText = surname.getText().toString();
	//	String ageText = age.getText().toString();
		
		if (nameText != null)
			PreferenceConnector.writeString(this, PreferenceConnector.NAME,
					nameText);
		if (surnameText != null)
			PreferenceConnector.writeString(this, PreferenceConnector.SURNAME,
					surnameText);
	/*	if (ageText != null && !ageText.equals(""))
			PreferenceConnector.writeInteger(this, PreferenceConnector.AGE,
					Integer.parseInt(ageText));*/
	}

	public void reset(View view) {
		/* A better way to delete all is:
		 * PreferenceConnector.getEditor(this).clear().commit();
		 */
		PreferenceConnector.getEditor(this).remove(PreferenceConnector.NAME)
				.commit();
		PreferenceConnector.getEditor(this).remove(PreferenceConnector.SURNAME)
				.commit();
		/*PreferenceConnector.getEditor(this).remove(PreferenceConnector.AGE)
				.commit();*/
		readPerson();
	}

	/*
	 * Read the data refer to saved person and visualize them into Edittexts
	 */
	private void readPerson() {
		name.setText(PreferenceConnector.readString(this,
				PreferenceConnector.NAME, null));
		surname.setText(PreferenceConnector.readString(this,
				PreferenceConnector.SURNAME, null));
		/*String agePref = ""
				+ PreferenceConnector.readInteger(this,
						PreferenceConnector.AGE, 0);
		age.setText((agePref.equals("0")) ? null : agePref);*/
	}
}