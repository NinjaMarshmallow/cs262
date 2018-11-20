package jwk24.cs262.calvin.edu.homework1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] operator_array = { "+", "-", "*", "/", "^" };

    /*
     * initialization function called during construction
     * @param Bundle
     * @return void
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeSpinner();
    }
    /*
     * initializes the operator selection spinner
     * Comments like this seem pretty useless...
     */
    private void initializeSpinner() {
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.operator_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*
     * onclick function for the Calculate Button
     * <p>
     * Calculates a result based on the current values of the
     * two number fields and applies the currently selected
     * operator to them. Then sets the result text to the new value.
     */
    public void calculateResult(View view) {
        EditText value1 = findViewById(R.id.value1field);
        EditText value2 = findViewById(R.id.value2field);
        Spinner spinner = findViewById(R.id.spinner);
        String operator = (String) spinner.getSelectedItem();
        double result = 0;
        switch(operator) {
            case "+":
                result = Float.parseFloat(value1.getText().toString()) + Float.parseFloat(value2.getText().toString());
                break;
            case "-":
                result = Float.parseFloat(value1.getText().toString()) - Float.parseFloat(value2.getText().toString());
                break;
            case "*":
                result = Float.parseFloat(value1.getText().toString()) * Float.parseFloat(value2.getText().toString());
                break;
            case "/":
                result = Float.parseFloat(value1.getText().toString()) / Float.parseFloat(value2.getText().toString());
                break;
            case "^":
                result = Math.pow(Float.parseFloat(value1.getText().toString()), Float.parseFloat(value2.getText().toString()));
                break;
        }
        TextView text = findViewById(R.id.result_text);

        text.setText(Double.toString(roundOff(result, 2)));
    }

    /*
     * Rounds some double to a certain amount of decimal places
     * @param double number
     * @param int decimals
     * @return double
     */
    private double roundOff(double number, int decimals) {
        double scaleFactor = 10 * decimals;
        return Math.round(number * scaleFactor) / scaleFactor;
    }
}
