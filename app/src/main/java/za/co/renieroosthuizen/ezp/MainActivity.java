package za.co.renieroosthuizen.ezp;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import za.co.renieroosthuizen.ezplib.exceptions.EZPException;
import za.co.renieroosthuizen.ezplib.exceptions.EZPValidationException;
import za.co.renieroosthuizen.ezplib.model.EZPPhoneNumber;
import za.co.renieroosthuizen.ezplib.view.EZPView;

public class MainActivity extends AppCompatActivity {

    private EZPView ezpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ezpView = (EZPView) findViewById(R.id.ezpView);
        findViewById(R.id.btnValidate).setOnClickListener(buttonClickListener);
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                ezpView.validatePhoneNumber();

                EZPPhoneNumber number = ezpView.getPhoneNumber();

                Snackbar.make(ezpView, "Phone Number is valid. " + number.format(EZPPhoneNumber.EZPPhoneNumberFormat.E164), Snackbar.LENGTH_LONG).show();

            } catch (EZPValidationException e) {
                e.printStackTrace();
                Snackbar.make(ezpView, e.getMessage(),Snackbar.LENGTH_LONG).show();
            } catch (EZPException e) {
                e.printStackTrace();
                Snackbar.make(ezpView, e.getMessage(),Snackbar.LENGTH_LONG).show();
            }
        }
    } ;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
