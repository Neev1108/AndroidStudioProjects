package edu.sjsu.android.mortgagecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText amount_borrowed;
    SeekBar interest_rate_bar;
    RadioGroup loan_terms;
    RadioButton year;
    CheckBox tax_insurance_box;
    Button calculate;
    TextView result;
    TextView progress_bar;

    double principal, interest_rate, monthly_payment, monthly_interest;
    int year_int;
    int months_of_loan;
    boolean tax_insurance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get views
        amount_borrowed = findViewById(R.id.editText_AmountBorrowed);
        interest_rate_bar = findViewById(R.id.seekBar_Interest);
        loan_terms = findViewById(R.id.radioGroupOfButtons);
        tax_insurance_box = findViewById(R.id.checkBox_TI);
        calculate = findViewById(R.id.button);
        result = findViewById(R.id.result);
        progress_bar = (TextView) findViewById(R.id.Progress);

        interest_rate_bar.setMax(20);
        interest_rate_bar.setProgress(10);


        interest_rate_bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
                progress_bar.setText(" " + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //when calculate is pressed
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get interest rate value from bar
                interest_rate = interest_rate_bar.getProgress();

                //calculate monthly interest rate
                monthly_interest = (double) (interest_rate / 1200);


                //get boolean value if insurance box is selected
                //principle is initial amount entered
                tax_insurance = !tax_insurance_box.isSelected();
                principal = Double.parseDouble(amount_borrowed.getText().toString());

                //get which year was selected, transfer that year into our code
                //calculate how many months has been in those number of years
                year = findViewById(loan_terms.getCheckedRadioButtonId());
                year_int = Integer.parseInt(year.getText().toString());
                months_of_loan = year_int * 12;


                //Do actual calculation: we have 4 scenarios
                //1. No interest and but taxes included
                //2. No interest and no taxes
                //3. Interest and taxes
                //4. Interest but no taxes


                //if interest rate is zero and taxes are included
                if (interest_rate == 0 && tax_insurance == true) {
                    monthly_payment = ((principal / months_of_loan) + (.001*principal));
                }

                //if interest rate is zero and no tax
                else if (interest_rate == 0 && tax_insurance == false) {
                    monthly_payment = principal / months_of_loan;
                }

                //if interest rate is not zero and taxes and insurance are included
                else if (interest_rate != 0 && tax_insurance == true) {
                    double denominator = 1 - (Math.pow(1 + monthly_interest, -months_of_loan));
                    monthly_payment = (principal * (monthly_interest / denominator)) + (.001*principal);
                }

                //if interest rate is not zero but no need to add tax
                else {
                    double denominator = 1 - (Math.pow(1 + monthly_interest, -months_of_loan));
                    monthly_payment= (monthly_interest / denominator);
                }

                //set monthly payment into edit text for our result
                result.setText(String.valueOf(monthly_payment));

            }
        });


    }
    }