package com.andela.gads2020leaderboard;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.andela.gads2020leaderboard.dialogs.ConfirmationDialog;
import com.andela.gads2020leaderboard.dialogs.SubmissionresultDialog;
import com.andela.gads2020leaderboard.service.AppExecutors;
import com.andela.gads2020leaderboard.utils.Utility;
import com.andela.gads2020leaderboard.utils.Validation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmissionActivity extends AppCompatActivity implements ConfirmationDialog.ISubmissionConfirmationDialogListener {
    private static final String LOG_TAG = SubmissionActivity.class.getSimpleName();
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailAddressEditText;
    private EditText projetUrlEditText;
    private Button submitProjectButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);
        Toolbar toolbar = findViewById(R.id.submission_toolbars);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_white_24);
            actionBar.setDisplayUseLogoEnabled(true);
            //actionBar.setLogo(R.drawable.gads_icon);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(R.layout.action_bar_title_submission);
        } else {
            setSupportActionBar(toolbar);
        }
        firstNameEditText = (EditText) findViewById(R.id.first_name);
        lastNameEditText = (EditText) findViewById(R.id.last_name);
        emailAddressEditText = (EditText) findViewById(R.id.email_address);
        projetUrlEditText = (EditText) findViewById(R.id.project_github);
        submitProjectButton = (Button) findViewById(R.id.submit_project);
        submitProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (performSubmissionValidation()) {
                    ConfirmationDialog dialog = new ConfirmationDialog();
                    dialog.show(getSupportFragmentManager(), ConfirmationDialog.class.getSimpleName());
                }
            }
        });
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Validation.isNameNotValid(firstNameEditText.getText().toString().trim())) {
                    firstNameEditText.setError(getString(R.string.required));
                } else {
                    firstNameEditText.setError(null);
                }
                if (Validation.isNameNotValid(lastNameEditText.getText().toString().trim())) {
                    lastNameEditText.setError(getString(R.string.required));
                } else {
                    lastNameEditText.setError(null);
                }
                if (Validation.isEmailAddressNotValid(emailAddressEditText.getText().toString().trim())) {
                    emailAddressEditText.setError(getString(R.string.required));
                } else {
                    emailAddressEditText.setError(null);
                }
                if (Validation.isTextNotValid(projetUrlEditText.getText().toString().trim())) {
                    projetUrlEditText.setError(getString(R.string.required));
                } else {
                    projetUrlEditText.setError(null);
                }
            }
        };
        firstNameEditText.addTextChangedListener(afterTextChangedListener);
        lastNameEditText.addTextChangedListener(afterTextChangedListener);
        emailAddressEditText.addTextChangedListener(afterTextChangedListener);
        projetUrlEditText.addTextChangedListener(afterTextChangedListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean performSubmissionValidation() {
        boolean isValid = false;
        if (Validation.isNameNotValid(firstNameEditText.getText().toString().trim())) {
            firstNameEditText.setError(getString(R.string.required));
        } else {
            firstNameEditText.setError(null);
            isValid = true;
        }
        if (Validation.isNameNotValid(lastNameEditText.getText().toString().trim())) {
            lastNameEditText.setError(getString(R.string.required));
            isValid = false;
        } else {
            lastNameEditText.setError(null);
            isValid = true;
        }
        if (Validation.isEmailAddressNotValid(emailAddressEditText.getText().toString().trim())) {
            emailAddressEditText.setError(getString(R.string.required));
            isValid = false;
        } else {
            emailAddressEditText.setError(null);
            isValid = true;
        }
        if (Validation.isTextNotValid(projetUrlEditText.getText().toString().trim())) {
            projetUrlEditText.setError(getString(R.string.required));
            isValid = false;
        } else {
            projetUrlEditText.setError(null);
            isValid = true;
        }
        return isValid;
    }

    @Override
    public void onConfirmationYesClick(boolean yes) {
        if (yes) {
            submitProjectButton.setEnabled(false);
            performSubmission(emailAddressEditText.getText().toString().trim(), firstNameEditText.getText().toString().trim(), lastNameEditText.getText().toString().trim(), projetUrlEditText.getText().toString().trim());
        }
    }

    private void performSubmission(final String emailAddress, final String firstName, final String lastName, final String projectGithub) {
        AppExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                Call<Void> callapi = Utility.getApiInterface("https://docs.google.com/forms/d/e/").submitProject(emailAddress, firstName, lastName, projectGithub);
                callapi.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        SubmissionresultDialog dialog = new SubmissionresultDialog();
                        Bundle arguments = new Bundle();
                        arguments.putInt("result_image", 1);
                        arguments.putString("result_message", getString(R.string.submission_successfull));
                        dialog.setArguments(arguments);
                        dialog.show(getSupportFragmentManager(), SubmissionresultDialog.class.getSimpleName());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        call.cancel();
                        Log.e(LOG_TAG, t.getLocalizedMessage(), t);
                        SubmissionresultDialog dialog = new SubmissionresultDialog();
                        Bundle arguments = new Bundle();
                        arguments.putInt("result_image", 0);
                        arguments.putString("result_message", getString(R.string.submission_not_successfull));
                        dialog.setArguments(arguments);
                        dialog.show(getSupportFragmentManager(), SubmissionresultDialog.class.getSimpleName());
                    }
                });
            }
        });
    }
}