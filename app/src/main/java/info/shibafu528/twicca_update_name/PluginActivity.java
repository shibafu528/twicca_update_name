package info.shibafu528.twicca_update_name;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by shibafu on 14/02/17.
 */
public class PluginActivity extends Activity {
    private EditText editSendTo, editName;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_plugin);

        editSendTo = (EditText) findViewById(R.id.edit_sendto);
        editName = (EditText) findViewById(R.id.edit_name);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);

        Button buttonSubmit = (Button) findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEditEmpty(editSendTo)) {
                    showToast(getString(R.string.message_error_sendto));
                }
                else if (isEditEmpty(editName)) {
                    showToast(getString(R.string.message_error_name));
                }
                else {
                    String text = "";
                    switch (radioGroup.getCheckedRadioButtonId()) {
                        case R.id.radio_long:
                            text = String.format("@%s update_name %s", editSendTo.getText(), editName.getText());
                            break;
                        case R.id.radio_short:
                            text = String.format("%s(@%s)", editName.getText(), editSendTo.getText());
                            break;
                    }

                    Uri.Builder builder = Uri.parse("https://twitter.com/intent/tweet").buildUpon();
                    builder.appendQueryParameter("text", text);

                    Intent intent = new Intent(Intent.ACTION_VIEW, builder.build());
                    startActivity(intent);
                    finish();
                }
            }
        });

        Intent intent = getIntent();
        if (intent.getAction().equals("jp.r246.twicca.ACTION_SHOW_TWEET")) {
            editSendTo.setText(intent.getStringExtra("user_screen_name"));
        }
        else if (intent.getAction().equals("jp.r246.twicca.ACTION_SHOW_USER")) {
            editSendTo.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
        }
    }

    private boolean isEditEmpty(EditText editText) {
        return editText.getText().length() == 0;
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
