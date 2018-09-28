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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeSpinner();
    }

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

    public void calculateResult(View view) {
        // Takes the values of the fields and applies the current operator to them
        // The result is set as the text of the Result TextView
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

    private double roundOff(double number, int decimals) {
        // Rounds a double to the desired number of decimal places
        double scaleFactor = 10 * decimals;
        return Math.round(number * scaleFactor) / scaleFactor;
    }
}
